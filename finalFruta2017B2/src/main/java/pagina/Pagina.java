package pagina;

/**
 * Created by Luciano on 07/02/2017.
 */

import bd.AccesoBd;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Pagina implements IPagina {


    private String head = "";
    private String body = "";


    private String pagina = "";
    private int idPagina;
    private String idioma = "";

    private AArmador miArmador;

    private String bd = "";
    private String login = "";
    private String password = "";

    private AccesoBd conexion = null;
    private ResultSet rs = null;

    public Pagina(String pagina, String idioma, AArmador armador, String bd, String login, String password) {
        this.pagina = pagina;
        this.idioma = idioma;
        this.miArmador = armador;
        this.bd = bd;
        this.login = login;
        this.password = password;
    }

    private AccesoBd conectar() throws SQLException, ClassNotFoundException {
        AccesoBd acceso = AccesoBd.getMiConexion();
        acceso.conectar("com.mysql.jdbc.Driver", this.bd, this.login, this.password);
        return acceso;
    }

    public String getPagina() throws ClassNotFoundException, SQLException {
        conexion = conectar();
        getHead();
        getBody();
        getJs();
        return this.miArmador.armar(this.head, this.body);
    }

    public void getJs() throws SQLException {
        String sql = "SELECT a.NombreFicheroJs as js, a.Path as ruta, b.OrdenJs FROM `fichero_js` a, pagina_js b WHERE a.IdFicheroJs=b.IdFicheroJs AND b.IdPagina=" + this.idPagina + " order by 3";
        rs = conexion.executeQuery(sql);
        while (rs.next()) {
            this.body = this.body + "<script src='" + rs.getString("ruta") + "/" + rs.getString("js") + ".js'></script>";
        }
        this.body = this.body + "<script src=\"https://code.jquery.com/jquery-1.12.0.min.js\"></script>\n" +
                "<script>window.jQuery || document.write('<script src=\"path/to/jquery-1.12.0.js\"><\\/script>";
    }


    public void getHead() throws SQLException {

        this.head = "<!DOCTYPE html><HTML lang='" + this.idioma + "'><head><meta http-equiv='content-type' content='text/html' charset=UTF-8> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> ";
        String sql = "SELECT p.IdPagina as IdPagina, p.AutorPagina as autor, pi.TituloPagina as titulo, pi.DescripcionPagina as descripcion, pi.KeywordsPagina as keywords"
                + " FROM pagina p, pagina_idioma pi"
                + " WHERE p.IdPagina = pi.IdPagina and p.NombrePagina = '" + this.pagina + "' and pi.IdiomaPagina ='" + this.idioma + "';";

        rs = conexion.executeQuery(sql);
        if (rs.next()) {
            this.idPagina = rs.getInt("IdPagina");
            this.head = this.head.concat("<title>" + rs.getString("titulo") + "</title>");
            this.head = this.head.concat("<meta name='author' content='" + rs.getString("autor") + "'>");
            this.head = this.head.concat("<meta name='description' content='" + rs.getString("descripcion") + "'>");
            this.head = this.head.concat("<meta name='keywords' content='" + rs.getString("keywords") + "'>");
        }
        sql = "SELECT b.NombreFicheroCss as css,b.Path as ruta FROM `pagina_css` a,fichero_css b WHERE a.`IdPagina`=" + this.idPagina + " and a.`idFicheroCss`=b.IdFicheroCss";
        rs = conexion.executeQuery(sql);
        while (rs.next()) {
            this.head = this.head + "<link rel='stylesheet' href='" + rs.getString("ruta") + "/" + rs.getString("css") + ".css'>";
        }
        this.head = this.head.concat("</head>");
    }

    public void getBody() throws SQLException {
        CallableStatement cstmt = (CallableStatement) conexion.getConexion().prepareCall("{call get_arbol(?,?)}");
        cstmt.registerOutParameter(1, Types.VARCHAR);
        cstmt.setInt(2, this.idPagina);
        cstmt.execute();
        body = cstmt.getString(1);
    }

    public void cerrarConexion() throws SQLException {
        conexion.cerrar();
    }
}
