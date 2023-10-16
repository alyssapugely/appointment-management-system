package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to make queries from the Customers table of the database.
 */
public class CustomersQuery {
    /** Retrieves the customers from the Customers table in the database and returns them as an ObservableList
     * @return ObservableList allCustomers
     * @throws SQLException throws SQL exceptions
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        // Creates observable list allCustomers
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        // Query to select the entire Customers table, plus matching division IDs from first_level_divisions table
        String sql = "SELECT * FROM customers tableA, first_level_divisions tableB, countries tableC WHERE tableA.Division_ID = tableB.Division_ID AND tableB.Country_ID = tableC.Country_ID";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        // Executes the query and saves results in a result set
        ResultSet rs = ps.executeQuery();
        // Iterates through result set and saves values to java variables
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String phoneNumber = rs.getString("Phone");
            String address = rs.getString("Address");
            String country = rs.getString("Country");
            String postalCode = rs.getString("Postal_Code");
            String division = rs.getString("Division");
            int divisionID = rs.getInt("Division_ID");
            // Creates new customer object
            Customer customer = new Customer(customerID, customerName, phoneNumber, address, country, postalCode, division, divisionID);
            // Adds customer to ObservableList allCustomers
            allCustomers.add(customer);
        }
        // Returns ObservableList allCustomers
        return allCustomers;
    }

    /** Inserts a new customer into the Customers table in the database
     * @param customerName string variable customerName
     * @param phoneNumber string variable phoneNumber
     * @param address string variable address
     * @param postalCode string variable postalCode
     * @param divisionID int variable divisionID
     * @throws SQLException throws SQL exceptions
     */
    public static void insert(String customerName, String phoneNumber, String address, String postalCode, int divisionID) throws SQLException {
        // Query to insert new customer into Customers table in database
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        // Executes query and saves number of rows affected to int variable
        int rowsAffected = ps.executeUpdate();
    }

    /** Updates an existing customer in the Customers table in the database
     * @param customerName string variable customerName
     * @param phoneNumber string variable phoneNumber
     * @param address string variable address
     * @param postalCode string variable postalCode
     * @param divisionID int variable divisionID
     * @param customerID int variable customerID
     * @throws SQLException throws SQL exceptions
     */
    public static void update(String customerName, String phoneNumber, String address, String postalCode, int divisionID, int customerID) throws SQLException {
        // Query to update existing customer in Customers table in database
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        // Creates prepared SQL statement to query
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);
        // Executes query and saves number of rows affected to int variable
        int rowsAffected = ps.executeUpdate();
    }
}
