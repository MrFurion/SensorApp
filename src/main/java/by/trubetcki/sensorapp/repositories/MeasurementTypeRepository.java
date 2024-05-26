package by.trubetcki.sensorapp.repositories;

import by.trubetcki.sensorapp.models.MeasurementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementTypeRepository extends JpaRepository<MeasurementType, Long> {
}
