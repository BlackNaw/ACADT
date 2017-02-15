package controlador;


import pagina.AArmador;
import pagina.Armador1;
import pagina.Pagina;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ControladorServidor {

    private String pagina = "";
    private String link = "hola";
    private String idioma = "";


    public ControladorServidor(HttpSession session) {
        this.pagina = (String) session.getAttribute("pagina");    // index primera vez
        this.idioma = (String) session.getAttribute("idioma");
    }

    public String elegirArmador() throws ClassNotFoundException, SQLException {


        if (this.pagina.equals("index")) {
            AArmador miArmador = new Armador1();
            link = new Pagina(this.pagina, this.idioma, miArmador,"jdbc:mysql://localhost/pagina2017b", "root", "").getPagina();
        }


        return link;
    }


}
