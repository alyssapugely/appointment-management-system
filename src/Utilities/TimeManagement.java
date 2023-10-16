package Utilities;

import DAO.AppointmentsQuery;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** This class contains methods to handle time variables throughout the program, including converting times to different
 * time zones and creating lists of all possible appointment times.
 */
public class TimeManagement {

    /** Takes a String parameter and converts it to a LocalTime object
     * @param timeString string variable input as a parameter
     * @return LocalTime variable localTime
     */
    public static LocalTime stringToLocalTime(String timeString) {
        // Format to represent times as hours:minutes, with 'a' representing AM or PM designation
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        // Converts string to LocalTime object based on the format provided
        LocalTime localTime = LocalTime.parse(timeString, dateTimeFormatter);
        return localTime;
    }

    /** Converts the local time input as a parameter to Eastern Standard Time
     * @param localTime LocalTime variable input as a parameter
     */
    public static LocalTime localTimeToEST(LocalTime localTime) {
        // The Eastern Standard Time Zone ID is America/New_York
        ZoneId estZoneID = ZoneId.of("America/New_York");
        // Converts localTime input as parameter to Eastern Standard Time with date
        ZonedDateTime estTime = ZonedDateTime.of(LocalDate.now(), localTime, ZoneId.systemDefault()).withZoneSameInstant(estZoneID);
        // Returns ZonedDateTime variable above as just EST time with no date included
        return estTime.toLocalTime();
    }

    /** Returns a string list of all possible appointment times
     * @return ObservableList of string appointment times
     */
    public static ObservableList<String> allAppointmentTimes() {
        // Creates list to hold all String appointment time values
        ObservableList<String> allAppointmentTimes = FXCollections.observableArrayList();
        // Saves local time at Midnight to variable localTime
        LocalTime localTime = LocalTime.MIDNIGHT;
        // Format to represent times as hours:minutes, with 'a' representing AM or PM designation
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        // Saves local times to list in half hour increments, from 12 AM to 11:30 PM (48 half hour increments total)
        for (int i = 0; i < 48; i++) {
            // Saves formatted local time to String variable formattedTime
            String formattedTime = localTime.format(dateTimeFormatter);
            // Adds formattedTime to list of all appointment times
            allAppointmentTimes.add(formattedTime);
            // Increases local time by a half hour
            localTime = localTime.plusMinutes(30);
        }
        return allAppointmentTimes;
    }

    /** Checks whether the LocalTime input as a parameter falls within the business hours of 8 AM to 10 PM EST
     * @param appointmentTime LocalTime variable input as a parameter
     * @param appointmentDescription string variable input as a parameter
     * @return true if the LocalTime parameter is within business hours, false if it is not
     */
    public static boolean businessHoursValidation(LocalTime appointmentTime, String appointmentDescription) {
        // Converts appointment time parameter to EST
        LocalTime estAppointmentTime = TimeManagement.localTimeToEST(appointmentTime);
        // Saves EST business opening time of 8:00 AM to LocalTime variable
        LocalTime estBusinessHoursStart = LocalTime.of(8,0);
        // Saves EST business closing time of 10:00 PM to LocalTime variable
        LocalTime estBusinessHoursEnd = LocalTime.of(22, 0);

        // If the EST appointment time falls outside of business hours, an alert message is displayed and the method returns false
        if (estAppointmentTime.isBefore(estBusinessHoursStart) || estAppointmentTime.isAfter(estBusinessHoursEnd)) {
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle("Appointment Outside of Business Hours");
            alert.setContentText("The appointment " + appointmentDescription + " time is scheduled outside the business hours of 8:00 AM - 10:00 PM EST");
            alert.showAndWait();
            return false;
        }
        // If the EST appointment time falls within business hours, the method returns true
        return true;
    }


    /** Displays an error message and returns false if there are any existing appointments that conflict with the time
     * of the new appointment. Otherwise, it returns true.
     * @param localStartDateTime LocalDateTime variable input as a parameter, appointment start time
     * @param localEndDateTime LocalDateTime variable input as a parameter, appointment end time
     * @param customerID int variable customerID input as a parameter
     * @param appointmentID int ID variable of the appointment being updated
     * @return false if there are conflicting appointment times, true otherwise
     * @throws SQLException throws SQL exceptions
     */
    public static boolean updateAppointmentOverlapValidation(LocalDateTime localStartDateTime, LocalDateTime localEndDateTime, int customerID, int appointmentID) throws SQLException {
        // Creates list of all existing appointments
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Creates list to hold all appointments belonging to customer ID input as parameter
        ObservableList<Appointment> allCustomerAppointments = FXCollections.observableArrayList();
        // Iterates through list of all appointments
        for (Appointment a: allAppointments) {
            // If current appointment customer ID matches ID input as parameter, appointment is added to allCustomerAppointments list
            if (a.getCustomerID() == customerID) {
                allCustomerAppointments.add(a);
            }
        }
        /* Iterates through list of all appointments belonging to customer ID input as parameter. If any existing appointment
        conflicts with the new appointment times, an error message is displayed and the method returns false. */

        for (Appointment a: allCustomerAppointments) {
            if (localStartDateTime.isBefore(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isAfter(a.getStartTime()) && localEndDateTime.isBefore(a.getEndTime())) {
                /* If the current appointment ID equals the appointment ID of the appointment being updated (input as parameter),
                returns true because there is no appointment time conflict */
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.isBefore(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.equals(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.isBefore(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isAfter(a.getStartTime()) && localEndDateTime.isAfter(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.equals(a.getStartTime()) && localEndDateTime.isBefore(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.equals(a.getStartTime()) && localEndDateTime.equals(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.equals(a.getStartTime()) && localEndDateTime.isAfter(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.isAfter(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isBefore(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.isAfter(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.equals(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
            if (localStartDateTime.isAfter(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isAfter(a.getEndTime())) {
                if (a.getAppointmentID() == appointmentID) {
                    return true;
                } else {
                    Alerts.overlappingAppointmentError();
                    return false;
                }
            }
        }
        // If there are no conflicting appointments, returns true
        return true;
    }

    /** Displays an error message and returns false if there are any existing appointments that conflict with the time
     * of the new appointment. Otherwise, it returns true.
     * @param localStartDateTime LocalDateTime variable input as a parameter, appointment start time
     * @param localEndDateTime LocalDateTime variable input as a parameter, appointment end time
     * @param customerID int variable customerID input as a parameter
     * @return false if there are conflicting appointment times, true otherwise
     * @throws SQLException throws SQL exceptions
     */
    public static boolean appointmentOverlapValidation(LocalDateTime localStartDateTime, LocalDateTime localEndDateTime, int customerID) throws SQLException {
        // Creates list of all existing appointments
        ObservableList<Appointment> allAppointments = AppointmentsQuery.getAllAppointments();
        // Creates list to hold all appointments belonging to customer ID input as parameter
        ObservableList<Appointment> allCustomerAppointments = FXCollections.observableArrayList();
        // Iterates through list of all appointments
        for (Appointment a: allAppointments) {
            // If current appointment customer ID matches ID input as parameter, appointment is added to allCustomerAppointments list
            if (a.getCustomerID() == customerID) {
                allCustomerAppointments.add(a);
            }
        }
        /* Iterates through list of all appointments belonging to customer ID input as parameter. If any existing appointment
        conflicts with the new appointment times, an error message is displayed and the method returns false. */

        for (Appointment a: allCustomerAppointments) {
            if (localStartDateTime.isBefore(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isAfter(a.getStartTime()) && localEndDateTime.isBefore(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.isBefore(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.equals(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.isBefore(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isAfter(a.getStartTime()) && localEndDateTime.isAfter(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.equals(a.getStartTime()) && localEndDateTime.isBefore(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.equals(a.getStartTime()) && localEndDateTime.equals(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.equals(a.getStartTime()) && localEndDateTime.isAfter(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.isAfter(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isBefore(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.isAfter(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.equals(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
            if (localStartDateTime.isAfter(a.getStartTime()) && localStartDateTime.isBefore(a.getEndTime()) && localEndDateTime.isAfter(a.getEndTime())) {
                Alerts.overlappingAppointmentError();
                return false;
            }
        }
        // If there are no conflicting appointments, returns true
        return true;
    }
}
