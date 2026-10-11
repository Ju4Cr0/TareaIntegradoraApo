
package com.icesi.sgmms.model;

import com.icesi.sgmms.exceptions.InvalidAssignmentException;
import com.icesi.sgmms.model.enums.VehicleStatus;
import com.icesi.sgmms.model.enums.VehicleType;
import com.icesi.sgmms.model.enums.IncidentStatus;
import com.icesi.sgmms.model.enums.IncidentType;
import com.icesi.sgmms.model.enums.Severity;

import java.time.LocalDateTime;

public class Incident {

    private final String id;
    private final IncidentType type;
    private final Severity severity;
    private final double latitude;
    private final double longitude;
    private final LocalDateTime timestamp;

    private IncidentStatus status;
    private Vehicle assignedVehicle;

    public Incident(String id, IncidentType type,
                    Severity severity, double latitude,
                    double longitude, LocalDateTime timestamp) {

        if (id == null || id.isBlank() ||
                type == null || severity == null ||
                timestamp == null) {
            throw new IllegalArgumentException(
                    "Invalid incident information"
            );
        }

        if (!Double.isFinite(latitude) ||
                !Double.isFinite(longitude) ||
                latitude < -90 || latitude > 90 ||
                longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(
                    "Invalid geographic coordinates"
            );
        }

        this.id = id;
        this.type = type;
        this.severity = severity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.status = IncidentStatus.PENDING;
        this.assignedVehicle = null;
    }

    public String getId() {
        return id;
    }

    public IncidentType getType() {
        return type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void assignVehicle(Vehicle vehicle) {

        if (vehicle == null) {
            throw new InvalidAssignmentException(
                    "Vehicle cannot be null"
            );
        }

        if (status != IncidentStatus.PENDING ||
                assignedVehicle != null) {
            throw new InvalidAssignmentException(
                    "Incident cannot be assigned"
            );
        }

        if (!vehicle.isAvailable()) {
            throw new InvalidAssignmentException(
                    "Vehicle is not available"
            );
        }

        boolean compatible =
                (type == IncidentType.ACCIDENT &&
                        vehicle.getType() == VehicleType.AMBULANCE) ||

                        (type == IncidentType.ROBBERY &&
                                vehicle.getType() == VehicleType.PATROL) ||

                        (type == IncidentType.FIRE &&
                                vehicle.getType() == VehicleType.FIRETRUCK);

        if (!compatible) {
            throw new InvalidAssignmentException(
                    "Vehicle type is incompatible with incident"
            );
        }

        assignedVehicle = vehicle;
        status = IncidentStatus.IN_PROGRESS;
        vehicle.changeStatus(VehicleStatus.EN_ROUTE);
    }

    public void resolve() {
        if (status != IncidentStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Incident is not in progress"
            );
        }

        status = IncidentStatus.RESOLVED;

        if (assignedVehicle != null) {
            assignedVehicle.changeStatus(
                    com.icesi.sgmms.model.enums.VehicleStatus.AVAILABLE
            );
        }
    }
    /**
     * Calculates the incident score based on severity
     * and timely resolution.
     *
     * Time complexity: O(1).
     * Auxiliary space complexity: O(1).
     *
     * @param resolvedOnTime whether the incident
     *                       was resolved on time
     * @return calculated incident score
     */
    public int calculateScore(boolean resolvedOnTime) {

        int score;

        switch (severity) {
            case HIGH:
                score = 100;
                break;

            case MEDIUM:
                score = 70;
                break;

            case LOW:
                score = 40;
                break;

            default:
                throw new IllegalStateException(
                        "Unknown incident severity"
                );
        }

        if (resolvedOnTime && status == IncidentStatus.RESOLVED) {
            score += 20;
        }

        return score;
    }
}
