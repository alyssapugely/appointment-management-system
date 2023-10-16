package Model;

/** This class is used to create Customer objects
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String phoneNumber;
    private String address;
    private String country;
    private String postalCode;
    private String division;
    private int divisionID;

    /** Defines the necessary parameters to create a Customer object
     * @param customerID customer's ID, int variable
     * @param customerName customer's name, String variable
     * @param phoneNumber customer's phone number, String variable
     * @param address customer's address, String variable
     * @param country customer's country, String variable
     * @param postalCode customer's postal code, String variable
     * @param division customer's division, String variable
     * @param divisionID division's ID, int variable
     */
    public Customer(int customerID, String customerName, String phoneNumber, String address, String country, String postalCode, String division, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.country = country;
        this.postalCode = postalCode;
        this.division = division;
        this.divisionID = divisionID;
    }

    /** Returns the customer's ID
     * @return int variable customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** Sets the customer's ID to the int value passed as a parameter
     * @param customerID int variable customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Returns the customer's name
     * @return string variable customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /** Sets the customer's name to the String value passed as a parameter
     * @param customerName string variable customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Returns the customer's phone number
     * @return string variable phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Sets the customer's phone number to the String value passed as a parameter
     * @param phoneNumber string variable phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** Returns the customer's address
     * @return string variable address
     */
    public String getAddress() {
        return address;
    }

    /** Sets the customer's address to the String value passed as a parameter
     * @param address string variable address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Returns the customer's country
     * @return string variable country
     */
    public String getCountry() {
        return country;
    }

    /** Sets the customer's country to the String value passed as a parameter
     * @param country string variable country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Returns the customer's postal code
     * @return string variable postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** Sets the customer's postal code to the String value passed as a parameter
     * @param postalCode string variable postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Returns the customer's division
     * @return string variable division
     */
    public String getDivision() {
        return division;
    }

    /** Sets the customer's division to the String value passed as a parameter
     * @param division string variable division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Returns the division's ID
     * @return int variable divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** Sets the division's ID to the int value passed as a parameter
     * @param divisionID int variable divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
