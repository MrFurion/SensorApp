package by.trubetcki.sensorapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SensorDto {
    private Long id;
    @NotEmpty(message = "Name should by not empty")
    @Size(min = 3, max = 30, message = "Name should by between 3 and 30 character")
    private String name;
    @NotEmpty(message = "Model should by not empty")
    @Size(min = 1, max = 15, message = "Model should by between 1 and 15 character")
    private String model;
    @Valid
    private RangeDto rangeDto;
    private String type;
    private String unit;
    @Size(min = 1, max = 40, message = "Location should by between 1 and 40 character")
    private String location;
    @Size(min = 1, max = 40, message = "Description should by between 1 and 200 character")
    private String description;
}
