package DAO;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to make queries from the First_Level_Divisions table of the database.
 */
public class DivisionsQuery {

    /** Retrieves the divisions from the First_Level_Divisions table in the database and returns them as an ObservableList
     * @return ObservableList allDivisions
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        // Creates observable list allDivisions
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        // Query to select the entire First_Level_Divisions table
        String sql = "SELECT * FROM first_level_divisions";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("COUNTRY_ID");
            // Creates new division object
            Division division = new Division(divisionID, divisionName, countryID);
            // Adds division to ObservableList allDivisions
            allDivisions.add(division);
        }
        // Returns ObservableList allDivisions
        return allDivisions;
    }

    /** Retrieves the divisions from the First_Level_Divisions table in the database that match the country ID input as
     * a parameter. It returns the matching divisions as an ObservableList.
     * @param countryID int value countryID
     * @return ObservableList allDivisions
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<Division> getAllDivisions(int countryID) throws SQLException {
        // Creates observable list allDivisions
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        // Query to select the Division matching the Country ID input as parameter
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int cID = rs.getInt("COUNTRY_ID");
            // Creates new division object
            Division division = new Division(divisionID, divisionName, cID);
            // Adds division to ObservableList allDivisions
            allDivisions.add(division);
        }
        // Returns ObservableList allDivisions
        return allDivisions;
    }
}
