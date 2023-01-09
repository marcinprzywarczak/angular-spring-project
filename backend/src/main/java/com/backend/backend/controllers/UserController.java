package com.backend.backend.controllers;

import com.backend.backend.dto.NewUserDto;
import com.backend.backend.dto.UpdateUserDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.models.*;
import com.backend.backend.payload.MessageResponse;
import com.backend.backend.repositories.RoleRepository;
import com.backend.backend.repositories.ToDoListRepository;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.services.UserService;
import com.backend.backend.validation.UserAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private RoleRepository roleRepository;
    @GetMapping
    public List<User> findAllUsers(final HttpServletRequest request, Authentication authentication) {
//        System.out.println("auth: " + authentication.getName());
        return userRepository.findAll();
    }

    @PostMapping("/filterUser")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> filterUser(@RequestBody Filter filter) {
        return userService.getUserFilter(filter);
    }

    @GetMapping("/all")
    public List<User> all() {
        return userRepository.findUserAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody UserDto userDto,
                           HttpServletRequest request,
                           Errors errors){

        User registered = userService.registerNewUserAccount(userDto);


        return registered;
    }

    @PostMapping("/addNewUser")
    @PreAuthorize("hasRole('ADMIN')")
    public User addNewUser(@Valid @RequestBody NewUserDto newUserDto,
                           HttpServletRequest request,
                           Errors errors){

        User registered = userService.addNewUserAccount(newUserDto);
        return registered;
    }

    @PutMapping("/{id}/updateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") long id,
                           @Valid @RequestBody UpdateUserDto updateUser){
        return userService.updateUser(updateUser, id);
    }
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long id){
        try {
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()) {
                Set<ToDoList> userLists = user.get().getUserToDoLists();
                this.toDoListRepository.deleteAll(userLists);
                Set<ToDoList> lists = user.get().getToDoLists();
                for (ToDoList list: lists
                     ) {
                    list.getUsers().remove(user.get());
                }
            }

            userRepository.deleteById(id);
            return ResponseEntity.ok().body(new MessageResponse("User successfully updated!"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> getUserRole() {
        return this.roleRepository.findAll();
    }


}
