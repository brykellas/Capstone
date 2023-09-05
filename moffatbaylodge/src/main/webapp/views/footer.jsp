<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%
    //String base = (String)application.getAttribute("base");
    //String imageURL = (String)application.getAttribute("imageURL");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
    href="https://fonts.googleapis.com/css2?family=Gloock&display=swap"
    rel="stylesheet"
    />

    <title>DELTA | CSD460</title>
</head>
<body>
    
    <footer>
        <div id="logo">
        <img src="<%=request.getContextPath()%>/images/logo.png" alt="Logo" />
        <br /><br />
        (c) 2023 DELTA
        </div>

        <div id="flinks">
        <a href="<%=base%>?action=home">Home</a>
        <a href="<%=base%>?action=aboutus">About Us</a>
        <a href="<%=base%>?action=reservation">Book</a>
        <a href="<%=base%>?action=attractions">Attractions</a>
        <a href="<%=base%>?action=reservationlookup">Reservation Lookup</a>
        </div>

        <div class="account"></div>
        <div id="socials">
        <a href="views/registration.html">Registration | </a>
        <% 
            if (session.getAttribute("username") != null) {
                out.print("<a href=\"" + base + "?action=logout\">Log Out</a>");
            } else {
                out.print("<a href=\"" + base + "?action=login\">Log In</a>");
            }
            %>
        <a href="https://www.facebook.com/"><img src="<%=request.getContextPath()%>/images/fb.png" /></a>
        <a href="https://www.instagram.com/"><img src="<%=request.getContextPath()%>/images/ig.png" /></a>
        <a href="https://www.youtube.com/"><img src="<%=request.getContextPath()%>/images/yt.png" /></a>
        </div>
    </footer>

</body>
</html>
