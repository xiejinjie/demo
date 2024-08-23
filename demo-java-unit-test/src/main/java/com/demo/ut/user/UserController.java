package com.demo.ut.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Map<String, Object> selectUserList() {
        List<User> users = userService.selectUserList();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "SUCCESS");
        res.put("data", users);
        return res;
    }


    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestBody User user) {
        if (user == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("code", 500);
            res.put("msg", "The user is empty");
            return res;
        }
        if (user.getName() == null || "".equals(user.getName())) {
            Map<String, Object> res = new HashMap<>();
            res.put("code", 500);
            res.put("msg", "The user name is empty");
            return res;
        }
        userService.insertUser(user);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "SUCCESS");
        return res;
    }
}
