<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%
    String base = (String)application.getAttribute("base");
    String imageURL = (String)application.getAttribute("imageURL");
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
    <header>
        <img src="<%=request.getContextPath()%>/images/header.jpg" alt="Moffat Bay Sunset" />
        <div class="header-text">Moffat Bay Lodge</div>
    </header>

    <div id="navbar">
        <a href="<%=base%>?action=home">Home</a>
        <a href="<%=base%>?action=aboutus">About Us</a>
        <a href="<%=base%>?action=reservation">Book</a>
        <a href="<%=base%>?action=attractions">Attractions</a>
        <a href="<%=base%>?action=reservationlookup">Reservation Lookup</a>

        <div class="account">
            <a href="<%=base%>?action=registration">Registration</a>
            <% 
            if (session.getAttribute("username") != null) {
                out.print("<a href=\"" + base + "?action=logout\">Log Out</a>");
            } else {
                out.print("<a href=\"" + base + "?action=login\">Log In</a>");
            }
            %>
        </div>
    </div>

</body>
</html>
