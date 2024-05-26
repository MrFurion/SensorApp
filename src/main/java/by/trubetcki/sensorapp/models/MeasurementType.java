package by.trubetcki.sensorapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "measurementtype")
public class MeasurementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
