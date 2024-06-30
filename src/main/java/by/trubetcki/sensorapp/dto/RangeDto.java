package by.trubetcki.sensorapp.dto;

import by.trubetcki.sensorapp.annotations.ValidRange;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidRange
public class RangeDto {
    @NotNull(message = "From not should by empty")
    Long from;
    @NotNull(message = "To not should by empty")
    Long to;
}
