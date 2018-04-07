package com.jezh.springsecurity.annotation.customAnnotation;

import com.jezh.springsecurity.annotation.customAnnotationsValidator.AliasArrayConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AliasArrayConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AliasArray {

    String[] value() default {"terrific", "incredible", "fabulous"};
    String message() default "you are wrong";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
