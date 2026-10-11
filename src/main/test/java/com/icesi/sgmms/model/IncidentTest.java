
package com.icesi.sgmms.model;

import com.icesi.sgmms.exceptions.InvalidAssignmentException;
import com.icesi.sgmms.model.enums.IncidentStatus;
import com.icesi.sgmms.model.enums.IncidentType;
import com.icesi.sgmms.model.enums.Severity;
import com.icesi.sgmms.model.enums.VehicleStatus;
import com.icesi.sgmms.model.enums.VehicleType;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static com.icesi.sgmms.model.enums.Severity.LOW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncidentTest {

    private Incident createIncident() {
        return new Incident(
                "I1",
                IncidentType.ACCIDENT,
                Severity.HIGH,
                3.52,
                -76.30,
                LocalDateTime.of(2026, 10, 11, 10, 0)
        );
    }

    @Test
    void newIncidentStartsPending() {
        Incident incident = createIncident();

        assertEquals(IncidentStatus.PENDING, incident.getStatus());
    }

    @Test
    void assignCompatibleVehicle() {
        Incident incident = createIncident();
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.AMBULANCE, 3.53, -76.31
        );

        incident.assignVehicle(vehicle);

        assertSame(vehicle, incident.getAssignedVehicle());
        assertEquals(IncidentStatus.IN_PROGRESS, incident.getStatus());
    }

    @Test
    void rejectIncompatibleVehicle() {
        Incident incident = createIncident();
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.PATROL, 3.53, -76.31
        );

        assertThrows(
                InvalidAssignmentException.class,
                () -> incident.assignVehicle(vehicle)
        );
    }

    @Test
    void resolveAssignedIncident() {
        Incident incident = createIncident();
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.AMBULANCE, 3.53, -76.31
        );

        incident.assignVehicle(vehicle);
        incident.resolve();

        assertEquals(IncidentStatus.RESOLVED, incident.getStatus());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    void rejectResolvingPendingIncident() {
        Incident incident = createIncident();

        assertThrows(
                IllegalStateException.class,
                incident::resolve
        );
    }
    @Test
    void calculateScoreHighSeverity() {
        Incident incident = createIncident();

        assertEquals(100, incident.calculateScore(false));
    }

    @Test
    void calculateScoreMediumSeverity() {
        Incident incident = new Incident(
                "I2",
                IncidentType.ROBBERY,
                Severity.MEDIUM,
                3.52,
                -76.30,
                LocalDateTime.of(2026, 10, 11, 10, 0)
        );

        assertEquals(70, incident.calculateScore(false));
    }

    @Test
    void calculateScoreLowSeverity() {
        Incident incident = new Incident(
                "I3",
                IncidentType.FIRE,
                Severity.LOW,
                3.52,
                -76.30,
                LocalDateTime.of(2026, 10, 11, 10, 0)
        );

        assertEquals(40, incident.calculateScore(false));
    }

    @Test
    void calculateScoreWithTimelyResolution() {
        Incident incident = createIncident();

        Vehicle vehicle = new Vehicle(
                "V1",
                VehicleType.AMBULANCE,
                3.53,
                -76.31
        );

        incident.assignVehicle(vehicle);
        incident.resolve();

        assertEquals(120, incident.calculateScore(true));
    }

    @Test
    void calculateScoreWithoutResolutionBonus() {
        Incident incident = createIncident();

        assertEquals(100, incident.calculateScore(true));
    }

}
