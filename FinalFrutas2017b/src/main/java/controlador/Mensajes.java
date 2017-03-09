package controlador;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Yo on 27/02/2017.
 */
public class Mensajes {
    public static void mostrarmensaje(String mensaje, String pagina, HttpServletResponse response) throws IOException {
        //Mostramos el mensaje de error
        System.out.println("mostrando mensaje: "+mensaje);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<link rel = stylesheet href=css/mensaje.css>");
        out.print("<div id = openModal class = modalDialog>");
        out.print("<div>");
        out.print("<a href= " + pagina + " title = Close class = 'close'>X</a>");
        out.print("<h2> Mensaje </h2>");
        out.print("<p>" + mensaje + "</p>");
        out.print("</div>");
        out.print("</div>");
    }
}
