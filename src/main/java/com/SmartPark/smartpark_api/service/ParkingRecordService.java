package com.SmartPark.smartpark_api.service;

import com.SmartPark.smartpark_api.dto.ParkingRecordResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingRecordService {
    public ParkingRecordResponse checkIn(String licensePlate, String lotId);
    ParkingRecordResponse checkOut(String licensePlate);
    List<ParkingRecordResponse> getActiveVehiclesByLot(String lotId);
    List<ParkingRecordResponse> getExpiredRecords(LocalDateTime parse);
}
