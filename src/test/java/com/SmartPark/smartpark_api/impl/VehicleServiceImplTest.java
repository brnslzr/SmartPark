package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.dto.VehicleRequest;
import com.SmartPark.smartpark_api.model.Vehicle;
import com.SmartPark.smartpark_api.repository.VehicleRepository;
import com.SmartPark.smartpark_api.util.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void registerVehicle_success() {
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("ABC-123");
        request.setType("CAR");
        request.setOwnerName("Juan");

        when(vehicleRepository.existsByLicensePlate("ABC-123")).thenReturn(false);

        Vehicle saved = new Vehicle("ABC-123", VehicleType.CAR, "Juan");
        when(vehicleRepository.save(any())).thenReturn(saved);

        Vehicle result = vehicleService.registerVehicle(request);

        assertEquals("ABC-123", result.getLicensePlate());
    }

    @Test
    void registerVehicle_alreadyExists() {
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("ABC-123");

        when(vehicleRepository.existsByLicensePlate("ABC-123")).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> vehicleService.registerVehicle(request));
    }

    @Test
    void getVehicle_success() {
        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");

        when(vehicleRepository.findByLicensePlate("ABC-123"))
                .thenReturn(vehicle);

        Vehicle result = vehicleService.getVehicleByLicensePlate("ABC-123");

        assertNotNull(result);
    }
}
