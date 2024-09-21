package controller;

import database.DatabaseConnectionHandler;
import delegates.*;
import model.*;
import ui.*;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class Toursite implements LoginWindowDelegate, TerminalTransactionsDelegate, MainWindowDelegate,
        SearchWindowDelegate, InsertWindowDelegate, DeleteWindowDelegate, UpdateWindowDelegate, ProjectionWindowDelegate,
        JoinWindowDelegate {
    private DatabaseConnectionHandler dbHandler = null;

    private LoginWindow loginWindow = null;
    private MainWindow mainWindow = null;
    private InsertWindow insertWindow = null;
    private DeleteWindow deleteWindow = null;
    private UpdateWindow updateWindow = null;
    private SearchWindow searchWindow = null;
    private ProjectionWindow projectionWindow = null;
    private JoinWindow joinWindow = null;

    public Toursite() {
        dbHandler = new DatabaseConnectionHandler();
    }

    /**
     * Main method called at launch time
     */
    public static void main(String[] args) {
        Toursite toursite = new Toursite();
        toursite.start();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
        mainWindow = new MainWindow();
        // mainWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     * <p>
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {

        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            TerminalTransactions transaction = new TerminalTransactions();
            // transaction.setupDatabase(this);
            // transaction.showMainMenu(this);
            mainWindow.showFrame(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }

    }

    // on Main Window, open corresponding popup window when button is clicked
    public void openWindow(String windowName) {
        switch (windowName) {
            case "InsertWindow": // INSERT
                insertWindow = new InsertWindow();
                insertWindow.showFrame(this);
                break;
            case "DeleteWindow": // DELETE
                deleteWindow = new DeleteWindow();
                deleteWindow.showFrame(this);
                break;
            case "UpdateWindow": // UPDATE
                updateWindow = new UpdateWindow();
                updateWindow.showFrame(this);
                break;
            case "SearchWindow": // Selection
                searchWindow = new SearchWindow();
                searchWindow.showFrame(this);
                break;
            case "ProjectionWindow": // Projection
                projectionWindow = new ProjectionWindow();
                projectionWindow.showFrame(this);
                break;
            case "JoinWindow":
                joinWindow = new JoinWindow();
                joinWindow.showFrame(this);
                break;

            case "GroupBy": // Aggregation with Group By
                dbHandler.doGroupBy();
                break;
            case "Having": // Aggregation with Having
                dbHandler.doHaving();
                break;
            case "NestedGroupBy": // Nested Aggregation with Group By
                dbHandler.doNestedGroupBy();
                break;
            case "Division":
                division();
        }
    }

    // INSERT
    public void insert(String country, String location, String lon, String lat) {
        if (country.isEmpty() || location.isEmpty() || lon.isEmpty() || lat.isEmpty()) {
            insertWindow.handleInsertFailed();
        } else {
            LocationIsInModel model = new LocationIsInModel(country, location, Double.parseDouble(lon), Double.parseDouble(lat));
            dbHandler.insertLocationIsIn(model);
            // TO DO: insert an event with a given country, location, and event name?
        }
    }

    // DELETE
    public void delete(String lon, String lat) {
        if (lon.isEmpty() || lat.isEmpty()) {
            deleteWindow.handleDeleteFailed();
        } else {
            dbHandler.deleteBranch("Location_IsIn", lon + "," + lat);
            // TO DO: delete an event with a given country, location, and event name?
        }
    }

    // UPDATE
    public void update(String fieldVal, String newVal, String siteID) {
        if (fieldVal.isEmpty() || newVal.isEmpty() || siteID.isEmpty()) {
            updateWindow.handleUpdateFailed();
        } else {
            dbHandler.updateBranch("Toursite_Located", fieldVal, newVal, siteID);

            // TO DO: update the old event name to the new event name?
        }
    }

    // Selection
    public void search(String location) {
        if (location.isEmpty()) {
            searchWindow.handleSearchFailed();
        } else {
            dbHandler.selectLocation(location);
        }
    }

    // Projection
    public void projection(String relation, String attributes) {
        if (relation.isEmpty() || attributes.isEmpty()) {
            projectionWindow.handleProjectionFailed();
        } else {
            dbHandler.doProjection(relation, attributes);
        }
    }

    /**
     * TermainalTransactionsDelegate Implementation
     * <p>
     * Insert a branch with the given info
     */
    public void insertBranch(ToursiteModel model) {
        dbHandler.insertBranch(model);
    }

    public void insertCountry(CountryModel model) {
        dbHandler.insertCountry(model);
    }

    public void insertLocationIsIn(LocationIsInModel model) {
        dbHandler.insertLocationIsIn(model);
    }

    public void insertToursiteLocated(ToursiteLocatedModel model) {
        dbHandler.insertToursiteLocated(model);

    }

    public void insertEventOffers(EventOffersModel model) {
        dbHandler.insertEventOffers(model);

    }

    public void insertInfoOffers(InfoOffersModel model) {
        dbHandler.insertInfoOffers(model);
    }

    public void insertAmenities(AmenitiesModel model) {
        dbHandler.insertAmenities(model);
    }

    public void insertHotel(HotelModel model) {
        dbHandler.insertHotel(model);
    }

    public void insertRestaurant(RestaurantModel model) {
        dbHandler.insertRestaurant(model);
    }

    public void insertReview(ReviewModel model) {
        dbHandler.insertReview(model);

    }

    public void insertRatings(RatingsModel model) {
        dbHandler.insertRatings(model);

    }

    /**
     * TermainalTransactionsDelegate Implementation
     * <p>
     * Delete branch with given branch ID.
     */
    public void deleteBranch(String tableName, String branchId) {
        dbHandler.deleteBranch(tableName, branchId);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     * <p>
     * Update the branch name for a specific ID
     */

    public void updateBranch(String tableName, String attributeToUpdate, String attributeVal, String Id) {
        dbHandler.updateBranch(tableName, attributeToUpdate, attributeVal, Id);
    }

    /**
     * TermainalTransactionsDelegate Implementation
     */

    public void selectLocation(String where_clause) {
        dbHandler.selectLocation(where_clause);

		/*
		for (int i = 0; i < models.length; i++) {
			Toursite_Located_1Model model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getLat());
			System.out.printf("%-10.10s", model.getLon());
			if (model.getAddress() == null) {
				System.out.printf("%-50.50s", " ");
			} else {
				System.out.printf("%-50.50s", model.getAddress());
			}
			if (model.getClimate() == null) {
				System.out.printf("%-30.30s", " ");
			} else {
				System.out.printf("%-30.30s", model.getClimate());
			}
			System.out.println();
		}
		 */
    }

    public void showTable() {
        showCountry();
        showLocation();
        showToursiteLocated();
        showEventOffers();
        showInfoOffers();
        showAmenities();
        showHotel();
        showRestaurant();
        showReview();
        showRating();
    }

    public void showCountry() {
        CountryModel[] models = dbHandler.getCountryInfo();
        for (int i = 0; i < models.length; i++) {
            CountryModel model = models[i];
            System.out.printf("%-20.20s", model.getName());
            System.out.println();
        }
        System.out.println();
    }

    public void showLocation() {
        LocationIsInModel[] models = dbHandler.getLocationInfo();
        for (int i = 0; i < models.length; i++) {
            LocationIsInModel model = models[i];
            System.out.printf("%-10.20s", model.getCname());
            if (model.getLname() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getLname());
            }
            System.out.printf("%-20.20s", model.getLon());
            System.out.printf("%-20.20s", model.getLat());

            System.out.println();
        }
        System.out.println();
    }

    public void showToursiteLocated() {
        ToursiteLocatedModel[] models = dbHandler.getToursiteLocated();
        for (int i = 0; i < models.length; i++) {
            ToursiteLocatedModel model = models[i];
            if (model.getAddress() == null) {
                System.out.printf("%-40.20s", " ");
            } else {
                System.out.printf("%-40.20s", model.getAddress());
            }
            if (model.getClimate() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getClimate());
            }
            System.out.printf("%-10.20s", model.getLon());
            System.out.printf("%-10.20s", model.getLat());
            System.out.printf("%-10.20s", model.getSiteID());
            if (model.gettName() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.gettName());
            }
            System.out.printf("%-20.20s", model.getFee());
            System.out.println();
        }
        System.out.println();
    }

    public void showEventOffers() {
        EventOffersModel[] models = dbHandler.getEventOffers();
        for (int i = 0; i < models.length; i++) {
            EventOffersModel model = models[i];
            if (model.getStartDate() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getStartDate());
            }
            if (model.getEndDate() == null) {
                System.out.printf("%-15.15s", " ");
            } else {
                System.out.printf("%-15.15s", model.getEndDate());
            }
            System.out.printf("%-20.20s", model.getDuration());
            System.out.printf("%-20.20s", model.getEventID());
            System.out.printf("%-20.20s", model.getSiteID());
            if (model.geteName() == null) {
                System.out.printf("%-15.15s", " ");
            } else {
                System.out.printf("%-15.15s", model.geteName());
            }
            System.out.println();

        }
        System.out.println();
    }

    public void showInfoOffers() {
        InfoOffersModel[] models = dbHandler.getInfoOffers();
        for (int i = 0; i < models.length; i++) {
            InfoOffersModel model = models[i];
            System.out.printf("%-10.10s", model.getInfoId());
            if (model.getCategory() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getCategory());
            }
            System.out.printf("%-20.20s", model.getVisitors());
            if (model.getDescription() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getDescription());
            }
            System.out.printf("%-10.10s", model.getSiteID());
            System.out.printf("%-10.10s", model.getEventID());
            System.out.println();
        }
        System.out.println();
    }

    public void showAmenities() {
        AmenitiesModel[] models = dbHandler.getAmenities();
        for (int i = 0; i < models.length; i++) {
            AmenitiesModel model = models[i];
            System.out.printf("%-10.10s", model.getAmId());
            if (model.getAname() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getAname());
            }
            if (model.getType() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getType());
            }
            if (model.getDist() == 0) {
                System.out.printf("%-20.20s", "0");
            } else {
                System.out.printf("%-20.20s", model.getDist());
            }
            System.out.printf("%-10.10s", model.getSiteID());
            System.out.println();
        }
        System.out.println();
    }

    public void showHotel() {
        HotelModel[] models = dbHandler.getHotels();
        for (int i = 0; i < models.length; i++) {
            HotelModel model = models[i];
            if (model.gethName() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.gethName());
            }
            System.out.printf("%-10.10s", model.getAmId());
            if (model.getRooms() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getRooms());
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showRestaurant() {
        RestaurantModel[] models = dbHandler.getRestaurants();
        for (int i = 0; i < models.length; i++) {
            RestaurantModel model = models[i];
            if (model.getrName() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getrName());
            }
            System.out.printf("%-10.10s", model.getAmId());
            if (model.getTables() == 0) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getTables());
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showReview() {
        ReviewModel[] models = dbHandler.getReview();
        for (int i = 0; i < models.length; i++) {
            ReviewModel model = models[i];
            System.out.printf("%-10.10s", model.getRevId());
            if (model.getCmt() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getCmt());
            }
            System.out.printf("%-10.10s", model.getInfoId());
            System.out.println();
        }
        System.out.println();
    }

    public void showRating() {
        RatingsModel[] models = dbHandler.getRatings();
        for (int i = 0; i < models.length; i++) {
            RatingsModel model = models[i];
            System.out.printf("%-10.10s", model.getScore());
            System.out.printf("%-10.10s", model.getRevId());
            System.out.println();
        }
        System.out.println();
    }

    public void showBranch() {

        ToursiteModel[] models = dbHandler.getBranchInfo();

        for (int i = 0; i < models.length; i++) {
            ToursiteModel model = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", model.getId());
            System.out.printf("%-20.20s", model.getName());
            if (model.getAddress() == null) {
                System.out.printf("%-20.20s", " ");
            } else {
                System.out.printf("%-20.20s", model.getAddress());
            }
            System.out.printf("%-15.15s", model.getCity());
            if (model.getPhoneNumber() == 0) {
                System.out.printf("%-15.15s", " ");
            } else {
                System.out.printf("%-15.15s", model.getPhoneNumber());
            }

            System.out.println();
        }
    }

    public void projectTable(String relation, String attributes) {
        dbHandler.doProjection(relation, attributes);
    }

    public void aggregationGroupBy() {
        dbHandler.doGroupBy();
    }

    public void aggregationHaving() {
        dbHandler.doHaving();
    }

    public void nestedAggregationGroupBy() {
        dbHandler.doNestedGroupBy();
    }

    public void division() {
        dbHandler.doDivision();
    }

    public void join(int score) {
        dbHandler.doJoin(score);
    }

    /**
     * TerminalTransactionsDelegate Implementation
     * <p>
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    /**
     * TerminalTransactionsDelegate Implementation
     * <p>
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
    public void databaseSetup() {
        dbHandler.databaseSetup();

    }
}
