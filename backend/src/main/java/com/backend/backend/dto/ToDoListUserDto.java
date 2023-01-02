package com.backend.backend.dto;

import com.backend.backend.models.ToDoList;
import com.backend.backend.models.ToDoListItem;
import com.backend.backend.models.User;

import java.util.Set;

public class ToDoListUserDto {
    private long id;
    private String name;
    private String color;
    private User user;

    private String text_color;

    private Set<ToDoListItem> toDoListItemSet;

    private String description;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ToDoListItem> getToDoListItemSet() {
        return toDoListItemSet;
    }

    public void setToDoListItemSet(Set<ToDoListItem> toDoListItemSet) {
        this.toDoListItemSet = toDoListItemSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }
}
