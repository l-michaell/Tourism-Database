package model;

public class ToursiteLocatedModel {
    private final String address;
    private final String climate;
    private final double lon;
    private final double lat;
    private final int siteID;
    private final String tName;
    private final double fee;

    public ToursiteLocatedModel(String address, String climate, double lon, double lat, int siteID, String tName, double fee) {
        this.address = address;
        this.climate = climate;
        this.lon = lon;
        this.lat = lat;
        this.siteID = siteID;
        this.tName = tName;
        this.fee = fee;
    }

    public int getSiteID() {
        return siteID;
    }

    public String gettName() {
        return tName;
    }

    public double getFee() {
        return fee;
    }

    public String getAddress() {
        return this.address;
    }

    public String getClimate() {
        return this.climate;
    }

    public double getLon() {
        return this.lon;
    }

    public double getLat() {
        return this.lat;
    }
}
