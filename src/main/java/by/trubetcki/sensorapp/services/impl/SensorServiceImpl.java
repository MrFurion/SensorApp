package by.trubetcki.sensorapp.services.impl;

import by.trubetcki.sensorapp.dto.SensorDto;
import by.trubetcki.sensorapp.exception.SensorNotFoundException;
import by.trubetcki.sensorapp.models.Sensor;
import by.trubetcki.sensorapp.repositories.SensorRepository;
import by.trubetcki.sensorapp.services.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    @Transactional
    public Sensor save(@Validated SensorDto sensorDto) {

        Sensor sensor = new Sensor();
        sensor.setName(sensorDto.getName());
        sensor.setModel(sensorDto.getModel());
        sensor.setRangeFrom(sensorDto.getRange().getFrom());
        sensor.setRangeTo(sensorDto.getRange().getTo());
        sensor.setType(sensorDto.getType());
        sensor.setUnit(sensorDto.getUnit());
        sensor.setLocation(sensorDto.getLocation());
        sensor.setDescription(sensorDto.getDescription());

        return sensorRepository.save(sensor);
    }

    public Page<Sensor> show(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }

    public Page<Sensor> search(String keyWord, Pageable pageable) {
        return sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(keyWord, keyWord, pageable);
    }

    @Transactional
    public Sensor update(SensorDto sensorDto, Long id) {

        Optional<Sensor> sensorOptional = sensorRepository.findById(id);
        if (sensorOptional.isPresent()){
            Sensor sensor = sensorOptional.get();

            Optional.ofNullable(sensorDto.getName()).ifPresent(sensor::setName);
            Optional.ofNullable(sensorDto.getModel()).ifPresent(sensor::setModel);
            Optional.ofNullable(sensorDto.getRange()).ifPresent(rangeDto -> {
                Optional.ofNullable(rangeDto.getFrom()).ifPresent(sensor::setRangeFrom);
                Optional.ofNullable(rangeDto.getTo()).ifPresent(sensor::setRangeTo);
            });
            Optional.ofNullable(sensorDto.getType()).ifPresent(sensor::setType);
            Optional.ofNullable(sensorDto.getUnit()).ifPresent(sensor::setUnit);
            Optional.ofNullable(sensorDto.getLocation()).ifPresent(sensor::setLocation);
            Optional.ofNullable(sensorDto.getDescription()).ifPresent(sensor::setDescription);

            return sensorRepository.save(sensor);
        } else {
            log.error("Sensor not found " + id);
            throw new SensorNotFoundException("Sensor not found " + id);
        }
    }
    @Transactional
    public void delete(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new SensorNotFoundException("Sensor not found with id: " + id);
        }
        sensorRepository.deleteById(id);
    }
}
