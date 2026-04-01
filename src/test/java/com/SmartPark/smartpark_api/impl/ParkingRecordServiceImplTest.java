package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.dto.ParkingRecordResponse;
import com.SmartPark.smartpark_api.impl.ParkingRecordServiceImpl;
import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.model.ParkingRecord;
import com.SmartPark.smartpark_api.model.Vehicle;
import com.SmartPark.smartpark_api.repository.ParkingLotRepository;
import com.SmartPark.smartpark_api.repository.ParkingRecordRepository;
import com.SmartPark.smartpark_api.repository.VehicleRepository;
import com.SmartPark.smartpark_api.util.Status;
import com.SmartPark.smartpark_api.util.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingRecordServiceImplTest {

    @Mock
    private ParkingRecordRepository parkingRecordRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingRecordServiceImpl service;

    // =========================
    // CHECK-IN SUCCESS
    // =========================
    @Test
    void checkIn_success() {

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(0);

        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(vehicle);
        when(parkingLotRepository.findByLotId("LOT-1")).thenReturn(lot);
        when(parkingRecordRepository.findByVehicleAndStatus(vehicle, Status.PARKED))
                .thenReturn(null);

        when(parkingRecordRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ParkingRecordResponse res = service.checkIn("ABC-123", "LOT-1");

        assertEquals("ABC-123", res.getLicensePlate());
        assertEquals("PARKED", res.getStatus());
    }

    // =========================
    // CHECK-IN ALREADY PARKED
    // =========================
    @Test
    void checkIn_vehicleAlreadyParked() {

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(1);

        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(vehicle);
        when(parkingLotRepository.findByLotId("LOT-1")).thenReturn(lot);
        when(parkingRecordRepository.findByVehicleAndStatus(vehicle, Status.PARKED))
                .thenReturn(new ParkingRecord());

        assertThrows(RuntimeException.class,
                () -> service.checkIn("ABC-123", "LOT-1"));
    }

    // =========================
    // CHECK-OUT SUCCESS
    // =========================
    @Test
    void checkOut_success() {

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");
        lot.setCostPerMinute(BigDecimal.valueOf(2));
        lot.setOccupiedSpaces(1); // 🔥 IMPORTANT FIX

        ParkingRecord record = new ParkingRecord();
        record.setVehicle(vehicle);
        record.setParkingLot(lot);
        record.setEntryTime(LocalDateTime.now().minusMinutes(5));
        record.setStatus(Status.PARKED);

        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(vehicle);
        when(parkingRecordRepository.findByVehicleAndStatus(vehicle, Status.PARKED))
                .thenReturn(record);

        when(parkingRecordRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ParkingRecordResponse res = service.checkOut("ABC-123");

        assertEquals("ABC-123", res.getLicensePlate());
        assertEquals("COMPLETED", res.getStatus());
    }

    // =========================
    // CHECK-OUT NO ACTIVE RECORD
    // =========================
    @Test
    void checkOut_noActiveRecord() {

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");

        when(vehicleRepository.findByLicensePlate("ABC-123")).thenReturn(vehicle);
        when(parkingRecordRepository.findByVehicleAndStatus(vehicle, Status.PARKED))
                .thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> service.checkOut("ABC-123"));
    }

    // =========================
    // GET ACTIVE VEHICLES
    // =========================
    @Test
    void getActiveVehiclesByLot_success() {

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");

        ParkingRecord record = new ParkingRecord();
        record.setParkingLot(lot);
        record.setStatus(Status.PARKED);

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");
        record.setVehicle(vehicle);

        when(parkingLotRepository.findByLotId("LOT-1")).thenReturn(lot);
        when(parkingRecordRepository.findByParkingLotAndStatus(lot, Status.PARKED))
                .thenReturn(List.of(record));

        List<ParkingRecordResponse> result =
                service.getActiveVehiclesByLot("LOT-1");

        assertEquals(1, result.size());
    }

    // =========================
    // GET EXPIRED RECORDS
    // =========================
    @Test
    void getExpiredRecords_success() {

        ParkingRecord record = new ParkingRecord();
        record.setStatus(Status.PARKED);

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "Juan");
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");

        record.setVehicle(vehicle);
        record.setParkingLot(lot);

        when(parkingRecordRepository.findByStatusAndEntryTimeBefore(
                eq(Status.PARKED), any(LocalDateTime.class)))
                .thenReturn(List.of(record));

        List<ParkingRecordResponse> result =
                service.getExpiredRecords(LocalDateTime.now());

        assertEquals(1, result.size());
    }
}