package com.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OrderBy(value = "id")
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonBackReference
    private Set<ToDoList> toDoLists = new HashSet<>();

    public Set<ToDoList> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(Set<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "toDoList-user")
    private Set<ToDoList> userToDoLists;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Set<ToDoList> getUserToDoLists() {
        return userToDoLists;
    }

    public void setUserToDoLists(Set<ToDoList> userToDoLists) {
        this.userToDoLists = userToDoLists;
    }
}
