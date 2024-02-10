package com.flapkap.vendingmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flapkap.vendingmachine.VendingMachineApplication;
import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.exception.AppExceptionHandler;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.service.UserService;
import com.flapkap.vendingmachine.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK,classes= VendingMachineApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ValidationService validationService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new AppExceptionHandler())
                .build();
    }

    @Test
    public void givenValidId_whenGetUserById_thenResturnUser() throws Exception {
        User user = new User(1,"testName", Role.buyer,"12345",30);
        when(userService.getById(1)).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        User userResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertEquals(userResult.getId(), user.getId());
        assertEquals(userResult.getUserName(), user.getUserName());
    }

    @Test
    public void givenValidUserObject_whenCreateUser_thenUserIsCreated() throws Exception {
        User user = new User(1,"testName", Role.buyer,"12345",30);
        when(userService.create(user)).thenReturn(1);

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isAccepted())
                .andReturn();

        Integer result = Integer.valueOf(mvcResult.getResponse().getContentAsString());

        assertEquals(result, user.getId());
    }
    @Test
    public void givenValidId_whenDeleteUser_thenNoContent() throws Exception {
        doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNoContent());


    }

    @Test
    public void givenValidUserObject_whenUpdateUser_thenNoContent() throws Exception {
        User user = new User(1, "testName", Role.buyer, "12345", 30);
        doNothing().when(userService).update(user);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());

    }



}
