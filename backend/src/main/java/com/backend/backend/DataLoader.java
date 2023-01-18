package com.backend.backend;

import com.backend.backend.models.ERole;
import com.backend.backend.models.Role;
import com.backend.backend.models.User;
import com.backend.backend.repositories.RoleRepository;
import com.backend.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    @Value("${backend.app.adminPassword}")
    private String password;

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //add admin user if not already exists
    public void run(ApplicationArguments args) {
        this.addRoles();
        if(userRepository.findByEmail("admin@test.com") != null)
            return;
        User user = new User();
        user.setName("admin");
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail("admin@test.com");
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role not found"));
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void addRoles() {
        Optional<Role> role = this.roleRepository.findByName(ERole.ROLE_ADMIN);
        if(!role.isPresent()) {
            Role addRole = new Role();
            addRole.setName(ERole.ROLE_ADMIN);
            this.roleRepository.save(addRole);
        }
        role = this.roleRepository.findByName(ERole.ROLE_USER);
        if(!role.isPresent()) {
            Role addRole = new Role();
            addRole.setName(ERole.ROLE_USER);
            this.roleRepository.save(addRole);
        }
    }
}