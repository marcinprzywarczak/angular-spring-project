package com.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;


public class ToDoListItemDao {

    @NotBlank(message = "Name is required!")
    private String name;
    @NotBlank(message = "Is done is required")
    private boolean isDone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
