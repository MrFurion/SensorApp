package by.trubetcki.sensorapp.services;

import org.springframework.validation.BindingResult;

public interface ValidationService {
    /**
     * This interface defines the validate method,
     * which takes an object of type T as an argument and returns a BindingResult object.
     * The generalized type <T> is used here, which means that the validate method can accept an object of any type.
     */
    <T> BindingResult validate(T object);
}
