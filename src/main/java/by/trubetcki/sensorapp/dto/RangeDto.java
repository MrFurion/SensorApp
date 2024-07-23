package by.trubetcki.sensorapp.dto;

import by.trubetcki.sensorapp.annotations.ValidRange;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidRange
public class RangeDto {
    @NotNull(message = "From should not be empty")
    Long from;
    @NotNull(message = "To should not be empty")
    Long to;
}
