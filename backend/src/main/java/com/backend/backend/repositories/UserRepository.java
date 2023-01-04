package com.backend.backend.repositories;

import com.backend.backend.models.ERole;
import com.backend.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();


    Page<User> findAll(Pageable pageable);

    User findByEmail(String email);

    @Query("SELECT u FROM User u")
    List<User> findUserAll();

    Page<User> findByEmailContaining(String email, Pageable pageable);

    Page<User> findDistinctByEmailContainingOrNameContainingOrId(String email, String name, long id, Pageable pageable);
    Page<User> findDistinctByEmailContainingOrNameContainingOrRolesNameStringContaining(String email, String name, String roleName, Pageable pageable);

    Page<User> findById(long id, Pageable pageable);


}
