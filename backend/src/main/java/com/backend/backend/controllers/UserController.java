package com.backend.backend.controllers;

import com.backend.backend.dto.NewUserDto;
import com.backend.backend.dto.UpdateUserDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.mail.EmailService;
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

    @Autowired
    private EmailService emailService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<User> findAllUsers(final HttpServletRequest request, Authentication authentication) {
        return userRepository.findAll();
    }

    @PostMapping("/filterUser")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> filterUser(@RequestBody Filter filter) {
        return userService.getUserFilter(filter);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addNewUser")
    @PreAuthorize("hasRole('ADMIN')")
    public User addNewUser(@Valid @RequestBody NewUserDto newUserDto,
                           HttpServletRequest request,
                           Errors errors){

        User registered = userService.addNewUserAccount(newUserDto);
//        this.emailService.sendMail(registered.getName(), registered.getEmail(), newUserDto.getPassword());
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
                if(user.get().getEmail().equals("admin@test.com")) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Cannot delete default admin account."));
                }
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
