package com.SmartPark.smartpark_api.service;

import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.model.ParkingRecord;
import com.SmartPark.smartpark_api.repository.ParkingLotRepository;
import com.SmartPark.smartpark_api.repository.ParkingRecordRepository;
import com.SmartPark.smartpark_api.util.Status;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingSchedulerService {

    private final ParkingRecordRepository parkingRecordRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingSchedulerService(ParkingRecordRepository parkingRecordRepository,
                            ParkingLotRepository parkingLotRepository) {
        this.parkingRecordRepository = parkingRecordRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void removeExpiredVehicles() {

        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(15);

        List<ParkingRecord> expiredRecords =
                parkingRecordRepository.findByStatusAndEntryTimeBefore(
                        Status.PARKED, cutoff
                );

        for (ParkingRecord record : expiredRecords) {

            record.setExitTime(LocalDateTime.now());
            record.setStatus(Status.COMPLETED);

            ParkingLot lot = record.getParkingLot();
            lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);

            parkingLotRepository.save(lot);
            parkingRecordRepository.save(record);
        }

        System.out.println("Auto-removed vehicles: " + expiredRecords.size());
    }
}