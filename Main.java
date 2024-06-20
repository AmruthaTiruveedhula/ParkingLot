import exceptions.NoParkingSpotsAvailableException;
import exceptions.NoVacantSpotsException;
import parkingspot.ParkingSpot;
import parkingspot.ParkingSpotType;
import paymentprocessor.PaymentProcessor;
import vehicle.Vehicle;
import vehicle.VehicleType;

public class Main { 
    public static void main(String[] args) throws Exception{
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        PriceEstimator priceEstimator = new PriceEstimator();
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(paymentProcessor, priceEstimator);

        ParkingFloor floor1 = new ParkingFloor("1");
        ParkingFloor floor2 = new ParkingFloor("2");
        parkingLotSystem.addParkingFloor(floor1);
        parkingLotSystem.addParkingFloor(floor2);

        ParkingSpotType.BIG.setPrice(100);
        ParkingSpotType.MEDIUM.setPrice(50);
        ParkingSpotType.SMALL.setPrice(25);

        ParkingSpot spot1 = new ParkingSpot("A1", "1", ParkingSpotType.SMALL);
        ParkingSpot spot2 = new ParkingSpot("B1", "1", ParkingSpotType.MEDIUM);
        ParkingSpot spot3 = new ParkingSpot("C1", "2", ParkingSpotType.BIG);
        floor1.addParkingSpot(spot1);
        floor1.addParkingSpot(spot2);
        floor2.addParkingSpot(spot3);

        Vehicle vehicle1 = new Vehicle("ABC123", VehicleType.CAR);

        try {
            Ticket ticket = parkingLotSystem.parkVehicle(vehicle1);
            System.out.println("Vehicle parked successfully. Ticket details:");
            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Floor ID: " + ticket.getFloorId());
            System.out.println("Spot ID: " + ticket.getParkingSpotId());
            System.out.println("Parking Spot Type: " + ticket.getParkingSpotType());
            System.out.println("Spot Status Before Vacating: " + spot2.getIsSpotFree());
            parkingLotSystem.vacateVehicle(ticket);
            System.out.println("Parking Price " + ticket.getAmount());
            System.out.println("Spot Status After Vacating: " + spot2.getIsSpotFree());
        } catch (NoParkingSpotsAvailableException | NoVacantSpotsException e) {
            System.err.println("Failed to park vehicle: " + e.getMessage());
        }
    }
}

