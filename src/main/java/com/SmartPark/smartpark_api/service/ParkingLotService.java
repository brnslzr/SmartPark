package com.SmartPark.smartpark_api.service;

import com.SmartPark.smartpark_api.dto.ParkingLotRequest;
import com.SmartPark.smartpark_api.dto.ParkingLotResponse;
import com.SmartPark.smartpark_api.model.ParkingLot;
import java.util.Map;

public interface ParkingLotService {
    ParkingLotResponse registerParkingLot(ParkingLotRequest request);
    Map<String, Object> getParkingLotStatus(String lotId);
    boolean isParkingLotFull(String lotId);
}
