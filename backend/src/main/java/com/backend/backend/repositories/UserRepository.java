package com.backend.backend.repositories;

import com.backend.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();

    User findByEmail(String email);

    @Query("SELECT u FROM User u")
    List<User> findUserAll();


}
