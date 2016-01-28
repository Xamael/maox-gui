package org.maox.arq.control;

import static org.maox.arq.error.IAppExceptionMessages.EX_END_APP;
import static org.maox.arq.error.IAppExceptionMessages.EX_FILE_NOT_FOUND;
import static org.maox.arq.error.IAppExceptionMessages.EX_FILE_WRITE_ERROR;
import static org.maox.arq.error.IAppExceptionMessages.EX_MENU_ERROR;
import static org.maox.arq.error.IAppExceptionMessages.EX_NOT_CONFIG;
import static org.maox.arq.error.IAppExceptionMessages.EX_XML_INVALID;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.jdom2.JDOMException;
import org.maox.arq.error.AppException;
import org.maox.arq.error.AppInfo;
import org.maox.arq.gui.component.GUIStatusBarFlags;
import org.maox.arq.gui.menu.GUIMenuBar;
import org.maox.arq.gui.menu.GUIMenuItem;
import org.maox.arq.gui.view.GUIDesktop;
import org.maox.arq.gui.view.GUIView;
import org.maox.arq.infra.Container;
import org.maox.arq.language.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador Principal de las Aplicaciones
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class ControllerDesktop implements IController {

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(ControllerDesktop.class);
	/* Fichero de configuración de arquitectura */
	private static final String PROP_ARQ = "/config/arq.properties";
	/* Fichero de Configuración */
	private Properties configArq = null;
	private Properties configApp = null;
	/* Marco Visual de Ejecución */
	private GUIDesktop frmApp = null;
	/* Barra de estado */
	private GUIStatusBarFlags status = null;
	/* Nombre de la aplicación */
	private String strTitleApp = null;

	/**
	 * Inicializa el controlador princpipal de la aplicación La parametrización de la aplicación vendrá desde el fichero
	 * de configuración
	 * 
	 * @param fileProperties Fichero de configuración
	 */
	public ControllerDesktop(Properties config) {
		String javaVersion = System.getProperty("java.specification.version");
		logger.info("Java Version {}", javaVersion);

		this.configApp = config;

		// Inicialización de Arquitectura
		initArq();

		// Inclusión de servidor de etiquetas para multileguaje
		Language.getInstance().addLangResource(configApp.getProperty("language"));
	}

	/**
	 * Añade la vista al escritorio. Este método suele ser llamado desde los controladores hijos
	 * 
	 * @param view Vista creada por un Control Hijo
	 */
	public void addView(GUIView view) {
		frmApp.addView(view);
		view.refresh(INIT, null);
	}

	@Override
	public void execute(int iAccion, Container param) {
		// Se borrara cualquier texto de la barra de estado antes de ejecutar
		// la acción
		status.setMessage("");

		switch (iAccion) {
		// Seleción de una entrada de menú
		case MENU_ACTION:
			openMenu(param);
			break;

		// Cierre de la aplicación
		case EXIT:
			// TODO: Control de salvado antes de salir
			exit(new AppInfo(EX_END_APP));
			break;
		}

	}

	/**
	 * Cierra la aplicación con error de Excepcion
	 * 
	 * @param e Excepcion de cierre
	 */
	protected void exit(AppException ex) {

		if (!(ex instanceof AppInfo)) {
			logger.error(ex.toString());
		}

		// Grabar posibles variables de configuracion
		saveConfig();

		// Desvincular el marco
		if (frmApp != null) {
			frmApp.dispose();
		}

		logger.info((new AppInfo(EX_END_APP)).toString());
		Runtime.getRuntime().exit(ex.getAppCode());
	}

	/**
	 * Devuelve el marco de aplicación
	 * 
	 * @return
	 */
	public GUIDesktop getDesktop() {
		return frmApp;
	}

	/**
	 * Devuleve las propiedades de la aplicación
	 * 
	 * @return Propiedades
	 */
	public Properties getProperties() {
		return configApp;
	}

	/**
	 * Inicialización de las propiedades de la Arquitectura
	 */
	private void initArq() {
		// Inicialización del fichero de propiedades de la Arq
		configArq = new Properties();

		try {
			configArq.load(ControllerDesktop.class.getResourceAsStream(PROP_ARQ));
		} catch (Exception e) {
			exit(new AppException(EX_NOT_CONFIG, e));
		}

		// Inclusión de servidor de etiquetas para multileguaje
		Language.getInstance().addLangResource(configArq.getProperty("languageGUI"));
	}

	/**
	 * Inicializa y muestra la Vista
	 */
	public void initView() {
		// Inicialización del Marco Visual de la aplicación y se
		// establece el controlador del marco
		try {
			frmApp = new GUIDesktop(this);
			frmApp.setTitleApp(strTitleApp);
			status = frmApp.getStatusBar();
		} catch (Exception e) {
			exit(new AppException(e));
		}

		// Establecer el Look and Feel
		frmApp.initLookAndFeel(configApp.getProperty("lookandfeel"));

		try {
			// Se obtiene el fichero de configuración del menu
			// Se incluye el menu de la aplicación
			frmApp.setMenu(new GUIMenuBar(ControllerDesktop.class.getResourceAsStream(configApp.getProperty("menu"))));
		} catch (JDOMException e) {
			exit(new AppException(EX_XML_INVALID, e));
		} catch (IOException e) {
			exit(new AppException(EX_FILE_NOT_FOUND, e));
		}

		// Posicion de la pantalla
		Properties initProp = new Properties();
		try {
			initProp.load(new FileInputStream("init.properties"));
		} catch (Exception e) {
		}

		String xPos = initProp.getProperty("xPos");
		String yPos = initProp.getProperty("yPos");
		xPos = xPos == null ? "0" : xPos;
		yPos = yPos == null ? "0" : yPos;

		frmApp.initPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));

		// Establecer resolución de pantalla
		String resolution = configApp.getProperty("resolution");
		if (resolution != null) {
			frmApp.initResolution(resolution);
		} else {
			String xRes = configApp.getProperty("xResolution");
			String yRes = configApp.getProperty("yResolution");
			frmApp.setSize(Integer.parseInt(xRes), Integer.parseInt(yRes));
		}

		// Refrescar y visualizar la pantalla
		// Ya lo hace el initResolution
		// frmApp.refresh();
	}

	/**
	 * Ejecuta el controlador asignado a la entrada de menu
	 * 
	 * @param param Contendrá los parametros de menu (Controlador a ejecutar)
	 */
	private void openMenu(Container param) {

		// A partir del nombre del controlador pasado como parámetro se ha de
		// instanciar y ejecutar su comando de incio
		String strControlador = (String) param.get(GUIMenuItem.CONTROL);

		try {
			@SuppressWarnings("rawtypes")
			Class classController = Class.forName(strControlador);
			Controller control = (Controller) classController.newInstance();
			// Se inicia el controlador y de le pasa el Controlador del Escritorio
			// para comunicación
			control.init(this);
		} catch (Exception e) {
			AppException ex = null;

			if (e instanceof AppException) {
				ex = (AppException) e;
			} else {
				ex = new AppException(EX_MENU_ERROR, e);
			}

			logger.error(ex.toString());
			showMessage(ex);
		}
	}

	/**
	 * Elimina una vista del escritorio. Este método suele ser llamado desde los controladores hijos
	 * 
	 * @param view Vista creada por un Control Hijo
	 */
	public void removeView() {
		frmApp.removeView();
	}

	/**
	 * Almacena en el fichero de configuración, parametros que hayan podido cambiar durante la ejecución
	 */
	private void saveConfig() {
		// Posicion de la pantalla
		if (frmApp != null) {
			Properties initProp = new Properties();

			// Posición de la pantalla
			initProp.put("xPos", "" + frmApp.getX());
			initProp.put("yPos", "" + frmApp.getY());

			// Guardado de cambios
			try {
				initProp.store(new FileWriter("init.properties"), "App Init");
			} catch (IOException e) {
				logger.error((new AppException(EX_FILE_WRITE_ERROR, e)).toString());
			}
		}
	}

	/**
	 * Inicialización del título de la aplicación
	 * 
	 * @param strTitle
	 */
	public void setTitleApp(String strTitle) {
		this.strTitleApp = strTitle;

		if (frmApp != null) {
			frmApp.setTitleApp(strTitleApp);
		}
	}

	/**
	 * Establece el título de una ventana en el marco
	 * 
	 * @param strTitle
	 */
	public void setTitleView(String strTitle) {
		if (frmApp != null) {
			if (strTitle != null) {
				frmApp.setTitle(strTitleApp + " - " + strTitle);
			} else {
				frmApp.setTitle(strTitleApp);
			}
		}
	}

	/**
	 * Muestra un mensaje en la barra de estado
	 * 
	 * @param ex Excepcion
	 */
	public void showMessage(AppException ex) {
		String strTexto = ex.getMessage();
		status.setMessageError(strTexto);
	}

	/**
	 * Muestra un mensaje en la barra de estado
	 * 
	 * @param ex Excepcion
	 */
	public void showMessage(String strTexto) {
		status.setMessage(strTexto);
	}
}
