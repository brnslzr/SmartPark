package com.SmartPark.smartpark_api.controller;

import com.SmartPark.smartpark_api.dto.VehicleRequest;
import com.SmartPark.smartpark_api.dto.VehicleResponse;
import com.SmartPark.smartpark_api.mapper.VehicleMapper;
import com.SmartPark.smartpark_api.model.Vehicle;
import com.SmartPark.smartpark_api.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> registerVehicle(@RequestBody VehicleRequest request) {

        Vehicle savedVehicle = vehicleService.registerVehicle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(VehicleMapper.toResponse(savedVehicle));
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable String licensePlate) {

        Vehicle vehicle = vehicleService.getVehicleByLicensePlate(licensePlate);

        return ResponseEntity.ok(VehicleMapper.toResponse(vehicle));
    }

    @GetMapping("/exists/{licensePlate}")
    public ResponseEntity<Boolean> existsVehicle(@PathVariable String licensePlate) {

        boolean exists = vehicleService.existsByLicensePlate(licensePlate);

        return ResponseEntity.ok(exists);
    }
}