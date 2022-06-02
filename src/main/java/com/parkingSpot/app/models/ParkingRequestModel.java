package com.parkingSpot.app.models;

import lombok.Data;

@Data
public class ParkingRequestModel {
    private long spotId;
    private String departureDate;
}
