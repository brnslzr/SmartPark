package Repository;

import model.ParkingLot;
import model.ParkingRecord;
import model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import util.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {

    ParkingRecord findByVehicleAndStatus(Vehicle vehicle, Status status);

    List<ParkingRecord> findByParkingLotAndStatus(ParkingLot parkingLot, Status status);

    List<ParkingRecord> findByStatusAndCheckInTimeBefore(Status status, LocalDateTime time);
}