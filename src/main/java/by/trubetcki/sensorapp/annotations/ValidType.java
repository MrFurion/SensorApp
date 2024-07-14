package by.trubetcki.sensorapp.annotations;

import by.trubetcki.sensorapp.util.TypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidType {
    String message() default "Invalid type value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
