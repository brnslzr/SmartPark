package repository;

import model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    public Vehicle findByLicensePlate(String licensePlate);
    public boolean existsByLicensePlate(String licensePlate);
}
