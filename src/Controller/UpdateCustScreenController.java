package Controller;

import DAO.CountriesQuery;
import DAO.CustomersQuery;
import DAO.DivisionsQuery;
import Model.Country;
import Model.Customer;
import Model.Division;
import Utilities.Alerts;
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
import java.util.Optional;
import java.util.ResourceBundle;

/** This class contains control logic for the Update Customer Screen, which is used to update the information of existing customers.
 */
public class UpdateCustScreenController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField addressTF;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Country> countryCB;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TextField postalCodeTF;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<Division> divisionCB;

    /** Cancels customer update by returning to the Main Screen
     * @param event clicking the Cancel Button
     * @throws IOException throws input/ouput exception
     */
    @FXML
    void cancelAction(ActionEvent event) throws IOException {
        // Alert is displayed to confirm cancellation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this action?\nCustomer" +
                " will not be updated.");
        Optional<ButtonType> userSelection = alert.showAndWait();
        // If the user clicks OK button, the Update Customer Screen closes and the Main Screen opens
        if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Uses information input by the user to update an existing customer in the Customers table of the database
     * @param event clicking the Save button on the Update Customer Screen
     * @throws SQLException throws SQL exceptions
     * @throws IOException throws input/output exceptions
     */
    @FXML
    void onSaveUpdateCustomer(ActionEvent event) throws SQLException, IOException {
        // Retrieves customer ID and saves it to integer variable
        int customerID = Integer.parseInt(idTF.getText());

        // Retrieves customer name input by user and saves input to variable, or displays error message if field is left blank
        String customerName = nameTF.getText();
        if (customerName.isBlank()) {
            Alerts.fieldIsBlankError("Name");
            return;
        }
        // Retrieves phone number input by user and saves input to variable, or displays error message if field is left blank
        String phoneNumber = phoneNumberTF.getText();
        if (phoneNumber.isBlank()) {
            Alerts.fieldIsBlankError("Phone Number");
            return;
        }
        // Retrieves address input by user and saves input to variable, or displays error message if field is left blank
        String address = addressTF.getText();
        if (address.isBlank()) {
            Alerts.fieldIsBlankError("Address");
            return;
        }
        // Displays error message if country field is left blank
        if (countryCB.getValue() == null) {
            Alerts.fieldIsBlankError("Country");
            return;
        }
        // Retrieves division input by user and saves input to variable, or displays error message if field is left blank
        if (divisionCB.getValue() == null) {
            Alerts.fieldIsBlankError("State/Province");
            return;
        }
        int divisionID = divisionCB.getValue().getDivisionID();
        // Retrieves postal code input by user and saves input to variable, or displays error message if field is left blank
        String postalCode = postalCodeTF.getText();
        if (postalCode.isBlank()) {
            Alerts.fieldIsBlankError("Postal Code");
            return;
        }
        /* Uses update method of CustomersQuery class to update the existing customer in the Customers table of the database,
        using above variables
         */
        CustomersQuery.update(customerName, phoneNumber, address, postalCode, divisionID, customerID);
        // When Save button is clicked, the application returns to the Main Screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Populates GUI fields with data corresponding to the customer selected by the user in the Main Screen Controller
     * @param selectedCustomer customer the user selects to update in the Main Screen Controller
     * @throws SQLException throws SQL exceptions
     */
    public void sendSelectedCustomer(Customer selectedCustomer) throws SQLException {
        // Variables are converted to the appropriate data type and then populated in the GUI fields
        idTF.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameTF.setText(selectedCustomer.getCustomerName());
        phoneNumberTF.setText(selectedCustomer.getPhoneNumber());
        addressTF.setText(selectedCustomer.getAddress());
        String countryName = selectedCustomer.getCountry();
        Country country = Country.stringToCountry(countryName);
        countryCB.setValue(country);
        String division = selectedCustomer.getDivision();
        divisionCB.setValue(Division.stringToDivision(division));
        postalCodeTF.setText(selectedCustomer.getPostalCode());
        // Gets ID of customer's country
        int countryID = country.getCountryID();
        // Sets Division ComboBox to hold list of division names that match country ID of user's country
        divisionCB.setItems(DivisionsQuery.getAllDivisions(countryID));

    }

    /** Displays states or provinces in the Division ComboBox that belong to the country selected by the user in the
     * Country ComboBox.
     * @param actionEvent selecting a country from the Country ComboBox
     * @throws SQLException throws SQL exceptions
     * @throws IOException throws input/output exceptions
     */
    public void selectCountry(ActionEvent actionEvent) throws SQLException, IOException {
        // Saves the ID of the country selected by user to countrySelectedID variable
        int countrySelectedID = countryCB.getValue().getCountryID();
        // Sets Division ComboBox to hold list of division names that match country ID of selected country
        divisionCB.setItems(DivisionsQuery.getAllDivisions(countrySelectedID));
    }

    /** Initializes the UpdateCustScreenController and populates the Country ComboBox with a list of all country names
     * @param url location used to resolve paths for the controller's root object, set to null if unknown
     * @param resourceBundle resources used to localize the root object, set to null if object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Creates list of all countries
            ObservableList<Country> allCountries = CountriesQuery.getAllCountries();
            // Sets Country ComboBox to hold list of all country names
            countryCB.setItems(allCountries);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
