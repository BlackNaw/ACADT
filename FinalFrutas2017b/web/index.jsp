<%--
  Created by IntelliJ IDEA.
  User: Luciano
  Date: 07/02/2017
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@page import="controlador.ControladorServidor"%>
<%@ page import="java.awt.*" %>
<%@ page import="java.util.Enumeration" %>


<%



    String paginaResultado = "";


    if (session.getAttribute("pagina") == null) {

      session = request.getSession();
      session.setAttribute("pagina", "index");
      session.setAttribute("idioma", request.getLocale().getLanguage());
      session.setAttribute("idSesion", session.getId());
    }

    if (session.getAttribute("idSesion") == session.getId()) {
      ControladorServidor c;
      c = new ControladorServidor(session);
      paginaResultado = c.elegirArmador();
    }

%>
<%=paginaResultado%>





