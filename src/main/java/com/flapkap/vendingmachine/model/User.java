package com.flapkap.vendingmachine.model;

import com.flapkap.vendingmachine.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "userTable")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Username is required")
    private String userName;
    private Role role;
    @NotBlank(message = "Password is required")
    private  String password;
    private Integer deposit = 0;
}
