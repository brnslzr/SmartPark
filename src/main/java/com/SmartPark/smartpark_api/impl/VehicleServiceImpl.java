package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.dto.VehicleRequest;
import com.SmartPark.smartpark_api.model.Vehicle;
import com.SmartPark.smartpark_api.repository.VehicleRepository;
import com.SmartPark.smartpark_api.service.VehicleService;
import com.SmartPark.smartpark_api.util.VehicleType;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle registerVehicle(VehicleRequest request) {

        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new RuntimeException("Vehicle already exists");
        }

        Vehicle vehicle = new Vehicle(
                request.getLicensePlate(),
                VehicleType.valueOf(request.getType().toUpperCase()),
                request.getOwnerName()
        );

        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleByLicensePlate(String licensePlate) {

        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);

        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }

        return vehicle;
    }

    @Override
    public boolean existsByLicensePlate(String licensePlate) {
        return vehicleRepository.existsByLicensePlate(licensePlate);
    }
}