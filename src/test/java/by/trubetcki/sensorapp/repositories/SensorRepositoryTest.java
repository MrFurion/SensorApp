package by.trubetcki.sensorapp.repositories;

import by.trubetcki.sensorapp.models.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@PropertySource("classpath:test.properties")
class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    Sensor sensor;
    Sensor sensor2;

    @BeforeEach
    void setUp() {
        sensor = new Sensor();
        sensor.setName("testsensor");
        sensor.setModel("t1000");
        sensor.setRangeFrom(2L);
        sensor.setRangeTo(3L);
        sensor.setType("Pressure");
        sensor.setUnit("%");
        sensor.setDescription("for kitcentest");

        sensor2 = new Sensor();
        sensor2.setName("testsensor2");
        sensor2.setModel("t2000");
        sensor2.setType("Pressure");
        sensor2.setRangeFrom(5L);
        sensor2.setRangeTo(10L);
        sensor2.setUnit("%");
        sensor2.setDescription("description2");

        sensorRepository.save(sensor);
        sensorRepository.save(sensor2);
    }

    @Test
    void testFindSensorName(){
        Pageable pageable = PageRequest.of(0, 10);
        String keyword = "t1000";
        Page<Sensor> sensorTest = sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword, pageable);
        assertFalse(sensorTest.isEmpty());
    }

    @Test
    void testFindByNameContainingIgnoreCaseOrModelContainingIgnoreCase_ModelMatch() {
        Pageable pageable = PageRequest.of(0, 10);
        String keyword = "t2000";
        Page<Sensor> result = sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(sensor2.getModel(), result.getContent().get(0).getModel());
    }

    @Test
    void testFindByNameContainingIgnoreCaseOrModelContainingIgnoreCase_PartialMatch() {
        Pageable pageable = PageRequest.of(0, 10);
        String keyword = "test";
        Page<Sensor> result = sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword, pageable);

        assertEquals(2, result.getTotalElements());
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void testFindByNameContainingIgnoreCaseOrModelContainingIgnoreCase_NoMatch() {
        Pageable pageable = PageRequest.of(0, 10);
        String keyword = "nonexistent";
        Page<Sensor> result = sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword, pageable);

        assertEquals(0, result.getTotalElements());
    }
}
