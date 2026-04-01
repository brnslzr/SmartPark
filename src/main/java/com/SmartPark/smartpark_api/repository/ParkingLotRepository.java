package com.SmartPark.smartpark_api.repository;

import com.SmartPark.smartpark_api.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {
    public ParkingLot findByLotId(String lotId);
    public Boolean existsByLotId(String lotId);
}
