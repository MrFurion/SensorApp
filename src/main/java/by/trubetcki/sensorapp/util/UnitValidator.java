package by.trubetcki.sensorapp.util;

import by.trubetcki.sensorapp.annotations.ValidType;
import by.trubetcki.sensorapp.annotations.ValidUnit;
import by.trubetcki.sensorapp.models.MeasurementType;
import by.trubetcki.sensorapp.models.MeasurementUnit;
import by.trubetcki.sensorapp.repositories.MeasurementUnitRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class UnitValidator implements ConstraintValidator<ValidUnit, String> {
    private final MeasurementUnitRepository measurementUnitRepository;
    private static final Set<String> VALID_UNITS = new HashSet<>();

    @Override
    public void initialize(ValidUnit constraintAnnotation) {
        List<MeasurementUnit> measurementUnits = measurementUnitRepository.findAll();
        measurementUnits.forEach(measurementType -> VALID_UNITS.add(measurementType.getName()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || VALID_UNITS.contains(value);
    }
}
