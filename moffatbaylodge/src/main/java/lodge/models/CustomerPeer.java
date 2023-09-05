/* Team Delta
 * Authors: Bryce Kellas
 * 
 * Supplemental class to Customer. Handles connection to database
 */

 package lodge.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lodge.beans.Customer;


public class CustomerPeer {
  	public static Customer getCustomerByEmail(DataManager dataManager, String keyword) {
		Customer customer = null;
		Connection connection = dataManager.getConnection();

		if (connection != null) {
			try {
				Statement s = connection.createStatement();
				String sql = "select id, first_name, last_name, email, phone, password from customers"
				+ " where email=\'" + keyword.trim() + "\'";
				try {
					ResultSet rs = s.executeQuery(sql);
					try {
						while (rs.next()) {
							customer = new Customer();
							customer.setId(rs.getInt(1));
							customer.setFirstName(rs.getString(2));
							customer.setLastName(rs.getString(3));
							customer.setEmail(rs.getString(4));
							customer.setPhoneNumber(rs.getString(5));
							customer.setPassword(rs.getString(6));
						}
					} finally { rs.close(); }
				} finally { s.close(); }
			} catch (SQLException e) {
				System.out.println("Could not search for customers:" + e.getMessage());
			} finally {
				dataManager.putConnection(connection);
			}
		}
		return customer;
	}
  
  	public static Customer getCustomerById(DataManager dataManager, String customerID) {
		Customer customer = null;
		Connection connection = dataManager.getConnection();
		
		if (connection != null) {
			try {
				Statement s = connection.createStatement();
				String sql = "select id, first_name, last_name, email, phone, password from customers"
					+ " where id=" + customerID;
				try {
					ResultSet rs = s.executeQuery(sql);
					if (rs.next()) {
						customer = new Customer();
						customer.setId(rs.getInt(1));
						customer.setFirstName(rs.getString(2));
						customer.setLastName(rs.getString(3));
						customer.setEmail(rs.getString(4));
						customer.setPhoneNumber(rs.getString(4));
						customer.setPassword(rs.getString(4));
					}
				} finally { s.close(); }
			} catch (SQLException e) {
				System.out.println("Could not get customer: " + e.getMessage());
			} finally {
				dataManager.putConnection(connection);
			}
		}
		return customer;
	}

	public static void insertCustomer(Statement stmt, Customer customer) throws SQLException {
		String sql = "insert into customers (first_name, last_name, email, phone, password) " 
			+ "values ('"
            + customer.getFirstName() + "','"
            + customer.getLastName() + "','"
            + customer.getEmail() + "','" 
			+ customer.getPhoneNumber() + "','" 
			+ customer.getPassword() + "')";
        stmt.executeUpdate(sql);
	}
}

