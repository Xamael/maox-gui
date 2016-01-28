package org.maox.arq.gui.menu;

import javax.swing.JMenu;

/**
 * Componente gráfico de la entrada de menú
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIMenuOption extends JMenu {

	/**
	 * Constructor con nombre de la opción de menu
	 * 
	 * @param strName
	 */
	public GUIMenuOption(String strName) {
		super(strName);
	}

	@Override
	public boolean isFocusable() {
		return false;
	}
}
