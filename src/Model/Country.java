package Model;
import DAO.CountriesQuery;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/** This class is used to create Country objects.
 */
public class Country {
    private int countryID;
    private String countryName;

    /** Defines the necessary parameters to create a Country object
     * @param countryID country's ID, int variable
     * @param countryName country's name, String variable
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /** Returns the country's ID
     * @return int variable countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /** Sets the country's ID to the int value passed as a parameter
     * @param countryID int variable countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Returns the country's name
     * @return string variable countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /** Sets the country's name to the String value passed as a parameter
     * @param countryName string variable countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** Takes a country name String as a parameter and returns a Country object
     * @param countryName string variable countryName
     * @return country object
     * @throws SQLException throws SQL exceptions
     */
    public static Country stringToCountry(String countryName) throws SQLException {
        // Creates an ObservableList of all countries
        ObservableList<Country> allCountries = CountriesQuery.getAllCountries();
        Country country = null;
        // Iterates through allCountries list
        for (Country c: allCountries) {
            // If the name of the Country object matches the countryName String parameter, the Country object is saved
            if (c.getCountryName().equals(countryName)) {
                country = c;
            }
        }
        // Returns the matching Country object
        return country;
    }

    /** Returns the country's name when a country object is referenced
     * @return string variable countryName
     */
    @Override
    public String toString() {
        return countryName;
    }
}
