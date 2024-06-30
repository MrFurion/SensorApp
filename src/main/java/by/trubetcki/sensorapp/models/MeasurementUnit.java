package by.trubetcki.sensorapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "unit")
public class MeasurementUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
}
