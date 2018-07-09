package com.cetiti.dataX.service.impl;

import com.cetiti.core.dataSource.XMLDataCenterConfigBuilder;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    XMLDataCenterConfigBuilder xmlDataCenterConfigBuilder = new XMLDataCenterConfigBuilder();
    @Autowired
    private DataPropertiesDao dataPropertiesDao;

    public List<Map> testServiceData(){
        SqlSession sqlSession = null;
        List<Map> list = new ArrayList<>();
        try {
            SqlSessionFactory sqlSessionFactory = xmlDataCenterConfigBuilder.sqlSessionFactoryBuild(dataPropertiesDao.getDataProperties(1));
            sqlSession = sqlSessionFactory.openSession();
            User user = new User();
            user.setId(121);
            list = sqlSession.selectList("com.cetiti.dataX.dao.UserDao.getUser",user);
        }finally {
            sqlSession.close();
        }
        return list;
    }
}
