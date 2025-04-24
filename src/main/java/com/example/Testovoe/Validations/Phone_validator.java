package com.example.Testovoe.Validations;

import com.example.Testovoe.Annotation.Valid_phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phone_validator implements ConstraintValidator<Valid_phone.ValidPhone, String> {

    private static final String PHONE_PATTERN = "^7920\\d{7}$";


    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return (validatePhone(phone));
    }

    @Override
    public void initialize(Valid_phone.ValidPhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private boolean validatePhone(String email) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
