package com.backend.backend.controllers;

import com.backend.backend.dto.UserDto;
import com.backend.backend.models.LoginForm;
import com.backend.backend.models.User;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.services.UserService;
import com.backend.backend.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllUsers(final HttpServletRequest request, Authentication authentication) {
//        System.out.println("auth: " + authentication.getName());
        return userRepository.findAll();
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


}
