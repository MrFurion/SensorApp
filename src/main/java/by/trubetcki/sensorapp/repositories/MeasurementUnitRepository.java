package by.trubetcki.sensorapp.repositories;

import by.trubetcki.sensorapp.models.MeasurementUnit;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {
}
