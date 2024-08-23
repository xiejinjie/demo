package com.demo.ut.user;

import com.demo.ut.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> selectUserList() {
        return userMapper.selectUserList();
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
