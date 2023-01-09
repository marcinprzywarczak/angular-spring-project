package com.backend.backend.dto;

import com.backend.backend.validation.PasswordMatches;
import com.backend.backend.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;

@PasswordMatches
public class UserDto {
    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Password is required!")
    private String password;
    @NotBlank(message = "Password confirmation is required!")
    private String matchingPassword;

    @NotBlank(message = "Email is required!")
    @ValidEmail(message = "Email is wrong!")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
