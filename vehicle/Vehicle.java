package vehicle;

public class Vehicle {
    private String registrationNumber;
    private VehicleType vehicleType;
    public Vehicle(String registrationNumber, VehicleType vehicleType) {
        this.registrationNumber = this.registrationNumber;
        this.vehicleType = vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
