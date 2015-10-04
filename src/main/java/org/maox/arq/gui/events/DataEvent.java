package org.maox.arq.gui.events;

import java.awt.AWTEvent;

/**
 * Un evento que indica que la información interna de un componenente ha cambiado
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class DataEvent extends AWTEvent {

	/**
	 * The first number in the range of ids used for action events.
	 */
	public static final int	ACTION_FIRST	= 1001;

	/**
	 * The last number in the range of ids used for action events.
	 */
	public static final int	ACTION_LAST		= 1001;

	/**
	 * This event id indicates that a meaningful action occured.
	 */
	public static final int	DATA_CHANGED	= ACTION_FIRST; //Event.ACTION_EVENT

	public DataEvent(Object source, int id) {
		super(source, id);
	}

}
