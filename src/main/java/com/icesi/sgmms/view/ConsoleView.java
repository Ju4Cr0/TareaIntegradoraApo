
package com.icesi.sgmms.view;

import com.icesi.sgmms.exceptions.InvalidAssignmentException;
import com.icesi.sgmms.controller.SGMMSController;
import com.icesi.sgmms.model.Incident;
import com.icesi.sgmms.model.Vehicle;
import com.icesi.sgmms.model.enums.IncidentType;
import com.icesi.sgmms.model.enums.Severity;
import com.icesi.sgmms.model.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ConsoleView {

    private final SGMMSController controller;
    private final Scanner scanner;

    public ConsoleView(SGMMSController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            showMenu();
            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1":
                        registerIncident();
                        break;
                    case "2":
                        registerVehicle();
                        break;
                    case "3":
                        findIncident();
                        break;
                    case "4":
                        findVehicle();
                        break;
                    case "5":
                        assignVehicle();
                        break;
                    case "6":
                        resolveIncident();
                        break;
                    case "7":
                        showStatistics();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Exiting SGMMS...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InvalidAssignmentException exception) {
                System.out.println(
                        "Assignment error: " + exception.getMessage()
                );

            } catch (IllegalArgumentException exception) {
                System.out.println(
                        "Invalid input: " + exception.getMessage()
                );

            } catch (IllegalStateException exception) {
                System.out.println(
                        "Operation error: " + exception.getMessage()
                );
            }
        }
    }

    private void showMenu() {
        System.out.println("\n========== SGMMS ==========");
        System.out.println("1. Register incident");
        System.out.println("2. Register vehicle");
        System.out.println("3. Find incident");
        System.out.println("4. Find vehicle");
        System.out.println("5. Assign vehicle to incident");
        System.out.println("6. Resolve incident");
        System.out.println("7. Show statistics");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void registerIncident() {
        String id = readText("Incident ID: ");

        IncidentType type = readEnum(
                "Incident type (ACCIDENT, ROBBERY, FIRE): ",
                IncidentType.class
        );

        Severity severity = readEnum(
                "Severity (HIGH, MEDIUM, LOW): ",
                Severity.class
        );

        double latitude = readDouble("Latitude: ");
        double longitude = readDouble("Longitude: ");

        Incident incident = new Incident(
                id,
                type,
                severity,
                latitude,
                longitude,
                LocalDateTime.now()
        );

        controller.registerIncident(incident);
        System.out.println("Incident registered successfully.");
    }

    private void registerVehicle() {
        String id = readText("Vehicle ID: ");

        VehicleType type = readEnum(
                "Vehicle type (PATROL, AMBULANCE, FIRETRUCK): ",
                VehicleType.class
        );

        double latitude = readDouble("Latitude: ");
        double longitude = readDouble("Longitude: ");

        Vehicle vehicle = new Vehicle(
                id, type, latitude, longitude
        );

        controller.registerVehicle(vehicle);
        System.out.println("Vehicle registered successfully.");
    }

    private void findIncident() {
        String id = readText("Incident ID: ");
        Incident incident = controller.findIncidentById(id);

        if (incident == null) {
            System.out.println("Incident not found.");
            return;
        }

        System.out.println("ID: " + incident.getId());
        System.out.println("Type: " + incident.getType());
        System.out.println("Severity: " + incident.getSeverity());
        System.out.println("Status: " + incident.getStatus());
        System.out.println("Timestamp: " + incident.getTimestamp());

        Vehicle assigned = incident.getAssignedVehicle();

        System.out.println("Assigned vehicle: " +
                (assigned == null ? "None" : assigned.getId()));
    }

    private void findVehicle() {
        String id = readText("Vehicle ID: ");
        Vehicle vehicle = controller.findVehicleById(id);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("ID: " + vehicle.getId());
        System.out.println("Type: " + vehicle.getType());
        System.out.println("Status: " + vehicle.getStatus());
        System.out.println("Latitude: " + vehicle.getLatitude());
        System.out.println("Longitude: " + vehicle.getLongitude());
    }

    private void assignVehicle() {
        String incidentId = readText("Incident ID: ");
        String vehicleId = readText("Vehicle ID: ");

        controller.assignVehicleToIncident(incidentId, vehicleId);

        System.out.println("Vehicle assigned successfully.");
    }

    private void resolveIncident() {
        String incidentId = readText("Incident ID: ");

        controller.resolveIncident(incidentId);

        System.out.println("Incident resolved successfully.");
    }

    private void showStatistics() {
        System.out.println("Registered incidents: " +
                controller.getIncidentCount());

        System.out.println("Registered vehicles: " +
                controller.getVehicleCount());
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }


    private double readDouble(String prompt) {

        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (!Double.isFinite(value)) {
                    System.out.println(
                            "Error: Enter a finite number."
                    );
                    continue;
                }

                return value;

            } catch (NumberFormatException exception) {
                System.out.println(
                        "Error: Invalid number. Please try again."
                );
            }
        }
    }


    private <E extends Enum<E>> E readEnum(
            String prompt, Class<E> enumClass) {

        System.out.print(prompt);
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            return Enum.valueOf(enumClass, input);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid value. Valid options: " +
                            java.util.Arrays.toString(enumClass.getEnumConstants())
            );
        }
    }
}
