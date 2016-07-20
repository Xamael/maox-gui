package org.maox.arq.gui.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.maox.arq.gui.component.GUIPanel;
import org.maox.arq.gui.menu.GUIMenuBar;
import org.maox.arq.gui.menu.GUIMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Marco de ejecución visual simple de aplicación
 *
 * @author Alex Orgaz
 */
@SuppressWarnings("serial")
public abstract class GUIFrame extends JFrame implements ActionListener, WindowListener {

	/* Log */
	final private static Logger logger = LoggerFactory.getLogger(GUIFrame.class);

	/* Menu de opciones */
	protected GUIMenuBar menu = null;

	/* Panel contenedor */
	protected GUIPanel contentPanel = null;

	/**
	 * Constructor: Crea un marco de ejecución y lo hace visible
	 */
	public GUIFrame() {
		// Creación del Marco de Ejecucion
		super();
		setName("Desktop");

		// El cierre de la aplicación lo controlará el Controlador
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		// Se inicializa la barra de estado y panel vacio para las pantallas
		initMenu();
		initComponents();

		// Captura de los eventos de menu
		// Para evitar problemas con el Windows Builder de Eclipse sólo lo se
		// inicializará si es distinto a null
		if (menu != null) {
			List<GUIMenuItem> list = menu.getMenuItems();
			if (list != null) {
				for (GUIMenuItem eMenu : list) {
					eMenu.addActionListener(this);
				}
			}
		}

		// Se añade el menu
		setJMenuBar(menu);

		// setVisible(true);
	}

	/**
	 * Cierra la aplicación con error de Excepcion
	 */
	abstract protected void exit();

	/**
	 * Inicialización de la parte gráfica del escritorio
	 */
	abstract protected void initComponents();

	/**
	 * Se establece el Look and Feel del escritorio
	 *
	 * @param strLookAndFeel
	 *            Clase de Look and Feel
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws Excepcion
	 */
	public void initLookAndFeel(String strLookAndFeel) {

		// Se establece el Look & Feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (strLookAndFeel.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					logger.info("Setting Look & Feel: {}", strLookAndFeel);
					break;
				}
			}
		} catch (Exception e) {
			logger.warn(e.toString());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}

		// Se actualiza el look and feel con la opción selecionada
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * Inicialización del menu
	 */
	abstract protected void initMenu();

	/**
	 * Mueve el Marco a una posición determinada
	 *
	 * @param xPos
	 *            Posición Horizaontal
	 * @param yPos
	 *            Posición Vertical
	 */
	public void initPosition(int xPos, int yPos) {
		setBounds(xPos, yPos, getWidth(), getHeight());
	}

	/**
	 * Refresacar el contenido de la pantalla
	 */
	public void refresh() {
		contentPanel.validate();
		contentPanel.repaint();
		contentPanel.setVisible(true);
		setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		exit();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
