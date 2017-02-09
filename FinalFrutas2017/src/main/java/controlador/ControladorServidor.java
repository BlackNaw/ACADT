package controlador;


import pagina.AArmador;
import pagina.Armador1;
import pagina.Pagina;

import java.sql.SQLException;

public class ControladorServidor {
	
	private String pagina = "";
	private String link = "hola";
	
	public ControladorServidor(String pagina){		
		this.pagina = pagina;	// index primera vez	
	}
	
	public String elegirArmador() throws ClassNotFoundException, SQLException{
		
		
		if(this.pagina.equals("index")){
			AArmador miArmador = new Armador1();
			link = new Pagina("index", miArmador,"jdbc:mysql://localhost/pagina2017","root","").getPagina();
		}

		
		return link;
	}
	

}
