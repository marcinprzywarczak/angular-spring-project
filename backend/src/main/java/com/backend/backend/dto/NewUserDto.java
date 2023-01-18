package com.backend.backend.dto;

import com.backend.backend.validation.PasswordMatches;
import com.backend.backend.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;


import java.util.Set;

public class NewUserDto {
    private Set<String> roles;

    @NotBlank(message = "Name is required!")
    private String name;


    @NotBlank(message = "Email is required!")
    @ValidEmail(message = "Email is wrong!")
    private String email;


    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> role) {
        this.roles = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
