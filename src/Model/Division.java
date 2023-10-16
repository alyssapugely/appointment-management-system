package Model;

import DAO.DivisionsQuery;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/** This class is used to create Division objects.
 */
public class Division {
    private int divisionID;
    private String divisionName;
    private int countryID;

    /** Defines the necessary parameters to create a Division object
     * @param divisionID division's ID, int variable
     * @param divisionName division's name, String variable
     * @param countryID country's ID, int variable
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /** Returns the division's ID
     * @return int variable divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** Sets the division's ID to the int value passed as a parameter
     * @param divisionID int variable divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** Returns the division's name
     * @return string variable divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /** Sets the division's name to the String value passed as a parameter
     * @param divisionName string variable divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
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

    /** Takes a division name String as a parameter and returns a Division object
     * @param divisionName string variable divisionName
     * @return division object
     * @throws SQLException throws SQL exceptions
     */
    public static Division stringToDivision(String divisionName) throws SQLException {
        // Creates an ObservableList of all divisions
        ObservableList<Division> allDivision = DivisionsQuery.getAllDivisions();
        Division division = null;
        // Iterates through allDivisions list
        for (Division d: allDivision) {
            // If the name of the Division object matches the divisionName String parameter, the Division object is saved
            if (d.getDivisionName().equals(divisionName)) {
                division = d;
            }
        }
        // Returns the matching Division object
        return division;
    }

    /** Returns the Division name when a Division object is referenced
     * @return string variable divisionName
     */
    @Override
    public String toString() {
        return divisionName;
    }
}
