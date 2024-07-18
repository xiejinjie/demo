package com.demo.sqlite;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Main
 *
 * @author xiejinjie
 * @date 2024/7/18
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            // add new user
            User user = new User();
            user.setName("Json");
            user.setAge(26);
            int res1 = mapper.insertUser(user);
            System.out.println("Add new user, result = " + res1);
            // select user
            List<User> users = mapper.selectUserList();
            System.out.println("Query user info, result = " + users);
            // remove user
            int res2 = mapper.deleteAllUsers();
            System.out.println("Remove users, result = " + res2);
        }
    }
}
