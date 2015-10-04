package org.maox.arq.error;

import static org.maox.arq.error.IAppExceptionMessages.SYS_WARNING;

/**
 * Mensajes informativos
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class AppInfo extends AppException {

	/**
	 * Información con código de mensaje
	 * 
	 * @param iCode Codigo de Mensaje
	 */
	public AppInfo(int iCode) {
		super(SYS_WARNING, iCode);
	}

	/**
	 * Información con código de mensaje
	 * 
	 * @param iTypeEx Tipo de Información
	 * @param iCode Codigo de Mensaje
	 */
	public AppInfo(int iTypeEx, int iCode) {
		super(iTypeEx, iCode);
	}

	/**
	 * Información con código de mensaje y Excepcion
	 * 
	 * @param iCode Codigo de Mensaje
	 * @param e Exception
	 */
	public AppInfo(int iCode, Exception e) {
		super(SYS_WARNING, iCode, e);
	}
}
