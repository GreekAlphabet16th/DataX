package com.cetiti.dataX.service;

import com.cetiti.core.dataSource.DataCenterBuilder;
import com.cetiti.core.support.BaseSupport;
import com.cetiti.core.support.OpenApiResult;
import com.cetiti.core.support.PageModel;
import com.cetiti.core.support.UUIDGenerator;
import com.cetiti.dataX.dao.ApiInfoDao;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.dao.ServiceResourceDao;
import com.cetiti.dataX.entity.ApiInfo;
import com.cetiti.dataX.entity.DataProperties;
import com.cetiti.dataX.entity.ServiceResource;
import com.cetiti.dataX.service.impl.ApiService;
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
public class ApiServiceImpl extends BaseSupport implements ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiServiceImpl.class);
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
                PageModel<Map> pageModel;
                if (!isNull(this.apiInfo.getCategoryId())) {
                    try {
                        List<String> mappers = new ArrayList<>();
                        ServiceResource serviceResource = serviceResourceDao.getServiceResource(this.apiInfo.getApiName());
                        mappers.add(serviceResource.getResourceUrl());
                        DataProperties properties = DataPropertiesDao.getDataProperties(serviceResource.getDataId());
                        SqlSessionFactory sqlSessionFactory = dataCenterBuilder.sqlSessionFactoryBuild(properties, mappers);
                        sqlSession = sqlSessionFactory.openSession();
                        this.pageLimit(parameters); //后端分页
                        list = sqlSession.selectList(apiMethod, this.sqlParameters);
                        pageModel = OpenApiResult.resultPage(list);
                    } finally {
                        sqlSession.close();
                        if (!isNull(this.sqlParameters)){
                            this.sqlParameters.clear();
                        }
                    }
                    return pageModel;
                }
            }
        }
        return null;
    }

    /**
     * 发布接口
     * */
    public int insertApiService(DataProperties dataProperties, List<String> mappers) {
        //数据唯一性验证
        if(!isNull(dataProperties) && mappers.size()!=0){
            try {
                dataProperties.setDataId(UUIDGenerator.generate());
                DataPropertiesDao.insertDataProperties(dataProperties); //添加数据库配置
                Map<String,Object> map = dataCenterBuilder.xmlServiceBulid(mappers,dataProperties.getDataId());
                ServiceResource serviceResource = (ServiceResource) map.get("serviceResource");
                List<ApiInfo> apiInfos = (List<ApiInfo>) map.get("apiInfos");
                if(!isNull(serviceResource)){
                    serviceResourceDao.insertServiceResource(serviceResource); //添加资源目录
                }
                if(!isNull(apiInfos)){
                    for (ApiInfo apiInfo : apiInfos){
                        apiInfoDao.insertApiInfo(apiInfo); //添加接口信息
                    }
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }
            return 0;
        }
        return 1;
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
            OpenApiResult.offsetPage(pageNum,pageSize);
        }else {
            OpenApiResult.offsetPage(pageNum,MAX_PAGE_SIZE);
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
                //参数验证
                if(!isNull(apiInfo.getParameters())){
                    if(apiInfo.getParameters().contains(",")){
                        List<String> list = Arrays.asList(apiInfo.getParameters().split(","));
                        for (int i = 0;i<list.size();i++){
                            for(Map.Entry<String,String> entry : parameters.entrySet()){
                                if(entry.getKey().equals(list.get(i))){
                                    this.sqlParameters.put(entry.getKey(),entry.getValue());
                                }
                            }
                        }
                    }else {
                        for(Map.Entry<String,String> entry : parameters.entrySet()){
                            if(entry.getKey().equals(apiInfo.getParameters())){
                                this.sqlParameters.put(entry.getKey(),entry.getValue());
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

}
