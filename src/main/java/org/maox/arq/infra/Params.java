package org.maox.arq.infra;

import java.util.Hashtable;

/**
 * Contenedor de parametros para el paso de información entre procesos o vistas al controlador Los parametros se organizarán en una tupla <Clave, Valor>
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class Params {

	/* Tabla con los parametros */
	private Hashtable<String, Object>	tblParam	= null;

	/**
	 * Constructor, inicializa la tabla para almacenar los parametros
	 */
	public Params() {
		tblParam = new Hashtable<String, Object>();
	}

	/**
	 * Crea una tabla de parametros y le inserta un valor inicial
	 * 
	 * @param strKey
	 * @param obj
	 */
	public Params(String strKey, Object obj) {
		tblParam = new Hashtable<String, Object>();
		tblParam.put(strKey, obj);
	}

	/**
	 * Recupera un parametro del contenedor a partir de la clave asignada
	 * 
	 * @param strKey Clave
	 * @return Valor asignado a la clave
	 */
	public Object getParam(String strKey) {
		return tblParam.get(strKey);
	}

	/**
	 * Incuye un parametro en el contenedor, si el parametro es nulo no se añade ya que el comportamiento de getParam sera el mismo con valor nulo o que no este el parametro
	 * 
	 * @param strKey Clave de acceso
	 * @param strValue Valor
	 */
	public void putParam(String strKey, Object strValue) {
		// Si el valor es nulo no se incorpora a la tabla
		if (strValue != null)
			tblParam.put(strKey, strValue);
	}
}
