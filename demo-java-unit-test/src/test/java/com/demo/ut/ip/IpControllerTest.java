package com.demo.ut.ip;

import com.demo.ut.BaseTest;
import com.demo.ut.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.Mockito.*;

@DisplayName("ip interface test")
public class IpControllerTest extends BaseTest {

    @Test
    @DisplayName("Get current ip success.")
    @SuppressWarnings("unchecked")
    public void getCurrentIpShouldReturnSuccess() throws Exception {
        // build test scenario
        Map<String, Object> map = JsonUtil.readValue("{\"status\":\"success\",\"country\":\"China\",\"countryCode\":\"CN\"," +
                "\"query\":\"192.168.56.11\"}", Map.class);
        Mockito.when(restClient.getForObject(any(), any(), new Object[]{})).thenReturn(map);

        // test & verify
        mockMvc.perform(
                MockMvcRequestBuilders.get("/ip/current")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(200))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data.ip",
                        Matchers.equalTo("192.168.56.11"))
        );
        verify(restClient, times(1)).getForObject("http://ip-api.com/json", Map.class);
    }

    @Test
    @DisplayName("Get current ip error when ip api unusable")
    public void getCurrentIpShouldReturnErrorWhenIpApiUnusable() {


    }
}
