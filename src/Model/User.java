package Model;

/** This class is used to create User objects.
 */
public class User {
    private int userID;
    private String username;
    private String password;

    /** Defines the necessary parameters to create a User object
     * @param userID user's ID, int variable
     * @param username user's username, String variable
     * @param password user's password, String variable
     */
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
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

    /** Returns the user's username
     * @return string variable username
     */
    public String getUsername() {
        return username;
    }

    /** Sets the user's username to the String value passed as a parameter
     * @param username string variable username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Returns the user's password
     * @return string variable password
     */
    public String getPassword() {
        return password;
    }

    /** Sets the user's password to the String value passed as a parameter
     * @param password string variable password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
