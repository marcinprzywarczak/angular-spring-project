package com.backend.backend.validation;
import com.backend.backend.dto.ChangePasswordDto;
import com.backend.backend.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UpdatePasswordMatchesValidator
        implements ConstraintValidator<UpdatePasswordMatches, Object> {

    @Override
    public void initialize(UpdatePasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        ChangePasswordDto changePasswordDto = (ChangePasswordDto) obj;
        if(changePasswordDto.getPassword() == null || changePasswordDto.getMatchingPassword() == null)
            return false;
        return changePasswordDto.getPassword().equals(changePasswordDto.getMatchingPassword());
    }
}