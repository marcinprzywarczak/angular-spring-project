package com.backend.backend.validation;
import com.backend.backend.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;
//        System.out.println("password validation" + user.getPassword().equals(user.getMatchingPassword()));
        return user.getPassword().equals(user.getMatchingPassword());
    }
}