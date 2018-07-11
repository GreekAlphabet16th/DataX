package com.cetiti.dataX.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cetiti.core.dataSource.DataCenterBuilder;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.entity.DataProperties;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * 描述：API接口访问service
 *
 * */
@Service
public class ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    DataCenterBuilder dataCenterBuilder = new DataCenterBuilder();
    @Autowired
    private DataPropertiesDao DataPropertiesDao;



    /**
     * rest方式接口访问
     * */
    public List<Map> RestApiService(String mapper, String method, String params){
        String queryMethod = mapper+"."+method;
        SqlSession sqlSession = null;
        Map<String,String> methodParams = this.jsonMapResolver(params);
        List<Map> list;
        try {
            List<String> mappers = new ArrayList<>();
            mappers.add("file:///D:/zly7056/Desktop/UserMapper.xml");
            DataProperties properties = DataPropertiesDao.getDataProperties(new BigDecimal(2));
            SqlSessionFactory sqlSessionFactory = dataCenterBuilder.sqlSessionFactoryBuild(properties,mappers);
            sqlSession = sqlSessionFactory.openSession();
            list = sqlSession.selectList(queryMethod,methodParams);
        }finally {
            sqlSession.close();
        }
        return list;
    }

    private Map<String,String> jsonMapResolver(String params){
        Map map = new HashMap();
        if(params != null){
            try {
                map = (Map) JSON.parse(params);
            }catch (JSONException e){
                LOGGER.error("JSON转换错误",e.toString());
            }

        }
        return map;
    }

}
