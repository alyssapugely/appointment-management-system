package Controller;

import DAO.AppointmentsQuery;
import DAO.JDBC;
import Model.Appointment;
import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class contains control logic for the Login Screen, which is used to validate user login credentials and display
 * Time Zone information.
 */
public class LoginScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTF;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TextField timezoneTF;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTF;

    /** Validates the user input login information, and opens the Main Screen upon a valid username/password combination.
     * It displays alert messages if invalid login information is entered.
     * @param actionEvent clicking the Login Button
     * @throws SQLException throws SQL exception
     * @throws IOException throws input/output exception
     */
    public void validateLogin(ActionEvent actionEvent) throws SQLException, IOException {
        Boolean valid = false;

        // Displays alert if username field is left blank
        if (usernameTF.getText().isBlank()) {
            Alerts.usernameEmptyAlert();
            return;
        }

        // Displays alert if password field is left blank
        if (passwordTF.getText().isBlank()) {
            Alerts.passwordEmptyAlert();
            return;
        }

        // Retrieves username and password strings input by user
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        // Queries Users table for matching username and password combination input by user
        String sql = "SELECT * FROM Users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        // If the result set contains values, a match was found
        while (rs.next()) {
            valid = true;

            // The successful login attempt is added to the login activity file
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("User " + username + " successfully logged in at " + simpleDateFormat.format(date) + " " + ZoneId.systemDefault().toString() + " time zone.\n");
            fileWriter.close();

            // The Main Screen opens
            stage = (Stage)loginButton.getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            // Displays alert to inform user if there are or aren't any upcoming appointments
            appointmentAlert();
        }

        // If a match wasn't found, an alert message is displayed
        if (!valid) {
            Alerts.invalidLoginAlert();

            // The failed login attempt is added to the login activity file
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            fileWriter.write("User " + username + " gave invalid log in at " + simpleDateFormat.format(date) + " " + ZoneId.systemDefault().toString() + " time zone.`\n");
            fileWriter.close();
        }
    }

    /** After a successful login, a message is displayed informing the user if there are, or aren't, any appointments
     * starting within 15 minutes of login.
     * @throws SQLException throws SQL exception
     */
    public void appointmentAlert() throws SQLException {
        // Saves user's current date and time to variable currentTime
        LocalDateTime currentTime = LocalDateTime.now();
        // Saves user's current date and time plus 15 minutes to variable currentPlus15
        LocalDateTime currentPlus15 = currentTime.plusMinutes(15);
        // Creates new ObservableList to hold upcoming appointments within 15 minutes of login
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        // Creates ObservableList of all existing appointments
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Iterates through list of all appointments
        for (Appointment a: allAppointments) {
            // If an appointment begins within 15 minutes of login
            if (a.getStartTime().isAfter(currentTime) && a.getStartTime().isBefore(currentPlus15)) {
                // The appointment is added to the upcomingAppointments list
                upcomingAppointments.add(a);
                int appointmentID = a.getAppointmentID();
                LocalDateTime startTime = a.getStartTime();
                // An alert is displayed informing the user of the details of the upcoming appointment
                Alerts.upcomingAppointmentAlert(appointmentID, startTime);
            }
        }
        // Displays alert letting user know there are no upcoming appointments if list is empty
        if (upcomingAppointments.isEmpty()) {
            Alerts.noUpcomingAppointmentsAlert();
        }
    }

    /** Closes the application
     * @param actionEvent clicking the Exit button
     */
    public void closeApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** Initializes the LoginScreenController and converts text from English to French if the user's locale is set to French.
     * @param url location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Locates properties file and obtains default locale
        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());
        // If user's locale is French, login screen text is converted to French based on values from properties file
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            loginLabel.setText(rb.getString("Login"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            timeZoneLabel.setText(rb.getString("Timezone"));
            loginButton.setText(rb.getString("Login"));
            exitButton.setText(rb.getString("Exit"));
        }
        // Displays user's time zone in text field
        timezoneTF.setText(ZoneId.systemDefault().toString());
    }
}
