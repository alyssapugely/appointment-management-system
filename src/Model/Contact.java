package Model;

/** This class is used to create Contact objects.
 */
public class Contact {
    private String contactName;
    private int contactID;
    private String contactEmail;

    /** Defines the necessary parameters to create a Contact object
     * @param contactName contact's name, String variable
     * @param contactID contact's ID, int variable
     * @param contactEmail contact's email, String variable
     */
    public Contact(String contactName, int contactID, String contactEmail) {
        this.contactName = contactName;
        this.contactID = contactID;
        this.contactEmail = contactEmail;
    }

    /** Returns the contact's name
     * @return string variable contactName
     */
    public String getContactName() {
        return contactName;
    }

    /** Sets the contact's name to the String value passed as a parameter
     * @param contactName string variable contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Returns the contact's ID
     * @return int variable contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** Sets the contact's ID to the int value passed as a parameter
     * @param contactID int variable contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** Returns the contact's email
     * @return string variable contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /** Sets the contact's email to the String value passed as a parameter
     * @param contactEmail string variable contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /** Returns the contact name when a contact object is referenced
     * @return The string contactName
     */
    @Override
    public String toString() {
        return contactName;
    }
}
