package com.SmartPark.smartpark_api.repository;

import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.model.ParkingRecord;
import com.SmartPark.smartpark_api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SmartPark.smartpark_api.util.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {

    ParkingRecord findByVehicleAndStatus(Vehicle vehicle, Status status);

    List<ParkingRecord> findByParkingLotAndStatus(ParkingLot parkingLot, Status status);

    List<ParkingRecord> findByStatusAndEntryTimeBefore(Status status, LocalDateTime time);
}