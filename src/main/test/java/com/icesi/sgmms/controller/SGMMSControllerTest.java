package com.icesi.sgmms.controller;

import com.icesi.sgmms.exceptions.InvalidAssignmentException;
import com.icesi.sgmms.model.Incident;
import com.icesi.sgmms.model.Vehicle;
import com.icesi.sgmms.model.enums.IncidentStatus;
import com.icesi.sgmms.model.enums.IncidentType;
import com.icesi.sgmms.model.enums.Severity;
import com.icesi.sgmms.model.enums.VehicleStatus;
import com.icesi.sgmms.model.enums.VehicleType;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SGMMSControllerTest {

    private Incident createIncident(IncidentType type) {
        return new Incident(
                "INC-001",
                type,
                Severity.HIGH,
                3.52,
                -76.30,
                LocalDateTime.of(2026, 10, 11, 10, 0)
        );
    }

    private Vehicle createVehicle(VehicleType type) {
        return new Vehicle(
                "VEH-001",
                type,
                3.53,
                -76.31
        );
    }

    @Test
    void assignAmbulanceToAccident() {
        SGMMSController manager = new SGMMSController();
        Incident incident = createIncident(IncidentType.ACCIDENT);
        Vehicle vehicle = createVehicle(VehicleType.AMBULANCE);

        manager.registerIncident(incident);
        manager.registerVehicle(vehicle);

        manager.assignVehicleToIncident("INC-001", "VEH-001");

        assertSame(vehicle, incident.getAssignedVehicle());
        assertEquals(IncidentStatus.IN_PROGRESS, incident.getStatus());
        assertEquals(VehicleStatus.EN_ROUTE, vehicle.getStatus());
    }

    @Test
    void assignPatrolToRobbery() {
        SGMMSController manager = new SGMMSController();
        Incident incident = createIncident(IncidentType.ROBBERY);
        Vehicle vehicle = createVehicle(VehicleType.PATROL);

        manager.registerIncident(incident);
        manager.registerVehicle(vehicle);

        manager.assignVehicleToIncident("INC-001", "VEH-001");

        assertSame(vehicle, incident.getAssignedVehicle());
    }

    @Test
    void assignFiretruckToFire() {
        SGMMSController manager = new SGMMSController();
        Incident incident = createIncident(IncidentType.FIRE);
        Vehicle vehicle = createVehicle(VehicleType.FIRETRUCK);

        manager.registerIncident(incident);
        manager.registerVehicle(vehicle);

        manager.assignVehicleToIncident("INC-001", "VEH-001");

        assertSame(vehicle, incident.getAssignedVehicle());
    }

    @Test
    void rejectIncompatibleVehicle() {
        SGMMSController manager = new SGMMSController();
        Incident incident = createIncident(IncidentType.FIRE);
        Vehicle vehicle = createVehicle(VehicleType.AMBULANCE);

        manager.registerIncident(incident);
        manager.registerVehicle(vehicle);

        assertThrows(
                InvalidAssignmentException.class,
                () -> manager.assignVehicleToIncident(
                        "INC-001", "VEH-001"
                )
        );

        assertEquals(IncidentStatus.PENDING, incident.getStatus());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }

    @Test
    void rejectUnavailableVehicle() {
        SGMMSController manager = new SGMMSController();
        Incident incident = createIncident(IncidentType.ACCIDENT);
        Vehicle vehicle = createVehicle(VehicleType.AMBULANCE);

        vehicle.changeStatus(VehicleStatus.OUT_OF_SERVICE);

        manager.registerIncident(incident);
        manager.registerVehicle(vehicle);

        assertThrows(
                InvalidAssignmentException.class,
                () -> manager.assignVehicleToIncident(
                        "INC-001", "VEH-001"
                )
        );
    }

    @Test
    void rejectNonExistingIncident() {
        SGMMSController manager = new SGMMSController();
        Vehicle vehicle = createVehicle(VehicleType.AMBULANCE);

        manager.registerVehicle(vehicle);

        assertThrows(
                InvalidAssignmentException.class,
                () -> manager.assignVehicleToIncident(
                        "UNKNOWN", "VEH-001"
                )
        );
    }

    @Test
    void rejectNonExistingVehicle() {
        SGMMSController manager = new SGMMSController();
        Incident incident = createIncident(IncidentType.ACCIDENT);

        manager.registerIncident(incident);

        assertThrows(
                InvalidAssignmentException.class,
                () -> manager.assignVehicleToIncident(
                        "INC-001", "UNKNOWN"
                )
        );
    }
    @Test
    void registerFirstIncident() {
        SGMMSController controller = new SGMMSController();

        controller.registerIncident(createIncident(IncidentType.ACCIDENT));

        assertEquals(1, controller.getIncidentCount());
    }

    @Test
    void registerMultipleIncidents() {
        SGMMSController controller = new SGMMSController();

        controller.registerIncident(createIncident(IncidentType.ACCIDENT));
        controller.registerIncident(new Incident(
                "INC-002", IncidentType.FIRE, Severity.HIGH,
                3.52, -76.30, LocalDateTime.of(2026, 10, 11, 10, 0)
        ));

        assertEquals(2, controller.getIncidentCount());
    }

    @Test
    void rejectNullIncidentRegistration() {
        SGMMSController controller = new SGMMSController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.registerIncident(null));
    }

    @Test
    void rejectDuplicateIncidentId() {
        SGMMSController controller = new SGMMSController();

        controller.registerIncident(createIncident(IncidentType.ACCIDENT));

        assertThrows(IllegalArgumentException.class,
                () -> controller.registerIncident(
                        createIncident(IncidentType.FIRE)));
    }

    @Test
    void preserveIncidentAfterRegistration() {
        SGMMSController controller = new SGMMSController();
        Incident incident = createIncident(IncidentType.ACCIDENT);

        controller.registerIncident(incident);

        assertSame(incident, controller.findIncidentById("INC-001"));
    }
    @Test
    void registerFirstVehicle() {
        SGMMSController controller = new SGMMSController();

        controller.registerVehicle(createVehicle(VehicleType.AMBULANCE));

        assertEquals(1, controller.getVehicleCount());
    }

    @Test
    void registerMultipleVehicles() {
        SGMMSController controller = new SGMMSController();

        controller.registerVehicle(createVehicle(VehicleType.AMBULANCE));
        controller.registerVehicle(new Vehicle(
                "VEH-002", VehicleType.PATROL, 3.52, -76.30
        ));

        assertEquals(2, controller.getVehicleCount());
    }

    @Test
    void rejectNullVehicleRegistration() {
        SGMMSController controller = new SGMMSController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.registerVehicle(null));
    }

    @Test
    void rejectDuplicateVehicleId() {
        SGMMSController controller = new SGMMSController();

        controller.registerVehicle(createVehicle(VehicleType.AMBULANCE));

        assertThrows(IllegalArgumentException.class,
                () -> controller.registerVehicle(
                        createVehicle(VehicleType.PATROL)));
    }

    @Test
    void preserveVehicleAfterRegistration() {
        SGMMSController controller = new SGMMSController();
        Vehicle vehicle = createVehicle(VehicleType.FIRETRUCK);

        controller.registerVehicle(vehicle);

        assertSame(vehicle, controller.findVehicleById("VEH-001"));
    }
    @Test
    void findExistingIncident() {
        SGMMSController controller = new SGMMSController();
        Incident incident = createIncident(IncidentType.ACCIDENT);
        controller.registerIncident(incident);

        assertSame(incident, controller.findIncidentById("INC-001"));
    }

    @Test
    void findNonExistingIncident() {
        SGMMSController controller = new SGMMSController();

        assertNull(controller.findIncidentById("UNKNOWN"));
    }

    @Test
    void findIncidentAmongMultiple() {
        SGMMSController controller = new SGMMSController();
        Incident second = new Incident(
                "INC-002", IncidentType.FIRE, Severity.LOW,
                3.52, -76.30, LocalDateTime.of(2026, 10, 11, 10, 0)
        );

        controller.registerIncident(createIncident(IncidentType.ACCIDENT));
        controller.registerIncident(second);

        assertSame(second, controller.findIncidentById("INC-002"));
    }

    @Test
    void rejectNullIncidentSearchId() {
        SGMMSController controller = new SGMMSController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.findIncidentById(null));
    }

    @Test
    void rejectBlankIncidentSearchId() {
        SGMMSController controller = new SGMMSController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.findIncidentById(" "));
    }
    @Test
    void findExistingVehicle() {
        SGMMSController controller = new SGMMSController();
        Vehicle vehicle = createVehicle(VehicleType.AMBULANCE);
        controller.registerVehicle(vehicle);

        assertSame(vehicle, controller.findVehicleById("VEH-001"));
    }

    @Test
    void findNonExistingVehicle() {
        SGMMSController controller = new SGMMSController();

        assertNull(controller.findVehicleById("UNKNOWN"));
    }

    @Test
    void findVehicleAmongMultiple() {
        SGMMSController controller = new SGMMSController();
        Vehicle second = new Vehicle(
                "VEH-002", VehicleType.PATROL, 3.52, -76.30
        );

        controller.registerVehicle(createVehicle(VehicleType.AMBULANCE));
        controller.registerVehicle(second);

        assertSame(second, controller.findVehicleById("VEH-002"));
    }

    @Test
    void rejectNullVehicleSearchId() {
        SGMMSController controller = new SGMMSController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.findVehicleById(null));
    }

    @Test
    void rejectBlankVehicleSearchId() {
        SGMMSController controller = new SGMMSController();

        assertThrows(IllegalArgumentException.class,
                () -> controller.findVehicleById(" "));
    }
}