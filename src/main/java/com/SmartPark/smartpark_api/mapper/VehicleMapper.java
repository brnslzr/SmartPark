package com.SmartPark.smartpark_api.mapper;

import com.SmartPark.smartpark_api.dto.VehicleResponse;
import com.SmartPark.smartpark_api.model.Vehicle;

public class VehicleMapper {

    public static VehicleResponse toResponse(Vehicle vehicle) {

        VehicleResponse response = new VehicleResponse();

        response.setLicensePlate(vehicle.getLicensePlate());
        response.setType(vehicle.getVehicleType().name());
        response.setOwnerName(vehicle.getOwnerName());

        return response;
    }
}
