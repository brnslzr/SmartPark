package com.SmartPark.smartpark_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Invalid license plate")
    private String licensePlate;

    private String type;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name must contain letters only")
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
