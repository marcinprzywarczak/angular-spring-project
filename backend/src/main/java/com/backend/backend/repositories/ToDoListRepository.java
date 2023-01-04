package com.backend.backend.repositories;

import com.backend.backend.models.ToDoList;
import com.backend.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
//    public List<ToDoList> findAllByUser(User user);
    public List<ToDoList> findDistinctByUsersOrUserOrderById(User users, User user);

    Optional<ToDoList> findById(long id, Sort sort);

}