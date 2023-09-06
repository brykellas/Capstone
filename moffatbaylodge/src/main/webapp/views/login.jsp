<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@page import="lodge.beans.Customer"%>
<%@page import="lodge.models.DataManager"%>
<%@page import="lodge.HashPassword"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="../css/style.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Gloock&display=swap" rel="stylesheet" />

        <title>Log In | Moffat Bay Lodge</title>
    </head>
    <body>      
        <div class="container">
            <%@ include file = "header.jsp" %>

            <div class="forms">

                <% // Display error message if username/password is invalid
                String loginMessage = (String)request.getAttribute("loginerror");  
                if(loginMessage != null)
                out.println("<font color=red size=4px>" + loginMessage + "</font>");
                String registerMessage = (String)request.getAttribute("registerwelcome");  
                if(registerMessage != null)
                out.println("<font color=black size=3px>" + registerMessage + "</font>");
                %>
            
                <h1>Log In</h1>
                <br /><br />
                <form method="POST" action="login">
                <input type="text" placeholder="Enter Username" name="username" required/>
                <br /><br />
                <input type="password" placeholder="Enter Password" name="password" required/>
                <br /><br />
                <button type="submit">Login</button>
                <br /><br />
                </form>
            </div>
            
            <%@ include file = "footer.jsp" %>
        </div>
    </body>
</html>
