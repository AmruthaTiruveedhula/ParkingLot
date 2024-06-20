package parkingspot;

import java.util.List;

import vehicle.Vehicle;

public class ParkingSpot {
    String id;
    String floorId;
    Boolean isSpotFree;
    ParkingSpotType type;
    Vehicle vehicle;
    Double price;

    public ParkingSpot(String id, String floorId, ParkingSpotType type){
        this.vehicle = null;
        this.type = type;
        this.id = id;
        this.floorId = floorId;
        this.isSpotFree = true;
        this.price = type.getPrice();
    }

    public void parkVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.isSpotFree = false;
    }
    
    public void removeVehicle(){
        this.vehicle = null;
        this.isSpotFree = true;
    }

    public String getId() {
        return id;
    }

    public String getFloorId() {
        return floorId;
    }

    public Boolean getIsSpotFree() {
        return isSpotFree;
    }

    public void setIsSpotFree(Boolean isSpotFree) {
        this.isSpotFree = isSpotFree;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}