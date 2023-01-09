package com.backend.backend.models;

import jakarta.validation.constraints.NotBlank;


public class LoginForm {
    @NotBlank(message = "Email is required!")
    private String login;
    @NotBlank(message = "Password is required!")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
