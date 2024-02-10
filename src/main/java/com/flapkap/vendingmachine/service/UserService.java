package com.flapkap.vendingmachine.service;

import com.flapkap.vendingmachine.enums.Role;
import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements InitializingBean {
    @Autowired
    private UserRepository userRepository;

    public User getById(Integer id) {
        log.info("Get user method is called.");
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found by id: " + id);
        }
    }

    public List<User> getAll() {
        log.info("Get all user method is called.");

        return userRepository.findAll();
    }

    public Integer create(User user) {
        log.info("create user method is called.");
        user = userRepository.save(user);

        return user.getId();
    }

    public void update(User user) {
        getById(user.getId());
        log.info("Update user method is called.");
        userRepository.save(user);

    }
    public void deleteUser(Integer userId) {
        log.info("Delete user method is called.");
        userRepository.deleteById(userId);


    }

    @Override
    public void afterPropertiesSet() {
        User user = new User();
        user.setRole(Role.buyer);
        user.setUserName("Shehab");
        user.setDeposit(0);
        user.setPassword("12345");
        create(user);

        User user2 = new User();
        user2.setRole(Role.buyer);
        user2.setUserName("Omar");
        user2.setDeposit(0);
        user2.setPassword("12345");
        create(user2);

        User user3 = new User();
        user3.setRole(Role.seller);
        user3.setUserName("Ali");
        user3.setDeposit(0);
        user3.setPassword("12345");
        create(user3);

        User user4 = new User();
        user4.setRole(Role.seller);
        user4.setUserName("Mahmoud");
        user4.setDeposit(0);
        user4.setPassword("12345");
        create(user4);
    }
}
