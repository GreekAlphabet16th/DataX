package com.cetiti.dataX.service;

import com.cetiti.core.controller.BaseController;
import com.cetiti.core.dataSource.DataCenterBuilder;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.dao.ApiInfoDao;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.dao.ServiceResourceDao;
import com.cetiti.dataX.entity.ApiInfo;
import com.cetiti.dataX.entity.DataProperties;
import com.cetiti.dataX.entity.ServiceResource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 描述：API接口访问service
 * @author zhouliyu
 * @version dataX v1.0.0
 * */
@Service
public class ApiService extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);
    private static final int MAX_PAGE_SIZE = 200;

    DataCenterBuilder dataCenterBuilder = new DataCenterBuilder();
    @Autowired
    private DataPropertiesDao DataPropertiesDao;
    @Autowired
    private ApiInfoDao apiInfoDao;
    @Autowired
    private ServiceResourceDao serviceResourceDao;

    private ApiInfo apiInfo;
    private Map<String,String> sqlParameters = new HashMap<>();


    /**
     * rest方式接口访问
     * @param mapper 接口名
     * @param method 方法名
     * @param parameters 传参
     * @return 数据集
     * */
    public PageModel<Map> RestApiService(String mapper, String method, Map<String,String> parameters){
        if(!isNull(mapper) && !isNull(method)){
            String apiMethod = new StringBuffer(mapper+'.'+method).toString();
            if(this.exitApiMethod(apiMethod,parameters)) {
                SqlSession sqlSession = null;
                List<Map> list;
                PageModel<Map> pageList;
                if (!isNull(this.apiInfo.getCategoryId())) {
                    try {
                        List<String> mappers = new ArrayList<>();
                        ServiceResource serviceResource = serviceResourceDao.getServiceResource(this.apiInfo.getCategoryId());
                        mappers.add(serviceResource.getResourceUrl());
                        DataProperties properties = DataPropertiesDao.getDataProperties(serviceResource.getDataId());
                        SqlSessionFactory sqlSessionFactory = dataCenterBuilder.sqlSessionFactoryBuild(properties, mappers);
                        sqlSession = sqlSessionFactory.openSession();
                        this.pageLimit(parameters); //后端分页
                        list = sqlSession.selectList(apiMethod, this.sqlParameters);
                        pageList = this.resultPage(list);
                    } finally {
                        sqlSession.close();
                        if (!isNull(this.sqlParameters)){
                            this.sqlParameters.clear();
                        }
                    }
                    return pageList;
                }
            }
        }
        return null;
    }

    /**
     * 最大返回200条数据
     * @param Parameters Url传入参数
     *
     * */
    private void pageLimit(Map<String,String> Parameters){
        int pageNum = Integer.parseInt(Parameters.get("pageNum"));
        int pageSize = Integer.parseInt(Parameters.get("pageSize"));
        if(pageSize <= MAX_PAGE_SIZE){
            this.offsetPage(pageNum,pageSize);
        }else {
            this.offsetPage(pageNum,MAX_PAGE_SIZE);
        }
    }

    /**
     * apiMethod 参数验证
     * @param apiMethod  方法名
     * @param parameters Url传入参数
     * @return
     * */
    private boolean exitApiMethod(String apiMethod, Map<String,String> parameters) {
        List<ApiInfo> apiInfos = apiInfoDao.apiInfoList();
        for (ApiInfo apiInfo:apiInfos){
            if(apiInfo.getApiName().equals(apiMethod)){
                this.apiInfo = apiInfo;
                List<String> list = Arrays.asList(apiInfo.getParameters().split(","));
                for (int i = 0;i<list.size();i++){
                    for(Map.Entry<String,String> entry : parameters.entrySet()){
                            if(entry.getKey().equals(list.get(i))){
                                this.sqlParameters.put(entry.getKey(),entry.getValue());
                            }
                        }
                }
                return true;
            }
        }
        return false;
    }

}
