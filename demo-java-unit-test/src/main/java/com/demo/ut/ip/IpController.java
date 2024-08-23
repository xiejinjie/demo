package com.demo.ut.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * IpController
 *
 * @author xiejinjie
 * @date 2024/8/8
 */
@RestController
@RequestMapping("/ip")
public class IpController {

    @Autowired
    private IpService ipService;

    @GetMapping("/current")
    public Map<String, Object> getCurIp() {
        try {
            String currentIp = ipService.getCurrentIp();
            Map<String, Object> res = new HashMap<>();
            res.put("code", 200);
            res.put("msg", "SUCCESS");
            res.put("data", Collections.singletonMap("ip", currentIp));
            return res;
        } catch (Exception e) {
            Map<String, Object> res = new HashMap<>();
            res.put("code", 500);
            res.put("msg", e.getMessage());
            return res;
        }
    }
}
