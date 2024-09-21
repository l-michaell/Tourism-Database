package delegates;

import model.*;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class (in this case Toursite).
 * <p>
 * TerminalTransactions calls the methods that we have listed below but
 * Toursite is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
    void databaseSetup();

    void deleteBranch(String tableName, String branchId);

    void insertBranch(ToursiteModel model);

    void insertCountry(CountryModel model);

    void insertLocationIsIn(LocationIsInModel model);

    void insertToursiteLocated(ToursiteLocatedModel model);

    void insertEventOffers(EventOffersModel model);

    void insertInfoOffers(InfoOffersModel model);

    void insertAmenities(AmenitiesModel model);

    void insertHotel(HotelModel model);

    void insertRestaurant(RestaurantModel model);

    void insertReview(ReviewModel model);

    void insertRatings(RatingsModel model);

    void showBranch();

    void updateBranch(String tableName, String attToUpdate, String attVal, String id);

    void selectLocation(String where_clause);

    void projectTable(String relation, String attributes);

    void aggregationGroupBy();

    void aggregationHaving();

    void nestedAggregationGroupBy();

    void division();

    void join(int score);

    void terminalTransactionsFinished();

    void showTable();

}
