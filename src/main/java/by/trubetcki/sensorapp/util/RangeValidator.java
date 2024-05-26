package by.trubetcki.sensorapp.util;

import by.trubetcki.sensorapp.dto.RangeDto;
import by.trubetcki.sensorapp.annotations.ValidRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<ValidRange, RangeDto> {
    @Override
    public void initialize(ValidRange constraintAnnotation) {
        // TODO document why this method is empty
    }

    @Override
    public boolean isValid(RangeDto rangeDto, ConstraintValidatorContext constraintValidatorContext) {
        if (rangeDto == null) {
            return true;
        }
        return rangeDto.getRangeTo() > rangeDto.getRangeFrom();
    }
}
