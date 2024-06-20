import java.time.LocalDateTime;
import java.util.UUID;

import parkingspot.ParkingSpotType;

public class Ticket {
    
    String ticketId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String parkingSpotId;
    String floorId;
    String vehicleRegistrationNumber;
    ParkingSpotType parkingSpotType;
    Double amount;

    public Ticket(String parkingSpotId, String floorId, String vehicleRegistrationNumber, ParkingSpotType parkingSpotType) {
        this.ticketId = UUID.randomUUID().toString();
        this.startTime = LocalDateTime.now();
        this.floorId = floorId;
        this.parkingSpotId = parkingSpotId;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.parkingSpotType = parkingSpotType;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getParkingSpotId() {
        return parkingSpotId;
    }

    public String getFloorId() {
        return floorId;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    public Double getAmount() {
        return amount;
    }
}
