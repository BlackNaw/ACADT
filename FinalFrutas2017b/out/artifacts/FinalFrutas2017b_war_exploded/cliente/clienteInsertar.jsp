<%--
  Created by IntelliJ IDEA.
  User: Luciano
  Date: 24/02/2017
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<jsp:useBean id="publi" class="publico.Publico" scope="application">
</jsp:useBean>
<jsp:setProperty name="publi" property="*"/>
<%@page import="org.json.simple.JSONArray" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="org.json.simple.parser.JSONParser" %>
<%@page import="org.json.simple.parser.ParseException" %>

<% String publico = request.getParameter("json");

    JSONParser parser = new JSONParser();

    Object obj = parser.parse(publico);

    JSONObject jsonObject = (JSONObject) obj;

    String usuario = (String) jsonObject.get("usuario");
    String password = (String) jsonObject.get("password");
    String mensaje ="**";
    int idUsuario = publi.identificar(usuario, password);
    if (idUsuario != -1){
        mensaje = "ok";
        session.setAttribute("pagina", "publico");
        session.setAttribute("usuario", usuario);
        session.setAttribute("idusuario", idUsuario);

    }
    else  {mensaje = "publico NO registrado";}

%>
<p><%=mensaje%></p>
