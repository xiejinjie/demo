package com.demo.sqlite;

import java.util.List;

/**
 * UserMapper
 *
 * @author xiejinjie
 * @date 2024/7/18
 */
public interface UserMapper {

    /**
     * select all users
     */
    List<User> selectUserList();


    /**
     * add new user
     */
    int insertUser(User user);

    /**
     * remove all users
     */
    int deleteAllUsers();
}
