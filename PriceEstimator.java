import java.time.Duration;
import java.time.LocalDateTime;

import parkingspot.ParkingSpotType;

public class PriceEstimator {

    public double calculatePrice(ParkingSpotType parkingSpotType, LocalDateTime startTime, LocalDateTime endTime) {
        long durationHours = Duration.between(startTime, endTime).toHours();
        double pricePerHour = parkingSpotType.getPrice();
        double totalPrice = durationHours * pricePerHour;
        return totalPrice;
    }
}
