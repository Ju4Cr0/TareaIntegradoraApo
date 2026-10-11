package com.icesi.sgmms.controller;

import com.icesi.sgmms.exceptions.InvalidAssignmentException;
import com.icesi.sgmms.model.Incident;
import com.icesi.sgmms.model.Vehicle;
import com.icesi.sgmms.structures.LinkedList;

public class SGMMSController {

    private final LinkedList<Incident> incidents;
    private final LinkedList<Vehicle> vehicles;

    public SGMMSController() {
        incidents = new LinkedList<>();
        vehicles = new LinkedList<>();
    }

    public void registerIncident(Incident incident) {
        if (incident == null) {
            throw new IllegalArgumentException(
                    "Incident cannot be null"
            );
        }

        if (findIncidentById(incident.getId()) != null) {
            throw new IllegalArgumentException(
                    "Incident ID already exists"
            );
        }

        incidents.add(incident);
    }

    public void registerVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle cannot be null"
            );
        }

        if (findVehicleById(vehicle.getId()) != null) {
            throw new IllegalArgumentException(
                    "Vehicle ID already exists"
            );
        }

        vehicles.add(vehicle);
    }

    public Incident findIncidentById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid incident ID"
            );
        }

        for (int i = 0; i < incidents.size(); i++) {
            Incident current = incidents.get(i);

            if (current.getId().equals(id)) {
                return current;
            }
        }

        return null;
    }

    public Vehicle findVehicleById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid vehicle ID"
            );
        }

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle current = vehicles.get(i);

            if (current.getId().equals(id)) {
                return current;
            }
        }

        return null;
    }

    public void assignVehicleToIncident(
            String incidentId,
            String vehicleId
    ) {
        Incident incident = findIncidentById(incidentId);
        Vehicle vehicle = findVehicleById(vehicleId);

        if (incident == null) {
            throw new InvalidAssignmentException(
                    "Incident not found"
            );
        }

        if (vehicle == null) {
            throw new InvalidAssignmentException(
                    "Vehicle not found"
            );
        }

        incident.assignVehicle(vehicle);
    }

    public int getIncidentCount() {
        return incidents.size();
    }

    public int getVehicleCount() {
        return vehicles.size();
    }
    /**
     * Resolves an incident using its identifier.
     *
     * Time complexity: O(n), because the incident is searched
     * in a custom linked list.
     *
     * @param incidentId identifier of the incident
     */
    public void resolveIncident(String incidentId) {
        Incident incident = findIncidentById(incidentId);

        if (incident == null) {
            throw new IllegalArgumentException("Incident not found");
        }

        incident.resolve();
    }
}