package org.maox.arq.gui.view;

import static org.maox.arq.error.IAppExceptionMessages.EX_LOOK_AND_FEEL_NOT_FOUND;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import org.maox.arq.control.ControllerDesktop;
import org.maox.arq.control.IController;
import org.maox.arq.error.AppInfo;
import org.maox.arq.error.Log;
import org.maox.arq.gui.component.GUIPanel;
import org.maox.arq.gui.component.GUIStatusBar;
import org.maox.arq.gui.menu.GUIMenuBar;
import org.maox.arq.gui.menu.GUIMenuItem;
import org.maox.arq.infra.Params;

/**
 * Marco de ejecución visual de una aplicación
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIDesktop extends JFrame implements ActionListener, WindowListener {

	/* Controlador */
	private IController control = null;
	/* Barra de estado */
	private GUIStatusBar statusBar = null;
	/* Panel contenedor */
	private GUIPanel contentPanel = null;
	/* Resolución de la aplicación */
	private int iWidthDesktop = 0;
	private int iHeigthDesktop = 0;
	/* Cola de vistas activas */
	Deque<GUIView> stackView = null;
	/* Título del escritorio */
	private String strTitleApp = null;

	/**
	 * Constructor: Crea un marco de ejecución y lo hace visible
	 */
	public GUIDesktop(ControllerDesktop control) {
		// Creación del Marco de Ejecucion
		super();
		setName("Desktop");

		// Se asigna su controlador
		this.control = control;

		// No se va a poder redimensionar
		setResizable(false);

		// El cierre de la aplicación lo controlará el Controlador
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		// Se inicializa la barra de estado y panel vacio para las pantallas
		initComponents();

		// Se inicializa el Controlador del Foco
		setFocusTraversalPolicy(new GUIFocusPolicy());

		// Se inicializa la cola de vistas que se visualizan
		stackView = new ArrayDeque<GUIView>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Tratamiento de los eventos de menu
		if (e.getSource() instanceof GUIMenuItem) {
			GUIMenuItem eventM = (GUIMenuItem) (e.getSource());

			Params pMenu = new Params();
			pMenu.putParam(GUIMenuItem.NAME, eventM.getName());
			pMenu.putParam(GUIMenuItem.CODIGO, eventM.getCode());
			pMenu.putParam(GUIMenuItem.CONTROL, eventM.getControllerName());

			control.execute(IController.MENU_ACTION, pMenu);
		} // Menus
	}

	/**
	 * Añade una vista al escritorio, si ya hay una activa, elimiina la activa almacenandola en la cola de vistas
	 * ocultas y muestra la nueva
	 * 
	 * @param view Panel a mostrar
	 */
	public void addView(GUIView view) {
		// Si hay una vista activa ya, se ha de quitar del panel de contenido
		GUIView currentView = stackView.peekFirst();

		if (currentView != null) {
			contentPanel.remove(currentView);
		}

		// Se añade la vista
		stackView.addFirst(view);

		showView(view);
	}

	/**
	 * Devuelve la barra de estado que la ventana
	 * 
	 * @return Barra de estado
	 */
	public GUIStatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * Inicialización de la parte gráfica del escritorio
	 */
	private void initComponents() {
		// Estructura Layout
		getContentPane().setLayout(new BorderLayout());

		// Se añade la barra de estatus en la parte de abajo
		statusBar = new GUIStatusBar();
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		// Se crea el panel donde se añadirán las pantallas
		contentPanel = new GUIPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
	}

	/**
	 * Se establece el Look and Feel del escritorio
	 * 
	 * @param strLookAndFeel Clase de Look and Feel
	 * @throws Excepcion
	 */
	public void initLookAndFeel(String strLookAndFeel) {
		// Se cambian ciertas propiedades gráficas
		// UIManager.put("nimbusBase", Color.RED/* GUISettings.DISABLED_FOREGROUND_COLOR */);

		// Se establece el Look & Feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (strLookAndFeel.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					Log.info(this.getClass(), "Setting Look & Feel: " + strLookAndFeel + ".");
					break;
				}
			}
		} catch (Exception e) {
			Log.warning(this.getClass(), new AppInfo(EX_LOOK_AND_FEEL_NOT_FOUND, e));
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
	}

	/**
	 * Mueve el Marco a una posición determinada
	 * 
	 * @param xPos Posición Horizaontal
	 * @param yPos Posición Vertical
	 */
	public void initPosition(int xPos, int yPos) {
		setBounds(xPos, yPos, getWidth(), getHeight());
	}

	/**
	 * Establece la resolución gráfica de las pantallas
	 * 
	 * @param strResolution
	 */
	public void initResolution(String strResolution) {
		if (strResolution == null) {
			Log.warning(this.getClass(), "Not found a Valid Resolution, XGA will be used.");
			strResolution = "XGA";
		}

		if (strResolution.equals("SVGA")) {
			iWidthDesktop = 800;
			iHeigthDesktop = 600;
		} else if (strResolution.equals("XGA")) {
			iWidthDesktop = 1024;
			iHeigthDesktop = 768;
		} else if (strResolution.equals("SXGA")) {
			iWidthDesktop = 1280;
			iHeigthDesktop = 960;
		} else // Resolución no soportada
		{
			Log.warning(this.getClass(), strResolution + "isn't a Valid Resolution, XGA will be used.");
			iWidthDesktop = 1024;
			iHeigthDesktop = 768;
		}

		Log.info(this.getClass(), "Resolution has been set to " + strResolution + ".");

		super.setSize(iWidthDesktop, iHeigthDesktop);
		refresh();

		// Zona que se puede usar para mostrar pantallas
		iWidthDesktop = contentPanel.getWidth();
		iHeigthDesktop = contentPanel.getHeight();

		Log.info(this.getClass(), "Printed area is " + iWidthDesktop + "x" + iHeigthDesktop + ".");
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

	/*
	 * Eventos de Ventana
	 */

	/**
	 * Eliminar la vista actva del panel contendor y la reemplaza por la anterior en la cola de vistas
	 */
	public void removeView() {
		// Se elimina la vista activa
		contentPanel.remove(stackView.removeFirst());

		// Si hay vistas en cola se muestra la siguiente en profundidad
		GUIView currentView = stackView.peekFirst();
		if (currentView != null) {
			showView(currentView);
		} else {
			setTitle(strTitleApp);
			refresh();
		}
	}

	/**
	 * Asigna un menu al escritorio
	 * 
	 * @param fileMenuXML Fichero XML con las instrucciones para crear un menu
	 */
	public void setMenu(GUIMenuBar menu) {
		// Se han de recorrer todas las entradas de menu y asignarles como controlador
		// de enventos el escritorio para que se procesen desde aquí las opciones
		// de menu
		Enumeration<GUIMenuItem> eMenuItems = menu.getMenuItems();

		while (eMenuItems.hasMoreElements()) {
			GUIMenuItem eMenu = eMenuItems.nextElement();
			eMenu.addActionListener(this);
		}

		// Se añade el menu
		setJMenuBar(menu);
	}

	@Override
	public void setSize(int xResolution, int yResolution) {
		Log.info(this.getClass(), "Resolution has been set to " + xResolution + " x " + yResolution + ".");
		super.setSize(xResolution, yResolution);

		refresh();

		// Zona que se puede usar para mostrar pantallas
		iWidthDesktop = contentPanel.getWidth();
		iHeigthDesktop = contentPanel.getHeight();

		Log.info(this.getClass(), "Printed area is " + iWidthDesktop + "x" + iHeigthDesktop + ".");
	}

	/**
	 * Establece el título de la aplicación (Escritorio)
	 * 
	 * @param strTitle
	 */
	public void setTitleApp(String strTitle) {
		super.setTitle(strTitle);
		strTitleApp = strTitle;
	}

	/**
	 * Muestra la vista en el panel de contenido
	 * 
	 * @param view Panel a mostrar
	 */
	private void showView(GUIView view) {
		// A partir de la resolución se determina el tamaño y posición del panel
		int ancho = view.getWidth();
		int alto = view.getHeight();

		// Control si la vista es más grande que el escritorio
		if (ancho > iWidthDesktop) {
			ancho = iWidthDesktop;
		}
		if (alto > iHeigthDesktop) {
			alto = iHeigthDesktop;
		}

		view.setBounds(iWidthDesktop / 2 - ancho / 2, iHeigthDesktop / 2 - alto / 2, ancho, alto);

		contentPanel.add(view);
		setTitle(strTitleApp + " - " + view.getTitle());

		refresh();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		control.execute(IController.EXIT, null);
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
