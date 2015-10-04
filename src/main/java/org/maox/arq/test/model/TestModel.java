package org.maox.arq.test.model;

import java.util.Date;

public class TestModel implements ITestModel {

	/* Campos de Entidad */
	String strCodigo = null;

	String strDescripcion = null;

	String strPais = null;
	String strTicket = null;
	Date fchAlta = null;
	Date fchBaja = null;

	/**
	 * Constructor de entidad
	 */
	public TestModel() {
		super();
	}

	/**
	 * Recupera el código unico de la entidad
	 * 
	 * @return
	 */
	public String getCodigo() {
		return strCodigo;
	}

	/**
	 * Recupera la descripción de la entidad
	 * 
	 * @return
	 */
	public String getDescripcion() {
		return strDescripcion;
	}

	/**
	 * Recupera el país unico de la entidad
	 * 
	 * @return
	 */
	public String getPais() {
		return strPais;
	}

	/**
	 * Recupera el ticket de la entidad
	 * 
	 * @return
	 */
	public String getTicket() {
		return strTicket;
	}

	/**
	 * Almacena el código de la entidad
	 * 
	 * @param strCodigo
	 */
	public void setCodigo(String strCodigo) {
		this.strCodigo = strCodigo;
	}

	/**
	 * Almacena la descripción de la entidad
	 * 
	 * @param strDescripcion
	 */
	public void setDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	/**
	 * Almacena el país de la entidad
	 * 
	 * @param strPais
	 */
	public void setPais(String strPais) {
		this.strPais = strPais;
	}

	/**
	 * Almacena el ticket de la entidad
	 * 
	 * @param strTicket
	 */
	public void setTicket(String strTicket) {
		this.strTicket = strTicket;
	}

}
