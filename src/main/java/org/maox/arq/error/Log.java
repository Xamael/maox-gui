package org.maox.arq.error;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Clase para almacenar los mensajes de error y demás trazas en fichero
 * 
 * @author Alex Orgaz
 * @version 01.00
 * @since 25/02/2014
 */
public class Log {

	/* Niveles de Traza Posibles */
	public final static int	TRAZA_ERROR			= 1;
	public final static int	TRAZA_WARNING		= 2;
	public final static int	TRAZA_INFO			= 3;
	public final static int	TRAZA_DEBUG			= 4;
	public final static int	TRAZA_DEBUG_EVENT	= 5;

	/**
	 * Escribe un mensaje de debug
	 * 
	 * @param c Clase que produjo la información de debug
	 * @param warning Información de debug
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void debug(Class cl, AppException debug) {
		Log.debug(cl, debug.toString());
	}

	/**
	 * Escribe un mensaje de debug
	 * 
	 * @param c Clase que produjo la información de debug
	 * @param warning Mensaje de información de debug
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void debug(Class cl, String debug) {
		Log log = getInstance();

		if (getInstance().iTrazaLevel >= TRAZA_DEBUG)
			try {
				log.errStream.write(cl.getSimpleName() + " (dbg): " + debug + "\n");
				log.errStream.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Escribe un mensaje de debug (eventos)
	 * 
	 * @param c Clase que produjo la información de debug
	 * @param warning Mensaje de información de debug
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void debugEvent(Class cl, String debug) {
		Log log = getInstance();

		if (getInstance().iTrazaLevel >= TRAZA_DEBUG_EVENT)
			try {
				log.errStream.write(cl.getSimpleName() + " (event): " + debug + "\n");
				log.errStream.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Escribe un mensaje de error junto con información de la Excepcion
	 * 
	 * @param error Mensaje de error
	 * @param ex Exception que provoco el mensaje
	 */
	static public synchronized void error(AppException error) {
		Log.error(null, error.toString());
	}

	/**
	 * Escribe un mensaje de error
	 * 
	 * @param c Clase que produjo el error
	 * @param error Error
	 * @param ex Exception que provoco el mensaje
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void error(Class cl, AppException error) {
		Log.error(cl, error != null ? error.toString() : null);
	}

	/**
	 * Escribe un mensaje de error
	 * 
	 * @param c Clase que produjo el error
	 * @param error Mensaje de error
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void error(Class cl, String error) {
		Log log = getInstance();

		if (getInstance().iTrazaLevel >= TRAZA_ERROR)
			try {
				if (error != null)
					log.errStream.write((cl != null ? cl.getSimpleName() : "static") + " (err): " + error + "\n");

				log.errStream.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Escribe un mensaje de error junto con información de la Excepcion
	 * 
	 * @param error Mensaje de error
	 * @param ex Exception que provoco el mensaje
	 */
	static public synchronized void error(String error) {
		Log.error(null, error);
	}

	/**
	 * Genera una instacia de registro de Log
	 * 
	 * @return instacia del Log
	 */
	static public synchronized Log getInstance() {
		if (_instancia == null) {
			_instancia = new Log();
		}

		return _instancia;
	}

	/**
	 * Escribe un mensaje de información
	 * 
	 * @param c Clase que produjo la información
	 * @param warning Información
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void info(Class cl, AppException info) {
		Log.info(cl, info.toString());
	}

	/**
	 * Escribe un mensaje de información
	 * 
	 * @param c Clase que produjo la información
	 * @param warning Mensaje de información
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void info(Class cl, String info) {
		Log log = getInstance();

		if (getInstance().iTrazaLevel >= TRAZA_INFO)
			try {
				log.errStream.write(cl.getSimpleName() + " (inf): " + info + "\n");
				log.errStream.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Escribe una traza simple
	 * 
	 * @param strTrace
	 */
	public static void trace(String strTrace) {
		Log log = getInstance();
		try {
			log.errStream.write("trace: " + strTrace + "\n");
			log.errStream.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Escribe un mensaje de advertencia
	 * 
	 * @param c Clase que produjo la advertencia
	 * @param warning Advertencia
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void warning(Class cl, AppException warning) {
		Log.warning(cl, warning.toString());
	}

	/**
	 * Escribe un mensaje de advertencia
	 * 
	 * @param c Clase que produjo la advertencia
	 * @param warning Mensaje de advertencia
	 */
	@SuppressWarnings("rawtypes")
	static public synchronized void warning(Class cl, String warning) {
		Log log = getInstance();

		if (getInstance().iTrazaLevel >= TRAZA_WARNING)
			try {
				log.errStream.write(cl.getSimpleName() + " (war): " + warning + "\n");
				log.errStream.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Nivel de traza actual */
	private int			iTrazaLevel;

	/* Stream donde se dejará el error */
	private Writer		errStream	= null;

	/* Instancia de traza */
	private static Log	_instancia	= null;

	/**
	 * Constructor, se utilizará el patrón singleton para que esten unificadas
	 * todas las trazas de la aplicación
	 */
	private Log() {
		super();

		// Se inicializa el stream donde se escribirá la infomación
		errStream = new OutputStreamWriter(System.err);

		// Nivel de traza por defecto
		setTrazeLevel(2);
	}

	/**
	 * Devuelve el nivel de traza actual
	 * 
	 * @return Nivel de Traza
	 */
	public int getTrazeLevel() {
		return iTrazaLevel;
	}

	/**
	 * Establece el fichero donde se guardarán las trazas
	 * 
	 * @param fileLog Fichero de traza
	 */
	public void setFileLog(File fileLog) {
		try {
			errStream = new OutputStreamWriter(new FileOutputStream(fileLog));
		}
		catch (FileNotFoundException e) {
			errStream = new OutputStreamWriter(System.err);
			e.printStackTrace();
		}
	}

	/**
	 * Establece el fichero donde se guardarán las trazas
	 * 
	 * @param fileLog Nombre del Fichero de Trazas
	 */
	public void setFileLog(String fileLog) {
		if (fileLog == null)
			errStream = new OutputStreamWriter(System.err);
		else
			setFileLog(new File(fileLog));
	}

	/**
	 * Establece el nivel de traza
	 * 
	 * @param iTrazaLevel Nivel de Traza
	 */
	public void setTrazeLevel(int iTrazaLevel) {
		this.iTrazaLevel = iTrazaLevel;
	}
}
