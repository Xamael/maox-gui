package org.maox.arq.test;

import org.maox.arq.control.Application;

/**
 * Aplicación de test para componentes gráficos
 * 
 * @author Alex Orgaz
 * @version 01.00
 * @since 25/02/2014
 */
public class AppGUI extends Application {

	/**
	 * Función de lanzamiento principal. Se seguirá el modelo MVC para el desarrollo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new AppGUI();
	}

	/**
	 * Constructor de la Aplicación de Test
	 */
	public AppGUI() {
		super();
		setTitle("Test GUI");
		// Se carga el fichero de configuración de la aplicación
		setConfigFile("/config/test/application.properties");
		start();
	}
}
