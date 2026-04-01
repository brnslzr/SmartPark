package com.SmartPark.smartpark_api.controller;

import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.service.ParkingLotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public ParkingLot registerParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.registerParkingLot(parkingLot);
    }
}