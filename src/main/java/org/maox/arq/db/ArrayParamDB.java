package org.maox.arq.db;

import static org.maox.arq.error.IAppExceptionMessages.EX_PARSE_ERROR;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import org.maox.arq.error.AppException;

/**
 * Clase que encapsula una lista de parametros de BBDD para los SQL.
 * 
 * @author Alex Orgaz
 * @version 01.00
 */
public class ArrayParamDB {
	// Atributos
	Vector<ParamDB>	vLista	= null;

	/**
	 * Constructor
	 */
	public ArrayParamDB() {
		vLista = new Vector<ParamDB>();
	}

	/**
	 * Vuelca en un String la informaci�n del vector de parametros
	 */
	@Override
	public String toString() {
		String strRes = null;

		Enumeration<ParamDB> eParam = elements();

		while (eParam.hasMoreElements()) {
			if (strRes == null)
				strRes = "";
			else
				strRes += ", ";

			ParamDB p = (ParamDB) eParam.nextElement();
			strRes += "[" + p + "]";
		}

		return strRes;
	}

	/**
	 * Devuelve una enumeración de la lista de parametros
	 * 
	 * @return Enumeraci�n
	 */
	public Enumeration<ParamDB> elements() {
		return vLista.elements();
	}

	/**
	 * A�ade un parametro de tipo entero
	 * 
	 * @param iParam
	 */
	public void add(int iParam) {
		vLista.addElement(new ParamDB(iParam));
	}

	/**
	 * Añade un parametro tipo real (float)
	 * 
	 * @param fParam
	 */
	public void add(float fParam) {
		vLista.addElement(new ParamDB(fParam));
	}

	/**
	 * Añade un parametro tipo real (double)
	 * 
	 * @param dParam
	 */
	public void add(double dParam) {
		vLista.addElement(new ParamDB(dParam));
	}

	/**
	 * Añade un parametro tipo cadena
	 * 
	 * @param strParam
	 */
	public void add(String strParam) {
		vLista.addElement(new ParamDB(strParam));
	}

	/**
	 * Añade un parametro tipo fecha
	 * 
	 * @param fecParam
	 */
	public void add(java.sql.Date fecParam) {
		vLista.addElement(new ParamDB(fecParam));
	}

	/**
	 * Añade un parametro tipo Timestamp
	 * 
	 * @param fecParam
	 */
	public void add(Timestamp tmpParam) {
		vLista.addElement(new ParamDB(tmpParam));
	}

	/**
	 * Añade un parametro tipo fecha (util)
	 * 
	 * @param fecParam
	 */
	public void add(java.util.Date fecParam) {
		vLista.addElement(new ParamDB(fecParam));
	}

	/**
	 * Añade un parametro tipo fecha formateada en cadena con la mascara de fecha
	 * 
	 * @param strParam
	 * @param strMascara
	 *            (Ver mascaras de SimpleDateFormat) ejemplo dd/MM/yyyy
	 *            (importante mayusculas y minusculas)
	 * @throws ParseException
	 */
	public void addDateMsk(String fecParam, String strMascara) throws AppException {
		try {
			if (fecParam != null && fecParam.length() > 0) {
				SimpleDateFormat formateador = new SimpleDateFormat(strMascara);
				vLista.addElement(new ParamDB(formateador.parse(fecParam)));
			}
			else {
				vLista.addElement(new ParamDB((Date) null));
			}
		}
		catch (ParseException eParse) {
			throw new AppException(EX_PARSE_ERROR, eParse, "Timestamp Mask " + fecParam + " to " + strMascara + ".");
		}
	}

	/**
	 * Añade un parametro tipo Timestamp formateada en cadena con la mascara de
	 * fecha
	 * 
	 * @param tmpParam
	 * @param strMascara
	 *            (Ver mascaras de SimpleDateFormat) ejemplo dd/MM/yyyy
	 *            (importante mayusculas y minusculas)
	 * @throws ParseException
	 */
	public void addTimeMsk(String tmpParam, String strMascara) throws AppException {
		try {
			if (tmpParam != null && tmpParam.length() > 0) {
				SimpleDateFormat formateador = new SimpleDateFormat(strMascara);
				vLista.addElement(new ParamDB(new Timestamp(formateador.parse(tmpParam).getTime())));
			}
			else {
				vLista.addElement(new ParamDB((Timestamp) null));
			}
		}
		catch (ParseException eParse) {
			throw new AppException(EX_PARSE_ERROR, eParse, "Timestamp Mask " + tmpParam + " to " + strMascara + ".");
		}
	}
}
