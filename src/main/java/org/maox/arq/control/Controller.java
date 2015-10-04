package org.maox.arq.control;

import static org.maox.arq.error.IAppExceptionMessages.EX_SQL_ERROR;
import static org.maox.arq.error.IAppExceptionMessages.EX_VIEW_ERROR;
import static org.maox.arq.error.IAppExceptionMessages.SYS_ERROR;

import java.sql.SQLException;

import org.maox.arq.error.AppException;
import org.maox.arq.error.Log;
import org.maox.arq.gui.view.GUIOptionPane;
import org.maox.arq.gui.view.GUIView;
import org.maox.arq.infra.Params;
import org.maox.arq.language.Language;

/**
 * Clase abstracta que define un controlador estandard.
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public abstract class Controller implements IController {

	/* Control padre del escritorio */
	private ControllerDesktop	controlDesk	= null;

	/* Vista asociada */
	protected String			viewName	= null;
	protected GUIView			view		= null;

	/**
	 * Cierre de las posibles conexiones a DB
	 */
	protected abstract void closeConnection();

	/**
	 * Se sale de una vista. Hay un control por si existen transacciones pendientes
	 */
	private void closeView() throws SQLException {
		// Comprobar si hay transacciones pendientes
		if (isWaitingForCommit()) {
			Object[] options = { Language.getString("ARQ_YES"), Language.getString("ARQ_NOT") };
			int iOption = GUIOptionPane.showOptionDialog(controlDesk.getDesktop(), Language.getString("DB_TRANSAC"), Language.getString("ARQ_WARNING"),
					GUIOptionPane.YES_NO_OPTION, GUIOptionPane.WARNING_MESSAGE, null, options, options[1]);

			if (iOption == GUIOptionPane.YES_OPTION) {
				rollback();
			}
			else {
				return;
			}
		}

		// Se solicita el cerrado del panel
		controlDesk.removeView();
	}

	/**
	 * Guarda los cambios de las conexiones pendientes de este controlador
	 */
	protected abstract void commit() throws SQLException;

	@Override
	public void execute(int iAction, Params param) {
		Log.debug(this.getClass(), "execute " + iAction);

		// Antes de la acción hay que borrar el area de mensajes de estado
		showMessage("");

		try {
			switch (iAction) {
			case EXIT:
				closeView();
				break;
			case EXIT_AND_SAVE:
				exitAndSave();
				break;
			}
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	/**
	 * Guarda los cambios y sale de la vista
	 */
	private void exitAndSave() throws SQLException {
		commit();
		closeConnection();
		closeView();
	}

	/**
	 * Manejador de Excepciones. Saca el error en la barra de estado
	 * 
	 * @param ex Excepcion
	 */
	protected void handleException(Exception ex) {
		if (ex instanceof SQLException) {
			AppException eppEx = new AppException(SYS_ERROR, EX_SQL_ERROR, ((SQLException) ex).getErrorCode(), ex, null);
			Log.error(this.getClass(), eppEx);
			controlDesk.showMessage(eppEx);
		}
		else {
			Log.error(this.getClass(), new AppException(ex));
		}
	}

	/**
	 * Inicialización de la vista
	 * 
	 * @throws AppException
	 */
	public void init(ControllerDesktop controlPadre) throws AppException {
		// Inicialización del control
		controlDesk = controlPadre;
		// Inicialización de la vista
		initView();
	}

	/**
	 * Inicialización de la vista
	 * 
	 * @throws AppException
	 */
	private void initView() throws AppException {
		// Creación de la Vista asociada
		@SuppressWarnings("rawtypes")
		Class classPanel;
		try {
			classPanel = Class.forName(viewName);
			view = (GUIView) classPanel.newInstance();
			controlDesk.addView(view);
			view.setController(this);
		}
		catch (Exception e) {
			throw new AppException(EX_VIEW_ERROR, e);
		}
	}

	/**
	 * Determina si hay alguna transacción pendiente en el proceso controlador
	 * 
	 * @return
	 */
	public abstract boolean isWaitingForCommit();

	/**
	 * Deshace los cambios de las conexiones pendientes de este controlador
	 */
	protected abstract void rollback() throws SQLException;

	/**
	 * Muestra un mensaje en la barra de estado del escritorio
	 * 
	 * @param msg
	 */
	public void showMessage(String msg) {
		controlDesk.showMessage(msg);
	}

}
