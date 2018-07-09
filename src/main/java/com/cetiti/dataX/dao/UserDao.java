package com.cetiti.dataX.dao;

import com.cetiti.dataX.entity.User;

import java.util.List;

public interface UserDao {

    List<User> selectUserList();

    User getUser(User user);
}
