package org.maox.arq.gui.view;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Tabla de datos
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUITable extends JTable {

	/**
	 * Constructor
	 */
	public GUITable() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param numRows
	 * @param numColumns
	 */
	public GUITable(int numRows, int numColumns) {
		super(numRows, numColumns);
	}

	/**
	 * Constructor
	 * 
	 * @param rowData
	 * @param columnNames
	 */
	public GUITable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	/**
	 * Constructor
	 * 
	 * @param dm
	 */
	public GUITable(TableModel dm) {
		super(dm);
	}

	/**
	 * Constructor
	 * 
	 * @param dm
	 * @param cm
	 */
	public GUITable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	/**
	 * Constructor
	 * 
	 * @param dm
	 * @param cm
	 * @param sm
	 */
	public GUITable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	/**
	 * Constructor
	 * 
	 * @param rowData
	 * @param columnNames
	 */
	public GUITable(Vector<Object> rowData, Vector<String> columnNames) {
		super(rowData, columnNames);
	}

	@Override
	public boolean isFocusable() {
		return false;
	}
}
