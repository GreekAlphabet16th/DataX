package com.cetiti.core.dataSource;

import com.cetiti.dataX.entity.DataProperties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class XMLDataCenterConfigBuilder {

    private static Logger LOGGER = LoggerFactory.getLogger(XMLDataCenterConfigBuilder.class);
    private DataProperties dataProperties;
    private String DEFAULT_MYBATIS_CONFIG = "spring/mybatis.xml";

    public SqlSessionFactory sqlSessionFactoryBuild(DataProperties dataProperties){
        Properties properties = this.propertiesLoad(dataProperties);
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(DEFAULT_MYBATIS_CONFIG);
        } catch (IOException e) {
            LOGGER.error("DEFAULT_MYBATIS_CONFIG error", e,
                    e.getCause());
        }
        //上传路径解析
        List<String> mappers = new ArrayList<String>();
        mappers.add("UserMapper.xml");
        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,properties);
        SqlSessionFactory sqlSessionFactory = new DataXSqlSessionFactoryBuilder().build(inputStream,properties,mappers);
        return sqlSessionFactory;
    }

    private Properties propertiesLoad(DataProperties dataProperties){
        Properties properties = new Properties();
        properties.setProperty("driver",dataProperties.getDriver());
        properties.setProperty("url",dataProperties.getUrl());
        properties.setProperty("username",dataProperties.getUserName());
        properties.setProperty("password",dataProperties.getPassWord());
        return properties;
    }
}
