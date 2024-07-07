package by.trubetcki.sensorapp.services;

import by.trubetcki.sensorapp.dto.RangeDto;
import by.trubetcki.sensorapp.dto.SensorDto;
import by.trubetcki.sensorapp.exception.SensorNotFoundException;
import by.trubetcki.sensorapp.models.Sensor;
import by.trubetcki.sensorapp.repositories.SensorRepository;
import by.trubetcki.sensorapp.services.impl.SensorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl adminService;

    private SensorDto sensorDto;
    private Sensor sensor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        RangeDto rangeDto = new RangeDto();
        rangeDto.setFrom(1L);
        rangeDto.setTo(100L);

        sensorDto = new SensorDto();
        sensorDto.setName("TestSensor");
        sensorDto.setModel("T1000");
        sensorDto.setRange(rangeDto);
        sensorDto.setType("Temperature");
        sensorDto.setUnit("Celsius");
        sensorDto.setLocation("Building 1");
        sensorDto.setDescription("A sensor used for testing");

        sensor = new Sensor();
        sensor.setName("TestSensor");
        sensor.setModel("T1000");
        sensor.setRangeFrom(1L);
        sensor.setRangeTo(100L);
        sensor.setType("Temperature");
        sensor.setUnit("Celsius");
        sensor.setLocation("Building 1");
        sensor.setDescription("A sensor used for testing");
    }

    @Test
    void saveSensor_Success() {
        sensorDto.setName("TestSensor");
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        Sensor savedSensor = adminService.save(sensorDto);

        assertNotNull(savedSensor);
        assertEquals("TestSensor", savedSensor.getName());
        verify(sensorRepository, times(1)).save(any(Sensor.class));
    }


    @Test
    void showAllSensor() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Sensor> page = new PageImpl<>(Collections.singletonList(sensor));
        when(sensorRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Sensor> result = adminService.show(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(sensorRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void searchSensor() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Sensor> page = new PageImpl<>(Collections.singletonList(sensor));
        when(sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(anyString(), anyString(), any(Pageable.class))).thenReturn(page);

        Page<Sensor> result = adminService.search("Test", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(sensorRepository, times(1)).findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(anyString(), anyString(), any(Pageable.class));
    }

    @Test
    void updateSensor_Success() {
        when(sensorRepository.findById(anyLong())).thenReturn(Optional.of(sensor));
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        Sensor updatedSensor = adminService.update(sensorDto, 1L);

        assertNotNull(updatedSensor);
        assertEquals("TestSensor", updatedSensor.getName());
        verify(sensorRepository, times(1)).findById(anyLong());
        verify(sensorRepository, times(1)).save(any(Sensor.class));
    }

    @Test
    void updateSensor_NotFound() {
        when(sensorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(SensorNotFoundException.class, () -> adminService.update(sensorDto, 1L));
    }

    @Test
    void deleteSensor_Success() {
        when(sensorRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(sensorRepository).deleteById(anyLong());

        adminService.delete(1L);

        verify(sensorRepository, times(1)).existsById(anyLong());
        verify(sensorRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteSensor_NotFound() {
        when(sensorRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(SensorNotFoundException.class, () -> adminService.delete(1L));
    }
}
