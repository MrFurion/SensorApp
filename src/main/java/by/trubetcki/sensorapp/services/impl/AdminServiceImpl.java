package by.trubetcki.sensorapp.services.impl;

import by.trubetcki.sensorapp.dto.SensorDto;
import by.trubetcki.sensorapp.exception.SensorNotFoundException;
import by.trubetcki.sensorapp.exception.ValidationException;
import by.trubetcki.sensorapp.models.Sensor;
import by.trubetcki.sensorapp.repositories.SensorRepository;
import by.trubetcki.sensorapp.services.AdminService;
import by.trubetcki.sensorapp.services.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final ValidationService validationService;
    private final SensorRepository sensorRepository;
    @Transactional
    public Sensor saveSensor(SensorDto sensorDto){

        BindingResult bindingResult = validationService.validate(sensorDto);
        if (bindingResult.hasErrors()){
            throw new ValidationException("Error of validation" + bindingResult.getAllErrors());
        }

        Sensor sensor = new Sensor();
        sensor.setName(sensorDto.getName());
        sensor.setModel(sensorDto.getModel());
        sensor.setRangeFrom(sensorDto.getRangeDto().getRangeFrom());
        sensor.setRangeTo(sensorDto.getRangeDto().getRangeTo());
        sensor.setType(sensorDto.getType());
        sensor.setUnit(sensorDto.getUnit());
        sensor.setLocation(sensorDto.getLocation());
        sensor.setDescription(sensorDto.getDescription());

        return sensorRepository.save(sensor);
    }

    public Page<Sensor> showAllSensor(Pageable pageable) {
        return sensorRepository.findAll(pageable);
    }
    public Page<Sensor> searchSensor(String keyWord, Pageable pageable){
        return sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(keyWord, keyWord, pageable);
    }

    @Transactional
    public Sensor updateSensor(SensorDto sensorDto, Long id){

        Optional<Sensor> sensorOptional = sensorRepository.findById(id);

        Map<String,String> updateSensor = new HashMap<>();
        updateSensor.put("Name", sensorDto.getName());
        updateSensor.put("Model", sensorDto.getModel());
        updateSensor.put("Unit", sensorDto.getUnit());
        updateSensor.put("Type", sensorDto.getType());
        updateSensor.put("Location", sensorDto.getLocation());
        updateSensor.put("Description", sensorDto.getDescription());

        if (sensorOptional.isPresent()){

            Sensor sensor = sensorOptional.get();

            updateSensor.forEach((field, value) ->
            {
                if (!value.isEmpty()) {
                    updateField(sensor, field, value);
                }
            });
            if (sensorDto.getRangeDto() != null) {
                updateField(sensor, "RangeFrom", sensorDto.getRangeDto().getRangeFrom());
                updateField(sensor, "RangeTo", sensorDto.getRangeDto().getRangeTo());
            }

            return  sensorRepository.save(sensor);
        }else {
            log.error("Sensor not found " + id);
            throw new SensorNotFoundException("Sensor not found " + id);
        }
    }
    private void updateField(Sensor sensor, String fieldName, Object value) {
        try {
            if (value != null) {
                String methodName = "set" + fieldName;
                Method method = Sensor.class.getMethod(methodName, value.getClass());
                method.invoke(sensor, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Transactional
    public void deleteSensor(Long id){
        if (!sensorRepository.existsById(id)) {
            throw new SensorNotFoundException("Sensor not found with id: " + id);
        }
        sensorRepository.deleteById(id);
    }

}
