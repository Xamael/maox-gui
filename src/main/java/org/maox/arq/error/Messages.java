package org.maox.arq.error;

public class Messages {

	public static String getMessage(int iCodeEx) {
		String strMensaje = "";
		switch (iCodeEx) {
		case 0:
			strMensaje = "Fin de Aplicación";
			break;
		case 1:
			strMensaje = "No existe fichero de configuración";
			break;
		case 2:
			strMensaje = "Error en la inicialización de la DB";
			break;
		case 3:
			strMensaje = "Error de conexión a la DB";
			break;
		case 4:
			strMensaje = "No se ha podido inicializar el esquema de DB";
			break;
		case 5:
			strMensaje = "Error iniciando proceso de menú";
			break;
		case 6:
			strMensaje = "Error iniciando vista";
			break;
		case 7:
			strMensaje = "Look & Feel no disponible";
			break;
		case 8:
			strMensaje = "Fichero no encontrado";
			break;
		case 9:
			strMensaje = "XML Invalido";
			break;
		case 10:
			strMensaje = "Error de escritura en fichero";
			break;
		case 11:
			strMensaje = "Etiqueta sin traducción";
			break;
		case 12:
			strMensaje = "Error SQL";
			break;
		case 13:
			strMensaje = "Error de E/S";
			break;
		case 14:
			strMensaje = "Error de parser";
			break;

		default:
			strMensaje = "Error no codificado";
			break;
		}

		return strMensaje;
	}

}
