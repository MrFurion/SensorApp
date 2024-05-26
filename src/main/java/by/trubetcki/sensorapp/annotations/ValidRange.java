package by.trubetcki.sensorapp.annotations;

import by.trubetcki.sensorapp.util.RangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRange {
    String message() default "rangeTo must be greater than rangeFrom";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
