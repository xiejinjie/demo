package com.demo.ut.user;

import com.demo.ut.BaseTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;

/**
 * UserControllerTest
 *
 * @author xiejinjie
 * @date 2024/8/8
 */
@DisplayName("user interface test")
public class UserControllerTest extends BaseTest {
    @Test
    @DisplayName("Add user succeed")
    public void addUserShouldReturnSuccess() throws Exception {
        // test & verify
        try {
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/add")
                            .content("{\"name\":\"Tom\",\"age\":20}")
                            .contentType("application/json")
            ).andExpect(
                    MockMvcResultMatchers.jsonPath("$.code",
                            Matchers.equalTo(200))
            );

            ResultSet resultSet = dbStatement.executeQuery("select name, age from user_t where name = 'Tom'");
            boolean next = resultSet.next();
            Assertions.assertTrue(next);
            Assertions.assertEquals("Tom", resultSet.getString("name"));
            Assertions.assertEquals(20, resultSet.getInt("age"));
        } finally {
            dbStatement.execute("delete from user_t where name = 'Tom'");
        }
    }

    @Test
    @DisplayName("Add user failed when user name is empty")
    public void addUserShouldReturnErrorWhenUserNameIsEmpty() throws Exception {
        // test & verify
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/add")
                        .content("{}")
                        .contentType("application/json")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(500))
        );
    }

    @Test
    @DisplayName("Select user list succeed")
    public void selectUserShouldReturnSuccess() throws Exception {
        // build test scenario
        dbStatement.execute("delete from user_t");
        dbStatement.execute("insert into user_t (name, age) values " +
                "('Tom', 20)," +
                "('Sam', 19)");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/list")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(200))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.hasSize(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data[0].name",
                        Matchers.equalTo("Tom"))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data[0].age",
                        Matchers.equalTo(20))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data[1].name",
                        Matchers.equalTo("Sam"))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data[1].age",
                        Matchers.equalTo(19))
        );
    }

}
