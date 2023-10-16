package Controller;

import DAO.AppointmentsQuery;
import DAO.ContactsQuery;
import DAO.CustomersQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/** This class contains control logic for the Reports Screen, which is used to display various reports regarding
 * appointments and customer information.
 */
public class ReportsScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Appointment, Integer> apptIDTC;

    @FXML
    private TableView<Appointment> apptsTableView;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<Contact> contactCB;

    @FXML
    private TableColumn<Appointment, Integer> customerIDTC;

    @FXML
    private TableColumn<Appointment, String> descriptionTC;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeTC;

    @FXML
    private ComboBox<String> divisionCB;

    @FXML
    private Button logoutButton;

    @FXML
    private ComboBox<Month> monthCB;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeTC;

    @FXML
    private TableColumn<Appointment, String> titleTC;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private TableColumn<Appointment, String> typeTC;

    @FXML
    private TextArea apptsPerMonthTypeTA;

    @FXML
    private TextArea customersPerDivisionTA;


    /** Displays a contact's appointments filtered by the contact selected in the Contact ComboBox.
     * @param event selecting a Contact from the Contact ComboBox
     * @throws SQLException throws SQL exceptions
     */
    @FXML
    void filterByContact(ActionEvent event) throws SQLException {
        // Saves the ID of the contact selected by user to contactIDSelected variable
        int contactIDSelected = contactCB.getValue().getContactID();
        // Creates list to hold all appointments of contact selected by user
        ObservableList<Appointment> allContactAppointments = FXCollections.observableArrayList();
        // Creates list of all appointments
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Iterates through list of all appointments
        for (Appointment a: allAppointments) {
            /* If current appointment contact ID matches selected contact ID, appointment is added to list of appointments
            for that contact */
            if (a.getContactID() == contactIDSelected) {
                allContactAppointments.add(a);
            }
        }
        // Populates the Appointments TableView and associated TableColumns
        apptsTableView.setItems(allContactAppointments);
        apptIDTC.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionTC.setCellValueFactory(new PropertyValueFactory<>("description"));
        startTimeTC.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeTC.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDTC.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /** Displays the number of customers per division
     * @param event selecting a division from the Division ComboBox
     * @throws SQLException throws SQL exceptions
     */
    @FXML
    void filterByDivision (ActionEvent event) throws SQLException {
        // Saves the division name selected by the user
        String divisionSelected = divisionCB.getValue();
        // Creates list of all customers
        ObservableList<Customer> allCustomers = CustomersQuery.getAllCustomers();

        // Creates counter variable and iterates through list allCustomers
        long counter = allCustomers.stream()
               // Filters out customers whose division does not match the selected division
               .filter(c -> c.getDivision().equals(divisionSelected))
                // Counts the customers who were not filtered out (their division matches the selected division)
               .count();
        // Populates the Customers Per Division Text Area with the counter value
        customersPerDivisionTA.setText(Long.toString(counter));
    }

    /** Displays the appointment types occurring within the selected month
     * @param event selecting a month from the Month ComboBox
     * @throws SQLException throws SQL exceptions
     */
    @FXML
    void filterByMonth(ActionEvent event) throws SQLException {
        // Saves month selected by user to variable selectedMonth
        Month selectedMonth = monthCB.getValue();
        // Creates list of all appointments
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Creates list to hold all appointment types occurring in selected month
        ObservableList<String> selectedMonthType = FXCollections.observableArrayList();
        // Iterates through list of all appointments
        for (Appointment a: allAppointments) {
            /* If the current appointment occurs within the selected month, its type is added to the selectedMonthTypeList
            if the type is not already in the list
             */
            if (a.getStartTime().getMonth().equals(selectedMonth) || a.getEndTime().getMonth().equals(selectedMonth)) {
                if (!selectedMonthType.contains(a.getType().toUpperCase())) {
                    selectedMonthType.add(a.getType().toUpperCase());
                }
            }
        }
        // Sets Type ComboBox to hold list of all appointment types occurring in the selected month
        typeCB.setItems(selectedMonthType);
    }

    /** Displays the number of appointments matching the month and type selected by the user.
     * @param event selecting a type from the Type ComboBox
     * @throws SQLException throws SQL exceptions
     */
    @FXML
    void filterByType(ActionEvent event) throws SQLException {
        // Saves month selected by user to variable selectedMonth
        Month selectedMonth = monthCB.getValue();
        // Saves type selected by user to variable selectedType
        String selectedType = typeCB.getValue().toUpperCase();
        // Creates list of all appointments
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Creates counter variable and iterates through list allAppointments
        long counter = allAppointments.stream()
                // Filters out appointments that don't fall within the selected month
                .filter(a -> a.getStartTime().getMonth().equals(selectedMonth) || a.getEndTime().getMonth().equals(selectedMonth))
                // Filters out appointments that are not the selected type
                .filter(a -> a.getType().toUpperCase().equals(selectedType))
                // Counts the appointments that were not filtered out (they fall within the selected month, and are the selected type)
                .count();
        // Sets apptsPerMonthTypeTA to display the counter value
        apptsPerMonthTypeTA.setText(Long.toString(counter));
    }

    /** Logs the user out of the application by returning to the Login Screen.
     * @param actionEvent clicking the Logout button
     * @throws IOException Throws input/output exception
     */
    public void openLoginScreen(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Closes the Reports Screen and opens the Main Screen
     * @param actionEvent clicking the Back button
     * @throws IOException Throws input/output exception
     */
    public void openMainScreen(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Initializes the ReportsScreenController and populates the Contact, Type, Location, and Month ComboBoxes.
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

            // Creates list of all appointments
            ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
            // Creates list to hold months that have appointments
            ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
            // Iterates through list of all appointments
            for (Appointment a: allAppointments) {
                // If the appointmentMonths list doesn't contain the current appointment start or end month, it's added to the list
                if (!appointmentMonths.contains(a.getStartTime().getMonth())) {
                    appointmentMonths.add(a.getStartTime().getMonth());
                }
                if (!appointmentMonths.contains(a.getEndTime().getMonth())) {
                    appointmentMonths.add(a.getEndTime().getMonth());
                }
            }
            // Sets Month ComboBox to hold list of months that have appointments
            monthCB.setItems(appointmentMonths);

            // Creates list of all customers
            ObservableList<Customer> allCustomers = CustomersQuery.getAllCustomers();
            // Creates list to hold all division names that have customers
            ObservableList<String> allDivisionNames = FXCollections.observableArrayList();
            // Iterates through list of all customers
            for (Customer c: allCustomers) {
                // If the allDivisionNames list does not contain the division name of the current customer, it is added to the list
                if (!allDivisionNames.contains(c.getDivision())) {
                    allDivisionNames.add(c.getDivision());
                }
            }
           // Sets Division ComboBox to hold list of all division names that have customers
            divisionCB.setItems(allDivisionNames);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
