package com.backend.backend.dto;

import com.backend.backend.validation.UpdatePasswordMatches;
import jakarta.validation.constraints.NotBlank;

@UpdatePasswordMatches
public class ChangePasswordDto {

    @NotBlank(message = "Current password is required!")
    private String oldPassword;
    @NotBlank(message = "Password is required!")
    private String password;
    @NotBlank(message = "Password confirmation is required!")
    private String matchingPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
}
