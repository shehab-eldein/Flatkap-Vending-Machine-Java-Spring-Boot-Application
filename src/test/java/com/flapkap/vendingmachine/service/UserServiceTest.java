package com.flapkap.vendingmachine.service;

import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenValidUser_WhenCreate_ThenUserIsCreatedSucc(){
        User user = new User(1,"testName", Role.buyer,"12345",30);
        when(userRepository.save(any())).thenReturn(user);

        Integer expectedId = userService.create(user);

        assertEquals(expectedId,user.getId());

    }

    @Test
    void whenAfterPropertiesSet_ThenUsersCreatedSucc() {
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"testName", Role.buyer,"12345",30));
        userService.afterPropertiesSet();
        verify(userRepository,times(4)).save(any(User.class));
    }

    @Test
    void givenUserId_WhenGetById_ThenReturnUser() {
        User user = new User(1, "testName", Role.buyer, "12345", 30);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getById(1);

        assertEquals(user, result);
    }
    @Test
    void givenInvalidUserId_WhenGetById_ThenThrowRuntimeException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getById(1));
    }

    @Test
    void whenGetAll_ThenReturnUserList() {
        List<User> userList = Arrays.asList(
                new User(1, "testName1", Role.buyer, "12345", 30),
                new User(2, "testName2", Role.seller, "54321", 25)
        );
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAll();

        assertEquals(userList.size(), result.size());
        assertEquals(userList, result);
    }
    @Test
    void givenInvalidUser_WhenUpdate_ThenThrowRuntimeException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.update(new User()));
    }
    @Test
    void givenUserId_WhenDeleteUser_ThenNoExceptionThrown() {
        doNothing().when(userRepository).deleteById(1);

        assertDoesNotThrow(() -> userService.deleteUser(1));
    }

}
