package pagina;

/**
 * Created by Luciano on 07/02/2017.
 */

import bd.AccesoBd;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Pagina implements IPagina{

    private String head = "";
    private String body = "<body> Hola Mundo</body></html>";

    private String pagina = "";
    private int idPagina;

    private AArmador miArmador;

    private String bd = "";
    private String login = "";
    private String password = "";

    private AccesoBd conexion = null;
    private  ResultSet rs = null;

    public Pagina(String pagina, AArmador armador , String bd, String login, String password){
        this.pagina = pagina;
        this.miArmador = armador;
        this.bd = bd;
        this.login = login;
        this.password = password;
    }
    private AccesoBd conectar() throws SQLException, ClassNotFoundException
    {
        AccesoBd acceso = AccesoBd.getMiConexion();
        acceso.conectar("com.mysql.jdbc.Driver", this.bd , this.login, this.password);
        return acceso;
    }
    public String getPagina() throws ClassNotFoundException, SQLException{
        conexion = conectar();
        getHead();
        //getBody();
      return this.miArmador.armar(this.head,this.body);
      //  return this.head + this.body;

    }
    public void getHead() throws SQLException {

        this.head = "<HTML lang='es'><head><meta http-equiv='content-type' content='text/html'; charset=UTF-8> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> ";
        String sql = "SELECT p.IdPagina as IdPagina, p.AutorPagina as autor, pi.TituloPagina as titulo, pi.DescripcionPagina as descripcion, pi.KeywordsPagina as keywords"
                + " FROM pagina p, pagina_idioma pi"
                + " WHERE p.IdPagina = pi.IdPagina and p.NombrePagina = '" + this.pagina + "' and pi.IdiomaPagina = 'es'";

        rs = conexion.executeQuery(sql);
        if(rs.next()){
            this.idPagina = rs.getInt("IdPagina");
            this.head = this.head.concat("<title>"+rs.getString("titulo")+"</title>");
            this.head = this.head.concat("<meta name='author' content='"+rs.getString("autor")+"'>");
            this.head = this.head.concat("<meta name='description' content='"+rs.getString("descripcion")+"'>");
            this.head = this.head.concat("<meta name='keywords' content='"+rs.getString("keywords")+"'>");
/*
            sql = "SELECT cs.Path as ruta, cs.Css as css FROM pagina_css pc, fichero_css cs WHERE pc.IdCss = cs.IdCss and pc.IdPagina=" + this.idPagina + ";";
            rs = conexion.executeQuery(sql);
            while(rs.next()){
                this.head = this.head + "<link rel='stylesheet' href='"+rs.getString("ruta")+"/"+rs.getString("css")+".css'>";
            }*/
        }

        this.head = this.head.concat("</head>");
    }
}
