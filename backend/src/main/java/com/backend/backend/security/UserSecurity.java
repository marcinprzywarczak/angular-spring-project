package com.backend.backend.security;

import com.backend.backend.models.ToDoList;
import com.backend.backend.repositories.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("userSecurity")
public class UserSecurity {
    @Autowired
    ToDoListRepository toDoListRepository;

    public boolean userIsAuthorOfList(Authentication authentication, Long listId) {
        Optional<ToDoList> toDoList = this.toDoListRepository.findById(listId);
        if(toDoList.isPresent()) {
            return Objects.equals(toDoList.get().getUser().getEmail(), authentication.getName());
        }
        return true;
    }
}
