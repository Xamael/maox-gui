package org.maox.arq.gui;

import org.maox.arq.gui.events.DataListener;

public interface IDataExchange {

	/**
	 * Los DataExchanger deberán ser capaces de notificar cambios en su información
	 * 
	 * @param listener
	 */
	public void addDataListener(DataListener listener);

	/**
	 * Obtiene la informaicón almacenada en el elemento
	 * 
	 * @return Objeto con la información
	 */
	public Object getData();

	/**
	 * Eliminar un observador de eventos DataListener
	 */
	public void removeDataListener(DataListener listener);

	/**
	 * Establece el valor de la información almacenada por el componente
	 * 
	 * @param oData
	 */
	public void setData(Object oData);

}
