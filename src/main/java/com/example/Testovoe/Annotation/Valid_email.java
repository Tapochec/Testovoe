package com.example.Testovoe.Annotation;

import com.example.Testovoe.Validations.Email_validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

public class Valid_email {
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = Email_validator.class)
    @Documented
    public @interface ValidEmail {
        String message() default "Инвалидный емейл";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
