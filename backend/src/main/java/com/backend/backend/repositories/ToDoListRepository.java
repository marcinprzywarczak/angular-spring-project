package com.backend.backend.repositories;

import com.backend.backend.models.ToDoList;
import com.backend.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    public List<ToDoList> findAllByUser(User user);

}