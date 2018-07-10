package com.cetiti.core.dataSource;


import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class DataXSqlSessionFactoryBuilder {
    public DataXSqlSessionFactoryBuilder() {
    }


    public SqlSessionFactory build(InputStream inputStream, Properties properties, List<String> mappers) {
        return this.build((InputStream)inputStream, (String)null, properties, mappers);
    }

    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties, List<String> mappers) {
        SqlSessionFactory var5;
        try {
            DataXConfigBuilder parser = new DataXConfigBuilder(inputStream, environment, properties, mappers);
            var5 = this.build(parser.parse());
        } catch (Exception var14) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", var14);
        } finally {
            ErrorContext.instance().reset();

            try {
                inputStream.close();
            } catch (IOException var13) {
                ;
            }

        }

        return var5;
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }

}
