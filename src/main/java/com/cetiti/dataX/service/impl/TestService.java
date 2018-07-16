package com.cetiti.dataX.service.impl;

import com.cetiti.core.dataSource.DataCenterBuilder;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.entity.DataProperties;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TestService {

    DataCenterBuilder dataCenterBuilder = new DataCenterBuilder();
    @Autowired
    private DataPropertiesDao DataPropertiesDao;

    public List<Map> testServiceData(){
        SqlSession sqlSession = null;
        List<Map> list;
        try {
            List<String> mappers = new ArrayList<>();
            mappers.add("file:///D:/zly7056/Desktop/UserMapper.xml");
            DataProperties properties = DataPropertiesDao.getDataProperties(3);
            SqlSessionFactory sqlSessionFactory = dataCenterBuilder.sqlSessionFactoryBuild(properties,mappers);
            sqlSession = sqlSessionFactory.openSession();
            Map<String, Object> params= new HashMap<>();
            params.put("id",121);
            params.put("name","test121");
            list = sqlSession.selectList("com.cetiti.dataX.dao.UserDao.selectUserList",null);
        }finally {
            sqlSession.close();
        }
        return list;
    }
}
