package com.backend.backend.security;

import com.backend.backend.models.ERole;
import com.backend.backend.models.Role;
import com.backend.backend.models.ToDoList;
import com.backend.backend.models.User;
import com.backend.backend.repositories.RoleRepository;
import com.backend.backend.repositories.ToDoListRepository;
import com.backend.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("userSecurity")
public class UserSecurity {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    public boolean userCanManageList(Authentication authentication, Long listId) {
        Optional<ToDoList> toDoList = this.toDoListRepository.findById(listId);
        User auth = userRepository.findByEmail(authentication.getName());
        Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
        if(toDoList.isPresent()) {
            if(adminRole.isPresent()){
                if(auth.getRoles().contains(adminRole.get())) {
                    return true;
                }
            }
            return Objects.equals(toDoList.get().getUser().getEmail(), authentication.getName());
        }
        return true;
    }

    public boolean userBelongsToList(Authentication authentication, Long listId) {
        Optional<ToDoList> toDoList = this.toDoListRepository.findById(listId);
        if(toDoList.isPresent()) {
            User auth = userRepository.findByEmail(authentication.getName());
            return toDoList.get().getUsers().contains(auth);
        }
        return true;
    }

    public boolean userIsAuthorOfList(Authentication authentication, Long listId) {
        Optional<ToDoList> toDoList = this.toDoListRepository.findById(listId);
        if(toDoList.isPresent()) {
            return Objects.equals(toDoList.get().getUser().getEmail(), authentication.getName());
        }
        return true;
    }
}
