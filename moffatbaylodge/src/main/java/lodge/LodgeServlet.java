/* Team Delta
 * Authors: Bryce Kellas
 * 
 * Servlet controller to decides URL to navigate to based on the links clicked in header and footer
 * Adapted from: Beginning Jakarta EE Web Development, Third Edition - 2020 - Authors: Luciano Manelli, Giulio Zambon
 *      Accessed 9/2/2023
 */
package lodge;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lodge.models.DataManager;

@WebServlet(name = "LodgeServlet", urlPatterns = {"/*"})
public class LodgeServlet extends jakarta.servlet.http.HttpServlet {
  
    private static final long serialVersionUID = 1L;

    public LodgeServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        System.out.println("*** initializing controller servlet.");
        super.init(config);

        DataManager dataManager = new DataManager();
        dataManager.setDbURL(config.getInitParameter("dbURL"));
        dataManager.setDbUserName(config.getInitParameter("dbUserName"));
        dataManager.setDbPassword(config.getInitParameter("dbPassword"));

        ServletContext context = config.getServletContext();
        context.setAttribute("base", config.getInitParameter("base"));
        context.setAttribute("imageURL", config.getInitParameter("imageURL"));
        context.setAttribute("dataManager", dataManager);

        try {  // load the database JDBC driver
            Class.forName(config.getInitParameter("jdbcDriver"));
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        //RequestDispatcher req = request.getRequestDispatcher("/index.jsp");
        //req.forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Default URL for home page
        String base = "/views/";
        String url = "index.jsp";

        // Decides URL to navigate to based on the links clicked in header and footer
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "home": 
                    url = "index.jsp";
                    break;
                case "registration":
                    url = base + "registration.jsp";
                    break;
                case "login":
                    url = base + "login.jsp";
                    break;
                case "aboutus":
                    url = base + "aboutus.jsp";
                    break;
                case "reservation":
                    url = base + "reservation.jsp";
                    break;
                case "reservationlookup":
                    url = base + "reservationlookup.jsp";
                    break;
                case "reservationsummary":
                    url = base + "reservationsummary.jsp";
                    break;
                case "attractions":
                    url = base + "attractions.jsp";
                    break;
                case "logout":
                    url = base + "logout.jsp";
                    break;
                    
                default:
                    
                    break;
            }
        }
        System.out.println("Navigating to "+url);
        RequestDispatcher req = request.getRequestDispatcher(url);
        req.forward(request, response);
    }
}
