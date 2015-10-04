package org.maox.arq.gui;

/**
 * Interfaz que determina si un elemento visual esta capacitado para compartir su información
 * de estado con los demás componentes. Permitiendo por ejemplo validaciones en cadena.
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public interface IStateExchange {

	/**
	 * Determina si el componente tiene un valor No Nulo o no
	 * 
	 * @return true o false
	 */
	public boolean hasValue();

	/**
	 * Determinda si el componente tiene un valor erroneo
	 * 
	 * @return true o false
	 */
	public boolean isInError();

	/**
	 * Determina si el componente esta en estado valido
	 * 
	 * @return true o false
	 */
	public boolean isInValidState();

	/**
	 * Determina si el componente es obligatorio
	 * 
	 * @return true o false
	 */
	public boolean isRequired();
}
