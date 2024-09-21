package model;

public class InfoOffersModel {
    private final int infoId;
    private final String category;
    private final int visitors;
    private final String description;
    private final int siteID;
    private final int eventID;

    public InfoOffersModel(int infoId, String category, int visitors, String description, int siteID, int eventID) {
        this.infoId = infoId;
        this.category = category;
        this.visitors = visitors;
        this.description = description;
        this.siteID = siteID;
        this.eventID = eventID;
    }

    public int getInfoId() {
        return infoId;
    }

    public String getCategory() {
        return category;
    }

    public int getVisitors() {
        return visitors;
    }

    public String getDescription() {
        return description;
    }

    public int getSiteID() {
        return siteID;
    }

    public int getEventID() {
        return eventID;
    }
}
