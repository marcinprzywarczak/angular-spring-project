package com.backend.backend.services;

import com.backend.backend.dto.UserDto;
import com.backend.backend.models.User;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
//        if (emailExists(userDto.getEmail())) {
//            throw new UserAlreadyExistException("There is an account with that email address: "
//                    + userDto.getEmail());
//        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        System.out.println("user dto, name:" + userDto.getName() + ", password: " + userDto.getPassword() + ", email: " + userDto.getEmail());

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
