package by.trubetcki.sensorapp.util;

import by.trubetcki.sensorapp.annotations.ValidType;
import by.trubetcki.sensorapp.models.MeasurementType;
import by.trubetcki.sensorapp.repositories.MeasurementTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class TypeValidator implements ConstraintValidator<ValidType, String> {
    private final MeasurementTypeRepository measurementTypeRepository;

    private static final Set<String> VALID_TYPES = new HashSet<>();

    @Override
    public void initialize(ValidType constraintAnnotation){
        List<MeasurementType> measurementTypes = measurementTypeRepository.findAll();
        measurementTypes.forEach(measurementType -> VALID_TYPES.add(measurementType.getName()));
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return VALID_TYPES.contains(value);
    }
}
