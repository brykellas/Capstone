/* Team Delta
 * Authors: Bryce Kellas, Jared Olson
 * 
 * Servlet controller to handle registration requests and validations.
 * Adapted from: Beginning Jakarta EE Web Development, Third Edition - 2020 - Authors: Luciano Manelli, Giulio Zambon
 *      Accessed 9/2/2023
 * 
 * TODO: Register: Use JavaScript for form validations?
 */
package lodge;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lodge.beans.Customer;
import lodge.models.DataManager;


@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register/*"})
public class RegistrationServlet extends jakarta.servlet.http.HttpServlet {
    
    
    public RegistrationServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        System.out.println("*** initializing registration servlet.");
        super.init(config);

        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doPost(request, response);
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set dataManager and pasword hasher
        DataManager dm = (DataManager)getServletContext().getAttribute("dataManager");
        HashPassword hp = new HashPassword();

        // Store DB customer info in Customer bean
        Customer customer = new Customer(); 
        customer.setFirstName(request.getParameter("firstname"));
        customer.setLastName(request.getParameter("lastname"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhoneNumber(request.getParameter("phone"));

        // Password and Email validations
        boolean isPasswordLengthValid = request.getParameter("psw").length() >= 8;
        boolean emailIsValid = request.getParameter("email").matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        boolean isCaseValid = StringUtils.isMixedCase(request.getParameter("psw"));
        
        // Valid entries, meets requirements
        if (isCaseValid && emailIsValid && isPasswordLengthValid) {
            dm.insertCustomer(customer);
            request.setAttribute("registerwelcome","Welcome: Please sign in");
            RequestDispatcher rd=request.getRequestDispatcher("?action=login");            
            rd.include(request, response);
        }
        if (!emailIsValid) {
            // Post back to login page
            request.setAttribute("emailerror","Invalid email");
            RequestDispatcher rd=request.getRequestDispatcher("?action=register");            
            rd.include(request, response);
        }
        if (isPasswordLengthValid) { 
            // Post back to login page
            request.setAttribute("passwordlengtherror","Invalid Password: Must be at least eight (8) characters");
            RequestDispatcher rd=request.getRequestDispatcher("?action=register");            
            rd.include(request, response);
        } 
        if (!isCaseValid) { 
            // Post back to login page
            request.setAttribute("passwordcaseerror","Invalid Password: Must have at least one (1) uppercase and one (1) lowercase letter");
            RequestDispatcher rd=request.getRequestDispatcher("?action=register");            
            rd.include(request, response);
        } 

        // Hash 
        try {
            customer.setPassword(hp.generateStrongPasswordHash(request.getParameter("psw")));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchProviderException e) {
            System.out.println(e.getMessage());
        }
        
    }

}
