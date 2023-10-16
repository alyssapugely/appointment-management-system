package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** This JDBC (Java Database Connectivity) class contains methods to open and close the connection with the specified
 * MySQL database.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface
    private static PreparedStatement preparedStatement;

    /** Connects the application to the specified database
     */
    public static void makeConnection() {
        try {
            // Locate Driver
            Class.forName(driver);
            // Reference the Connection object
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /** Returns the specified connection
     * @return specified connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /** Ends the application's connection to the specified database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Creates the prepared statement
     * @param sqlStatement SQL statement string
     * @param conn databases connection
     * @throws SQLException throws SQL exceptions
     */
    public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
        if (conn != null)
            preparedStatement = conn.prepareStatement(sqlStatement);
        else
            System.out.println("Prepared Statement Creation Failed!");
    }

    /** Returns the prepared statement.=
     * @return prepared statement
     * @throws SQLException throws SQL exceptions
     */
    public static PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement != null)
            return preparedStatement;
        else System.out.println("Null reference to Prepared Statement");
        return null;
    }
}