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
<%
  String resultado="";


  if(session.getAttribute("pagina") == null){

    session = request.getSession();
    session.setAttribute("pagina", "index");
    session.setAttribute("idioma", "es");
    session.setAttribute("idSesion", session.getId());
  }

  if(session.getAttribute("idSesion") == session.getId())
  {
    ControladorServidor c;
    c = new ControladorServidor((String) session.getAttribute("pagina"));
    resultado = c.elegirArmador();
  }
%>
<%=resultado%>


