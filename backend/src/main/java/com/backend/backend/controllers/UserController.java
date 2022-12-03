package com.backend.backend.controllers;

import com.backend.backend.dto.UserDto;
import com.backend.backend.models.User;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.services.UserService;
import com.backend.backend.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<User> findAllUsers() {
        System.out.println("test");
        List<User> lista = userRepository.findAll();
        System.out.println(lista.get(0).getName());
        System.out.println(lista.size());
        return userRepository.findAll();
    }

    @GetMapping("/all")
    public List<User> all() {

        System.out.println("test");
        System.out.println(userRepository.findUserAll());
        return userRepository.findUserAll();
    }

    @GetMapping("/test")
    public String test() {
        return "{test: test}";
    }

    @GetMapping("/{id}")
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
    public String register(@Valid @RequestBody UserDto userDto,
                           HttpServletRequest request,
                           Errors errors){
        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            return "An account for that username/email already exists.";
        }

        return "Success";
    }
}
