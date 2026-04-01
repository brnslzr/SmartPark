package com.SmartPark.smartpark_api.controller;

import com.SmartPark.smartpark_api.dto.ParkingLotRequest;
import com.SmartPark.smartpark_api.dto.ParkingLotResponse;
import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.service.ParkingLotService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public ParkingLotResponse registerParkingLot(@RequestBody ParkingLotRequest request) {
        return parkingLotService.registerParkingLot(request);
    }

    @GetMapping("/{lotId}/status")
    public Map<String, Object> getParkingLotStatus(@PathVariable String lotId) {
        return parkingLotService.getParkingLotStatus(lotId);
    }

    // ✅ 3. Check if Full
    @GetMapping("/{lotId}/is-full")
    public boolean isParkingLotFull(@PathVariable String lotId) {
        return parkingLotService.isParkingLotFull(lotId);
    }
}