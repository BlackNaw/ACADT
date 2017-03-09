package bd;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoBd {
	private static AccesoBd miConexion = null;
	private Connection conexion = null;
	private String driver = null;
	private String dbName= null;
	private String user = null;
	private String password= null;
	
	synchronized public static AccesoBd getMiConexion(){
		
		if (miConexion == null) miConexion = new AccesoBd();
		 return miConexion;
		
		
	}
	
	synchronized public void conectar(String driver, String dbName,String user,String password )
	throws SQLException, ClassNotFoundException{
		
		if(conexion != null) if(!this.driver.equals(driver) ||
				!this.dbName.equals(dbName) || 
				(this.user != null && !this.user.equals(user)) || 
				(this.password != null && !this.password.equals(password))
				
						){conexion.close();	}
								else return;	
		
		
		// Conexi�n Nueva
		//Cargar el driver		
		Class.forName(driver);
		
		if(user == null || password == null) conexion = (Connection) DriverManager.getConnection(dbName);
		else conexion = (Connection) DriverManager.getConnection(dbName,user,password);
		
		System.out.println("Conectado a BD " + dbName);
		this.driver = driver;
		this.dbName = dbName;
		this.user = user;
		this.password = password;
	}
	
	// Cerramos conexi�n
	synchronized public void cerrar() throws SQLException{
		if(conexion != null) conexion.close();
	}
	
	
	// Para ejecutar sentencias Select
	synchronized public ResultSet executeQuery(String sqlSentence) throws SQLException{
		if(conexion == null) return null;
		Statement sentence = (Statement) conexion.createStatement();		
		return sentence.executeQuery(sqlSentence);
	}

	// Para ejecutar sentencias update, delete e insert
	
	synchronized public int executeUpdate(String sqlSentence) throws SQLException{
		if(conexion == null) return -1;
		Statement sentence = (Statement) conexion.createStatement();
		return sentence.executeUpdate(sqlSentence);
	}
	 public Connection getConexion(){
		 return this.conexion;

	 }
}
