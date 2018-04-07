package com.jezh.springsecurity.annotation.customAnnotation;

import com.jezh.springsecurity.annotation.customAnnotationsValidator.CourseCodeConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourseCodeConstraintValidator.class)
// this is where I can apply or list this annotation
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {

//    define default value, this default will be used if any other value isn't defined when annotation
//    will be implemented to field or method
    String value() default "jezhische";

//    define default message for error message
    String message() default "jezhische is the rule!";

//    define default groups
//    We're NOT gonna use ANY GROUPING of grouping constraints, so there is simply the basic boilerplate here
    Class<?>[] groups() default{};

//    define default payloads
//    We're NOT gonna use ANY PAYLOADS, so we just give the default here for payload (payload can give additional
//     details about error message that has occured)
    Class<? extends Payload>[] payload() default{};

}
