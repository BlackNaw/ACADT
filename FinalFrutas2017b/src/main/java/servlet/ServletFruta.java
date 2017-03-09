package servlet;

import DAO.Fruta;
import bd.AccesoBd;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Yo on 07/03/2017.
 */
@WebServlet(value="/ServletFruta",name = "ServletFruta")
public class ServletFruta extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mensaje="";
        ArrayList<Fruta> frutas=new ArrayList<Fruta>();
        try {
            AccesoBd accesoBd=new AccesoBd();
            accesoBd.conectar("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pagina2017b?useInformationSchema=true", "cliente", "cliente");
//            ResultSet rs = accesoBd.executeQuery("SELECT f.nombreFruta AS fruta, v.nombreVariedad AS variedad , a.nombreAlmacen\n" +
//                    " AS almacen, m.nombreMedida AS medida , d.stock_distribucion AS stock , d.precio_distribucion AS precio" +
//                    "FROM `distribucion` d, almacen a, variedad v, medida m, fruta f" +
//                    "WHERE v.codFruta = f.codFruta AND a.codAlmacen = d.codAlmacen AND " +
//                    "m.codMedida = d.codMedida AND d.codVariedad = v.codVariedad");
            ResultSet rs = accesoBd.executeQuery("SELECT * from fruta f order by f.codFruta ");
            System.out.println(rs);
            while (rs.next()) {
                String nombreFruta = rs.getString("nombreFruta");
//                String nombreVariedad = rs.getString("variedad");
//                String nombreAlmacen = rs.getString("almacen");
//                String medida = rs.getString("medida");
//                int stock = rs.getInt("stock");
//                float precio = rs.getFloat("precio");
                int codFruta = rs.getInt("codFruta");
                frutas.add(new Fruta(nombreFruta, codFruta ));
            }
            ObjectMapper mapperObj = new ObjectMapper();
            String jsonStr="";
            for (Fruta fruta :
                    frutas) {
                jsonStr+=mapperObj.writeValueAsString(fruta.getNombreFruta());
                jsonStr+=mapperObj.writeValueAsString(String.valueOf(fruta.getCodFruta()));
            }
            System.out.println(jsonStr);
            response.setHeader("frutas", jsonStr);
        } catch (SQLException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (ClassNotFoundException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (JsonGenerationException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (JsonMappingException e) {
            mensaje = "Error " +  e.getMessage();
        } catch (IOException e) {
            mensaje = "Error " + e.getMessage();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
