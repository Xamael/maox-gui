package org.maox.arq.db;

import java.sql.Timestamp;

/**
 * Clase que encapsula un parametro de BBDD para los SQL.
 * 
 * @author Alex Orgaz
 * @version 01.00
 */
public class ParamDB {
	// Constantes
	private final int	INT			= 1;
	private final int	REAL		= 2;
	private final int	STRING		= 3;
	private final int	DATE		= 4;
	private final int	TIME_STAMP	= 5;

	// Atributos
	int					iTipo;
	boolean				bNulo;
	int					iParam;
	double				dParam;
	String				strParam;
	java.sql.Date		fchParam;
	Timestamp			tmpParam;

	/**
	 * Constructor: Crea un parametro de tipo Entero
	 * 
	 * @param iEntero
	 */
	public ParamDB(int iEntero) {
		iTipo = INT;
		bNulo = false;
		iParam = iEntero;
	}

	/**
	 * Constructor: Crea un parametro de tipo Real
	 * 
	 * @param fReal
	 */
	public ParamDB(float fReal) {
		iTipo = REAL;
		bNulo = false;
		dParam = fReal;
	}

	/**
	 * Constructor: Crea un parametro de tipo Real (double)
	 * 
	 * @param dReal
	 */
	public ParamDB(double dReal) {
		iTipo = REAL;
		bNulo = false;
		dParam = dReal;
	}

	/**
	 * Constructor: Crea un parametro de tipo Cadena
	 * 
	 * @param strCadena
	 */
	public ParamDB(String strCadena) {
		iTipo = STRING;

		if (strCadena != null && strCadena.length() > 0) {
			bNulo = false;
			strParam = new String(strCadena);
		}
		else {
			bNulo = true;
			strParam = null;
		}
	}

	/**
	 * Constructor: Crea un parametro de tipo Date
	 * 
	 * @param fchCadena
	 */
	public ParamDB(java.sql.Date fchCadena) {
		iTipo = DATE;

		if (fchCadena != null) {
			bNulo = false;
			fchParam = (java.sql.Date) fchCadena.clone();
		}
		else {
			bNulo = true;
			fchParam = null;
		}
	}

	/**
	 * Constructor: Crea un parametro de tipo Date (Util)
	 * 
	 * @param fchCadena
	 */
	public ParamDB(java.util.Date fchCadena) {
		iTipo = DATE;

		if (fchCadena != null) {
			bNulo = false;
			fchParam = new java.sql.Date(fchCadena.getTime());
		}
		else {
			bNulo = true;
			fchParam = null;
		}
	}

	/**
	 * Constructor: Crea un parametro de tipo TimeStamp
	 * 
	 * @param tmpCadena
	 */
	public ParamDB(Timestamp tmpCadena) {
		iTipo = TIME_STAMP;

		if (tmpCadena != null) {
			bNulo = false;
			tmpParam = (Timestamp) tmpCadena.clone();
		}
		else {
			bNulo = true;
			tmpParam = null;
		}
	}

	/**
	 * Vuelca en un String la informaci�n del parametro
	 */
	@Override
	public String toString() {
		String strRes = "";

		if (isInteger())
			strRes = "INT: " + (bNulo ? "<null>" : "" + iParam);
		else if (isReal())
			strRes = "REAL: " + (bNulo ? "<null>" : "" + dParam);
		else if (isString())
			strRes = "CHAR: " + (bNulo ? "<null>" : strParam);
		else if (isDate())
			strRes = "DATE: " + (bNulo ? "<null>" : fchParam);
		else if (isTime())
			strRes = "TIME: " + (bNulo ? "<null>" : tmpParam);

		return strRes;
	}

	/**
	 * Detemina si el parametro es de tipo entero
	 * 
	 * @return true o false
	 */
	public boolean isInteger() {
		if (iTipo == INT)
			return true;

		return false;
	}

	/**
	 * Detemina si el parametro es de tipo real
	 * 
	 * @return true o false
	 */
	public boolean isReal() {
		if (iTipo == REAL)
			return true;

		return false;
	}

	/**
	 * Detemina si el parametro es de tipo entero
	 * 
	 * @return true o false
	 */
	public boolean isString() {
		if (iTipo == STRING)
			return true;

		return false;
	}

	/**
	 * Detemina si el parametro es de tipo fecha
	 * 
	 * @return true o false
	 */
	public boolean isDate() {
		if (iTipo == DATE)
			return true;

		return false;
	}

	/**
	 * Detemina si el parametro es de tipo Time Stamp
	 * 
	 * @return true o false
	 */
	public boolean isTime() {
		if (iTipo == TIME_STAMP)
			return true;

		return false;
	}

	/**
	 * Detemina si el parametro es de nulo
	 * 
	 * @return true o false
	 */
	public boolean isNull() {
		return bNulo;
	}

	/**
	 * Devuelve el valor entero de un paramtro
	 * 
	 * @return Valor entero del parametro
	 */
	public int getInt() {
		return iParam;
	}

	/**
	 * Devuelve el valor real de un paramtro
	 * 
	 * @return Valor real del parametro
	 */
	public double getDouble() {
		return dParam;
	}

	/**
	 * Devuelve el valor cadena de un paramtro
	 * 
	 * @return Valor real del parametro
	 */
	public String getString() {
		return strParam;
	}

	/**
	 * Devuelve el valor Fecha de un paramtro
	 * 
	 * @return Valor Fecha del parametro
	 */
	public java.sql.Date getDate() {
		return fchParam;
	}

	/**
	 * Devuelve el valor Timestamp de un paramtro
	 * 
	 * @return Valor Fecha del parametro
	 */
	public Timestamp getTime() {
		return tmpParam;
	}
}
