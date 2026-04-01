package model;

import jakarta.persistence.*;
import util.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ParkingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private ParkingLot parkingLot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private BigDecimal totalCost;
    @Enumerated(EnumType.STRING)
    private Status status;
}