package com.kasean.spring.boot.homework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.others.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");

        User user = new User();
        user.setName("John Doe");

        mockMvc.perform(post("/v1/user/create")
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetUser() throws Exception {
        User user = new User();
        user.setName("John Doe");

        var createResult = mockMvc.perform(post("/v1/user/create")
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andReturn();

        var id = getId(createResult);

        mockMvc.perform(get("/v1/user/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.id").value(id));
    }



    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setName("John Doe");

        User updatedUser = new User();
        updatedUser.setName("Jane Doe");

        var createResult = mockMvc.perform(post("/v1/user/create")
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andReturn();

        var id = getId(createResult);

        mockMvc.perform(put("/v1/user/" + id + "/update")
                        .content("{\"newName\":\"Jane Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedUser.getName()))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testDeleteUser() throws Exception {

        User user = new User();
        user.setName("John Doe");

        var createResult = mockMvc.perform(post("/v1/user/create")
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andReturn();

        var id = getId(createResult);

        mockMvc.perform(delete("/v1/user/" + id))
                .andExpect(status().isOk());
    }

    private String getId(MvcResult createResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asText();
    }
}
