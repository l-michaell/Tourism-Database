package model;

public class HotelModel {
    private final String hName;
    private final int AmId;
    private final int rooms;

    public HotelModel(String hName, int amId, int rooms) {
        this.hName = hName;
        AmId = amId;
        this.rooms = rooms;
    }

    public String gethName() {
        return hName;
    }

    public int getAmId() {
        return AmId;
    }

    public int getRooms() {
        return rooms;
    }
}
