package com.demo.ut.ip;

import com.demo.ut.util.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * IpService
 *
 * @author xiejinjie
 * @date 2024/8/8
 */
@Service
public class IpService {

    @Autowired
    private RestClient restClient;

    /**
     * Get current ip
     */
    @SuppressWarnings("unchecked")
    public String getCurrentIp() {
        Map<String, Object> response = restClient.getForObject("http://ip-api.com/json", Map.class);
        if (response == null) {
            return null;
        }
        return (String) response.get("query");
    }
}
