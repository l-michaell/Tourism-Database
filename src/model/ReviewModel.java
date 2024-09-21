package model;

public class ReviewModel {
    private final int RevId;
    private final String cmt;
    private final int InfoId;

    public ReviewModel(int revId, String cmt, int infoId) {
        RevId = revId;
        this.cmt = cmt;
        InfoId = infoId;
    }

    public int getRevId() {
        return RevId;
    }

    public String getCmt() {
        return cmt;
    }

    public int getInfoId() {
        return InfoId;
    }
}
