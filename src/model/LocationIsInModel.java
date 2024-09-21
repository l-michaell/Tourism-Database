package model;

public class LocationIsInModel {
    private final String cname;
    private final String lname;
    private final double lon;
    private final double lat;

    public LocationIsInModel(String cname, String lname, double lon, double lat) {
        this.cname = cname;
        this.lname = lname;
        this.lon = lon;
        this.lat = lat;
    }

    public String getCname() {
        return this.cname;
    }

    public String getLname() {
        return this.lname;
    }

    public double getLon() {
        return this.lon;
    }

    public double getLat() {
        return this.lat;
    }
}
