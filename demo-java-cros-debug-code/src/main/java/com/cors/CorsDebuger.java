package com.cors;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author jjxiek
 * @since 2019/11/4 17:29
 */
public class CorsDebuger {
    public static void main(String[] args){
        String origin = "localhost.com";
        String url = "http://baidu.com";
        crossOriginRequest(origin, url, HttpMethod.GET,null, String.class);
    }
    public static <T> ResponseEntity<T> crossOriginRequest(String origin, String url, HttpMethod method, HttpHeaders headers, Class<T> clazz){
        if (headers == null) {
            headers = new HttpHeaders();
        }
        // 设置允许修改origin
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        // 发送跨域请求
        RestTemplate restTemplate = new RestTemplate();
        if (origin !=null){
            headers.add("Origin",origin);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<T> exchangeResult = restTemplate.exchange(url, method, request, clazz);
        System.out.println("response headers:\r\n" + exchangeResult.getHeaders());
        System.out.println("response body:\r\n" + exchangeResult.getBody());
        return exchangeResult;
    }
}
