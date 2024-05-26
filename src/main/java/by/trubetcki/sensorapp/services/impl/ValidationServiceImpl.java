package by.trubetcki.sensorapp.services.impl;

import by.trubetcki.sensorapp.services.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    @Override
    public <T> BindingResult validate(T object) {
        BindingResult bindingResult = new BeanPropertyBindingResult(object, "object");
        validator.validate(object, bindingResult);
        return bindingResult;
    }
}
