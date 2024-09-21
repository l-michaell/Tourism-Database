package model;

public class RatingsModel {
    private final int score;
    private final int RevId;

    public RatingsModel(int score, int revId) {
        this.score = score;
        RevId = revId;
    }

    public int getScore() {
        return score;
    }

    public int getRevId() {
        return RevId;
    }
}
