package com.cetiti.core.dataSource;

import com.cetiti.dataX.entity.DataProperties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class DataCenterBuilder {

    private static Logger LOGGER = LoggerFactory.getLogger(DataCenterBuilder.class);

    private static final String DEFAULT_MYBATIS_CONFIG = "spring/dataCenter.xml";

    public SqlSessionFactory sqlSessionFactoryBuild(DataProperties DataProperties,List<String> mappers){
        Properties properties = this.propertiesLoad(DataProperties);
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(DEFAULT_MYBATIS_CONFIG);
        } catch (IOException e) {
            LOGGER.error("DEFAULT_MYBATIS_CONFIG error", e,
                    e.getCause());
        }
        SqlSessionFactory sqlSessionFactory = new DataXSqlSessionFactoryBuilder().build(inputStream,properties,mappers);
        return sqlSessionFactory;
    }

    private Properties propertiesLoad(DataProperties DataProperties){
        Properties properties = new Properties();
        properties.setProperty("driver", DataProperties.getDriver());
        properties.setProperty("url", DataProperties.getUrl());
        properties.setProperty("username", DataProperties.getUserName());
        properties.setProperty("password", DataProperties.getPassWord());
        return properties;
    }

}
