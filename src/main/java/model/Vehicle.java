package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import util.VehicleType;

@Entity
@Getter
@Setter
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private String ownerName;
}
