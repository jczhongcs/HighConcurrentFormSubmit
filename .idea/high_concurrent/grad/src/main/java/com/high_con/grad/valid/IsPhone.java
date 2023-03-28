package com.high_con.grad.valid;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = { IsPhoneValid.class})
public @interface IsPhone {

    boolean required() default true;

    String message() default "your phone num is incorrect form";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
