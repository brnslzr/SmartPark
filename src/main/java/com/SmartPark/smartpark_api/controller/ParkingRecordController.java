package com.SmartPark.smartpark_api.controller;

import com.SmartPark.smartpark_api.dto.ParkingRecordResponse;
import com.SmartPark.smartpark_api.service.ParkingRecordService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parking-records")
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;

    public ParkingRecordController(ParkingRecordService parkingRecordService) {
        this.parkingRecordService = parkingRecordService;
    }

    @PostMapping("/check-in")
    public ParkingRecordResponse checkIn(@RequestParam String licensePlate,
                                         @RequestParam String lotId) {
        return parkingRecordService.checkIn(licensePlate, lotId);
    }

    @PostMapping("/check-out")
    public ParkingRecordResponse checkOut(@RequestParam String licensePlate) {
        return parkingRecordService.checkOut(licensePlate);
    }

    @GetMapping("/active/{lotId}")
    public List<ParkingRecordResponse> getActive(@PathVariable String lotId) {
        return parkingRecordService.getActiveVehiclesByLot(lotId);
    }

    @GetMapping("/expired")
    public List<ParkingRecordResponse> getExpired(@RequestParam String time) {
        return parkingRecordService.getExpiredRecords(LocalDateTime.parse(time));
    }
}
