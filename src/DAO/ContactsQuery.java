package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to make queries from the Contacts table of the database.
 */
public class ContactsQuery {
    /** Retrieves the contacts from the Contacts table in the database and returns them as an ObservableList
     * @return ObservableList allContacts
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        // Creates observable list allContacts
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        // Query to select the entire Contacts table
        String sql = "SELECT * FROM contacts";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            String contactName = rs.getString("Contact_Name");
            int contactID = rs.getInt("Contact_ID");
            String contactEmail = rs.getString("Email");
            // Creates new contact object
            Contact contact =  new Contact(contactName, contactID, contactEmail);
            // Adds contact to ObservableList allContacts
            allContacts.add(contact);
        }
        // Returns ObservableList allContacts
        return allContacts;
    }
}

