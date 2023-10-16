package Utilities;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.util.Locale;

/** This class contains methods to display various alerts.
 */
public class Alerts {

    /** Displays an error message if a user leaves the username field blank when attempting to login, displayed in
     * English or French depending on the user's locale
     */
    public static void usernameEmptyAlert() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        if (Locale.getDefault().getLanguage().equals("fr")) {
            alert.setTitle("Informations de connexion non valides");
            alert.setContentText("Le champ Nom d'utilisateur ne peut pas être vide");
        } else {
            alert.setTitle("Invalid Login Credentials");
            alert.setContentText("Username field cannot be empty");
        }
        alert.showAndWait();
    }

    /** Displays an error message if a user leaves the password field blank when attempting to login, displayed in English
     * or French depending on the user's locale
     */
    public static void passwordEmptyAlert() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        if (Locale.getDefault().getLanguage().equals("fr")) {
            alert.setTitle("Informations de connexion non valides");
            alert.setContentText("Le champ Mot de passe ne peut pas être vide");
        } else {
            alert.setTitle("Invalid Login Credentials");
            alert.setContentText("Password field cannot be empty");
        }
        alert.showAndWait();
    }


    /** Displays an error message if a user enters invalid login credentials, displayed in English or French depending
     * on the user's locale
     */
    public static void invalidLoginAlert() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        if (Locale.getDefault().getLanguage().equals("fr")) {
            alert.setTitle("Informations de connexion non valides");
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect");
        } else {
            alert.setTitle("Invalid Login Credentials");
            alert.setContentText("Incorrect username or password");
        }
        alert.showAndWait();
    }

    /** Displays a message informing the user that there is an appointment beginning within 15 minutes of login, as well
     * as the appointment details
     * @param appointmentID ID of the upcoming appointment
     * @param startTime start time of the upcoming appointment
     */
    public static void upcomingAppointmentAlert(int appointmentID, LocalDateTime startTime) {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setTitle("Upcoming Appointment");
        alert.setContentText("Appointment beginning within 15 minutes!\nAppointment ID: " + appointmentID + "\nStart Time: " + startTime);
        alert.showAndWait();
    }

    /** Displays a message informing the user that there are no appointments beginning within 15 minutes of login
     */
    public static void noUpcomingAppointmentsAlert() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setTitle("No Upcoming Appointments");
        alert.setContentText("There are no appointments starting within the next 15 minutes.");
        alert.showAndWait();
    }

    /** Displays a message informing the user that the selected appointment was successfully cancelled, along with
     * displaying the appointment's ID and type
     * @param appointmentID ID of the cancelled appointment
     * @param appointmentType type of the cancelled appointment
     */
    public static void appointmentCancelledAlert(int appointmentID, String appointmentType) {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setTitle("Appointment Cancelled");
        alert.setContentText("Appointment successfully cancelled.\nID: " + appointmentID + "\nType: " + appointmentType);
        alert.showAndWait();
    }

    /** Informs the user that a customer still has associated appointments that need to be cancelled before the customer
     * can be deleted
     */
    public static void customerDeletionError() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Customer Deletion Error");
        alert.setContentText("This customer's appointments must be cancelled before this customer can be deleted.");
        alert.showAndWait();
    }

    /** Displays a message informing the user that the selected customer was successfully deleted, along with displaying
     * the customer's name and ID
     * @param customerID ID of the deleted customer
     * @param customerName name of the deleted customer
     */
    public static void customerDeletedAlert(int customerID, String customerName) {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setTitle("Customer Deleted");
        alert.setContentText("Customer successfully deleted.\nID: " + customerID + "\nName: " + customerName);
        alert.showAndWait();
    }

    /** Displays an error message informing the user that a field can't be left blank when adding or updating a customer
     * or appointment
     * @param fieldName name of the field that was left blank
     */
    public static void fieldIsBlankError(String fieldName) {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("User Input Error");
        alert.setContentText("The " + fieldName + " field cannot be empty.");
        alert.showAndWait();
    }

    /** Displays an error message informing the user that the customer they tried to schedule an appointment for already
     * has an existing appointment that conflicts with the selected appointment times
     */
    public static void overlappingAppointmentError() {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Overlapping Appointment Error");
        alert.setContentText("The customer you tried to schedule an appointment for has an existing appointment that conflicts with this time.");
        alert.showAndWait();
    }
}
