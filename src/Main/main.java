package Main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/** This class contains methods that start the application. This application is an Appointment Management System that
 * can create, update, and delete appointments and users. It also uses MySQL Workbench to store information about the
 * appointments and users in the system.
 */

public class main extends Application {
    /** Creates the primary FXML stage and loads the Login Screen
     * @param stage primary stage
     * @throws Exception throws objects derived from the Exception class
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setTitle("Appointment Management System");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /** This is the entry point to the program. It also opens and closes the connection with the MySQL database
     * @param args command line arguments
     * @throws SQLException throws SQL exceptions
     */
    public static void main(String[] args) throws SQLException {
        // Opens database connection
        JDBC.makeConnection();
        launch(args);
        // Closes database connection
        JDBC.closeConnection();
    }
}
