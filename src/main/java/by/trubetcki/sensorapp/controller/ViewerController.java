package by.trubetcki.sensorapp.controller;

import by.trubetcki.sensorapp.models.Sensor;
import by.trubetcki.sensorapp.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/viewer")
public class ViewerController {

    private final AdminService adminService;

    @Operation(summary = "Find All Sensor for Viewer")
    @GetMapping
    public ResponseEntity<Page<Sensor>> showAllSensor(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Sensor> sensors = adminService.showAllSensor(pageable);

        return ResponseEntity.ok(sensors);
    }
}
