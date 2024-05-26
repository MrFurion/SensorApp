package by.trubetcki.sensorapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    @Column(name = "range_from")
    private Long rangeFrom;
    @Column(name = "range_to")
    private Long rangeTo;
    private String type;
    private String unit;
    private String location;
    private String description;
}
