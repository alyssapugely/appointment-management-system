package Controller;

import DAO.AppointmentsQuery;
import DAO.ContactsQuery;
import DAO.CustomersQuery;
import DAO.UsersQuery;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utilities.Alerts;
import Utilities.TimeManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class contains control logic for the Add Appointment Screen, which is used to create new appointments.
 */
public class AddApptScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Contact> contactCB;

    @FXML
    private ComboBox<Integer> custIdCB;

    @FXML
    private TextField descriptionTF;

    @FXML
    private DatePicker endDateDP;

    @FXML
    private ComboBox<String> endTimeCB;

    @FXML
    private TextField idTF;

    @FXML
    private TextField locationTF;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDateDP;

    @FXML
    private ComboBox<String> startTimeCB;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField typeTF;

    @FXML
    private ComboBox<Integer> userIdCB;

    /** Cancels new appointment creation by returning to the Main Screen
     * @param event clicking the Cancel Button
     * @throws IOException throws input/ouput exception
     */
    @FXML
    void cancelAction(ActionEvent event) throws IOException {
        // Alert is displayed to confirm cancellation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this action?\nNew appointment" +
                " will not be saved.");
        Optional<ButtonType> userSelection = alert.showAndWait();
        // If the user clicks OK button, the Add Appointment Screen closes and the Main Screen opens
        if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Uses information input by the user to insert a new appointment into the Appointments table of the database.
     * @param event clicking the Save button on the Add Appointment Screen
     * @throws SQLException throws SQL exceptions
     * @throws IOException  throws input/output exceptions
     */
    @FXML
    void saveNewAppointment(ActionEvent event) throws SQLException, IOException {
        // Retrieves title input by user and saves input to variable, or displays error message if field is left blank
        String title = titleTF.getText();
        if (title.isBlank()) {
            Alerts.fieldIsBlankError("Title");
            return;
        }
        // Retrieves type input by user and saves input to variable, or displays error message if field is left blank
        String type = typeTF.getText();
        if (type.isBlank()) {
            Alerts.fieldIsBlankError("Type");
            return;
        }
        // Retrieves description input by user and saves input to variable, or displays error message if field is left blank
        String description = descriptionTF.getText();
        if (description.isBlank()) {
            Alerts.fieldIsBlankError("Description");
            return;
        }
        // Retrieves location input by user and saves input to variable, or displays error message if field is left blank
        String location = locationTF.getText();
        if (location.isBlank()) {
            Alerts.fieldIsBlankError("Location");
            return;
        }
        // Retrieves contact input by user and saves input to variable, or displays error message if field is left blank
        if (contactCB.getValue() == null) {
            Alerts.fieldIsBlankError("Contact");
            return;
        }
        int contactID = contactCB.getValue().getContactID();
        // Retrieves start date input by user and saves input to variable, or displays error message if field is left blank
        if (startDateDP.getValue() == null) {
            Alerts.fieldIsBlankError("Start Date");
            return;
        }
        LocalDate startDate = startDateDP.getValue();
        // Retrieves start time input by user and saves input to variable, or displays error message if field is left blank
        if (startTimeCB.getValue() == null) {
            Alerts.fieldIsBlankError("Start Time");
            return;
        }
        LocalTime localStartTime = TimeManagement.stringToLocalTime(startTimeCB.getValue());
        // Retrieves end date input by user and saves input to variable, or displays error message if field is left blank
        if (endDateDP.getValue() == null) {
            Alerts.fieldIsBlankError("End Date");
            return;
        }
        LocalDate endDate = endDateDP.getValue();
        // Retrieves end time input by user and saves input to variable, or displays error message if field is left blank
        if (endTimeCB.getValue() == null) {
            Alerts.fieldIsBlankError("End Time");
            return;
        }
        LocalTime localEndTime = TimeManagement.stringToLocalTime(endTimeCB.getValue());
        // Retrieves customer ID input by user and saves input to variable, or displays error message if field is left blank
        if (custIdCB.getValue() == null) {
            Alerts.fieldIsBlankError("Customer ID");
            return;
        }
        int customerID = custIdCB.getValue();
        // Retrieves user ID input by user and saves input to variable, or displays error message if field is left blank
        if (userIdCB.getValue() == null) {
            Alerts.fieldIsBlankError("User ID");
            return;
        }
        int userID = userIdCB.getValue();

        // Creates LocalDateTime variables from LocalTime and LocalDate variables
        LocalDateTime localStartDateTime = LocalDateTime.of(startDate, localStartTime);
        LocalDateTime localEndDateTime = LocalDateTime.of(endDate, localEndTime);

        // If the appointment starts and ends at the same time, an alert is displayed
        if (localStartDateTime.equals(localEndDateTime)) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Scheduling Error");
            alert.setContentText("The appointment starts and ends at the same time.");
            alert.showAndWait();
            return;
        }

        // If the start date/time is after the end date/time, an alert is displayed
        if (localStartDateTime.isAfter(localEndDateTime)) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Scheduling Error");
            alert.setContentText("The appointment start date or time occurs after the end date or time.");
            alert.showAndWait();
            return;
        }

        // If no fields are left empty, validates that the appointment start time falls within business hours
        if (TimeManagement.businessHoursValidation(localStartTime, "start") == false) {
            return;
        }
        // Validates that the appointment end time falls within business hours
        if (TimeManagement.businessHoursValidation(localEndTime, "end") == false) {
            return;
        }

        // Validates that new appointment will not overlap with any existing appointments for the customer
        if (TimeManagement.appointmentOverlapValidation(localStartDateTime, localEndDateTime, customerID) == false) {
            return;
        }

        // If all validations pass, converts LocalDateTime objects to Timestamp objects to insert into database
        Timestamp startTimestamp = Timestamp.valueOf(localStartDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(localEndDateTime);

        /* Uses insert method of AppointmentsQuery class to insert a new appointment into the Appointments table of the
        database, using above variables */
        AppointmentsQuery.insert(title, description, location, type, startTimestamp, endTimestamp, customerID, userID, contactID);

        // When Save button is clicked, the application returns to the Main Screen
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Initializes the AddApptScreenController and populates the Contact, Customer ID, User ID, Start Time, and End Time ComboBoxes.
     * @param url location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Creates list of all contacts
            ObservableList<Contact> allContacts = ContactsQuery.getAllContacts();
            // Sets Contact ComboBox to hold list of all contact names
            contactCB.setItems(allContacts);

            // Creates list of all customers
            ObservableList<Customer> allCustomers = CustomersQuery.getAllCustomers();
            // Creates list to hold all customer IDs
            ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
            // Iterates through allCustomers list and adds each customer ID to allCustomerIDs list
            for (Customer c : allCustomers) {
                int customerID = c.getCustomerID();
                allCustomerIDs.add(customerID);
            }
            // Sets Customer ID ComboBox to hold list of all customer IDs
            custIdCB.setItems(allCustomerIDs);

            // Creates list of all users
            ObservableList<User> allUsers = UsersQuery.getAllUsers();
            // Creates list to hold all user IDs
            ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
            // Iterates through allUsers list and adds each user ID to allUserIDs list
            for (User u : allUsers) {
                int userID = u.getUserID();
                allUserIDs.add(userID);
            }
            // Sets User ID ComboBox to hold list of all user IDs
            userIdCB.setItems(allUserIDs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Populates Start Time and End Time ComboBoxes with list of all possible appointment times
        ObservableList<String> allAppointmentTimes = TimeManagement.allAppointmentTimes();
        startTimeCB.setItems(allAppointmentTimes);
        endTimeCB.setItems(allAppointmentTimes);
    }
}


