package com.cetiti.dataX.service;

import com.cetiti.dataX.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList();

    User getUser(User user);
}
