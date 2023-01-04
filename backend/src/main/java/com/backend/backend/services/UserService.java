package com.backend.backend.services;

import com.backend.backend.dto.NewUserDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.models.*;
import com.backend.backend.repositories.RoleRepository;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role not found"));
        roles.add(userRole);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User addNewUserAccount(NewUserDto newUserDto) throws UserAlreadyExistException {
        if (emailExists(newUserDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + newUserDto.getEmail());
        }

        User user = new User();
        user.setName(newUserDto.getName());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setEmail(newUserDto.getEmail());
        Set<String> strRoles = newUserDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("ROLE_ADMIN".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean checkUserCredentials(LoginForm loginForm) {
        User user = this.userRepository.findByEmail(loginForm.getLogin());
        return this.passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
    }

    public Page<User> getUserFilter(Filter filter) {
        if(filter.getSortField() == null)
            filter.setSortField("id");
        int page = filter.getFirst() / filter.getRows();

        Pageable pageable = PageRequest.of(page, filter.getRows(), Sort.by(filter.getSortOrder() == 1 ? Sort.Direction.ASC : Sort.Direction.DESC, filter.getSortField()));
        if(filter.getGlobalFilter() == null) {
            Page<User> page1 =  userRepository.findAll(pageable);
            page1.stream().se
            page1 = page1.stream().distinct();
            return
        }
        else {
            long id = 0;
            try{
                id = Long.parseLong(filter.getGlobalFilter());

            } catch (NumberFormatException e) {
                return userRepository.findDistinctByEmailContainingOrNameContainingOrRolesNameStringContaining(filter.getGlobalFilter(),filter.getGlobalFilter(), filter.getGlobalFilter(), pageable);
            }
            return userRepository.findDistinctByEmailContainingOrNameContainingOrId(filter.getGlobalFilter(), filter.getGlobalFilter(), id, pageable);
        }
    }
}
