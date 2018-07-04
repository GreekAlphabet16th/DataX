package com.cetiti.dataX.web;

import com.cetiti.dataX.entity.User;
import com.cetiti.dataX.service.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args){
        String propertiesPath = "properties/db.properties";
        String resource = "spring/mybatis.xml";
        SqlSession sqlSession = null;
        Properties properties = null;
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            InputStream proStram = Resources.getResourceAsStream(propertiesPath);
            properties = new Properties();
            properties.load(proStram);
            //properties加载顺序
            properties.setProperty("url","jdbc:mysql://192.168.138.130:3306/dataX2?useUnicode=true&amp;characterEncoding=UTF-8");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,properties);
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.selectUserList();
            for(User user : userList){
                System.out.println(user.getSex().getName());
                System.out.println(user.toString());
            }
            User user = new User();
            user.setId(121);
            System.out.println(userMapper.getUser(user).toString());
        } finally {
            sqlSession.close();
        }

    }
}
