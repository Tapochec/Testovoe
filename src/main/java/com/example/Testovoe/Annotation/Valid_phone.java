package com.example.Testovoe.Annotation;

import com.example.Testovoe.Validations.Phone_validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

public class Valid_phone {
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = Phone_validator.class)
    @Documented
    public @interface ValidPhone {
        String message() default "Инвалидный телефон";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
