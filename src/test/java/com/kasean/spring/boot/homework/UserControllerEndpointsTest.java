package com.kasean.spring.boot.homework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasean.spring.boot.homework.others.controllers.models.CreateUserRequest;
import com.kasean.spring.boot.homework.others.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientRegistrationRepository clientRegistrationRepository = Mockito.mock(ClientRegistrationRepository.class);

    @BeforeEach
    void setup() { // other way: config in application.yaml, but I prefer code config in tests
        when(clientRegistrationRepository.findByRegistrationId(anyString()))
                .thenReturn(ClientRegistration.withRegistrationId("mock_registration_id")
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationUri("mock_auth_uri")
                        .clientSecret("mock_client_secret")
                        .clientId("mock_client_id")
                        .redirectUri("mock_redirect_uri")
                        .tokenUri("mock_token_uri")
                        .build());
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");

        User user = new User();
        user.setName("John Doe");

        mockMvc.perform(post("/v1/user/create")
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf())
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
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf())
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andReturn();

        var id = getId(createResult);

        mockMvc.perform(get("/v1/user/" + id)
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf()))
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
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf())
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andReturn();

        var id = getId(createResult);

        mockMvc.perform(put("/v1/user/" + id + "/update")
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf())
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
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf())
                        .content("{\"name\":\"John Doe\"}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andReturn();

        var id = getId(createResult);

        mockMvc.perform(delete("/v1/user/" + id)
                        .with(authentication(createOAuth2Authentication()))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    private String getId(MvcResult createResult) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asText();
    }

    private Authentication createOAuth2Authentication() {
        OAuth2User oauth2User = createOAuth2User();
        return new OAuth2AuthenticationToken(oauth2User, oauth2User.getAuthorities(), "oauth2");
    }

    private OAuth2User createOAuth2User() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "user123");
        attributes.put("login", "test user");

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")),
                attributes,
                "sub"
        );
    }
}
