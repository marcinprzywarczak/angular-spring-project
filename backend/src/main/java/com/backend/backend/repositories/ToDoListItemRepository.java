package com.backend.backend.repositories;

import com.backend.backend.models.ToDoListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListItemRepository extends JpaRepository<ToDoListItem, Long> {
}
