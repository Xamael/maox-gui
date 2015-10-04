package org.maox.arq.error;

import static org.maox.arq.error.IAppExceptionMessages.APP_ERROR;
import static org.maox.arq.error.IAppExceptionMessages.APP_WARNING;
import static org.maox.arq.error.IAppExceptionMessages.EX_UNKNOW;
import static org.maox.arq.error.IAppExceptionMessages.SYS_ERROR;
import static org.maox.arq.error.IAppExceptionMessages.SYS_WARNING;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Clase que encapsula las posibles excepciones controladas de la aplicación
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class AppException extends Exception {

	/* Código de mensaje de la excepcion */
	private int			iCodeEx;
	/* Código de error SQL */
	private int			iCodeSQL;
	/* Excepción Java que produjo el error */
	private Exception	ex;
	/* Tipo de Excepcion */
	private int			iTypeEx;
	/* Texto Libre */
	private String		strTexto;

	/**
	 * Constructor Vacio
	 */
	public AppException() {
		super();
		init(SYS_ERROR, EX_UNKNOW, 0, null, null);
	}

	/**
	 * Constructor de una excepcion con código y excepcion
	 * 
	 * @param iCodeEx Código Excepcion
	 */
	public AppException(int iCodeEx) {
		super();
		init(SYS_ERROR, iCodeEx, 0, null, null);
	}

	/**
	 * Constructor de una excepcion con código y excepcion
	 * 
	 * @param ex Excepión que provoco el error
	 */
	public AppException(Exception ex) {
		super();
		init(SYS_ERROR, EX_UNKNOW, 0, ex, null);
	}

	/**
	 * Constructor de una excepcion con código y excepcion
	 * 
	 * @param iCodeEx Código Excepcion
	 * @param ex Excepión que provoco el error
	 */
	public AppException(int iCodeEx, Exception ex) {
		super();
		init(SYS_ERROR, iCodeEx, 0, ex, null);
	}

	/**
	 * Constructor de una excepcion con código y tipo
	 * 
	 * @param iTypeExcep Tipo Excepcion
	 * @param iCodeEx Código Excepcion
	 */
	public AppException(int iTypeExcep, int iCodeEx) {
		super();
		init(iTypeExcep, iCodeEx, 0, null, null);
	}

	/**
	 * Constructor de una excepcion con código y tipo
	 * 
	 * @param iTypeExcep Tipo Excepcion
	 * @param iCodeEx Código Excepcion
	 * @param ex Excepión que provoco el error
	 */
	public AppException(int iTypeExcep, int iCodeEx, Exception ex) {
		super();
		init(iTypeExcep, iCodeEx, 0, ex, null);
	}

	/**
	 * Constructor con texto libre
	 * 
	 * @param iCodeEx Código interno de incidencia
	 * @param strText Texto Libre
	 */
	public AppException(int iCodeEx, String strText) {
		super();
		init(SYS_ERROR, iCodeEx, 0, null, strText);
	}

	/**
	 * Constructor con texto libre y excepcion
	 * 
	 * @param iCodeEx Código interno de incidencia
	 * @param ex Excepión que provoco el error
	 * @param strText Texto Libre
	 */
	public AppException(int iCodeEx, Exception ex, String strText) {
		super();
		init(SYS_ERROR, iCodeEx, 0, ex, strText);
	}

	/**
	 * Constructor con texto libre y excepcion
	 * 
	 * @param iCodeEx Código interno de incidencia
	 * @param ex Excepión que provoco el error
	 * @param strText Texto Libre
	 */
	public AppException(int iTypeExcep, int iCodeEx, int iCodeSQL, Exception ex, String strText) {
		super();
		init(iTypeExcep, iCodeEx, iCodeSQL, ex, strText);
	}

	/**
	 * Devuelve el tipo de error de la excepcion
	 * 
	 * @return
	 */
	public int getAppCode() {
		return iCodeEx;
	}

	/**
	 * Inicialización del Constructor
	 * 
	 * @param iTypeExcep Tipo Excepcion
	 * @param iCodeEx Código Excepcion
	 * @param iCodeSQL Código SQL del error en DB
	 * @param ex Excepión que provoco el error
	 */
	private void init(int iTypeExcep, int iCodeEx, int iCodeSQL, Exception ex, String strText) {
		this.iCodeEx = iCodeEx;
		this.iCodeSQL = iCodeSQL;
		this.iTypeEx = iTypeExcep;
		this.ex = ex;
		this.strTexto = strText;
	}

	@Override
	public String toString() {
		String str = "";

		// Tipo de error
		switch (iTypeEx) {
		case SYS_ERROR:
			str += "ERROR (SISTEMA): E";
			break;
		case SYS_WARNING:
			str += "INFO (SISTEMA): E";
			break;
		case APP_ERROR:
			str += "ERROR (APP): E";
			break;
		case APP_WARNING:
			str += "INFO (APP): E";
			break;
		}

		// Código de Error
		String strCode = "";
		if (iCodeEx != -1)
			strCode = "" + iCodeEx;
		else
			strCode = "-01";

		for (int i = strCode.length(); i < 4 && iCodeEx != -1; i++)
			str += "0";

		str += strCode + " - ";

		// Mensaje de Error
		str += Messages.getMessage(iCodeEx);

		// Mensaje de texto libre
		if (strTexto != null)
			str += " " + strTexto;

		// Error SQL
		if (iCodeSQL != 0) {
			String strCodeSQL = "" + iCodeSQL;
			str += "\nSQL_CODE: ";
			for (int i = strCodeSQL.length(); i < 4; i++)
				str += "0";
			str += strCodeSQL;
		}

		// Excepcion
		if (ex != null) {
			str += "\nEXCEPTION: " + ex.getMessage() + "\n";

			StringWriter buff = new StringWriter();
			ex.printStackTrace(new PrintWriter(buff));
			str += buff.toString();
		}

		return str;
	}

	@Override
	public String getMessage() {
		String strMensaje = "";

		if (iCodeEx != -1)
			strMensaje = Messages.getMessage(iCodeEx);
		else
			strMensaje = super.getMessage();

		if (ex != null)
			strMensaje += ": " + ex.getMessage();

		return strMensaje;
	}
}
