import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.NoParkingSpotsAvailableException;
import exceptions.NoVacantSpotsException;
import parkingspot.ParkingSpot;
import parkingspot.ParkingSpotType;
import paymentprocessor.PaymentProcessor;
import vehicle.Vehicle;


public class ParkingLotSystem {
    private List<ParkingFloor> floors;
    PaymentProcessor paymentProcessor;
    PriceEstimator priceEstimator;

    public ParkingLotSystem(PaymentProcessor paymentProcessor, PriceEstimator priceEstimator) {
        floors = new ArrayList<>();
        this.paymentProcessor = paymentProcessor;
        this.priceEstimator = priceEstimator;
    }

    public void addParkingFloor(ParkingFloor floor) {
        this.floors.add(floor);
    }

    public void removeParkingFloor(ParkingFloor floor) {
        this.floors.remove(floor);
    }

    public void addParkingSpot(String floorId, ParkingSpot spot) {
        ParkingFloor floor = getFloorById(floorId);
        floor.addParkingSpot(spot);
    }

    public void removeParkingSpot(String floorId, ParkingSpot spot) {
        ParkingFloor floor = getFloorById(floorId);
        floor.removeParkingSpot(spot);
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        boolean vehicleParked = false;
        for (ParkingFloor floor : floors) {
            try {
                List<ParkingSpot> vacantSpots = floor.getVacantSpots(vehicle.getVehicleType());
                if (!vacantSpots.isEmpty()) {
                    ParkingSpot firstVacantSpot = vacantSpots.get(0);
                    firstVacantSpot.parkVehicle(vehicle);
                    vehicleParked = true;
                    System.out.println("Vehicle parked successfully on Floor " + floor.getFloorId() +
                            ", Spot ID: " + firstVacantSpot.getId());
                    return generateTicket(firstVacantSpot, vehicle);
                }
            } catch (NoParkingSpotsAvailableException e) {
                System.err.println("No parking spots available on Floor " + floor.getFloorId());
            } catch (NoVacantSpotsException e) {
                System.err.println("No vacant spots available on Floor " + floor.getFloorId());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        throw new NoParkingSpotsAvailableException("No suitable parking spots available for vehicle type: " + vehicle.getVehicleType());
    }
    
    public void vacateVehicle(Ticket ticket) {
        ticket.setEndTime(LocalDateTime.now());
        Double amount = priceEstimator.calculatePrice(ticket.getParkingSpotType(), ticket.startTime, ticket.endTime);
        paymentProcessor.processPayment(amount);
        ticket.setAmount(amount);
        ParkingSpot parkingSpot = findParkingSpotById(ticket.getFloorId(), ticket.getParkingSpotId(), ticket.getParkingSpotType());
        parkingSpot.removeVehicle();
    }

    private Ticket generateTicket(ParkingSpot parkingSpot, Vehicle vehicle) {
        return new Ticket(parkingSpot.getId(), parkingSpot.getFloorId(), vehicle.getRegistrationNumber(), parkingSpot.getType());
    }

    public ParkingSpot findParkingSpotById(String floorId, String parkingSpotId, ParkingSpotType parkingSpotType) {
        ParkingFloor parkingFloor = getFloorById(floorId);
        return parkingFloor.findParkingSpotById(parkingSpotId, parkingSpotType);
    }

    private ParkingFloor getFloorById(String floorId) {
        Optional<ParkingFloor> optionalFloor = floors.stream()
                .filter(floor -> floor.getFloorId().equals(floorId))
                .findFirst();

        return optionalFloor.orElseThrow(() -> 
                new IllegalArgumentException("Floor with id " + floorId + " not found"));
    }
}
