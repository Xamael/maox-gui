package org.maox.arq.gui.component;

import static org.maox.arq.error.IAppExceptionMessages.EX_FILE_NOT_FOUND;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.maox.arq.error.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Componente visual de un botón de aplicación
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIButton extends JButton {

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(GUIButton.class);
	/* Iconos predefinidos */
	public final static String ICON_ACCEPT = "/img/icons/accept16.png";
	public final static String ICON_ADD = "/img/icons/add16.png";
	public final static String ICON_CANCEL = "/img/icons/cancel16.png";
	public final static String ICON_CLOSE = "/img/icons/close16.png";
	public final static String ICON_EDIT = "/img/icons/edit16.png";
	public final static String ICON_EXPORT = "/img/icons/export16.png";
	public final static String ICON_GRAPH = "/img/icons/graph16.png";
	public final static String ICON_IMPORT = "/img/icons/import16.png";
	public final static String ICON_INFO = "/img/icons/info16.png";
	public final static String ICON_WARNING = "/img/icons/warning16.png";

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
			logger.warn((new AppException(EX_FILE_NOT_FOUND, strIcon)).toString());
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
