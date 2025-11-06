package uz.lb.updaterserver.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import uz.lb.updaterserver.validation.StrongPasswordValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {
    String message() default "Password is too weak or contains invalid characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
