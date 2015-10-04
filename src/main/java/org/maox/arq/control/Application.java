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
import org.maox.arq.error.Log;

/**
 * Clase abstracta de apliación que implementa lo necesario para arrancar la App
 * 
 * @author Alex Orgaz
 * @version 01.00
 * @since 25/02/2014
 */
public abstract class Application {
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
	 * @param e Posible Excepción que ocasiono la finalización
	 */
	protected void exit(Exception e) {
		int iCodigo = 0;

		if (e != null) {
			iCodigo = -1;
			Log.error(this.getClass(), new AppException(EX_UNKNOW, e));
		}

		Log.info(this.getClass(), new AppInfo(EX_END_APP));
		Runtime.getRuntime().exit(iCodigo);
	}

	/**
	 * Devuelve del nombre del fichero de configuración
	 * 
	 * @returnn Nmbre del fichero de configuración
	 */
	public String getConfigFileName() {
		return configName;
	}

	/**
	 * Lectura del fichero de configuración, se utilizará el fichero application.properties por defecto
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
			Log.error(this.getClass(), new AppException(SYS_ERROR, EX_NOT_CONFIG));
		}

		// Se almacena el propio nombre del fichero de configuración por si el
		// controlador necesaita guardar algún cambio
		config.put("config", getConfigFileName());
	}

	/**
	 * Arranca una aplicación Visual
	 */
	protected void initController() {
		// Se arranca el Controlador Principal de la aplicación
		control = new ControllerDesktop(config);
		control.setTitleApp(strTitle);
	}

	/**
	 * Inicialización del donde se dejarán los LOG
	 */
	protected void initLog() {
		// Fichero de Log
		String logName = config.getProperty("log");
		Log.getInstance().setFileLog(logName);

		// Nivel de Traza
		try {
			int iLvlTraze = Integer.parseInt(config.getProperty("trazeLevel"));
			Log.getInstance().setTrazeLevel(iLvlTraze);
		} catch (NumberFormatException eNum) {
		}
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
			initLog();
			initController();
			initView();
		} catch (Exception e) {
			exit(e);
		}
	}
}
