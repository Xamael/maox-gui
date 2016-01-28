package org.maox.arq.language;

import static org.maox.arq.error.IAppExceptionMessages.EX_LABEL_NO_TRANSLATE;

import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.maox.arq.error.AppException;
import org.slf4j.LoggerFactory;

/**
 * Obtención de textos según la configuración local de idioma
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class Language {

	/* Patrón singletón */
	private static Language _language = null;
	/* Lista de posibles recursos de etiquetas */
	private Vector<String> vLabels = null;

	/**
	 * Instanciador (Patrón singleton)
	 * 
	 * @return Instancia del servidor de Lenguaje
	 */
	public static Language getInstance() {
		if (_language == null) {
			_language = new Language();
		}

		return _language;
	}

	/**
	 * Devuelve el texto de una codificación en un idioma determinado por la Localización
	 * 
	 * @param strText Clave
	 * @return Texto Traducido
	 */
	public static String getString(String strText) {

		Iterator<String> iResLabels = Language.getInstance().getResourceLabels();
		String strLang = null;

		while (iResLabels.hasNext() && strLang == null) {
			try {
				strLang = ResourceBundle.getBundle(iResLabels.next()).getString(strText);
			} catch (MissingResourceException eMiss) {
			}
		}

		if (strLang == null) {
			LoggerFactory.getLogger(Language.class)
					.error((new AppException(EX_LABEL_NO_TRANSLATE, strText)).toString());
			strLang = strText;
		}

		return strLang;
	}

	/**
	 * Constructor Patrón singletón
	 */
	private Language() {
		vLabels = new Vector<String>();
		// "org.maox.arq.language.GUI"
		// "org.maox.crumena.language.AppLang"
	}

	/**
	 * Incluye un recurso de etiquetas
	 * 
	 * @param strLang
	 */
	public void addLangResource(String strLang) {
		vLabels.add(strLang);
	}

	/**
	 * Devuelve la lista de recursos de etiquetas disponibles
	 * 
	 * @return
	 */
	public Iterator<String> getResourceLabels() {
		return vLabels.iterator();
	}
}
