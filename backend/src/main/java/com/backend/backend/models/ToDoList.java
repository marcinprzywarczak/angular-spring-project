package com.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "todo_list")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    private String color;
    private String text_color;

    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "toDoList-user")
    private User user;

    @OneToMany(mappedBy = "toDoList")
    @JsonManagedReference
    @OrderBy(value = "id")
    private Set<ToDoListItem> toDoListItemSet;

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

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }
}
