package by.trubetcki.sensorapp.controller;

import by.trubetcki.sensorapp.dto.SensorDto;
import by.trubetcki.sensorapp.exception.SensorNotFoundException;
import by.trubetcki.sensorapp.models.MeasurementType;
import by.trubetcki.sensorapp.models.Sensor;
import by.trubetcki.sensorapp.services.SensorService;
import by.trubetcki.sensorapp.services.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {
    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    public static final String ROLE_ROLE_ADMIN_ROLE_VIEWER = "hasAnyRole('ROLE_ADMIN', 'ROLE_VIEWER')";
    private final SensorService adminService;
    private final TypeService typeService;

    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "Save new sensor")
    @PostMapping
    public ResponseEntity<Sensor> create(@Validated @RequestBody SensorDto sensorDto){
       Sensor sensor = adminService.save(sensorDto);
        return ResponseEntity.ok(sensor);
    }

    @PreAuthorize(ROLE_ROLE_ADMIN_ROLE_VIEWER)
    @Operation(summary = "Find All Sensor for admin and viewer")
    @GetMapping
    public ResponseEntity<Page<Sensor>> show(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = adminService.show(pageable);
        return ResponseEntity.ok(sensors);
    }

    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "Search sensor by name or model")
    @GetMapping("/model")

    public ResponseEntity<Page<Sensor>> search(@RequestParam String keyword,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = adminService.search(keyword, pageable);
        return ResponseEntity.ok(sensors);
    }

    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "Update sensor")
    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(@PathVariable Long id, @Validated @RequestBody SensorDto sensorDto){
        Sensor sensor = adminService.update(sensorDto, id);
        return ResponseEntity.ok(sensor);
    }

    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "Delete sensor by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            adminService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found with id: " + id);
        }
    }
    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "get all Measurement Type")
    @GetMapping("/type")
    public ResponseEntity<List<MeasurementType>> getMeasurementType(){
        List<MeasurementType> measurementType = typeService.findAllMeasurementType();
        return ResponseEntity.ok(measurementType);
    }
}
