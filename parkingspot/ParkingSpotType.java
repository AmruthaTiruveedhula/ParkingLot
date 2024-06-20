package parkingspot;

public enum ParkingSpotType {
    
    BIG,
    MEDIUM,
    SMALL;
    
    private double price;

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}