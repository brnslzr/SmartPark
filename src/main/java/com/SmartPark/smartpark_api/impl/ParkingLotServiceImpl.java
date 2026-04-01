package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.dto.ParkingLotRequest;
import com.SmartPark.smartpark_api.dto.ParkingLotResponse;
import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.repository.ParkingLotRepository;
import com.SmartPark.smartpark_api.service.ParkingLotService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotServiceImpl(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public ParkingLotResponse registerParkingLot(ParkingLotRequest request) {
        if (parkingLotRepository.existsByLotId(request.getLotId())) {
            throw new RuntimeException("Parking lot already exists");
        }

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setLotId(request.getLotId());
        parkingLot.setLocation(request.getLocation());
        parkingLot.setCapacity(request.getCapacity());
        parkingLot.setOccupiedSpaces(0); // always default here
        parkingLot.setCostPerMinute(request.getCostPerMinute());

        ParkingLot saved = parkingLotRepository.save(parkingLot);

        ParkingLotResponse response = new ParkingLotResponse();
        response.setLotId(saved.getLotId());
        response.setLocation(saved.getLocation());
        response.setCapacity(saved.getCapacity());
        response.setOccupiedSpaces(saved.getOccupiedSpaces());

        return response;
    }
    @Override
    public Map<String, Object> getParkingLotStatus(String lotId) {

        ParkingLot lot = parkingLotRepository.findByLotId(lotId);

        if (lot == null) {
            throw new RuntimeException("Parking lot not found");
        }

        int capacity = lot.getCapacity();
        int occupied = lot.getOccupiedSpaces();
        int available = capacity - occupied;

        Map<String, Object> response = new HashMap<>();
        response.put("capacity", capacity);
        response.put("occupied", occupied);
        response.put("available", available);

        return response;
    }
    @Override
    public boolean isParkingLotFull(String lotId) {

        ParkingLot lot = parkingLotRepository.findByLotId(lotId);

        if (lot == null) {
            throw new RuntimeException("Parking lot not found");
        }
        return lot.getOccupiedSpaces() >= lot.getCapacity();
    }
}
