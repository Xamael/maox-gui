package org.maox.arq.gui.component;

import static org.maox.arq.error.IAppExceptionMessages.EX_FILE_NOT_FOUND;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.maox.arq.error.AppException;
import org.maox.arq.error.Log;

/**
 * Componente visual de un botón de aplicación
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton {

	/* Iconos predefinidos */
	public final static String ICON_ACCEPT = "/org/maox/arq/img/icons/accept16.png";
	public final static String ICON_ADD = "/org/maox/arq/img/icons/add16.png";
	public final static String ICON_CANCEL = "/org/maox/arq/img/icons/cancel16.png";
	public final static String ICON_CLOSE = "/org/maox/arq/img/icons/close16.png";
	public final static String ICON_EDIT = "/org/maox/arq/img/icons/edit16.png";
	public final static String ICON_EXPORT = "/org/maox/arq/img/icons/export16.png";
	public final static String ICON_GRAPH = "/org/maox/arq/img/icons/graph16.png";
	public final static String ICON_IMPORT = "/org/maox/arq/img/icons/import16.png";
	public final static String ICON_INFO = "/org/maox/arq/img/icons/info16.png";
	public final static String ICON_WARNING = "/org/maox/arq/img/icons/warning16.png";

	/* Constantes de tipo de boton */
	public final static int NORMAL_BUTTON = 0;
	public final static int CLOSE_BUTTON = 1;
	public final static int ACCEPT_BUTTON = 2;

	/* Tipo de botón */
	private int iType = NORMAL_BUTTON;

	/**
	 * Constructor por defecto
	 */
	public GUIButton() {
		super();
		initialize();
	}

	/**
	 * Constructor con imagen
	 */
	public GUIButton(Icon icon) {
		super(icon);
		initialize();
	}

	/**
	 * Constructor con texto
	 */
	public GUIButton(String text) {
		super(text);
		initialize();
		setText(text);
	}

	/**
	 * Constructor con imagen y texto
	 */
	public GUIButton(String text, Icon icon) {
		super(text, icon);
		initialize();
		setText(text);
	}

	/**
	 * Recupera el tipo del botón
	 * 
	 * @return
	 */
	public int getType() {
		return iType;
	}

	/**
	 * Inicialización del componente
	 */
	protected void initialize() {
		setSize(90, 28);

	}

	/**
	 * Establece un icono a partir de la ruta de un fichero de imagen
	 * 
	 * @param strIcon
	 */
	public void setIcon(String strIcon) {
		// Se obtiene el recurso de sistema
		URL urlIcon = getClass().getResource(strIcon);

		if (urlIcon != null) {
			ImageIcon icon = new ImageIcon(urlIcon);
			setIcon(icon);
		} else {
			Log.warning(this.getClass(), new AppException(EX_FILE_NOT_FOUND, strIcon));
		}
	}

	/**
	 * Establece el tipo del botón
	 * 
	 * @param iType
	 */
	public void setType(int iType) {
		this.iType = iType;
	}
}
