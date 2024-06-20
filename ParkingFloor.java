import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import exceptions.NoParkingSpotsAvailableException;
import exceptions.NoVacantSpotsException;
import exceptions.ParkingSpotNotFoundException;
import parkingspot.ParkingSpot;
import parkingspot.ParkingSpotType;
import vehicle.VehicleType;

public class ParkingFloor {
    
    private String floorId;
    private Map<ParkingSpotType, List<ParkingSpot>> parkingSpots;

    public ParkingFloor(String floorId) {
        this.floorId = floorId;
        this.parkingSpots = new HashMap<>();
        for (ParkingSpotType type : ParkingSpotType.values()) {
            this.parkingSpots.put(type, new ArrayList<>());
        }
    }
    
    public void addParkingSpot(ParkingSpot parkingSpot) {
        ParkingSpotType type = parkingSpot.getType();
        this.parkingSpots.get(type).add(parkingSpot);
    }

    public void removeParkingSpot(ParkingSpot parkingSpot) {
        ParkingSpotType type = parkingSpot.getType();
        this.parkingSpots.get(type).remove(parkingSpot);
    }

    public List<ParkingSpot> getVacantSpots(VehicleType vehicleType) {
        ParkingSpotType matchingSpotType = getMatchingSpotType(vehicleType);
        List<ParkingSpot> spots = parkingSpots.get(matchingSpotType);
        if (spots == null || spots.isEmpty()) {
            throw new NoParkingSpotsAvailableException("No parking spots available for type: " + matchingSpotType);
        }
        List<ParkingSpot> vacantSpots = spots.stream()
                .filter(ParkingSpot::getIsSpotFree)
                .collect(Collectors.toList());
        if (vacantSpots.isEmpty()) {
            throw new NoVacantSpotsException("No vacant spots available for type: " + matchingSpotType);
        }
        return vacantSpots;
    }

    public ParkingSpot findParkingSpotById(String parkingSpotId, ParkingSpotType parkingSpotType) {
        List<ParkingSpot> parkingSpots = this.parkingSpots.get(parkingSpotType);
        if (parkingSpots == null) {
            throw new ParkingSpotNotFoundException("Parking spot type " + parkingSpotType + " not found on floor " + floorId);
        }
        Optional<ParkingSpot> optionalSpot = parkingSpots.stream()
                .filter(parkingSpot -> parkingSpot.getId().equals(parkingSpotId))
                .findFirst();
        return optionalSpot.orElseThrow(() -> 
            new ParkingSpotNotFoundException("Parking spot with id " + parkingSpotId + " not found"));
    }

    private ParkingSpotType getMatchingSpotType(VehicleType vehicleType) {
        ParkingSpotType parkingSpotType;
        switch(vehicleType) {
            case VehicleType.BIKE:
                parkingSpotType = ParkingSpotType.SMALL;
                break;
            case VehicleType.CAR:
                parkingSpotType = ParkingSpotType.MEDIUM;
                break;
            case VehicleType.TRUCK:
                parkingSpotType = ParkingSpotType.BIG;
                break;
            default:
                throw new IllegalArgumentException("No suitable parking spots available for vehicle type: " + vehicleType);
        }
        return parkingSpotType;
    }

    public String getFloorId() {
        return floorId;
    }

    public Map<ParkingSpotType, List<ParkingSpot>> getParkingSpots() {
        return parkingSpots;
    }
}
