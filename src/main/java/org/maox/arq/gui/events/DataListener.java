package org.maox.arq.gui.events;

import java.util.EventListener;

/**
 * Observador de eventos DataEvent que son producidos al cambiar la información
 * de un Compoenente que implemente IDataExchange
 * 
 * @author Alex Orgaz
 * @version 01.00
 */
public interface DataListener extends EventListener {

	/**
	 * Invocado cuando un Dato cambia
	 * 
	 * @param e
	 */
	public void dataChanged(DataEvent e);
}
