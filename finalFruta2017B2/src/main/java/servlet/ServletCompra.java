package servlet;

import dao.ClientePedidoCabecera;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Yo on 12/03/2017.
 */
@WebServlet(value = "/ServletCompra", name = "ServletCompra")
public class ServletCompra extends HttpServlet {
    private String mensaje = "";
    private JSONParser parser;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getParameter("json");
        parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(json);
            jsonArray = (JSONArray) obj;
            ArrayList<Object> lineas = convert(jsonArray);
//            jsonObject = (JSONObject) obj;
            mensaje = ClientePedidoCabecera.generarPedido(lineas);
        } catch (ParseException e) {
            mensaje = "Error " + e.getMessage();
        }
        response.getWriter().write(mensaje);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static ArrayList<Object> convert(JSONArray jArr) {
        ArrayList<Object> list = new ArrayList<Object>();
        for (Object objeto: jArr) {
            list.add(objeto);
        }
        return list;
    }
}
