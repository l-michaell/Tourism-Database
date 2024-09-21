package model;

public class RestaurantModel {
    private final String rName;
    private final int AmId;
    private final int tables;

    public RestaurantModel(String rName, int amId, int tables) {
        this.rName = rName;
        AmId = amId;
        this.tables = tables;
    }

    public String getrName() {
        return rName;
    }

    public int getAmId() {
        return AmId;
    }

    public int getTables() {
        return tables;
    }
}
