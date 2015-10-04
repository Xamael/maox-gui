package org.maox.arq.gui.menu;

import javax.swing.JMenuItem;

/**
 * Componente de una rama final de menú
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIMenuItem extends JMenuItem {

	public static final String	NAME				= "NAME";
	public static final String	CODIGO				= "CODIGO";
	public static final String	CONTROL				= "CONTROL";

	private String				strCode				= null;
	private String				strControllerName	= null;

	/**
	 * Crea una entrada de menu con un texto y una tecla de acceso rapido asociada
	 * 
	 * @param string Texto del Menu
	 * @param vkP Tecla de Acceso Rapido Asociada
	 */
	public GUIMenuItem(String string, int vkP) {
		super(string, vkP);
	}

	/**
	 * Devuelve el código asignado a la entrada de menu
	 * 
	 * @return Código del Menu
	 */
	public String getCode() {
		return strCode;
	}

	/**
	 * Devuelve el nombre del controlador asignado a la entrada del menu
	 * 
	 * @return Nombre del controlador
	 */
	public String getControllerName() {
		return strControllerName;
	}

	/**
	 * Establece el código de la entrada de menu
	 * 
	 * @param string
	 */
	public void setCode(String strCode) {
		this.strCode = strCode;
	}

	/**
	 * Establece el nombre del controlador que se ejecutará cuando se pinche esta entrada de menu
	 * 
	 * @param string
	 */
	public void setControllerName(String strControllerName) {
		this.strControllerName = strControllerName;

	}

}
