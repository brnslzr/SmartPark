package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.repository.ParkingLotRepository;
import com.SmartPark.smartpark_api.service.ParkingLotService;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotServiceImpl(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public ParkingLot registerParkingLot(ParkingLot parkingLot) {

        // Check if lot already exists
        if (parkingLotRepository.existsByLotId(parkingLot.getLotId())) {
            throw new RuntimeException("Parking lot already exists");
        }

        // Initialize occupied spaces to 0 if null
        if (parkingLot.getOccupiedSpaces() == null) {
            parkingLot.setOccupiedSpaces(0);
        }

        return parkingLotRepository.save(parkingLot);
    }
}
