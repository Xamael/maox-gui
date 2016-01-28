package org.maox.arq.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import org.maox.arq.error.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulación de transacción a BD
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class ConnectionDB {

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(ConnectionDB.class);

	/* Conexión a DB */
	Connection conn = null;
	Statement st = null;
	PreparedStatement psmt = null;
	ResultSet res = null;

	/* Flag que indica si hay alguna transacción pendiente */
	private boolean bCommitPdte = false;

	/**
	 * Constructor a traves de una conexión a DB
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public ConnectionDB(Connection conn) throws SQLException {
		this.conn = conn;

		// Por derecto no serán autocommit
		conn.setAutoCommit(false);
	}

	/**
	 * Pasa los paramtros a la sentencia SQL
	 * 
	 * @param vParams
	 * @throws SQLException
	 */
	private void addParams(ArrayParamDB vParams) throws SQLException {
		// Paso de parametros
		psmt.clearParameters();

		if (vParams != null) {
			Enumeration<ParamDB> eParams = vParams.elements();

			int idx = 1;

			while (eParams.hasMoreElements()) {
				ParamDB param = eParams.nextElement();

				if (param.isInteger()) {
					if (!param.isNull()) {
						psmt.setInt(idx, param.getInt());
					} else {
						psmt.setNull(idx, Types.INTEGER);
					}
				}

				else if (param.isReal()) {
					if (!param.isNull()) {
						psmt.setDouble(idx, param.getDouble());
					} else {
						psmt.setNull(idx, Types.REAL);
					}
				}

				else if (param.isString()) {
					if (!param.isNull()) {
						psmt.setString(idx, param.getString());
					} else {
						psmt.setNull(idx, Types.VARCHAR);
					}
				}

				else if (param.isDate()) {
					if (!param.isNull()) {
						psmt.setDate(idx, param.getDate());
					} else {
						psmt.setNull(idx, Types.DATE);
					}
				}

				else if (param.isTime()) {
					if (!param.isNull()) {
						psmt.setTimestamp(idx, param.getTime());
					} else {
						psmt.setNull(idx, Types.TIMESTAMP);
					}
				}

				idx++;
			}
		}
	}

	/**
	 * Cierre de los recursos de conexión abiertos
	 */
	public void close() {
		try {
			if (st != null) {
				st.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (res != null) {
				res.close();
			}

			// TODO: ¿Pool?
			// conn.close();
		} catch (SQLException eSQL) {
			logger.error((new AppException(eSQL)).toString());
		}
	}

	/**
	 * Realiza el commit de la transacción
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		conn.commit();
		bCommitPdte = false;
	}

	/**
	 * Ejecuta una sentencia en la DB
	 * 
	 * @param sql
	 */
	public void execute(String sql) throws SQLException {
		st = conn.createStatement();
		st.execute(sql);

		// Se indica que la transacción está pendiente
		bCommitPdte = true;
	}

	/**
	 * Ejecuta un script con varias sentencias SQL
	 * 
	 * @param script Ruta del fichero SQL
	 */
	public void executeScript(String script) throws SQLException, FileNotFoundException, IOException {
		// Vector con las sentencias SQL
		Vector<String> vSQL = new Vector<String>();

		// Lectura del fichero
		BufferedReader fSQL = new BufferedReader(new FileReader(script));

		// Lectura de las líneas de un fichero
		String strLine = null;
		String strQuery = "";

		// TODO: Si el comentario está al final de la línea
		// TODO: Varios tokens por línea
		while ((strLine = fSQL.readLine()) != null) {
			// Si no comienza por comentario '--'
			if (strLine.startsWith("--")) {
				continue;
			}

			// Se añade la sentencia
			strQuery = strQuery + " " + strLine;

			// Si finaliza por ; se almacena la sentencia
			if (strQuery.indexOf(';') != -1) {
				vSQL.add(strQuery);
				strQuery = "";
			}
		}

		// Se cierra el fichero de lectura
		fSQL.close();

		Iterator<String> iSQL = vSQL.iterator();
		while (iSQL.hasNext()) {
			update(iSQL.next());
		}
	}

	/**
	 * Indica si la transacción está pendiente
	 * 
	 * @return boolean
	 */
	public boolean isWaitingForCommit() {
		return bCommitPdte;
	}

	/**
	 * Carga una SQL a partir de un recurso
	 * 
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public String loadQuery(String resourceName) throws IOException {
		// Lectura del fichero
		BufferedReader fSQL = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader()
				.getResourceAsStream(resourceName)));

		// Lectura de las líneas de un fichero
		String line = null;
		StringBuilder sql = new StringBuilder();

		while ((line = fSQL.readLine()) != null) {

			// Se eliminan los espacios en blanco innecesarios
			line = line.trim();

			// Se eliminan los comentarios que comienzan por --
			int idxComentario = line.indexOf("--");

			// Se añade la sentencia eliminando los comentarios '--' en caso necesario
			if (idxComentario == -1) {
				sql.append(line).append(' ');
			} else {
				sql.append(line.substring(0, idxComentario)).append(' ');
			}
		}

		// Se cierra el fichero de lectura
		fSQL.close();

		return sql.toString();
	}

	/**
	 * Realiza una consulta en la DB sin parametros
	 * 
	 * @param sql
	 */
	public ResultSet query(String sql) throws SQLException {
		return query(sql, null);
	}

	/**
	 * Realiza una consulta en la DB con parametros
	 * 
	 * @param sql Sentencia a ejecutar
	 * @param params Parametros
	 * @return Cursor de Resultados
	 * @throws SQLException
	 */
	public ResultSet query(String sql, ArrayParamDB vParams) throws SQLException {

		logger.debug("SQL: {}", sql);
		logger.debug("Params: {}", (vParams == null ? "[]" : vParams));

		res = null;

		psmt = conn.prepareStatement(sql);
		addParams(vParams);
		res = psmt.executeQuery();

		return res;
	}

	/**
	 * Dehace los cambios de la transacción
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		conn.rollback();
		bCommitPdte = false;
	}

	/**
	 * Realiza una sentencia INSERT, UPDATE o DELETE en la DB sin parametros
	 * 
	 * @param sql
	 */
	public void update(String sql) throws SQLException {
		update(sql, null);
	}

	/**
	 * Realiza una sentencia INSERT, UPDATE o DELETE en la DB con parametros
	 * 
	 * @param sql
	 * @param vParams
	 * @throws SQLException
	 */
	public void update(String sql, ArrayParamDB vParams) throws SQLException {
		logger.debug("SQL: {}", sql);
		logger.debug("Params: {}", (vParams == null ? "[]" : vParams));

		psmt = conn.prepareStatement(sql);
		addParams(vParams);
		psmt.executeUpdate();

		// Se indica que la transacción está pendiente
		bCommitPdte = true;
	}
}
