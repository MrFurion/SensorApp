package by.trubetcki.sensorapp.controller;

import by.trubetcki.sensorapp.dto.SensorDto;
import by.trubetcki.sensorapp.exception.SensorNotFoundException;
import by.trubetcki.sensorapp.models.MeasurementType;
import by.trubetcki.sensorapp.models.Sensor;
import by.trubetcki.sensorapp.services.AdminService;
import by.trubetcki.sensorapp.services.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final TypeService typeService;

    @Operation(summary = "Save new sensor")
    @PostMapping
    public ResponseEntity<Sensor> createSensor(@Validated @RequestBody SensorDto sensorDto){
       Sensor sensor = adminService.saveSensor(sensorDto);
        return ResponseEntity.ok(sensor);
    }

    @Operation(summary = "Find All Sensor for admin")
    @GetMapping
    public ResponseEntity<Page<Sensor>> showAllSensor(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = adminService.showAllSensor(pageable);
        return ResponseEntity.ok(sensors);
    }

    @Operation(summary = "Search sensor by name or model")
    @GetMapping("/search")
    public ResponseEntity<Page<Sensor>> searchSensor(@RequestParam String keyword,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = adminService.searchSensor(keyword, pageable);
        return ResponseEntity.ok(sensors);
    }

    @Operation(summary = "Update sensor")
    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @Validated @RequestBody SensorDto sensorDto){
        Sensor sensor = adminService.updateSensor(sensorDto, id);
        return ResponseEntity.ok(sensor);
    }

    @Operation(summary = "Delete sensor by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSensor(@PathVariable Long id){
        try {
            adminService.deleteSensor(id);
            return ResponseEntity.noContent().build();
        } catch (SensorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found with id: " + id);
        }
    }

    @Operation(summary = "get all Measurement Type")
    @GetMapping("/type")
    public ResponseEntity<List<MeasurementType>> getMeasurementType(){
        List<MeasurementType> measurementType = typeService.findAllMeasurementType();
        return ResponseEntity.ok(measurementType);
    }
}
