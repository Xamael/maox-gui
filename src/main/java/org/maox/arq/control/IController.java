package org.maox.arq.control;

import org.maox.arq.infra.Params;

/**
 * Interfaz de los Controladores
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public interface IController {

	/* Parametros */
	final public static String	SUPER_CONTROL	= "SUPER_CONTROL";

	/* Acciones */
	/* Iniciar un Controlador */
	final public static int		INIT			= 1;
	/* Salir sin guardar cambios */
	final public static int		EXIT			= 0;
	/* Acción de menú */
	final public static int		MENU_ACTION		= 2;
	/* Abrir una ventana */
	final public static int		OPEN_WINDOW		= 3;
	/* Salir guardando cambios */
	final public static int		EXIT_AND_SAVE	= 4;
	/* Buscar */
	final public static int		SEARCH			= 5;
	/* Insertar */
	final public static int		INSERT			= 6;
	/* Actualizar */
	final public static int		UPDATE			= 7;
	/* Eliminar */
	final public static int		DELETE			= 8;

	/**
	 * Ejecución de una acción lanzado desde la vista
	 * 
	 * @param iAccion Código de Acción
	 */
	public void execute(int iAction, Params param);
}
