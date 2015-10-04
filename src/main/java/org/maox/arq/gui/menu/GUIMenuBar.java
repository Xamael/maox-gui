package org.maox.arq.gui.menu;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JMenuBar;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

/**
 * Menu de aplicación, cada entrada de menu ha de almacenar el controlador que se activara al pinchar sobre el
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIMenuBar extends JMenuBar {

	/* Elementos de menu contenidos en la barra de menu */
	private Vector<GUIMenuItem> vMenuItems = null;

	/**
	 * Construye un menu a partir de un fichero XML
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 */
	public GUIMenuBar(InputStream is) throws JDOMException, IOException {
		super();

		vMenuItems = new Vector<GUIMenuItem>();

		// Inicializar Parser y que no valide
		SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);

		// Abrir el fichero
		Document doc = builder.build(is);

		// Se construye el arbol en memoria
		Element eRaiz = doc.getRootElement();
		Element eMenuBar = eRaiz.getChild("MenuBar");

		// Se recorren las posibles entradas de Menu
		List<Element> lMenuOption = eMenuBar.getChildren("MenuOption");
		Iterator<Element> iMenuOption = lMenuOption.iterator();

		// Se agregan a la barra de menu
		while (iMenuOption.hasNext()) {
			Element eMenuOption = iMenuOption.next();
			GUIMenuOption menuOption = new GUIMenuOption(eMenuOption.getAttributeValue("name"));
			// menuC.setMnemonic(KeyEvent.VK_C);
			add(menuOption);

			// Se optienen los posibles items de la barra de menu
			List<Element> lMenuItem = eMenuOption.getChildren("MenuItem");
			Iterator<Element> iMenuItem = lMenuItem.iterator();

			// Se agregan los items de menu
			while (iMenuItem.hasNext()) {
				Element eMenuItem = iMenuItem.next();
				GUIMenuItem menuItem = new GUIMenuItem(eMenuItem.getAttributeValue("name"), KeyEvent.VK_P);
				menuItem.setCode(eMenuItem.getAttributeValue("code"));
				menuItem.setControllerName(eMenuItem.getAttributeValue("controller"));
				// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
				menuOption.add(menuItem);
				vMenuItems.add(menuItem);
			}
		}
	}

	/**
	 * Devuelve todas las opciones (finales) de menu contenidos en este menu
	 * 
	 * @return
	 */
	public Enumeration<GUIMenuItem> getMenuItems() {
		return vMenuItems.elements();
	}

	@Override
	public boolean isFocusable() {
		return false;
	}
}
