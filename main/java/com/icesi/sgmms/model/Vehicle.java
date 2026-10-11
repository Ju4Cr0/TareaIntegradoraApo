
package com.icesi.sgmms.model;

import com.icesi.sgmms.model.enums.VehicleStatus;
import com.icesi.sgmms.model.enums.VehicleType;

public class Vehicle {

    private final String id;
    private final VehicleType type;
    private double latitude;
    private double longitude;
    private VehicleStatus status;

    public Vehicle(String id, VehicleType type,
                   double latitude, double longitude) {

        if (id == null || id.isBlank() || type == null) {
            throw new IllegalArgumentException(
                    "Invalid vehicle information"
            );
        }

        validatePosition(latitude, longitude);

        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = VehicleStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void updatePosition(double latitude, double longitude) {
        validatePosition(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void changeStatus(VehicleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Vehicle status cannot be null"
            );
        }

        this.status = newStatus;
    }

    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }

    private void validatePosition(double latitude, double longitude) {
        if (!Double.isFinite(latitude) ||
                !Double.isFinite(longitude) ||
                latitude < -90 || latitude > 90 ||
                longitude < -180 || longitude > 180) {

            throw new IllegalArgumentException(
                    "Invalid geographic coordinates"
            );
        }
    }
}
