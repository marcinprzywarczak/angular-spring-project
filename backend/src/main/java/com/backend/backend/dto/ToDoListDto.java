package com.backend.backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Set;

public class ToDoListDto {
    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Color is required!")
    private String color;

    @NotBlank(message = "Text color is required!")
    private String text_color;

    @NotBlank(message = "Description is required!")
    private String description;

    private Set<String> users;

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }
}
