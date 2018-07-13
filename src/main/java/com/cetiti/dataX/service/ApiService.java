package com.cetiti.dataX.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.cetiti.core.controller.BaseController;
import com.cetiti.core.dataSource.DataCenterBuilder;
import com.cetiti.core.support.BaseSupport;
import com.cetiti.core.support.PageModel;
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
public class ApiService extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    DataCenterBuilder dataCenterBuilder = new DataCenterBuilder();
    @Autowired
    private DataPropertiesDao DataPropertiesDao;



    /**
     * rest方式接口访问 http://Endpoint/rest/mapper/method/?Parameters
     * @param mapper 接口名
     * @param method 方法名
     * @param Parameters 传参
     * @return 数据集
     * */
    public PageModel<Map> RestApiService(String mapper, String method, Map<String,String> Parameters){
        if(!isNull(mapper) && !isNull(method)){
            String apiMethod = new StringBuffer(mapper+'.'+method).toString();
            //后端分页

            //参数判断
            Map<String,Object> sqlParameters = new LinkedHashMap<>();
            for(Map.Entry<String,String> entry : Parameters.entrySet()){
                if(!entry.getKey().equals("format") || entry.getKey().equals("pageNum") || entry.getKey().equals("pageSize")){
                    sqlParameters.put(entry.getKey(),entry.getValue());
                }
            }
            SqlSession sqlSession = null;
            List<Map> list;
            PageModel<Map> pageList;
            try {
                List<String> mappers = new ArrayList<>();
                mappers.add("file:///D:/zly7056/Desktop/UserMapper.xml");
                DataProperties properties = DataPropertiesDao.getDataProperties(new BigDecimal(3));
                SqlSessionFactory sqlSessionFactory = dataCenterBuilder.sqlSessionFactoryBuild(properties,mappers);
                sqlSession = sqlSessionFactory.openSession();
                this.offsetPage(0,10);
                list = sqlSession.selectList(apiMethod,sqlParameters);
                pageList = this.resultPage(list);
            }finally {
                sqlSession.close();
            }
            return pageList;
        }
        return null;
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
