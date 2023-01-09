package com.backend.backend.controllers;

import com.backend.backend.dto.ToDoListDto;
import com.backend.backend.dto.ToDoListItemDao;
import com.backend.backend.mail.EmailService;
import com.backend.backend.models.ToDoList;
import com.backend.backend.models.ToDoListItem;
import com.backend.backend.models.User;
import com.backend.backend.payload.MessageResponse;
import com.backend.backend.repositories.ToDoListItemRepository;
import com.backend.backend.repositories.ToDoListRepository;
import com.backend.backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api/toDoList")
@CrossOrigin(origins = "http://localhost:4200")
public class ToDoListController {
    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ToDoListItemRepository toDoListItemRepository;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ToDoList addNewList(Authentication authentication,@Valid @RequestBody ToDoListDto toDoListDto) {
        User auth = userRepository.findByEmail(authentication.getName());
        Set<String> users = toDoListDto.getUsers();
        Set<User> listUsers = new HashSet<>();
        users.forEach(user -> {
            System.out.println("user: " + user);
            User user1 =  userRepository.findByEmail(user);
            if(user1 != null) {
                listUsers.add(user1);
            }
        });
        ToDoList toDoList = new ToDoList();
        toDoList.setUser(auth);
        toDoList.setName(toDoListDto.getName());
        toDoList.setColor(toDoListDto.getColor());
        toDoList.setDescription(toDoListDto.getDescription());
        toDoList.setText_color(toDoListDto.getText_color());
        toDoList.setUsers(listUsers);
        return toDoListRepository.save(toDoList);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ToDoList> getAllList(Authentication authentication) {
        User auth = userRepository.findByEmail(authentication.getName());
        return this.toDoListRepository.findDistinctByUsersOrUserOrderById(auth, auth);
    }

    @GetMapping("/all")
    public List<ToDoList> getAll() {
        return this.toDoListRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("(isAuthenticated() and @userSecurity.userBelongsToList(authentication, #id)) or hasRole('ADMIN')")
    public ResponseEntity<ToDoList> findUserById(@PathVariable(value = "id") long id, Authentication authentication) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id, Sort.by(Sort.Direction.ASC, "toDoListItemSet.id"));
        if(toDoList.isPresent()) {
            return ResponseEntity.ok().body(toDoList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated() and @userSecurity.userCanManageList(authentication, #id)")
    public ResponseEntity<?> updateToDoList(@PathVariable(value = "id") long id, Authentication authentication,
                                            @RequestBody @Valid ToDoListDto toDoListDto) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        if(toDoList.isPresent()) {
            ToDoList toDoListEdit = toDoList.get();
            Set<String> users = toDoListDto.getUsers();
            Set<User> listUsers = new HashSet<>();
            users.forEach(user -> {
                User user1 =  userRepository.findByEmail(user);
                if(user1 != null) {
                    listUsers.add(user1);
                }
            });
            toDoListEdit.setUsers(listUsers);
            toDoListEdit.setName(toDoListDto.getName());
            toDoListEdit.setDescription(toDoListDto.getDescription());
            toDoListEdit.setColor(toDoListDto.getColor());
            toDoListEdit.setText_color(toDoListDto.getText_color());
            toDoListRepository.save(toDoListEdit);
            return ResponseEntity.ok().body(new MessageResponse("To do list successfully updated!"));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated() and @userSecurity.userCanManageList(authentication, #id)")
    public ResponseEntity<HttpStatus> deleteToDoList(@PathVariable(value = "id") long id, Authentication authentication) {
        try{
            toDoListRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/addNewItem")
    @PreAuthorize("(isAuthenticated() and @userSecurity.userBelongsToList(authentication, #id)) or hasRole('ADMIN')")
    public ResponseEntity<?> addNewItemToList(@PathVariable(value = "id") long id, @RequestBody ToDoListItemDao toDoListItemDao) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        if(toDoList.isPresent()) {
            ToDoListItem toDoListItem = new ToDoListItem();
            toDoListItem.setName(toDoListItemDao.getName());
            toDoListItem.setDone(toDoListItem.isDone());
            toDoListItem.setToDoList(toDoList.get());
            this.toDoListItemRepository.save(toDoListItem);
            return ResponseEntity.ok().body(new MessageResponse("New item successfully added!"));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteItem/{id}")
    @PreAuthorize("(isAuthenticated() and @userSecurity.userBelongsToList(authentication, #id)) or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteToDoListItem(@PathVariable(value = "id") long id, Authentication authentication) {
        try{
            toDoListItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/checkAsDone")
    @PreAuthorize("(isAuthenticated() and @userSecurity.userBelongsToList(authentication, #id)) or hasRole('ADMIN')")
    public ResponseEntity<?> checkItemAsDone(@PathVariable(value = "id") long id, @RequestBody boolean isDone) {
        Optional<ToDoListItem> toDoListItem = toDoListItemRepository.findById(id);
        if(toDoListItem.isPresent()) {
            toDoListItem.get().setDone(isDone);
            this.toDoListItemRepository.save(toDoListItem.get());
            return ResponseEntity.ok().body(new MessageResponse("Item successfully updated!"));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mail")
    public void sendMail() {
        this.emailService.sendMail();
    }

}
