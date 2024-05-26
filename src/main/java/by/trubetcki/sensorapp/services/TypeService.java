package by.trubetcki.sensorapp.services;

import by.trubetcki.sensorapp.models.MeasurementType;

import java.util.List;

public interface TypeService {

    /**
     * Find all measurement type
     *
     * @return List MeasuremenType
     */
    List<MeasurementType> findAllMeasurementType();
}
