package model;

public class EventOffersModel {
    private final String startDate;
    private final String endDate;
    private final double duration;
    private final int eventID;
    private final int siteID;
    private final String eName;

    public EventOffersModel(String startDate, String endDate, double duration, int eventID, int siteID, String eName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.eventID = eventID;
        this.siteID = siteID;
        this.eName = eName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getDuration() {
        return duration;
    }

    public int getEventID() {
        return eventID;
    }

    public int getSiteID() {
        return siteID;
    }

    public String geteName() {
        return eName;
    }
}
