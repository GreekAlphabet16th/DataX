package com.cetiti.dataX.service.impl;

import com.cetiti.core.dataSource.DataCenterBuilder;
import com.cetiti.core.support.ApiResult;
import com.cetiti.core.support.BaseSupport;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.dao.ApiMethodInfoDao;
import com.cetiti.dataX.dao.DataCenterDao;
import com.cetiti.dataX.dao.XmlResourceDao;
import com.cetiti.dataX.entity.*;
import com.cetiti.dataX.service.OpenApiService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OpenApiServiceImpl extends BaseSupport implements OpenApiService {

    private static final int MAX_PAGE_SIZE = 200;
    private ApiMethodInfo apiMethodInfo;
    private Map<String,String> sqlParameters = new HashMap<>();
    DataCenterBuilder dataCenterBuilder = new DataCenterBuilder();
    @Autowired
    private ApiMethodInfoDao apiMethodInfoDao;
    @Autowired
    private XmlResourceDao xmlResourceDao;
    @Autowired
    private DataCenterDao dataCenterDao;


    @Transactional(isolation= Isolation.DEFAULT, propagation= Propagation.REQUIRED)
    public int insertOpenApiService(XmlResource xmlResource, List<String> mappers) {
        if(!isNull(mappers)){
            XmlResource var = dataCenterBuilder.xmlResourceBuild(xmlResource,mappers);
            xmlResourceDao.insertXmlResource(xmlResource);
            for(ApiMethodInfo apiMethodInfo : var.getApiMethodInfos()){
                apiMethodInfoDao.insertApiMethodInfo(apiMethodInfo);
            }
            return 0;
        }
        return 1;
    }


    @Override
    public PageModel<Map> RestOpenApiService(String mapper, String method, Map<String, String> parameters) {

        if(!isNull(mapper) && !isNull(method)){
            String apiMethod = new StringBuffer(mapper+'.'+method).toString();
            if(this.exitApiMethod(apiMethod,parameters)) {
                SqlSession sqlSession = null;
                List<Map> list;
                PageModel<Map> pageModel;
                XmlResource xmlResource = this.apiMethodInfo.getXmlResource();
                if (!isNull(xmlResource.getCategoryId())) {
                    try {
                        List<String> mappers = new ArrayList<>();
                        mappers.add(xmlResource.getMapperUrl());
                        DataCenter dataCenter = dataCenterDao.getDataCenter(xmlResource.getSqlId());
                        SqlSessionFactory sqlSessionFactory = dataCenterBuilder.sqlSessionFactoryBuild(dataCenter, mappers);
                        sqlSession = sqlSessionFactory.openSession();
                        this.pageLimit(parameters); //后端分页
                        list = sqlSession.selectList(apiMethod, this.sqlParameters);
                        pageModel = ApiResult.resultPage(list);
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

    @Override
    public PageModel<XmlResource> XmlResourceList(int pageNum, int pageSize) {
        ApiResult.offsetPage(pageNum,pageSize);
        List<XmlResource> list = xmlResourceDao.xmlResourceList();
        PageModel<XmlResource> result = ApiResult.resultPage(list);
        return result;
    }

    @Override
    public PageModel<ApiMethodInfo> ApiMethodInfoList(int pageNum, int pageSize) {
        ApiResult.offsetPage(pageNum,pageSize);
        List<ApiMethodInfo> list = apiMethodInfoDao.apiMethodInfoList();
        PageModel<ApiMethodInfo> result = ApiResult.resultPage(list);
        return result;
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
            ApiResult.offsetPage(pageNum,pageSize);
        }else {
            ApiResult.offsetPage(pageNum,MAX_PAGE_SIZE);
        }
    }

    /**
     * apiMethod 参数验证
     * @param apiMethod  方法名
     * @param parameters Url传入参数
     * @return
     * */
    private boolean exitApiMethod(String apiMethod, Map<String,String> parameters) {
        List<ApiMethodInfo> apiMethodInfos = apiMethodInfoDao.apiMethodInfoList();
        for (ApiMethodInfo apiMethodInfo:apiMethodInfos){
            if(apiMethodInfo.getApiName().equals(apiMethod)){
                this.apiMethodInfo = apiMethodInfo;
                //参数验证
                if(!isNull(apiMethodInfo.getApiParameters())){
                    if(apiMethodInfo.getApiParameters().contains(",")){
                        List<String> list = Arrays.asList(apiMethodInfo.getApiParameters().split(","));
                        for (int i = 0;i<list.size();i++){
                            for(Map.Entry<String,String> entry : parameters.entrySet()){
                                if(entry.getKey().equals(list.get(i))){
                                    this.sqlParameters.put(entry.getKey(),entry.getValue());
                                }
                            }
                        }
                    }else {
                        for(Map.Entry<String,String> entry : parameters.entrySet()){
                            if(entry.getKey().equals(apiMethodInfo.getApiParameters())){
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
