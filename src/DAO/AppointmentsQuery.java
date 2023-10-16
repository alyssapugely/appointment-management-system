package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/** This class is used to make queries from the Appointments table of the database.
 */
public class AppointmentsQuery {

    /** Retrieves the appointments from the Appointments table in the database and returns them as an ObservableList
     * @return ObservableList getAllAppointments
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        // Creates observable list allAppointments
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        // Query to select the entire Appointments table, plus matching contact names from Contacts table
        String sql = "SELECT * FROM Appointments tableA, Contacts tableB WHERE tableA.Contact_ID = tableB.Contact_ID";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp startDate = rs.getTimestamp("Start");
            Timestamp endDate = rs.getTimestamp("End");
            // Converts TimeStamp to LocalDateTime
            LocalDateTime startTime = startDate.toLocalDateTime();
            LocalDateTime endTime = endDate.toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            // Creates new appointment object
            Appointment appointment = new Appointment(appointmentID, title, description, location, contactName, type, startTime, endTime, customerID, userID, contactID);
            // Adds appointment to ObservableList allAppointments
            allAppointments.add(appointment);
        }
        // Returns ObservableList allAppointments
        return allAppointments;
    }

    /** Inserts a new appointment into the Appointments table in the database
     * @param title string variable title
     * @param description string variable description
     * @param location string variable location
     * @param type string variable type
     * @param startTime timestamp variable startTime
     * @param endTime timestamp variable endTime
     * @param customerID int variable customerID
     * @param userID int variable userID
     * @param contactID int variable contactID
     * @throws SQLException throws SQL exceptions
     */
    public static void insert(String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, int customerID, int userID, int contactID) throws SQLException {
        // Query to insert new appointment into Appointments table in database
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTime);
        ps.setTimestamp(6, endTime);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        // Executes query and saves number of rows affected to int variable
        int rowsAffected = ps.executeUpdate();
    }


    /** Updates an existing appointment in the Appointments table in the database
     * @param title string variable title
     * @param description string variable description
     * @param location string variable location
     * @param type string variable type
     * @param startTime timestamp variable startTime
     * @param endTime timestamp variable endTime
     * @param customerID int variable customerID
     * @param userID int variable userID
     * @param contactID int variable contactID
     * @param appointmentID int variable appointmentID
     * @throws SQLException throws SQL exceptions
     */
    public static void update(String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, int customerID, int userID, int contactID, int appointmentID) throws SQLException {
        // Query to update existing appointment in Appointments table in database
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTime);
        ps.setTimestamp(6, endTime);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, appointmentID);
        // Executes query and saves number of rows affected to int variable
        int rowsAffected = ps.executeUpdate();
    }
}
