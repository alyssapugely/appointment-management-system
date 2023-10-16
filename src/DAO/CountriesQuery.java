package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to make queries from the Countries table of the database.
 */
public class CountriesQuery {
    /** Retrieves the countries from the Countries table in the database and returns them as an ObservableList.
     * @return ObservableList allCountries
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        // Creates observable list allCountries
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        // Query to select the entire Countries table
        String sql = "SELECT * FROM countries";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            // Creates new country object
            Country country = new Country(countryID, countryName);
            // Adds country to ObservableList allCountries
            allCountries.add(country);
        }
        // Returns ObservableList allCountries
        return allCountries;
    }
}
