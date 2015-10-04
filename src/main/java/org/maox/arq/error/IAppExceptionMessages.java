package org.maox.arq.error;

/**
 * Conjunto de mensajes de error de sistema
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public interface IAppExceptionMessages {

	/* Tipos posibles de Excepcion */
	public static final int	SYS_ERROR					= 0;
	public static final int	SYS_WARNING					= 1;
	public static final int	APP_ERROR					= 2;
	public static final int	APP_WARNING					= 3;
	public static final int	SQL_ERROR					= 4;

	/* Códigos de Mensaje */
	public static final int	EX_UNKNOW					= -1;
	public static final int	EX_END_APP					= 0;
	public static final int	EX_NOT_CONFIG				= 1;
	public static final int	EX_DB_NOT_INIT				= 2;
	public static final int	EX_DB_NOT_CONNECTION		= 3;
	public static final int	EX_SCHEMA_NOT_INIT			= 4;
	public static final int	EX_MENU_ERROR				= 5;
	public static final int	EX_VIEW_ERROR				= 6;
	public static final int	EX_LOOK_AND_FEEL_NOT_FOUND	= 7;
	public static final int	EX_FILE_NOT_FOUND			= 8;
	public static final int	EX_XML_INVALID				= 9;
	public static final int	EX_FILE_WRITE_ERROR			= 10;
	public static final int	EX_LABEL_NO_TRANSLATE		= 11;
	public static final int	EX_SQL_ERROR				= 12;
	public static final int	EX_IO_ERROR					= 13;
	public static final int	EX_PARSE_ERROR				= 14;
}
