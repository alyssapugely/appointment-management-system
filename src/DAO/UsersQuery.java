package DAO;

import Model.Division;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to make queries from the Users table of the database.
 */
public class UsersQuery {
    /** Retrieves the users from the Users table in the database and returns them as an ObservableList
     * @return ObservableList allUsers
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        // Creates observable list allUsers
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        // Query to select the entire Users table
        String sql = "SELECT * FROM users";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("Password");
            // Creates new user object
            User user = new User(userID, username, password);
            // Adds user to ObservableList allUsers
            allUsers.add(user);
        }
        // Returns ObservableList allUsers
        return allUsers;
    }
}
