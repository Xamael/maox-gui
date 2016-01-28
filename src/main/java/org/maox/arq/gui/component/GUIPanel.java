package org.maox.arq.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.maox.arq.gui.IDataExchange;
import org.maox.arq.gui.IStateExchange;
import org.maox.arq.gui.events.DataEvent;
import org.maox.arq.gui.events.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class GUIPanel extends JPanel implements IStateExchange, DataListener {

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(GUIPanel.class);
	/* Posibles resoluciones */
	protected static final int SVGA = 1;
	protected static final int XGA = 2;
	protected static final int SXGA = 3;
	/* Constantes de tamaño, dependen de la resolución escogida */
	protected static final int SVGA_HEIGTH = 523;
	protected static final int SVGA_WIDTH = 794;
	protected static final int XGA_HEIGTH = 691;
	protected static final int XGA_WIDTH = 1018;
	protected static final int SXGA_HEIGTH = 883;
	protected static final int SXGA_WIDTH = 1274;
	/* Listas de componentes resgistrados */
	protected Vector<Component> vAllComponents = new Vector<Component>();
	protected Vector<IStateExchange> vStateComp = new Vector<IStateExchange>();
	protected Vector<IDataExchange> vDataComp = new Vector<IDataExchange>();
	protected Vector<GUIButton> vButtons = new Vector<GUIButton>();
	/* Estados del panel */
	private boolean inError = false;

	/**
	 * Constructor del panel
	 */
	public GUIPanel() {
		super();
		initialize();
	}

	@Override
	public Component add(Component comp) {
		super.add(comp);
		register(comp);

		return comp;
	}

	@Override
	public Component add(Component comp, int index) {
		super.add(comp, index);
		register(comp);

		return comp;
	}

	@Override
	public void add(Component comp, Object obj) {
		super.add(comp, obj);
		register(comp);
	}

	@Override
	public void add(Component comp, Object obj, int index) {
		super.add(comp, obj, index);
		register(comp);
	}

	@Override
	public Component add(String name, Component comp) {
		super.add(name, comp);
		register(comp);

		return comp;
	}

	/**
	 * Valida todos los componentes si son obligatorios o/y estan en error y se activa los botones OK en caso necesario
	 */
	private void checkStateComponents() {
		Iterator<IStateExchange> iComp = vStateComp.iterator();

		inError = false;

		while (iComp.hasNext() && !inError) {
			IStateExchange comp = iComp.next();

			if (comp.isInError() || (comp.isRequired() && !comp.hasValue())) {
				inError = true;
			}
		}

		// Activar los botones OK
		Iterator<GUIButton> iButton = vButtons.iterator();

		while (iButton.hasNext()) {
			GUIButton button = iButton.next();
			if (button.getType() == GUIButton.ACCEPT_BUTTON) {
				button.setEnabled(!isInError() && isEnabled());
			}
		}
	}

	/**
	 * Limpia los compontes del panel
	 */
	public void clearComponents() {
		// Se recorren los compoentes del panel
		Iterator<IDataExchange> iComp = vDataComp.iterator();

		while (iComp.hasNext()) {
			IDataExchange comp = iComp.next();
			comp.setData(null);
		}

	}

	@Override
	public void dataChanged(DataEvent event) {
		// Si un componente ha cambiado si información interna se ha de comprobar
		// si los componentes están en estado valido y si es así habilitar los
		// posibles botones de tipo OK que haya.
		checkStateComponents();
		logger.debug("checking status ... {}", !isInError());
	}

	@Override
	public boolean hasValue() {
		// Se comprobará si todos los campos obligatorios tienen valor.
		Iterator<IStateExchange> iComp = vStateComp.iterator();

		boolean bRes = false;

		while (iComp.hasNext()) {
			IStateExchange comp = iComp.next();

			// Componente optativo (con valor o sin el)
			if (!comp.isRequired()) {
				bRes = (bRes || comp.hasValue());
			} else if (comp.isRequired()) {
				bRes = comp.hasValue();
				if (!bRes) {
					break;
				}
			}
		}

		return bRes;
	}

	/**
	 * Inicialización del Componente
	 */
	private void initialize() {
		// Sin Layout ni título
		setLayout(null);
		// Color de fondo por defecto
		setBackground(new Color(240, 240, 240));
		// Borde por defecto
		setBorder(new LineBorder(Color.LIGHT_GRAY));
	}

	@Override
	public boolean isInError() {
		return inError;
	}

	@Override
	public boolean isInValidState() {
		// Se comprobará si todos los campos están en estado valido
		Iterator<IStateExchange> iComp = vStateComp.iterator();

		boolean bRes = true;

		while (iComp.hasNext() && bRes) {
			IStateExchange comp = iComp.next();

			if (!comp.isInValidState()) {
				bRes = false;
			}
		}

		return bRes;
	}

	@Override
	public boolean isRequired() {
		// Se comprobará si existe algún campo obligatorio
		Iterator<IStateExchange> iComp = vStateComp.iterator();

		boolean bRes = false;

		while (iComp.hasNext() && !bRes) {
			IStateExchange comp = iComp.next();

			if (comp.isRequired()) {
				bRes = true;
			}
		}

		return bRes;
	}

	/**
	 * Registra un componente como parte de este panel
	 * 
	 * @param comp Componente a registrar
	 */
	private void register(Component comp) {

		// Se añade en la lista de componentes global
		vAllComponents.add(comp);

		// Dependiendo del tipo de componente se almacenará en una lista

		// Componenetes que permiten saber si están en estado valido o no (obligatorios, dato erroneo)
		if (comp instanceof IStateExchange) {
			vStateComp.add((IStateExchange) comp);
		}

		// Componenetes que almacenan información
		if (comp instanceof IDataExchange) {
			vDataComp.add((IDataExchange) comp);

			// Se registra el componente por si modifica su información que lo sepa el panel
			((IDataExchange) comp).addDataListener(this);
		}

		// Botones
		if (comp instanceof GUIButton) {
			vButtons.add((GUIButton) comp);
		}

		// Por ultimo se pasa un chequeo para activar los botones si es necesario
		checkStateComponents();
	}

	@Override
	public void requestFocus() {
		// El foco del panel se situará en su primer componente posible
		// Se activa/desactiva cada uno de los componentes agregados al panel
		Iterator<Component> iComp = vAllComponents.iterator();

		while (iComp.hasNext()) {
			Component comp = iComp.next();
			if (comp.isFocusable() && comp.isEnabled()) {
				comp.requestFocusInWindow();
				break;
			}
		}
	}

	/**
	 * Activará o desactivará el panel y todos los elementos que contenga
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		// Se activa/desactiva cada uno de los componentes agregados al panel
		Iterator<Component> iComp = vAllComponents.iterator();

		while (iComp.hasNext()) {
			Component comp = iComp.next();
			comp.setEnabled(enabled);
		}

		// En caso de activación se realiza un chequeo para validar los campos
		checkStateComponents();
	}

}
