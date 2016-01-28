package org.maox.arq.control;

import static org.maox.arq.error.IAppExceptionMessages.EX_END_APP;
import static org.maox.arq.error.IAppExceptionMessages.EX_NOT_CONFIG;
import static org.maox.arq.error.IAppExceptionMessages.EX_UNKNOW;
import static org.maox.arq.error.IAppExceptionMessages.SYS_ERROR;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.maox.arq.error.AppException;
import org.maox.arq.error.AppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase abstracta de aplicación que implementa lo necesario para arrancar la
 * App
 *
 * @author Alex Orgaz
 * @version 01.00
 * @since 25/02/2014
 */
public abstract class Application {

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(Application.class);
	/* Ruta del fichero de configuración */
	private String configName = null;
	/* Fichero de Propiedades de Configuración de la Aplicación */
	private Properties config = null;
	/* Controlador de la aplicación */
	private ControllerDesktop control = null;
	/* Titulo de la aplicación */
	private String strTitle = "Application";

	/**
	 * Fin de aplicación
	 *
	 * @param e
	 *            Posible Excepción que ocasiono la finalización
	 */
	protected void exit(Exception e) {
		int iCodigo = 0;

		if (e != null) {
			iCodigo = -1;
			logger.error((new AppException(EX_UNKNOW, e)).toString());
		}

		logger.info((new AppInfo(EX_END_APP)).toString());
		Runtime.getRuntime().exit(iCodigo);
	}

	/**
	 * Devuelve el fichero de configuración
	 *
	 * @return Propiedades de configuración
	 */
	public Properties getConfig() {
		return config;
	}

	/**
	 * Devuelve el nombre del fichero de configuración
	 *
	 * @returnn Nmbre del fichero de configuración
	 */
	public String getConfigFileName() {
		return configName;
	}

	/**
	 * Lectura del fichero de configuración, se utilizará el fichero
	 * application.properties por defecto
	 */
	protected void initConfig() throws IOException {
		initConfig(null);
	}

	/**
	 * Lectura del fichero de configuración
	 */
	protected void initConfig(String configName) throws IOException {
		// Se establece el nombre del fichero de configuración
		setConfigFile(configName);
		// Se lee el fichero de propiedades de la aplicación
		config = new Properties();

		try {
			config.load(Application.class.getResourceAsStream(getConfigFileName()));
		} catch (FileNotFoundException e) {
			// No se ha encontrado el fichero
			logger.error((new AppException(SYS_ERROR, EX_NOT_CONFIG)).toString());
		}

		// Se almacena el propio nombre del fichero de configuración por si el
		// controlador necesita guardar algún cambio
		config.put("config", getConfigFileName());
	}

	/**
	 * Inicializa el controlador de Escritorio
	 */
	protected void initController() {
		// Se arranca el Controlador Principal de la aplicación
		control = new ControllerDesktop(config);
		control.setTitleApp(strTitle);
	}

	/**
	 * Inicializa el controlador de Escritorio
	 */
	protected void initController(ControllerDesktop control) {
		// Se arranca el Controlador Principal de la aplicación
		this.control = control;
		control.setTitleApp(strTitle);
	}

	/**
	 * Inicializa la Vista
	 */
	protected void initView() {
		control.initView();
	}

	/**
	 * Establece el nombre del fichero de configuración
	 *
	 * @param configName
	 */
	protected void setConfigFile(String configName) {
		if (configName != null) {
			this.configName = configName;
		}
	}

	/**
	 * Establece el título de la aplicación
	 *
	 * @param strTitle
	 */
	public void setTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	/**
	 * Arranque de la aplicación
	 */
	protected void start() {
		try {
			initConfig();
			initController();
			initView();
		} catch (Exception e) {
			exit(e);
		}
	}
}
