package com.backend.backend.dto;

import com.backend.backend.validation.PasswordMatches;
import com.backend.backend.validation.ValidEmail;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@PasswordMatches
public class NewUserDto extends UserDto {
    private Set<String> role;


    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
