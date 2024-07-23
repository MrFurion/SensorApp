package by.trubetcki.sensorapp.dto;

import by.trubetcki.sensorapp.annotations.ValidType;
import by.trubetcki.sensorapp.annotations.ValidUnit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SensorDto {
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 character")
    private String name;
    @NotEmpty(message = "Model should not be empty")
    @Size(min = 1, max = 15, message = "Model should be between 1 and 15 character")
    private String model;
    @Valid
    @NotNull
    private RangeDto range;
    @ValidType(message = "Unknown type. Types allowed: Pressure, Voltage, Temperature, Humidity.")
    private String type;
    @ValidUnit(message = "Unknown unit. Unit allowed: bar, voltage, °С, %")
    private String unit;
    @Size(min = 1, max = 40, message = "Location should be between 1 and 40 character")
    private String location;
    @Size(min = 1, max = 200, message = "Description should be between 1 and 200 character")
    private String description;
}
