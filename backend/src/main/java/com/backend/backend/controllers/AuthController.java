package com.backend.backend.controllers;


import com.backend.backend.dto.ChangePasswordDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.exceptions.GenericResponse;
import com.backend.backend.models.LoginForm;
import com.backend.backend.models.User;
import com.backend.backend.payload.MessageResponse;
import com.backend.backend.payload.UserInfoResponse;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.security.jwt.JwtUtils;
import com.backend.backend.security.services.UserDetailsImpl;
import com.backend.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        if(userRepository.findByEmail(loginRequest.getLogin()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Bad credentials"));
        }
        if(!this.userService.checkUserCredentials(loginRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Bad credentials"));
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getName(),
                        userDetails.getEmail(),
                        roles));

    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody UserDto userDto,
                         HttpServletRequest request,
                         Errors errors){

        return userService.registerNewUserAccount(userDto);
    }
    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updatePassword(Authentication authentication,
                               @Valid @RequestBody ChangePasswordDto changePasswordDto
                         ) {
        User auth = userRepository.findByEmail(authentication.getName());
        if(!userService.checkIfValidOldPassword(auth, changePasswordDto.getOldPassword())) {
            List<ObjectError> errors = new ArrayList<>();
            errors.add(new FieldError("oldPassword", "oldPassword", "Incorrect current password"));

            return ResponseEntity.badRequest().body(new GenericResponse(errors, "oldPassword"));
        }
        userService.changeUserPassword(auth, changePasswordDto.getPassword());
        return ResponseEntity.ok().body(new MessageResponse("Password successfully updated!"));

    }
}
