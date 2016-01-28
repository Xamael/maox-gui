package org.maox.arq.test.language;

import java.util.ListResourceBundle;

/**
 * Definición de etiquetas en idioma español para la aplicación Idioma: Español
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class AppLang_es extends ListResourceBundle {

	/* Tabla con los textos báscicos del GUI */
	private final Object[][] tblLabel = { { "LBL_EMISION", "Emisión" }, { "LBL_COUNTRY", "País" },
			{ "LBL_DESCRIP", "Descripción" }, { "LBL_TICKET", "Ticket" }, { "VIEW_ENTITIES", "Entidades" },
			{ "VIEW_COMPONENTS", "Componentes" }, { "PNL", "Panel" } };

	@Override
	protected Object[][] getContents() {
		return tblLabel;
	}

}
