package com.SmartPark.smartpark_api.service;

import com.SmartPark.smartpark_api.dto.VehicleRequest;
import com.SmartPark.smartpark_api.model.Vehicle;

public interface VehicleService {

        Vehicle registerVehicle(VehicleRequest vehicle);

        Vehicle getVehicleByLicensePlate(String licensePlate);

        boolean existsByLicensePlate(String licensePlate);
}
