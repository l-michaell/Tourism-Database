package model;

public class AmenitiesModel {
    private final int AmId;
    private final String Aname;
    private final String type;
    private final double dist;
    private final int siteID;

    public AmenitiesModel(int amId, String aname, String type, double dist, int siteID) {
        AmId = amId;
        Aname = aname;
        this.type = type;
        this.dist = dist;
        this.siteID = siteID;
    }

    public int getAmId() {
        return AmId;
    }

    public String getAname() {
        return Aname;
    }

    public String getType() {
        return type;
    }

    public double getDist() {
        return dist;
    }

    public int getSiteID() {
        return siteID;
    }
}
