package controlador;


import pagina.AArmador;
import pagina.Armador1;
import pagina.Pagina;

import java.sql.SQLException;

public class ControladorServidor {

    private String pagina = "";
    private String idioma = "";
    private String link = "";
    private String usuario="";
    public ControladorServidor(String pagina, String idioma,String usuario) {

        this.pagina = pagina;    // index primera vez
        this.idioma = idioma;  // es primera vez
        this.usuario = usuario;

    }

    public String elegirArmador() throws ClassNotFoundException, SQLException {
        String bd="jdbc:mysql://localhost/pagina2017b";
        String password="";
        if(!usuario.equals("root")){
            bd=bd+"?useInformationSchema=true";
            password=usuario;
        }
        AArmador miArmador = new Armador1();
        link = new Pagina(this.pagina, this.idioma, miArmador, bd, this.usuario, password).getPagina();

        return link;
    }


}
