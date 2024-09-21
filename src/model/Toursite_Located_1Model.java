package model;

public class Toursite_Located_1Model {
    private final String address;
    private final String climate;
    private final float lon;
    private final float lat;

    public Toursite_Located_1Model(String address, String climate, float lon, float lat) {
        this.address = address;
        this.climate = climate;
        this.lon = lon;
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public String getClimate() {
        return climate;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }
}
