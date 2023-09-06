/* Team Delta
 * Authors: Bryce Kellas, Jared Olson
 * 
 * Servlet controller to handle login requests and validations
 * Adapted from: Beginning Jakarta EE Web Development, Third Edition - 2020 - Authors: Luciano Manelli, Giulio Zambon
 *      Accessed 9/2/2023
 * 
 * TODO: Setup class to handle messages?
 * TODO: Login: Use JavaScript for form validations?
 */
package lodge;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lodge.beans.Customer;
import lodge.models.DataManager;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login/*"})
public class LoginServlet extends jakarta.servlet.http.HttpServlet {
    

    public LoginServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        System.out.println("*** initializing login servlet.");
        super.init(config);

        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doPost(request, response);
        //HttpSession session = request.getSession();
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Pull username and password from the request
        String email = request.getParameter("username"); 
        String password = request.getParameter("password");

        // Set DataManager and customer bean
        DataManager dm = (DataManager)getServletContext().getAttribute("dataManager");
        Customer customer = dm.getCustomerLogin(email);
        HashPassword hp = new HashPassword();
        boolean validPassword = false;

        // Email and Password Validations
        try {
            validPassword = hp.validatePassword(password, customer.getPassword());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.out.println(e.getMessage());
        }

        if (customer != null && validPassword) {
            session.setAttribute("userid", customer.getId());
            session.setAttribute("username", customer.getEmail());
            session.setAttribute("uname", customer.getFirstName());
            
            // Navigate to home page
            RequestDispatcher req = request.getRequestDispatcher("?action=home");
            req.forward(request, response);
        }
        else {  
            request.setAttribute("loginerror","Invalid Username or Password");
            System.out.println("invalid login");
                        
            // Post back to login page
            RequestDispatcher rd=request.getRequestDispatcher("?action=login");            
            rd.include(request, response);
        }
    }
}
