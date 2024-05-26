package by.trubetcki.sensorapp.dto;

import by.trubetcki.sensorapp.annotations.ValidRange;
import lombok.Data;

@Data
@ValidRange
public class RangeDto {
    Long rangeFrom;
    Long rangeTo;
}
