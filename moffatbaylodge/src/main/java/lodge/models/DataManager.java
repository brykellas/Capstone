/* Team Delta
 * Authors: Bryce Kellas
 * 
 * DataManager controller to database connections
 * Adapted from: Beginning Jakarta EE Web Development, Third Edition - 2020 - Authors: Luciano Manelli, Giulio Zambon
 *      Accessed 9/2/2023
 */

package lodge.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lodge.beans.Customer;
//import lodge.beans.Booking;
//import lodge.beans.Room;

public class DataManager {
    private String dbURL = "";
    private String dbUserName = "";
    private String dbPassword = "";

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }
    public String getDbURL() {
        return dbURL;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }
    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    public String getDbPassword() {
        return dbPassword;
    }

    public Connection getConnection() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(getDbURL(), getDbUserName(), getDbPassword());
        } catch (SQLException e) {
            System.out.println("Could not connect to DB: " + e.getMessage());
        }
      return conn;
    }
    public void putConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); }
            catch (SQLException e) { }
        }
    }

    //---------- Booking operations ----------
    /*
    public String getBookingName(String categoryID) {
        Booking category = BookingPeer.getBookingById(this, categoryID);
        return (category == null) ? null : category.getName();
    }

    public Hashtable<String, String> getBookings() {
        return BookingPeer.getAllBookings(this);
    }

    public Enumeration<String> getCatIDs() {
        return BookingPeer.getAllBookings(this).keys();
    }
    */

    //---------- Customer operations ----------
    public Customer getCustomerLogin(String email) {
        return CustomerPeer.getCustomerByEmail(this, email);
    }

    public Customer getCustomerDetails(String customerId) {
        return CustomerPeer.getCustomerById(this, customerId);
    }

    public void insertCustomer(Customer customer) {
        Connection connection = getConnection();

        if (connection != null) {
            Statement stmt = null;
            try {
                connection.setAutoCommit(false);
                stmt = connection.createStatement();
                try {
                    CustomerPeer.insertCustomer(stmt, customer);
                    try { stmt.close(); }
                    finally { stmt = null; }
                    connection.commit();
                } catch (SQLException e) {
                    System.out.println("Could not insert customer: " + e.getMessage());
                    e.printStackTrace();
                    try { connection.rollback(); }
                    catch (SQLException ee) { }
                }
            } catch (SQLException e) {
                System.out.println("Could not insert customer: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (stmt != null) {
                    try { stmt.close(); }
                    catch (SQLException e) { }
                }
                putConnection(connection);
            }
        }
    }
}
