package by.trubetcki.sensorapp.services;

import by.trubetcki.sensorapp.dto.SensorDto;
import by.trubetcki.sensorapp.models.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    /**
     * Create new sensor with use data of SensorDto.
     *
     * @param sensorDto data of sensor
     */
    Sensor saveSensor(SensorDto sensorDto);

    /**
     * Find all sensors
     *
     * @return List of sensors
     */
    Page<Sensor> showAllSensor(Pageable pageable);

    /**
     * Delete sensor by him id.
     *
     * @param id id of sensor
     */
    void deleteSensor(Long id);

    /**
     * Update sensor by id.
     *
     * @param sensorDto data of sensor
     * @param id id of sensor
     * @return updated sensor
     */
    Sensor updateSensor(SensorDto sensorDto, Long id);

    /**
     * Search sensor by keys word of name or model
     *
     * @param keyWord keys word for search sensor
     * @param pageable for pagination
     * @return
     */
    Page<Sensor> searchSensor(String keyWord, Pageable pageable);
}
