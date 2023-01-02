package com.backend.backend.controllers;

import com.backend.backend.dto.ToDoListDto;
import com.backend.backend.dto.ToDoListItemDao;
import com.backend.backend.dto.ToDoListUserDto;
import com.backend.backend.models.ToDoList;
import com.backend.backend.models.ToDoListItem;
import com.backend.backend.models.User;
import com.backend.backend.payload.MessageResponse;
import com.backend.backend.repositories.ToDoListItemRepository;
import com.backend.backend.repositories.ToDoListRepository;
import com.backend.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/toDoList")
@CrossOrigin(origins = "http://localhost:4200")
public class ToDoListController {
    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoListItemRepository toDoListItemRepository;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ToDoList addNewList(Authentication authentication, @RequestBody ToDoListDto toDoListDto) {
        User auth = userRepository.findByEmail(authentication.getName());

        ToDoList toDoList = new ToDoList();
        toDoList.setName(toDoListDto.getName());
        toDoList.setColor(toDoListDto.getColor());
        toDoList.setDescription(toDoListDto.getDescription());
        toDoList.setText_color(toDoListDto.getText_color());
        toDoList.setUser(auth);
        return toDoListRepository.save(toDoList);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ToDoListUserDto> getAllList(Authentication authentication) {
        User auth = userRepository.findByEmail(authentication.getName());

        List<ToDoList> toDoLists = this.toDoListRepository.findAllByUser(auth);
        List<ToDoListUserDto> responseList = new ArrayList<>();
        for(ToDoList list : toDoLists) {
            ToDoListUserDto item = new ToDoListUserDto();
            item.setName(list.getName());
            item.setColor(list.getColor());
            item.setUser(list.getUser());
            item.setDescription(list.getDescription());
            item.setToDoListItemSet(list.getToDoListItemSet());
            item.setId(list.getId());
            item.setText_color(list.getText_color());
            responseList.add(item);
        }
        return responseList;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @userSecurity.userIsAuthorOfList(authentication, #id)")
    public ResponseEntity<ToDoListUserDto> findUserById(@PathVariable(value = "id") long id, Authentication authentication) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        if(toDoList.isPresent()) {
            ToDoListUserDto item = new ToDoListUserDto();
            item.setName(toDoList.get().getName());
            item.setColor(toDoList.get().getColor());
            item.setUser(toDoList.get().getUser());
            item.setDescription(toDoList.get().getDescription());
            item.setToDoListItemSet(toDoList.get().getToDoListItemSet());
            item.setId(toDoList.get().getId());
            item.setText_color(toDoList.get().getText_color());
//            if(!Objects.equals(item.getUser().getEmail(), authentication.getName()))
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return ResponseEntity.ok().body(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated() and @userSecurity.userIsAuthorOfList(authentication, #id)")
    public ResponseEntity<?> updateToDoList(@PathVariable(value = "id") long id, Authentication authentication,
                                            @RequestBody ToDoListDto toDoListDto) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        if(toDoList.isPresent()) {
            ToDoList toDoListEdit = toDoList.get();
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
    @PreAuthorize("isAuthenticated() and @userSecurity.userIsAuthorOfList(authentication, #id)")
    public ResponseEntity<HttpStatus> deleteToDoList(@PathVariable(value = "id") long id, Authentication authentication) {
        try{
            toDoListRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/addNewItem")
    @PreAuthorize("isAuthenticated() and @userSecurity.userIsAuthorOfList(authentication, #id)")
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
    @PreAuthorize("isAuthenticated() and @userSecurity.userIsAuthorOfList(authentication, #id)")
    public ResponseEntity<HttpStatus> deleteToDoListItem(@PathVariable(value = "id") long id, Authentication authentication) {
        try{
            toDoListItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/checkAsDone")
    @PreAuthorize("isAuthenticated() and @userSecurity.userIsAuthorOfList(authentication, #id)")
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

}
