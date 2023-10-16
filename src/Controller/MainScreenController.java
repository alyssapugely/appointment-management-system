package Controller;

import DAO.AppointmentsQuery;
import DAO.CustomersQuery;
import DAO.JDBC;
import Model.Appointment;
import Model.Customer;
import Utilities.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class contains control logic for the Main Screen, which is used to add new appointments and customers and update
 * or delete existing appointments and customers. It also contains a feature to filter appointments by current week or month,
 * and buttons to open the Reports Screen or log out of the application.
 */
public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button addApptButton;

    @FXML
    private Button addCustButton;

    @FXML
    private TableColumn<Customer, String> addressTC;

    @FXML
    private RadioButton allApptsRB;

    @FXML
    private TableColumn<Appointment, Integer> apptIDTC;

    @FXML
    private TableView<Appointment> apptsTableView;

    @FXML
    private TableColumn<Appointment, String> contactNameTC;

    @FXML
    private RadioButton currentMonthRB;

    @FXML
    private RadioButton currentWeekRB;

    @FXML
    private TableColumn<Customer, Integer> custIDTC;

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TableColumn<Appointment, Integer> apptCustIDTC;

    @FXML
    private Button deleteApptButton;

    @FXML
    private Button deleteCustButton;

    @FXML
    private TableColumn<Appointment, String> descriptionTC;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeTC;

    @FXML
    private TableColumn<Appointment, String> locationTC;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<Customer, String> nameTC;

    @FXML
    private TableColumn<Customer, String> phoneTC;

    @FXML
    private TableColumn<Customer, String> postalTC;

    @FXML
    private Button reportsButton;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeTC;

    @FXML
    private TableColumn<Customer, String> stateTC;

    @FXML
    private TableColumn<Appointment, String> titleTC;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private TableColumn<Appointment, String> typeTC;

    @FXML
    private Button updateApptButton;

    @FXML
    private Button updateCustButton;

    @FXML
    private TableColumn<Appointment, Integer> userIDTC;

    /** Opens the Add Appointment screen.
     * @param actionEvent clicking the Add button under the Appointments table
     * @throws IOException throws input/output exception
     */
    public void addAppointment (ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddApptScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** If appointment is selected, the Update Appointment Screen opens with fields populated with data corresponding to
     * the selected appointment.
     * @param actionEvent clicking the Update button under the Appointments table
     * @throws IOException throws input/output exception
     * @throws SQLException throws SQL exceptions
     */
    public void updateAppointment (ActionEvent actionEvent) throws IOException, SQLException {
        // Saves appointment selected by user to variable selectedAppt
        Appointment selectedAppt = apptsTableView.getSelectionModel().getSelectedItem();
        // If an appointment is selected by user, the Update Appointment Screen opens
        if (selectedAppt != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateApptScreen.fxml"));
            loader.load();
            UpdateApptScreenController updateApptScreenController = loader.getController();
            updateApptScreenController.sendSelectedAppt(selectedAppt);
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            // If an appointment is not selected, an alert message is displayed
            Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment must be selected to update");
            alert.show();
        }
    }

    /** Deletes the appointment selected by the user in the Appointments table, or displays an alert message if the user
     * clicks the delete button without first selecting an appointment.
     * @param actionEvent clicking the Delete button under the Appointments table
     * @throws SQLException throws SQL exception
     */
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        // Saves appointment selected by user to variable selectedAppt
        Appointment selectedAppt = apptsTableView.getSelectionModel().getSelectedItem();
        // If an appointment is selected by user
        if (selectedAppt != null) {
            // Saves appointment ID to variable appointmentID
            int appointmentID = selectedAppt.getAppointmentID();
            // Saves appointment type to variable appointmentType
            String appointmentType = selectedAppt.getType();
            // Alert is displayed to confirm appointment deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this appointment?");
            Optional<ButtonType> userSelection = alert.showAndWait();
            // If the user clicks OK button, the appointment is deleted
            if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
                // Run query to delete appointment from Appointments table in database
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, appointmentID);
                boolean delete = ps.execute();
                // An alert is displayed informing user of successful deletion
                Alerts.appointmentCancelledAlert(appointmentID, appointmentType);
                // The Appointments TableView is populated with the updated list of all appointments
                apptsTableView.setItems(AppointmentsQuery.getAllAppointments());
                populateAppointmentsTable();
            }
            // If no appointment is selected and the delete button is clicked, an error message is displayed
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment must be selected to delete.");
            alert.show();
        }
    }

    /** Display all existing appointments in the Appointments TableView.
     * @param actionEvent selecting the All Appointments radio button
     * @throws SQLException throws SQL exception
     */
    public void onAllAppointments(ActionEvent actionEvent) throws SQLException {
        apptsTableView.setItems(AppointmentsQuery.getAllAppointments());
    }

    /** Displays all appointments occurring within the current month in the Appointments TableView.
     * @param actionEvent selecting the Current Month radio button
     * @throws SQLException throws SQL exceptions
     */
    public void onCurrentMonth(ActionEvent actionEvent) throws SQLException {
        // Saves user's current date/time to variable currentTime
        LocalDateTime currentTime = LocalDateTime.now();
        // Saves user's current month to variable currentMonth
        int currentMonth = currentTime.getMonthValue();
        // Creates ObservableList containing all existing appointment
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Creates ObservableList to hold appointments occurring within the current month
        ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();
        // Iterates through list of all appointments
        for (Appointment a : allAppointments) {
            // If appointment falls within the current month, it's added to list of upcoming appointments
            if (currentMonth == a.getStartTime().getMonthValue() && currentTime.getYear() == a.getStartTime().getYear()) {
                currentMonthAppointments.add(a);
            }
        }
        // Displays only appointments occurring within the current month in the Appointments TableView
        apptsTableView.setItems(currentMonthAppointments);
    }

    /** Displays all appointments occurring within the current week in the Appointments TableView
     * @param actionEvent selecting the Current Week radio button
     * @throws SQLException throws SQL exception
     */
    public void onCurrentWeek(ActionEvent actionEvent) throws SQLException {
        // Saves user's current date/time to variable currentTime
        LocalDateTime currentTime = LocalDateTime.now();
        // Saves user's current date to variable currentDate
        LocalDate currentDate = currentTime.toLocalDate();
        // Saves user's current day of week to variable currentDayName
        String currentDayName = LocalDateTime.now().getDayOfWeek().toString();
        // Creates ObservableList containing all existing appointment
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Creates ObservableList to hold appointments occurring within the current week
        ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
        // Iterates through all appointment
        for (Appointment a: allAppointments) {
            // If the current day is Sunday
            if (currentDayName.equalsIgnoreCase("Sunday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(1)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(7))) {
                    currentWeekAppointments.add(a);
                }
            }
            // If the current day is Monday
            if (currentDayName.equalsIgnoreCase("Monday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(2)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(6))) {
                    currentWeekAppointments.add(a);
                }
            }
            // If the current day is Tuesday
            if (currentDayName.equalsIgnoreCase("Tuesday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(3)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(5))) {
                    currentWeekAppointments.add(a);
                }
            }
            // If the current day is Wednesday
            if (currentDayName.equalsIgnoreCase("Wednesday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(4)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(4))) {
                    currentWeekAppointments.add(a);
                }
            }
            // If the current day is Thursday
            if (currentDayName.equalsIgnoreCase("Thursday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(5)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(3))) {
                    currentWeekAppointments.add(a);
                }
            }
            // If the current day is Friday
            if (currentDayName.equalsIgnoreCase("Friday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(6)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(2))) {
                    currentWeekAppointments.add(a);
                }
            }
            // If the current day is Saturday
            if (currentDayName.equalsIgnoreCase("Saturday")) {
                // If an appointment is after past Saturday and before next Sunday, it's added to the list currentWeekAppointments
                if (a.getStartTime().toLocalDate().isAfter(currentDate.minusDays(7)) && a.getStartTime().toLocalDate().isBefore(currentDate.plusDays(1))) {
                    currentWeekAppointments.add(a);
                }
            }
        }
        // The Appointments TableView is set to display the list of the current week's appointments
        apptsTableView.setItems(currentWeekAppointments);
    }

    /** Opens the Reports Screen
     * @param actionEvent clicking the Reports button
     * @throws IOException throws input/output exception
     */
    public void openReports(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ReportsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Logs the user out of the application by returning to the Login Screen
     * @param actionEvent clicking the Logout button
     * @throws IOException throws input/output exception
     */
    public void openLoginScreen(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Opens the Add Customer Screen
     * @param actionEvent clicking the Add button under the Customers table
     * @throws IOException Throws input/output exception
     */
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Deletes the customer selected by the user in the Customers table, as well as deletes the customer's appointments.
     * It displays an alert message if the user clicks the delete button without first selecting a customer.
     * @param actionEvent clicking the Delete button under the Customers table
     * @throws SQLException throws SQL exception
     */
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        // Saves customer selected by user to variable selectedCustomer
        Customer selectedCustomer = custTableView.getSelectionModel().getSelectedItem();
        // If a customer is selected by user
        if (selectedCustomer != null) {
            // Saves customer ID to variable customerID
            int customerID = selectedCustomer.getCustomerID();
            // Saves customer name to variable customerName
            String customerName = selectedCustomer.getCustomerName();
            // Alert is displayed to confirm customer deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?\n" +
                    "This customer's appointments will also be deleted.");
            Optional<ButtonType> userSelection = alert.showAndWait();
            // If the user clicks OK button, the customer and their appointments are deleted
            if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
                // Run query to delete customer's appointments from Appointments table in database
                String sql1 = "DELETE FROM appointments WHERE Customer_ID = ?";
                PreparedStatement ps1 = JDBC.connection.prepareStatement(sql1);
                ps1.setInt(1, customerID);
                boolean delete1 = ps1.execute();
                // Run query to delete customer from Customers table in database
                String sql2 = "DELETE FROM customers WHERE Customer_ID = ?";
                PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
                ps2.setInt(1, customerID);
                boolean delete2 = ps2.execute();
                // An alert is displayed informing user of successful deletion
                Alerts.customerDeletedAlert(customerID, customerName);
                // The Customers TableView is populated with the updated list of all customers
                custTableView.setItems(CustomersQuery.getAllCustomers());
                populateCustomersTable();
                // The Appointments TableView is populated with the updated list of all appointments
                apptsTableView.setItems(AppointmentsQuery.getAllAppointments());
                populateAppointmentsTable();
            }
            // If no customer is selected and the delete button is clicked, an error message is displayed
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer must be selected to delete.");
            alert.show();
        }
    }

    /** If a customer is selected, it opens the Update Customer Screen populated with information from the selected customer.
     * If a customer is not selected, an error message is displayed.
     * @param actionEvent clicking the Update button under the Customers table
     * @throws IOException throws input/output exception
     * @throws SQLException throws SQL exceptions
     */
    public void updateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        // Saves customer selected by user to variable selectedCustomer
        Customer selectedCustomer = custTableView.getSelectionModel().getSelectedItem();
        // If a customer is selected by user, the Update Customer Screen opens
        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateCustScreen.fxml"));
            loader.load();

            UpdateCustScreenController updateCustScreenController = loader.getController();
            updateCustScreenController.sendSelectedCustomer(selectedCustomer);

            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            // If a customer is not selected, an alert message is displayed
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer must be selected to update.");
            alert.show();
        }
    }


    /** Correlates which columns from the Appointments table in the database should populate the columns in the
     * Appointments TableView.
     */
    public void populateAppointmentsTable() {
        apptIDTC.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionTC.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTC.setCellValueFactory(new PropertyValueFactory<>("location"));
        startTimeTC.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeTC.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactNameTC.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptCustIDTC.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDTC.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /** Correlates which columns from the Customers table in the database should populate the columns in the Customers TableView.
     */
    public void populateCustomersTable() {
        custIDTC.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameTC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneTC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        stateTC.setCellValueFactory(new PropertyValueFactory<>("division"));
        postalTC.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    }

    /** Initializes the MainScreenController and populates the Appointments and Customers Tableviews.
     * @param url location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populates the Appointments TableView and associated TableColumns
        try {
            apptsTableView.setItems(AppointmentsQuery.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateAppointmentsTable();

        // Populates the Customer TableView and associated TableColumns
        try {
            custTableView.setItems(CustomersQuery.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        populateCustomersTable();
    }
}
