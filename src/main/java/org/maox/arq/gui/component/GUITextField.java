package org.maox.arq.gui.component;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.event.EventListenerList;
import javax.swing.text.Document;

import org.maox.arq.gui.GUISettings;
import org.maox.arq.gui.IDataExchange;
import org.maox.arq.gui.IStateExchange;
import org.maox.arq.gui.events.DataEvent;
import org.maox.arq.gui.events.DataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Componente visual de un campo de texto
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUITextField extends JTextField implements IStateExchange, IDataExchange {

	/**
	 * Manejador de evento de la modificación de la información en el componente
	 */
	private class ChangeDocListener implements KeyListener, FocusListener, KeyEventDispatcher {

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB) {
				transferData();
			}

			// If an implementation of this method returns false, then the KeyEvent is passed to the next
			// KeyEventDispatcher in the chain, ending with the current KeyboardFocusManager. If an implementation
			// returns true, the KeyEvent is assumed to have been dispatched (although this need not be the case), and
			// the current KeyboardFocusManager will take no further action with regard to the KeyEvent.
			return false;
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			transferData();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// Cuando se pulse ENTER, será como pulsar TAB
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				transferData();
				transferFocus();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(GUITextField.class);

	/* Indicador si el campo es obligatorio o no */
	private boolean bRequired = false;

	/* Información actual del contenedor */
	private Object data = null;

	/**
	 * Constructor básico
	 */
	public GUITextField() {
		super();
		init(null);
	}

	/**
	 * Constructor
	 * 
	 * @param doc
	 * @param text
	 * @param columns
	 */
	public GUITextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		init(text);
	}

	/**
	 * Constructor
	 * 
	 * @param columns
	 */
	public GUITextField(int columns) {
		super(columns);
		init(null);
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 */
	public GUITextField(String text) {
		super(text);
		init(text);
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 * @param columns
	 */
	public GUITextField(String text, int columns) {
		super(text, columns);
		init(text);
	}

	@Override
	public synchronized void addDataListener(DataListener listener) {
		listenerList.add(DataListener.class, listener);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is
	 * lazily created using the parameters passed into the fire method. The listener list is processed in last to first
	 * order.
	 * 
	 * @see EventListenerList
	 */
	protected void fireDataChanged() {
		logger.debug("firing dataChanged: {}", data);

		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();

		DataEvent e = new DataEvent(this, DataEvent.DATA_CHANGED);
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DataListener.class) {
				((DataListener) listeners[i + 1]).dataChanged(e);
			}
		}
	}

	@Override
	public Object getData() {
		// TODO Dependiendo del formateador asociado se podría obtener el tipo devuelto
		return data;
	}

	@Override
	public boolean hasValue() {
		return (data != null);
	}

	/**
	 * Inicialización básica del componente
	 * 
	 * @param data
	 */
	private void init(String data) {
		// Tamaño por defecto
		setSize(100, 22);
		setColumns(10);

		// Inicialización de la información por defecto
		this.data = data;

		// Se registra el documento en un el capturador de eventos
		ChangeDocListener listener = new ChangeDocListener();
		addKeyListener(listener);
		addFocusListener(listener);

		// Se determina el color del componente
		setBackground(GUISettings.BACKGROUND_COLOR);
		setForeground(GUISettings.FOREGROUND_COLOR);

		// Se capturaran las pulsaciones de eventos antes de transferir el foco
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(listener);
	}

	@Override
	public boolean isInError() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInValidState() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRequired() {
		return bRequired;
	}

	@Override
	public synchronized void removeDataListener(DataListener listener) {
		listenerList.remove(DataListener.class, listener);
	}

	@Override
	public void setData(Object oData) {
		if (oData != null && oData.toString().equals("")) {
			data = null;
		} else {
			data = oData;
		}

		// TODO Dependiendo del formateador asociado se podría hacer el toString
		setText(data == null ? "" : data.toString());

		// Se disparará el evento de dato cambiado
		fireDataChanged();
	}

	/**
	 * Establece si el campo es obligatorio o no
	 * 
	 * @param bReq
	 */
	public void setRequired(boolean bReq) {
		bRequired = bReq;

		if (bRequired) {
			setBackground(GUISettings.REQUIRED_BACKGROUND_COLOR);
		} else {
			setBackground(GUISettings.BACKGROUND_COLOR);
		}
	}

	/**
	 * Transfiere el contenido del campo al contendor interno del dato
	 */
	private void transferData() {
		if (!getText().equals(data == null ? "" : data)) {
			setData(getText());
		}
	}
}
