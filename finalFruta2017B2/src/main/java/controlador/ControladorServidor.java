package controlador;


import pagina.AArmador;
import pagina.Armador1;
import pagina.Pagina;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ControladorServidor {

    private String pagina = "";
    private String link = "";
    private String idioma;
    private String usuario;
    private String parametro = "";
    private String password = "";

    public ControladorServidor(HttpSession session) {

        this.pagina = (String) session.getAttribute("pagina");    // index primera vez
        this.idioma = (String) session.getAttribute("idioma");
        this.usuario = (String) session.getAttribute("usuario");

    }

    public String elegirArmador() throws ClassNotFoundException, SQLException {

        if (!usuario.equals("root")) {
            parametro = "?useInformationSchema=true";
            password = usuario;
        }
        AArmador miArmador = new Armador1();
        link = new Pagina(this.pagina, this.idioma, miArmador, "jdbc:mysql://localhost/pagina2017b" + parametro, usuario, password).getPagina();

        return link;
    }


}
