package Controller;

import DAO.CountriesQuery;
import DAO.CustomersQuery;
import DAO.DivisionsQuery;
import Model.Country;
import Model.Division;
import Utilities.Alerts;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class contains control logic for the Add Customer Screen, which is used to create new customers.
 */
public class AddCustScreenController implements Initializable {
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


    /** Uses information input by the user to insert a new customer into the Customers table of the database.
     * @param event clicking the Save button on the Add Customer Screen
     * @throws SQLException throws SQL exceptions
     * @throws IOException throws input/output exceptions
     */
    @FXML
    void addNewCustomer(ActionEvent event) throws SQLException, IOException {
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

        /* Uses insert method of CustomersQuery class to insert a new customer into the Customers table of the database,
        using above variables */
            CustomersQuery.insert(customerName, phoneNumber, address, postalCode, divisionID);
            // When Save button is clicked, the application returns to the Main Screen
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /** Displays states or provinces in the Division ComboBox that belong to the country selected by the user in the Country ComboBox.
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

    /** Cancels new customer creation by returning to the Main Screen
     * @param event clicking the Cancel Button
     * @throws IOException throws input/ouput exception
     */
    @FXML
    void cancelAction(ActionEvent event) throws IOException {
        // Alert is displayed to confirm cancellation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this action?\nNew customer" +
                " will not be saved.");
        Optional<ButtonType> userSelection = alert.showAndWait();
        // If the user clicks OK button, the Add Customer Screen closes and the Main Screen opens
        if (userSelection.isPresent() && userSelection.get() == ButtonType.OK) {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            Stage stage = new Stage();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Initializes the AddCustScreenController and populates the Country ComboBox with a list of all country names.
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
