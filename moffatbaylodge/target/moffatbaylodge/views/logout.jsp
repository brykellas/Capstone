<%
String base = (String)application.getAttribute("base");
session.invalidate();
response.sendRedirect(base + "?action=home");
%>