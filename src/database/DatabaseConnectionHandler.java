package database;

import model.*;
import util.PrintablePreparedStatement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void deleteBranch(String tableName, String branchId) {
        String idname = "";
        if (tableName.equals("Review")) {
            idname = "RevID";
        } else if (tableName.equals("Country")) {
            idname = "name";
        } else if (tableName.equals("Toursite_Located")) {
            idname = "siteID";
        } else if (tableName.equals("Event_Offers")) {
            idname = "eventID";
        } else if (tableName.equals("Information_Offers")) {
            idname = "InfoID";
        } else if (tableName.equals("Amenities")) {
            idname = "AmID";
        } else if (tableName.equals("Hotel")) {
            idname = "AmID";
        } else if (tableName.equals("Restaurant")) {
            idname = "AmID";
        } else if (tableName.equals("Ratings")) {
            idname = "RevID";
        }


        try {
            if (tableName.equals("Location_IsIn")) {
                String[] parts = branchId.split(",");
                String lon = parts[0];
                String lat = parts[1];
                String query = "DELETE FROM " + tableName + " WHERE lon = ? AND lat = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setString(1, lon);
                ps.setString(2, lat);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
                }
                String query1 = "SELECT * FROM location_isin";
                PrintablePreparedStatement ps1 = new PrintablePreparedStatement(connection.prepareStatement(query1), query1, false);
                ResultSet rs = ps1.executeQuery();

                JTable table = new JTable(buildTableModel(rs));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));

                rs.close();
                ps1.close();

                connection.commit();

                ps.close();
            } else {
                String query = "DELETE FROM " + tableName + " WHERE " + idname + " = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setString(1, branchId);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
                }
                connection.commit();
                ps.close();
            }

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertCountry(CountryModel model) {
        try {
            String query = "INSERT INTO Country VALUES (?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, model.getName());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertLocationIsIn(LocationIsInModel model) {
        try {
            String query = "INSERT INTO Location_Isin VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, model.getCname());
            if (model.getLname() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, model.getLname());
            }
            ps.setDouble(3, model.getLon());
            ps.setDouble(4, model.getLat());
            ps.executeUpdate();
            connection.commit();
            String query1 = "SELECT * FROM location_isin";
            PrintablePreparedStatement ps1 = new PrintablePreparedStatement(connection.prepareStatement(query1), query1, false);
            ResultSet rs = ps1.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps1.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "There Does Not Exists A Country With That Name", "Foreign Key Violation", JOptionPane.ERROR_MESSAGE);
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertToursiteLocated(ToursiteLocatedModel model) {
        try {
            String query = "INSERT INTO Toursite_Located VALUES (?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            if (model.getAddress() == null) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, model.getAddress());
            }
            if (model.getClimate() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, model.getClimate());
            }
            ps.setDouble(3, model.getLon());
            ps.setDouble(4, model.getLat());
            ps.setInt(5, model.getSiteID());
            if (model.gettName() == null) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, model.gettName());
            }
            if (model.getFee() == 0) {
                ps.setNull(7, java.sql.Types.DOUBLE);
            } else {
                ps.setDouble(7, model.getFee());
            }
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertEventOffers(EventOffersModel model) {
        try {
            String query = "INSERT INTO Event_Offers VALUES (?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            if (model.getStartDate() == null) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, model.getStartDate());
            }
            if (model.getEndDate() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, model.getEndDate());
            }
            ps.setDouble(3, model.getDuration());
            ps.setInt(4, model.getEventID());
            ps.setInt(5, model.getSiteID());
            if (model.geteName() == null) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, model.geteName());
            }
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertInfoOffers(InfoOffersModel model) {
        try {
            String query = "INSERT INTO Information_Offers VALUES (?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getInfoId());
            if (model.getCategory() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, model.getCategory());
            }
            if (model.getVisitors() == 0) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, model.getVisitors());
            }
            if (model.getDescription() == null) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, model.getDescription());
            }
            ps.setInt(5, model.getSiteID());
            ps.setInt(6, model.getEventID());

            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertAmenities(AmenitiesModel model) {
        try {
            String query = "INSERT INTO Amenities VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getAmId());
            if (model.getAname() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, model.getAname());
            }
            if (model.getType() == null) {
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps.setString(3, model.getType());
            }
            ps.setDouble(4, model.getDist());
            ps.setInt(5, model.getSiteID());

            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertHotel(HotelModel model) {
        try {
            String query = "INSERT INTO Hotel VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            if (model.gethName() == null) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, model.gethName());
            }
            ps.setInt(2, model.getAmId());
            if (model.getRooms() == 0) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, model.getRooms());
            }
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertRestaurant(RestaurantModel model) {
        try {
            String query = "INSERT INTO Restaurant VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            if (model.getrName() == null) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, model.getrName());
            }
            ps.setInt(2, model.getAmId());
            if (model.getTables() == 0) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, model.getTables());
            }
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertReview(ReviewModel model) {
        try {
            String query = "INSERT INTO Review VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getRevId());
            if (model.getCmt() == null) {
                ps.setNull(2, java.sql.Types.VARCHAR);
            } else {
                ps.setString(2, model.getCmt());
            }
            ps.setInt(3, model.getInfoId());

            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertRatings(RatingsModel model) {
        try {
            String query = "INSERT INTO Ratings VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getScore());
            ps.setInt(2, model.getRevId());
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertBranch(ToursiteModel model) {
        try {
            String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getName());
            ps.setString(3, model.getAddress());
            ps.setString(4, model.getCity());
            if (model.getPhoneNumber() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, model.getPhoneNumber());
            }

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    public CountryModel[] getCountryInfo() {
        ArrayList<CountryModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Country";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CountryModel model = new CountryModel(rs.getString("NAME"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new CountryModel[result.size()]);
    }

    public LocationIsInModel[] getLocationInfo() {
        ArrayList<LocationIsInModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Location_Isin";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocationIsInModel model = new LocationIsInModel(rs.getString("cname"),
                        rs.getString("lname"),
                        rs.getDouble("lon"),
                        rs.getDouble("lat"));

                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new LocationIsInModel[result.size()]);
    }

    public ToursiteLocatedModel[] getToursiteLocated() {
        ArrayList<ToursiteLocatedModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Toursite_Located";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ToursiteLocatedModel model = new ToursiteLocatedModel(rs.getString("address"),
                        rs.getString("climate"),
                        rs.getDouble("lon"),
                        rs.getDouble("lat"),
                        rs.getInt("siteID"),
                        rs.getString("name"),
                        rs.getDouble("fee"));

                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ToursiteLocatedModel[result.size()]);
    }

    public EventOffersModel[] getEventOffers() {
        ArrayList<EventOffersModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Event_Offers";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EventOffersModel model = new EventOffersModel(rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getDouble("duration"),
                        rs.getInt("eventID"),
                        rs.getInt("siteID"),
                        rs.getString("name"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new EventOffersModel[result.size()]);

    }

    public InfoOffersModel[] getInfoOffers() {
        ArrayList<InfoOffersModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Information_Offers";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InfoOffersModel model = new InfoOffersModel(rs.getInt("InfoId"),
                        rs.getString("category"),
                        rs.getInt("visitors"),
                        rs.getString("description"),
                        rs.getInt("siteID"),
                        rs.getInt("eventID"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new InfoOffersModel[result.size()]);

    }

    public AmenitiesModel[] getAmenities() {
        ArrayList<AmenitiesModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Amenities";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AmenitiesModel model = new AmenitiesModel(rs.getInt("AmID"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("distance"),
                        rs.getInt("siteID"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new AmenitiesModel[result.size()]);
    }

    public HotelModel[] getHotels() {
        ArrayList<HotelModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Hotel";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HotelModel model = new HotelModel(rs.getString("name"),
                        rs.getInt("AmID"),
                        rs.getInt("rooms"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new HotelModel[result.size()]);

    }

    public RestaurantModel[] getRestaurants() {
        ArrayList<RestaurantModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Restaurant";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RestaurantModel model = new RestaurantModel(rs.getString("name"),
                        rs.getInt("AmID"),
                        rs.getInt("tables"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new RestaurantModel[result.size()]);
    }

    public ReviewModel[] getReview() {
        ArrayList<ReviewModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Review";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReviewModel model = new ReviewModel(rs.getInt("RevID"),
                        rs.getString("cmt"),
                        rs.getInt("InfoID"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ReviewModel[result.size()]);
    }

    public RatingsModel[] getRatings() {
        ArrayList<RatingsModel> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Ratings";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RatingsModel model = new RatingsModel(rs.getInt("score"),
                        rs.getInt("RevID"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new RatingsModel[result.size()]);

    }

    public ToursiteModel[] getBranchInfo() {
        ArrayList<ToursiteModel> result = new ArrayList<ToursiteModel>();

        try {
            String query = "SELECT * FROM branch";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ToursiteModel model = new ToursiteModel(rs.getString("branch_addr"),
                        rs.getString("branch_city"),
                        rs.getInt("branch_id"),
                        rs.getString("branch_name"),
                        rs.getInt("branch_phone"));
                result.add(model);
            }

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ToursiteModel[result.size()]);
    }


    public void updateBranch(String tableName, String attrToUpdate, String attrVal, String Id) {
        try {
            if (tableName.equals("Location_IsIn")) {
                String[] str = Id.split(",");
                String query = "UPDATE Location_IsIn SET " + attrToUpdate + " = ? WHERE lon = ? AND lat = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setString(1, attrVal);
                ps.setString(2, str[0]);
                ps.setString(3, str[1]);

                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Toursite_Located")) {
                String query = "UPDATE Toursite_Located SET " + attrToUpdate + " = ? WHERE siteID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("address") || attrToUpdate.equals("climate") || attrToUpdate.equals("name")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setDouble(1, Double.parseDouble(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Event_Offers")) {
                String query = "UPDATE Event_Offers SET " + attrToUpdate + " = ? WHERE eventID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("startDate") || attrToUpdate.equals("endDate") || attrToUpdate.equals("name")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setDouble(1, Integer.parseInt(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Information_Offers")) {
                String query = "UPDATE Information_Offers SET " + attrToUpdate + " = ? WHERE InfoID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("category") || attrToUpdate.equals("description")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setInt(1, Integer.parseInt(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Amenities")) {
                String query = "UPDATE Amenities SET " + attrToUpdate + " = ? WHERE AmID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("name")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setInt(1, Integer.parseInt(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Hotel")) {
                String query = "UPDATE Hotel SET " + attrToUpdate + " = ? WHERE AmID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("name")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setInt(1, Integer.parseInt(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Restaurant")) {
                String query = "UPDATE Restaurant SET " + attrToUpdate + " = ? WHERE AmID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("name")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setInt(1, Integer.parseInt(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Review")) {
                String query = "UPDATE Review SET " + attrToUpdate + " = ? WHERE RevID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                if (attrToUpdate.equals("cmt")) {
                    ps.setString(1, attrVal);
                } else {
                    ps.setInt(1, Integer.parseInt(attrVal));
                }
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            } else if (tableName.equals("Ratings")) {
                String query = "UPDATE Ratings SET " + attrToUpdate + " = ? WHERE RevID = ?";
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setInt(1, Integer.parseInt(attrVal));
                ps.setString(2, Id);
                int rowCount = ps.executeUpdate();
                if (rowCount == 0) {
                    System.out.println(WARNING_TAG + " Branch " + Id + " does not exist!");
                }
                connection.commit();
                ps.close();
            }


        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void selectLocation(String where_clause) {
        ArrayList<Toursite_Located_1Model> result = new ArrayList<Toursite_Located_1Model>();

        try {
            String query = "SELECT * FROM toursite_located WHERE " + where_clause;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
/*
			while(rs.next()) {
				Toursite_Located_1Model model = new Toursite_Located_1Model(rs.getString("address"),
						rs.getString("climate"),
						rs.getFloat("lon"),
						rs.getFloat("lat"));
				result.add(model);
			}
*/
            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        // return result.toArray(new Toursite_Located_1Model[result.size()]);
    }

    public void doProjection(String relation, String attributes) {
        try {
            String query = "SELECT " + attributes + " FROM " + relation;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void doGroupBy() {
        try {
            String query = "SELECT cname as country, COUNT(*) as count FROM location_isin GROUP BY cname";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void doHaving() {
        try {
            String query = "SELECT siteID, COUNT(*) as count FROM information_offers GROUP BY siteID HAVING COUNT(*) > 1";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void doNestedGroupBy() {
        try {
            String query = "SELECT siteID, COUNT(*) as Number_Of_Events FROM event_offers e GROUP BY e.siteID HAVING 0 < " +
                    "(SELECT COUNT(*) FROM event_offers e2 WHERE e.siteID = e2.siteID AND e2.duration > 1)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void doJoin(int score) {
        try {
            String query = "SELECT i.siteid, i.description, r.cmt, rs.score FROM ratings rs, review r, information_offers i " +
                    "WHERE i.infoid = r.infoid AND r.revid = rs.revid AND rs.score > ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, score);
            ResultSet rs = ps.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void doDivision() {
        try {
            String query = "SELECT t.siteid FROM toursite_located t WHERE NOT EXISTS " +
                    "(SELECT i.category FROM information_offers i MINUS " +
                    "(SELECT i1.category FROM information_offers i1 WHERE t.siteid = i1.siteid))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void databaseSetup() {
        dropBranchTableIfExists();

        try {
            String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        ToursiteModel branch1 = new ToursiteModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
        insertBranch(branch1);

        ToursiteModel branch2 = new ToursiteModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
        insertBranch(branch2);
    }

    private void dropBranchTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase("branch")) {
                    ps.execute("DROP TABLE branch");
                    break;
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> colNames = new Vector<String>();
        int colCount = metaData.getColumnCount();
        for (int col = 1; col <= colCount; col++) {
            colNames.add(metaData.getColumnName(col));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int colIndex = 1; colIndex <= colCount; colIndex++) {
                vector.add(rs.getObject(colIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, colNames);
    }
}
