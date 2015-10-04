package org.maox.arq.language;

import java.util.ListResourceBundle;

/**
 * Definición de etiquetas en idioma español para los interfacez visuales
 * Idioma: Español
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class GUI_es extends ListResourceBundle {

	/* Tabla con los textos báscicos del GUI */
	private Object[][]	tblLabel	= { { "BTN_CLOSE", "Cerrar" }, { "BTN_ACCEPT", "Aceptar" }, { "BTN_SEARCH", "Buscar" }, { "BTN_CANCEL", "Cancelar" },
			{ "BTN_ADD", "Añadir" }, { "BTN_DEL", "Eliminar" }, { "BTN_EDIT", "Editar" }, { "PNL_SEARCH", "Busqueda" }, { "PNL_DETAIL", "Detalle" },
			{ "PNL_RESULT", "Resultados" }, { "DB_TRANSAC", "Existen transacciones pendientes. ¿Desea salir?" }, { "ARQ_YES", "Si" }, { "ARQ_NOT", "No" },
			{ "ARQ_WARNING", "Advertencia" } };

	@Override
	protected Object[][] getContents() {
		return tblLabel;
	}

}
