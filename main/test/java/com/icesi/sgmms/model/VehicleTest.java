
package com.icesi.sgmms.model;

import com.icesi.sgmms.model.enums.VehicleStatus;
import com.icesi.sgmms.model.enums.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleTest {

    @Test
    void newVehicleIsAvailable() {
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.AMBULANCE, 3.52, -76.30
        );

        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
        assertTrue(vehicle.isAvailable());
    }

    @Test
    void vehicleBecomesUnavailableWhenEnRoute() {
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.PATROL, 3.52, -76.30
        );

        vehicle.changeStatus(VehicleStatus.EN_ROUTE);

        assertFalse(vehicle.isAvailable());
    }

    @Test
    void updateVehiclePosition() {
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.FIRETRUCK, 3.52, -76.30
        );

        vehicle.updatePosition(3.53, -76.31);

        assertEquals(3.53, vehicle.getLatitude());
        assertEquals(-76.31, vehicle.getLongitude());
    }

    @Test
    void rejectInvalidCoordinates() {
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.AMBULANCE, 3.52, -76.30
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.updatePosition(100, -76.30)
        );
    }

    @Test
    void rejectNullVehicleStatus() {
        Vehicle vehicle = new Vehicle(
                "V1", VehicleType.PATROL, 3.52, -76.30
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> vehicle.changeStatus(null)
        );
    }
}
