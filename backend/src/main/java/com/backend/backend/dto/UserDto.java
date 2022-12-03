package com.backend.backend.dto;

import com.backend.backend.validation.PasswordMatches;
import com.backend.backend.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserDto {
    @NotNull(message = "The full name is required.")
    @Size(min = 1, max = 2, message = "min validtioan")
    private String name;

    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    @ValidEmail
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
