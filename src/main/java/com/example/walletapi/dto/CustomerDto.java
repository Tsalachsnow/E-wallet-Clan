package com.example.walletapi.dto;


import javax.validation.constraints.*;
import lombok.Data;


@Data
public class CustomerDto {
    @NotEmpty(message = "Username Name cannot be empty")
    @Size(min = 2, message = "username must not be less than 1")
    private String userName;

    @NotEmpty(message = "first Name cannot be empty")
    @Size(min = 2, message = "first Name must not be less than 2")
    private String fullName;


    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "password must not be less than 8")
    private String password;


    private String gender;
}
