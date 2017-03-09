<%--
  Created by IntelliJ IDEA.
  User: JAVI
  Date: 02/03/2017
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
session.invalidate();
response.sendRedirect("/");
%>


