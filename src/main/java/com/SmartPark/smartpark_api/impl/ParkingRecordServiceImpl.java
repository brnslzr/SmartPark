package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.dto.ParkingRecordResponse;
import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.model.ParkingRecord;
import com.SmartPark.smartpark_api.model.Vehicle;
import com.SmartPark.smartpark_api.repository.ParkingLotRepository;
import com.SmartPark.smartpark_api.repository.ParkingRecordRepository;
import com.SmartPark.smartpark_api.repository.VehicleRepository;
import com.SmartPark.smartpark_api.service.ParkingRecordService;
import org.springframework.stereotype.Service;
import com.SmartPark.smartpark_api.util.Status;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingRecordServiceImpl implements ParkingRecordService {

    private final ParkingRecordRepository parkingRecordRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingRecordServiceImpl(ParkingRecordRepository parkingRecordRepository,
                                    VehicleRepository vehicleRepository,
                                    ParkingLotRepository parkingLotRepository) {
        this.parkingRecordRepository = parkingRecordRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public ParkingRecordResponse checkIn(String licensePlate, String lotId) {

        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }

        ParkingLot lot = parkingLotRepository.findByLotId(lotId);
        if (lot == null) {
            throw new RuntimeException("Parking lot not found");
        }

        if (lot.getOccupiedSpaces() >= lot.getCapacity()) {
            throw new RuntimeException("Parking lot is full");
        }

        // Check if already active
        ParkingRecord existing = parkingRecordRepository
                .findByVehicleAndStatus(vehicle, Status.PARKED);

        if (existing != null) {
            throw new RuntimeException("Vehicle already checked in");
        }

        ParkingRecord record = new ParkingRecord();
        record.setVehicle(vehicle);
        record.setParkingLot(lot);
        record.setEntryTime(LocalDateTime.now());
        record.setStatus(Status.PARKED);

        lot.setOccupiedSpaces(lot.getOccupiedSpaces() + 1);

        parkingLotRepository.save(lot);
        ParkingRecord saved = parkingRecordRepository.save(record);

        return mapToResponse(saved);
    }

    @Override
    public ParkingRecordResponse checkOut(String licensePlate) {

        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }

        ParkingRecord record = parkingRecordRepository
                .findByVehicleAndStatus(vehicle, Status.PARKED);

        if (record == null) {
            throw new RuntimeException("No active record found");
        }

        record.setExitTime(LocalDateTime.now());
        record.setStatus(Status.COMPLETED);

        ParkingLot lot = record.getParkingLot();

        // 🔥 CALCULATE COST
        long minutes = Duration.between(
                record.getEntryTime(),
                record.getExitTime()
        ).toMinutes();

        BigDecimal totalCost = BigDecimal.valueOf(minutes)
                .multiply(lot.getCostPerMinute());
        record.setTotalCost(totalCost);

        // update occupied spaces
        lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);

        parkingLotRepository.save(lot);
        ParkingRecord updated = parkingRecordRepository.save(record);

        return mapToResponse(updated);
    }

    @Override
    public List<ParkingRecordResponse> getActiveVehiclesByLot(String lotId) {

        ParkingLot lot = parkingLotRepository.findByLotId(lotId);
        if (lot == null) {
            throw new RuntimeException("Parking lot not found");
        }

        List<ParkingRecord> records =
                parkingRecordRepository.findByParkingLotAndStatus(lot, Status.PARKED);

        return records.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ParkingRecordResponse> getExpiredRecords(LocalDateTime time) {

        List<ParkingRecord> records =
                parkingRecordRepository.findByStatusAndEntryTimeBefore(Status.PARKED, time);

        return records.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔁 Mapper
    private ParkingRecordResponse mapToResponse(ParkingRecord record) {
        ParkingRecordResponse res = new ParkingRecordResponse();
        res.setLicensePlate(record.getVehicle().getLicensePlate());
        res.setLotId(record.getParkingLot().getLotId());
        res.setEntryTime(record.getEntryTime());
        res.setExitTime(record.getExitTime());
        res.setStatus(record.getStatus().name());
        return res;
    }
}
