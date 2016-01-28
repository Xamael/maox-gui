package org.maox.arq.control;

import org.maox.arq.infra.Container;

/**
 * Interfaz de los Controladores
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public interface IController {

	/* Parametros */
	final public static String SUPER_CONTROL = "SUPER_CONTROL";

	/* Acciones */
	final public static int BASE = 9000;
	/* Salir sin guardar cambios */
	final public static int EXIT = BASE + 0;
	/* Iniciar un Controlador */
	final public static int INIT = BASE + 1;
	/* Acción de menú */
	final public static int MENU_ACTION = BASE + 2;
	/* Abrir una ventana */
	final public static int OPEN_WINDOW = BASE + 3;
	/* Salir guardando cambios */
	final public static int EXIT_AND_SAVE = BASE + 4;
	/* Buscar */
	final public static int SEARCH = BASE + 5;
	/* Insertar */
	final public static int INSERT = BASE + 6;
	/* Actualizar */
	final public static int UPDATE = BASE + 7;
	/* Eliminar */
	final public static int DELETE = BASE + 8;

	/**
	 * Ejecución de una acción lanzado desde la vista
	 * 
	 * @param iAccion Código de Acción
	 */
	public void execute(int iAction, Container param);
}
