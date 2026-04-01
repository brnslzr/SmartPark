package com.SmartPark.smartpark_api.model;

import com.SmartPark.smartpark_api.util.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

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
    @NotBlank
    private String ownerName;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate must contain only letters, numbers, and dashes")
    private String licensePlate;
}
