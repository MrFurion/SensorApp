package by.trubetcki.sensorapp.services.impl;

import by.trubetcki.sensorapp.models.MeasurementType;
import by.trubetcki.sensorapp.repositories.MeasurementTypeRepository;
import by.trubetcki.sensorapp.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TypeImpl implements TypeService {

    private final MeasurementTypeRepository measurementTypeRepository;

    @Override
    public List<MeasurementType> findAllMeasurementType() {
        return measurementTypeRepository.findAll();
    }
}
