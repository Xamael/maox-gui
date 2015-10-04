package org.maox.arq.gui.component;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Componente visual de una etiqueta
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUILabel extends JLabel {

	/**
	 * Constructor básico
	 */
	public GUILabel() {
		super();
		init();
	}

	/**
	 * Constructor con una imagen o icono
	 * 
	 * @param image Imagen
	 */
	public GUILabel(Icon image) {
		super(image);
		init();
	}

	/**
	 * Constructor con una imagen o icono y un alineamiento
	 * 
	 * @param image Imagen
	 * @param horizontalAlignment Alineamiento
	 */
	public GUILabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		init();
	}

	/**
	 * Constructor con un texto
	 * 
	 * @param text Texto
	 */
	public GUILabel(String text) {
		super(text);
		init();
	}

	/**
	 * Constructor con un texto, imagen y alineamiento
	 * 
	 * @param text
	 * @param icon
	 * @param horizontalAlignment
	 */
	public GUILabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		init();
	}

	/**
	 * Constructor con un texto y alineamiento
	 * 
	 * @param text
	 * @param horizontalAlignment
	 */
	public GUILabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		init();
	}

	private void init() {
	}

	@Override
	public boolean isFocusable() {
		/* Las etiquetas no podrán tener el foco */
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
}
