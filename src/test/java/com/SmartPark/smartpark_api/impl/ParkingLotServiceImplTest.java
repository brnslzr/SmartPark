package com.SmartPark.smartpark_api.impl;

import com.SmartPark.smartpark_api.dto.ParkingLotRequest;
import com.SmartPark.smartpark_api.dto.ParkingLotResponse;
import com.SmartPark.smartpark_api.model.ParkingLot;
import com.SmartPark.smartpark_api.repository.ParkingLotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    @Test
    void registerParkingLot_success() {
        ParkingLotRequest request = new ParkingLotRequest();
        request.setLotId("LOT-1");
        request.setCapacity(10);

        when(parkingLotRepository.existsByLotId("LOT-1")).thenReturn(false);

        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(0);

        when(parkingLotRepository.save(any())).thenReturn(lot);

        ParkingLotResponse response =
                parkingLotService.registerParkingLot(request);

        assertEquals("LOT-1", response.getLotId());
    }

    @Test
    void getParkingLotStatus_success() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("LOT-1");
        lot.setCapacity(10);
        lot.setOccupiedSpaces(3);

        when(parkingLotRepository.findByLotId("LOT-1")).thenReturn(lot);

        Map<String, Object> result =
                parkingLotService.getParkingLotStatus("LOT-1");

        assertEquals(7, result.get("available"));
    }

    @Test
    void isParkingLotFull_true() {
        ParkingLot lot = new ParkingLot();
        lot.setCapacity(10);
        lot.setOccupiedSpaces(10);

        when(parkingLotRepository.findByLotId("LOT-1")).thenReturn(lot);

        assertTrue(parkingLotService.isParkingLotFull("LOT-1"));
    }
}