package org.maox.arq.gui.view;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * Modelo de Tablas
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUITableModel extends DefaultTableModel {

	/**
	 * Constructor
	 */
	public GUITableModel() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param rowCount
	 * @param columnCount
	 */
	public GUITableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	/**
	 * Constructor
	 * 
	 * @param columnNames
	 * @param rowCount
	 */
	public GUITableModel(Vector<String> columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	/**
	 * Constructor
	 * 
	 * @param columnNames
	 * @param rowCount
	 */
	public GUITableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	/**
	 * Constructor
	 * 
	 * @param data
	 * @param columnNames
	 */
	public GUITableModel(Vector<Object> data, Vector<String> columnNames) {
		super(data, columnNames);
	}

	/**
	 * Constructor
	 * 
	 * @param data
	 * @param columnNames
	 */
	public GUITableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	/**
	 * Elimina todas las lineas de una tabla
	 */
	public void removeAll() {
		int iCont = getRowCount();
		for (int idx = iCont - 1; idx >= 0; idx--)
			removeRow(idx);
	}

}
