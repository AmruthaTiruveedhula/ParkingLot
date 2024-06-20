package parkingspot;

class BigParkingSpot extends ParkingSpot{

    ParkingSpot(String id){
        super(id, ParkingSpot.BIG, ParkingSpot.BIG.getPrice());
    }
}