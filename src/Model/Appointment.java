package Model;

import java.time.LocalDateTime;

/** This class is used to create Appointment objects.
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;
    private int contactID;

    /** Defines the necessary parameters to create an Appointment object
     * @param appointmentID appointment ID, int variable
     * @param title appointment title, String variable
     * @param description appointment description, String variable
     * @param location appointment location, String variable
     * @param contactName appointment contact's name, String variable
     * @param type appointment type, String variable
     * @param startTime time and date the appointment starts, LocalDateTime variable
     * @param endTime time and date the appointment ends, LocalDateTime variable
     * @param customerID appointment customer's ID, int variable
     * @param userID user's ID, int variable
     * @param contactID appointment contact's ID, int variable
     */
    public Appointment(int appointmentID, String title, String description, String location, String contactName, String type, LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /** Returns the appointment ID
     * @return int variable appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** Sets the appointment ID to the int value passed as a parameter
     * @param appointmentID int variable appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** Returns the appointment title
     * @return string variable title
     */
    public String getTitle() {
        return title;
    }

    /** Sets the appointment title to the String value passed as a parameter
     * @param title string variable title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Returns the appointment description
     * @return string variable description
     */
    public String getDescription() {
        return description;
    }

    /** Sets the appointment description to the String value passed as a parameter
     * @param description string variable description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Returns the appointment location
     * @return string variable location
     */
    public String getLocation() {
        return location;
    }

    /** Sets the appointment location to the String value passed as a parameter
     * @param location string variable location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Returns the appointment contact's name
     * @return string variable contactName
     */
    public String getContactName() {
        return contactName;
    }

    /** Sets the appointment contact's name to the String value passed as a parameter
     * @param contactName string variable contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Returns the appointment's type
     * @return string variable type
     */
    public String getType() {
        return type;
    }

    /** Sets the appointment's type to the String value passed as a parameter
     * @param type string variable type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Returns the appointment's start date and time
     * @return LocalDateTime variable startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** Sets the appointment's start date and time to the LocalDateTime value passed as a parameter
     * @param startTime LocalDateTime variable startTime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** Returns the appointment's end date and time
     * @return LocalDateTime variable endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** Sets the appointment's end date and time to the LocalDateTime value passed as a parameter
     * @param endTime LocalDateTime variable startTime
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** Returns the appointment customer's ID
     * @return int variable customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** Sets the appointment customer's ID to the int value passed as a parameter
     * @param customerID int variable customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Returns the user's ID
     * @return int variable userID
     */
    public int getUserID() {
        return userID;
    }

    /** Sets the user's ID to the int value passed as a parameter
     * @param userID int variable userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** Returns the appointment contact's ID
     * @return int variable contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** Sets the appointment contact's ID to the int value passed as a parameter
     * @param contactID int variable contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
