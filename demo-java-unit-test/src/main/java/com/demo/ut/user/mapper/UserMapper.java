package com.demo.ut.user.mapper;

import com.demo.ut.user.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUserList();

    void insertUser(User user);
}
