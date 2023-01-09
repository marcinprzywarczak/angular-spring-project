package com.backend.backend.dto;

import com.backend.backend.validation.PasswordMatches;
import com.backend.backend.validation.ValidEmail;


import java.util.Set;

@PasswordMatches
public class NewUserDto extends UserDto {
    private Set<String> roles;


    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> role) {
        this.roles = role;
    }
}
