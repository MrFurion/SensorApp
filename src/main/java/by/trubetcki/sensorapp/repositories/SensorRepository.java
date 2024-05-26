package by.trubetcki.sensorapp.repositories;

import by.trubetcki.sensorapp.models.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Page<Sensor> findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(String name, String model, Pageable pageable);
}
