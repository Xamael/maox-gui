package org.maox.arq.db;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Servidor de conexiones a BBDD. Centralizará todas las peticiones y transacciones
 * y mantendra un control de las conexiones abiertas para mantener los recursos del sistema
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class ServerDB {
	// Patrón singletón
	private static ServerDB	_serverDB	= null;

	/**
	 * Instanciador (Patrón singleton)
	 * 
	 * @return Instancia del servidor de conexiones
	 */
	public static ServerDB getInstance() {
		if (_serverDB == null)
			_serverDB = new ServerDB();

		return _serverDB;
	}

	/* Conexión a la DB */
	private ConnectionDB	conn	= null;

	/**
	 * Constructor privado, Sólo se podrá instanciar la clase con getInstance
	 */
	private ServerDB() {
	}

	/**
	 * Devuelve una conexión para hacer sentencias SQL
	 * 
	 * @return Conexión
	 */
	public ConnectionDB getConnection() {
		return conn;
	}

	/**
	 * Inicialización del Driver de DB y conexión
	 * 
	 * @param driverName
	 * @param urlDB
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void initDriver(String driverName, String urlDB) throws ClassNotFoundException, SQLException {
		// Cargamos el controlador JDBC  
		Class.forName(driverName);
		// Nos conectamos a la base de datos creándola en caso de que no exista   
		// Indicamos que se guarda en un fichero ...
		conn = new ConnectionDB(DriverManager.getConnection("jdbc:hsqldb:file:" + urlDB));
	}

}
