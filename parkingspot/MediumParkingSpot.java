package parkingspot;

class MediumParkingSpot extends ParkingSpot{
    
    ParkingSpot(String id){
        super(id, ParkingSpot.BIG, ParkingSpot.MEDIUM.getPrice())
    }
}
