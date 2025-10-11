package com.deepecho.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrength {
    String message() default "密码必须包含大小写字母和数字，长度至少8位";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 