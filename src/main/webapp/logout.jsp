<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // Invalidează sesiunea
  session.invalidate();
  // Redirecționează către login
  response.sendRedirect("login.jsp");
%>