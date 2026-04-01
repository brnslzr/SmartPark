package com.SmartPark.smartpark_api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponse {

    private String licensePlate;
    private String type;
    private String ownerName;
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
