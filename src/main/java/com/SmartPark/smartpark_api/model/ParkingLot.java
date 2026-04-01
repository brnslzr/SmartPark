package com.SmartPark.smartpark_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "parkinglot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lotId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    private Integer occupiedSpaces;

    @Column(nullable = false)
    private BigDecimal costPerMinute;
}
