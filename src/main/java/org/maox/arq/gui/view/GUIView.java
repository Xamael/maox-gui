package org.maox.arq.gui.view;

import org.maox.arq.control.Controller;
import org.maox.arq.gui.component.GUIPanel;
import org.maox.arq.infra.Params;

/**
 * Componente visual que representa a una vista y encapsula la comunicación con el controlador
 * Por defecto estará establicida en resolución SVGA
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public abstract class GUIView extends GUIPanel {

	/* Modos de acceso a la vista (Sólo lectura o permisos de modificación */
	protected static final int	READ		= 1;
	protected static final int	WRITE		= 2;

	/* Estados internos de la vista */
	static final int			NOD			= 0;	// No definido
	protected static final int	QRY			= 1;	// Consulta
	protected static final int	ADD			= 2;	// Añadir
	protected static final int	EDT			= 3;	// Edición
	protected static final int	DEL			= 4;	// Eliminación

	/* Controlador de la vista */
	private Controller			controller	= null;

	/* Titulo de la vista */
	private String				strTitle	= null;

	/* Resolución y tamaño de panel */
	private int					iResolution;
	private int					hView;
	private int					wView;

	/* Estado interno y modo de acceso */
	private int					status		= NOD;
	private int					mode		= READ;

	/**
	 * Constructor
	 */
	public GUIView() {
		super();
		setResolution(SVGA);
	}

	/**
	 * Constructor
	 * 
	 * @param iResolution
	 */
	public GUIView(int iResolution) {
		super();
		setResolution(iResolution);
	}

	/**
	 * Cambia el estado inserno de la vista
	 * 
	 * @param status Nuevo estado
	 */
	protected abstract void changeStatus(int status);

	/**
	 * Solicita al controlador la ejecución una acción sin parámetros
	 * 
	 * @param iAction Codigo de acción
	 */
	protected void execute(int iAction) {
		execute(iAction, null);

	}

	/**
	 * Solicita al controlador la ejecución una acción con parámetros
	 * 
	 * @param iAction Codigo de acción
	 * @param params Contenedor de Paramtros
	 */
	protected void execute(int iAction, Params params) {
		controller.execute(iAction, params);
	}

	/**
	 * Obtiene la resolución Horizontal de la pantalla
	 * 
	 * @return
	 */
	protected int getHResolution() {
		return hView;
	}

	/**
	 * Obtiene la resolución de la pantalla
	 * 
	 * @return
	 */
	protected int getResolution() {
		return iResolution;
	}

	/**
	 * Devuelve en que modo esta la vista
	 * 
	 * @return
	 */
	protected int getStatus() {
		return status;
	}

	/**
	 * Recopera el título de la vista
	 * 
	 * @return Titulo
	 */
	public String getTitle() {
		return strTitle;
	}

	/**
	 * Obtiene la resolución vertical de la pantalla
	 * 
	 * @return
	 */
	protected int getWResolution() {
		return wView;
	}

	/**
	 * Determina si la vista es de sólo lectura
	 * 
	 * @return
	 */
	protected boolean isReadOnly() {
		return (mode == READ);
	}

	/**
	 * Determina si la vista tiene alguna transacción pendiente
	 * 
	 * @return
	 */
	protected boolean isWaitingForCommit() {
		if (controller == null)
			return false;

		return controller.isWaitingForCommit();
	}

	/**
	 * Determina si la vista tiene permisos de escritura
	 * 
	 * @return
	 */
	protected boolean isWriteable() {
		return (mode == WRITE);
	}

	/**
	 * Refresco de la vista
	 * 
	 * @param iAction Acción de refresco
	 * @param pResult Datos de refresco
	 */
	public abstract void refresh(int iAction, Params pResult);

	/**
	 * Establece el modo de acceso de la vista (Sólo lectura o modificación)
	 * 
	 * @param mode
	 */
	protected void setAccessMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Establece el controlador asociado a la vista
	 * 
	 * @param controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Establece la resolución de la vista y adapta el tamaño de los paneles acorde a ella
	 * 
	 * @param iResolution Resolucion (SVGA, XGA, SXGA)
	 */
	protected void setResolution(int iResolution) {
		this.iResolution = iResolution;

		switch (iResolution) {
		case SVGA:
			hView = SVGA_HEIGTH;
			wView = SVGA_WIDTH;
			break;
		case XGA:
			hView = XGA_HEIGTH;
			wView = XGA_WIDTH;
			break;
		case SXGA:
			hView = SXGA_HEIGTH;
			wView = SXGA_WIDTH;
			break;
		}
	}

	/**
	 * Establece el estado interno de la vista
	 * 
	 * @param status
	 */
	protected void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Establece el título de la vista
	 * 
	 * @param strTitle Título
	 */
	public void setTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	/**
	 * Muestra un mensaje en la barra de estado del escritorio
	 * 
	 * @param msg
	 */
	protected void showMessage(String msg) {
		this.controller.showMessage(msg);
	}

}
