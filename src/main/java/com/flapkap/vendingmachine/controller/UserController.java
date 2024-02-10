package com.flapkap.vendingmachine.controller;


import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.model.User;
import com.flapkap.vendingmachine.service.UserService;
import com.flapkap.vendingmachine.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationService validationService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return  ResponseEntity.ok(userService.getAll());
    }

    @PostMapping()
    public ResponseEntity<Integer> create(@Valid @RequestBody User user) {
        validationService.validateUserName(user.getUserName());
        validationService.validateRole(user.getRole());
        return ResponseEntity.accepted().body(userService.create(user));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> deleteUser (@PathVariable Integer id) {
        userService.deleteUser(id);
        return  ResponseEntity.noContent().build();
    }
    @PutMapping ()
    public ResponseEntity<Object> update(@Valid @RequestBody User user){
        validationService.validateUserName(user.getUserName());
        validationService.validateRole(user.getRole());
        userService.update(user);
        return  ResponseEntity.noContent().build();
    }

}
