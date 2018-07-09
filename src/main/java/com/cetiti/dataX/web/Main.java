package com.cetiti.dataX.web;

import com.cetiti.dataX.dao.UserDao;
import com.cetiti.dataX.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
            //通过接口动态代理
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> userList = userDao.selectUserList();
            for(User user : userList){
                System.out.println(user.getSex());
                System.out.println(user.toString());
            }
            User user = new User();
            user.setId(121);
            System.out.println(userDao.getUser(user).toString());
            //实现类
            List<Map> list = sqlSession.selectList("com.cetiti.dataX.dao.UserDao.getUser",user);
            System.out.println(list.toString());
            //dataProperties

        } finally {
            sqlSession.close();
        }

    }
}
