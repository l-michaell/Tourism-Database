package ui;

import delegates.TerminalTransactionsDelegate;
import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is only responsible for handling terminal text inputs.
 */
public class TerminalTransactions {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private BufferedReader bufferedReader = null;
    private TerminalTransactionsDelegate delegate = null;

    public TerminalTransactions() {
    }

    /**
     * Displays simple text interface
     */
    public void showMainMenu(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. Insert into table");
            System.out.println("2. Delete from table");
            System.out.println("3. Update table");
            System.out.println("4. Show tables");
            System.out.println("5. Quit");
            System.out.println("6. Selection - select toursites based on conditions");
            System.out.println("7. Projection - choose any column(s) any table");
            System.out.println("8. Group By Aggregation - How many locations each country has");
            System.out.println("9. Having Aggregation - Find sites and # of events with at least one event");
            System.out.println("10. Group By Nested Aggregation - Find sites with events that have duration > 1");
            System.out.println("11. Division - Find all the sites where there are all types of events");
            System.out.println("12. Join - ");
            System.out.print("Please choose one of the above 8 options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleInsertOption();
                        break;
                    case 2:
                        handleDeleteOption();
                        break;
                    case 3:
                        handleUpdateOption();
                        break;
                    case 4:
                        delegate.showTable();
                        break;
                    case 5:
                        handleQuitOption();
                        break;
                    case 6:
                        handleSelectionOption();
                        break;
                    case 7:
                        handleProjectionOption();
                        break;
                    case 8:
                        handleGroupByAggregationOption();
                        break;
                    case 9:
                        handleHavingAggregationOption();
                        break;
                    case 10:
                        handleNestedGroupByAggregationOption();
                        break;
                    case 11:
                        handleDivisionOption();
                        break;
                    case 12:
                        handleJoinOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void handleDeleteOption() {
        String table = null;
        while (table == null) {
            System.out.print("Please enter the table name to delete: ");
            table = readLine();
        }
        String branchId = null;
        while (branchId == null) {
            System.out.print("Please enter the table primary key you wish to delete: ");
            branchId = readLine();
//			if (branchId != INVALID_INPUT) {
//				delegate.deleteBranch(branchId);
//			}
        }
        delegate.deleteBranch(table, branchId);
    }

    private void handleInsertCountry() {
        String cname = null;
        while (cname == null) {
            System.out.print("Please enter the Country cname you wish to insert: ");
            cname = readLine();
        }
        CountryModel model = new CountryModel(cname);
        delegate.insertCountry(model);
    }

    private void handleInsertLocationIsin() {
        String cname = null;
        while (cname == null || cname.length() <= 0) {
            System.out.print("Please enter the Location_IsIn cname you wish to insert: ");
            cname = readLine();
        }

        System.out.print("Please enter the Location_IsIn lname you wish to insert: ");
        String lname = readLine();

        int lon = INVALID_INPUT;
        while (lon == INVALID_INPUT) {
            System.out.print("Please enter the Location_IsIn lon you wish to insert: ");
            lon = readInteger(false);
        }

        int lat = INVALID_INPUT;
        while (lat == INVALID_INPUT) {
            System.out.print("Please enter the Location_IsIn lat you wish to insert: ");
            lat = readInteger(false);
        }
        LocationIsInModel model = new LocationIsInModel(cname,
                lname,
                lon,
                lat);
        delegate.insertLocationIsIn(model);
    }

    private void handleInsertToursiteLocated() {
        System.out.print("Please enter the Toursite_Located address you wish to insert: ");
        String address = readLine();

        System.out.print("Please enter the Toursite_Located climate you wish to insert: ");
        String climate = readLine();

        int lon = INVALID_INPUT;
        while (lon == INVALID_INPUT) {
            System.out.print("Please enter the Toursite_Located lon you wish to insert: ");
            lon = readInteger(false);
        }

        int lat = INVALID_INPUT;
        while (lat == INVALID_INPUT) {
            System.out.print("Please enter the Toursite_Located lat you wish to insert: ");
            lat = readInteger(false);
        }

        int siteID = INVALID_INPUT;
        while (siteID == INVALID_INPUT) {
            System.out.print("Please enter the Toursite_Located siteID you wish to insert: ");
            siteID = readInteger(false);
        }

        System.out.print("Please enter the Toursite_Located name you wish to insert: ");
        String tname = readLine();

        System.out.print("Please enter the Toursite_Located fee you wish to insert: ");
        int fee = readInteger(true);

        ToursiteLocatedModel model = new ToursiteLocatedModel(address, climate, lon, lat, siteID, tname, fee);
        delegate.insertToursiteLocated(model);
    }

    private void handleInsertEventOffers() {
        System.out.print("Please enter the Event_Offers startDate you wish to insert: ");
        String startDate = readLine();

        System.out.print("Please enter the Event_Offers endDate you wish to insert: ");
        String endDate = readLine();

        int duration = INVALID_INPUT;
        while (duration == INVALID_INPUT) {
            System.out.print("Please enter the Event_Offers duration you wish to insert: ");
            duration = readInteger(false);
        }

        int eventID = INVALID_INPUT;
        while (eventID == INVALID_INPUT) {
            System.out.print("Please enter the Event_Offers eventID you wish to insert: ");
            eventID = readInteger(false);
        }

        int siteID = INVALID_INPUT;
        while (siteID == INVALID_INPUT) {
            System.out.print("Please enter the Event_Offers siteID you wish to insert: ");
            siteID = readInteger(false);
        }

        System.out.print("Please enter the Event_Offers name you wish to insert: ");
        String ename = readLine();

        EventOffersModel model = new EventOffersModel(startDate, endDate, duration,
                eventID, siteID, ename);
        delegate.insertEventOffers(model);
    }

    public void handleInsertInfoOffers() {
        int infoID = INVALID_INPUT;
        while (infoID == INVALID_INPUT) {
            System.out.print("Please enter the Information_Offers infoID you wish to insert: ");
            infoID = readInteger(false);
        }

        System.out.print("Please enter the Information_Offers category you wish to insert: ");
        String category = readLine();

        System.out.print("Please enter the Information_Offers visitors you wish to insert: ");
        int visitors = readInteger(true);

        System.out.print("Please enter the Information_Offers description you wish to insert: ");
        String description = readLine();

        int siteID = INVALID_INPUT;
        while (siteID == INVALID_INPUT) {
            System.out.print("Please enter the Information_Offers siteID you wish to insert: ");
            siteID = readInteger(false);
        }

        int eventID = INVALID_INPUT;
        while (eventID == INVALID_INPUT) {
            System.out.print("Please enter the Information_Offers eventID you wish to insert: ");
            eventID = readInteger(false);
        }

        InfoOffersModel model = new InfoOffersModel(infoID, category, visitors,
                description, siteID, eventID);
        delegate.insertInfoOffers(model);
    }


    private void handleInsertAmenities() {
        int AmID = INVALID_INPUT;
        while (AmID == INVALID_INPUT) {
            System.out.print("Please enter the Amenities AmID you wish to insert: ");
            AmID = readInteger(false);
        }
        System.out.print("Please enter the Amenities name you wish to insert: ");
        String aName = readLine();

        System.out.print("Please enter the Amenities description you wish to insert: ");
        String type = readLine();

        System.out.print("Please enter the Amenities distance you wish to insert: ");
        int dist = readInteger(true);

        int siteID = INVALID_INPUT;
        while (siteID == INVALID_INPUT) {
            System.out.print("Please enter the Amenities siteID you wish to insert: ");
            siteID = readInteger(false);
        }

        AmenitiesModel model = new AmenitiesModel(AmID, aName, type, dist, siteID);
        delegate.insertAmenities(model);
    }

    private void handleInsertHotel() {
        System.out.print("Please enter the Hotel name you wish to insert: ");
        String name = readLine();

        int AmID = INVALID_INPUT;
        while (AmID == INVALID_INPUT) {
            System.out.print("Please enter the Amenities AmID you wish to insert: ");
            AmID = readInteger(false);
        }
        System.out.print("Please enter the Hotel rooms you wish to insert: ");
        int rooms = readInteger(true);

        HotelModel model = new HotelModel(name, AmID, rooms);
        delegate.insertHotel(model);
    }

    private void handleInsertRestaurant() {
        System.out.print("Please enter the Restaurant name you wish to insert: ");
        String name = readLine();

        int AmID = INVALID_INPUT;
        while (AmID == INVALID_INPUT) {
            System.out.print("Please enter the Restaurant AmID you wish to insert: ");
            AmID = readInteger(false);
        }
        System.out.print("Please enter the Restaurant tables you wish to insert: ");
        int tables = readInteger(true);

        RestaurantModel model = new RestaurantModel(name, AmID, tables);
        delegate.insertRestaurant(model);
    }

    private void handleInsertReview() {
        int revID = INVALID_INPUT;
        while (revID == INVALID_INPUT) {
            System.out.print("Please enter the Reviews RevID you wish to insert: ");
            revID = readInteger(false);
        }
        System.out.print("Please enter the Reviews comment you wish to insert: ");
        String cmt = readLine();
        int infoID = INVALID_INPUT;
        while (infoID == INVALID_INPUT) {
            System.out.print("Please enter the Reviews InfoID you wish to insert: ");
            infoID = readInteger(false);
        }
        ReviewModel model = new ReviewModel(revID, cmt, infoID);
        delegate.insertReview(model);
    }

    private void handleInsertRatings() {
        System.out.print("Please enter the Ratings score you wish to insert: ");
        int score = readInteger(true);
        int revID = INVALID_INPUT;
        while (revID == INVALID_INPUT) {
            System.out.print("Please enter the Ratings RevID you wish to insert: ");
            revID = readInteger(false);
        }
        RatingsModel model = new RatingsModel(score, revID);
        delegate.insertRatings(model);


    }

    private void handleInsertOption() {
        String table = null;
        while (table == null) {
            System.out.print("Please enter the Table name you wish to insert: ");
            table = readLine();
        }
        if (table.equalsIgnoreCase("Country")) {
            handleInsertCountry();
        } else if (table.equalsIgnoreCase("Location_IsIn")) {
            handleInsertLocationIsin();
        } else if (table.equalsIgnoreCase("Toursite_Located")) {
            handleInsertToursiteLocated();
        } else if (table.equalsIgnoreCase("Event_Offers")) {
            handleInsertEventOffers();
        } else if (table.equalsIgnoreCase("Information_Offers")) {
            handleInsertInfoOffers();
        } else if (table.equalsIgnoreCase("Amenities")) {
            handleInsertAmenities();
        } else if (table.equalsIgnoreCase("Hotel")) {
            handleInsertHotel();
        } else if (table.equalsIgnoreCase("Restaurant")) {
            handleInsertRestaurant();
        } else if (table.equalsIgnoreCase("Review")) {
            handleInsertReview();
        } else if (table.equalsIgnoreCase("Ratings")) {
            handleInsertRatings();
        } else {
        }
    }

    private void handleQuitOption() {
        System.out.println("Good Bye!");

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        }

        delegate.terminalTransactionsFinished();
    }

    private void handleUpdateOption() {
        String table = null;
        while (table == null) {
            System.out.print("Please enter the table you wish to update: ");
            table = readLine();
        }

        String id = null;
        while (id == null) {
            System.out.print("Please enter the table ID you wish to update: ");
            id = readLine();
        }

        String attributeToUpdate = null;
        while (attributeToUpdate == null || attributeToUpdate.length() <= 0) {
            System.out.print("Please enter the attribute you wish to update: ");
            attributeToUpdate = readLine();
        }

        String updateAttVal = null;
        while (updateAttVal == null || updateAttVal.length() <= 0) {
            System.out.print("Please enter the new attribute value you wish: ");
            updateAttVal = readLine();
        }


        delegate.updateBranch(table, attributeToUpdate, updateAttVal, id);
    }

    private void handleSelectionOption() {
        String where_clause = null;
        while (where_clause == null || where_clause.length() <= 0) {
            System.out.print("Find suitable locations conditioned on columns using SQL syntax: ");
            where_clause = readLine().trim();
        }

        delegate.selectLocation(where_clause);
    }

    private void handleProjectionOption() {
        String relation = null;
        while (relation == null || relation.length() <= 0) {
            System.out.print("Choose table: ");
            relation = readLine().trim();
        }

        String columns = null;
        while (columns == null || relation.length() <= 0) {
            System.out.print("Choose columns: ");
            columns = readLine().trim();
        }

        delegate.projectTable(relation, columns);
    }

    private void handleGroupByAggregationOption() {
        delegate.aggregationGroupBy();
    }

    private void handleHavingAggregationOption() {
        delegate.aggregationHaving();
    }

    private void handleNestedGroupByAggregationOption() {
        delegate.nestedAggregationGroupBy();
    }

    private void handleDivisionOption() {
        delegate.division();
    }

    private void handleJoinOption() {
        int score = INVALID_INPUT;
        while (score == INVALID_INPUT) {
            System.out.print("See reviews with score greater than: ");
            score = readInteger(false);
        }
        delegate.join(score);
    }

    private int readInteger(boolean allowEmpty) {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e) {
            if (allowEmpty && line.length() == 0) {
                input = EMPTY_INPUT;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }

    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }
}
