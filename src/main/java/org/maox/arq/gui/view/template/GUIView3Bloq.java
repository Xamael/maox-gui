package org.maox.arq.gui.view.template;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.maox.arq.control.Controller;
import org.maox.arq.gui.component.GUIButton;
import org.maox.arq.gui.component.GUIPanel;
import org.maox.arq.gui.component.GUIScrollPane;
import org.maox.arq.gui.view.GUITable;
import org.maox.arq.gui.view.GUITableModel;
import org.maox.arq.gui.view.GUIView;
import org.maox.arq.infra.Container;
import org.maox.arq.language.Language;

/**
 * Patron de Vista de 3 bloques: Busqueda, Detalle y Resultados. Permite consulta y modificación
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public abstract class GUIView3Bloq extends GUIView implements ActionListener, ListSelectionListener {

	/* Caracteristicas visuales */
	final private int			btnWidth	= 100;	// Ancho de botón
	final private int			btnHeigth	= 30;	// Alto de botón

	/* Paneles Visuales */
	private GUIPanel			pnlQuery	= null;
	private GUIPanel			pnlDetail	= null;
	private GUIPanel			pnlResult	= null;
	private GUIScrollPane		scrTable	= null;

	/* Botones */
	private GUIButton			btnSearch	= null;
	private GUIButton			btnAccept	= null;
	private GUIButton			btnCancel	= null;
	private GUIButton			btnAdd		= null;
	private GUIButton			btnEdit		= null;
	private GUIButton			btnRemove	= null;
	private GUIButton			btnClose	= null;
	private GUIButton			btnSave		= null;

	/* Otros Elementos */
	private GUITable			table;				// Tabla
	private ListSelectionModel	listSelectionModel; // Modelo de seleccion
	private int					lastRowSelected;	// Ultima fila seleccionada

	/**
	 * Constructor
	 */
	public GUIView3Bloq() {
		super();
		initView();
		initBasicConnections();
	}

	/**
	 * Constructor con Resolución (SVGA, XGA, SXGA, ...)
	 */
	public GUIView3Bloq(int iResolution) {
		super(iResolution);
		initView();
		initBasicConnections();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(getbtnSearch()))
			pulseSearch();
		if (event.getSource().equals(getbtnAccept()))
			pulseAccept();
		if (event.getSource().equals(getbtnCancel()))
			pulseCancel();
		if (event.getSource().equals(getbtnAdd()))
			pulseAdd();
		if (event.getSource().equals(getbtnEdit()))
			pulseEdit();
		if (event.getSource().equals(getbtnRemove()))
			pulseRemove();
		if (event.getSource().equals(getbtnSave()))
			pulseSave();
		else if (event.getSource().equals(getbtnClose()))
			pulseClose();
	}

	/**
	 * Activa o desactiva los bloques a partir del estado de entrada
	 */
	@Override
	protected void changeStatus(int status) {

		// Si no hay cambio de estado no se hace nada
		if (status == getStatus())
			return;

		setStatus(status);

		// Habilitación de los paneles según el estado
		switch (status) {
		case QRY:
			getpnlQuery().setEnabled(true);
			getpnlDetail().setEnabled(false);
			getpnlResult().setEnabled(true);
			getbtnEdit().setEnabled(false);
			getbtnRemove().setEnabled(false);
			table.setRowSelectionAllowed(true);
			getbtnSave().setEnabled(isWaitingForCommit());
			getpnlQuery().requestFocus();
			break;
		default:
			getpnlQuery().setEnabled(false);
			getpnlDetail().setEnabled(true);
			getpnlResult().setEnabled(true);
			table.setRowSelectionAllowed(false);
			getbtnSave().setEnabled(isWaitingForCommit());
			getpnlDetail().requestFocus();
			break;
		}
	}

	/**
	 * Borrado de los datos que hay en la tabla de resultados
	 */
	protected void clearTable() {
		getTableModel().removeAll();
	}

	/**
	 * Botón de Cancelación de Edición
	 * 
	 * @return
	 */
	protected GUIButton getbtnAccept() {
		if (btnAccept == null) {
			btnAccept = new GUIButton(Language.getString("BTN_ACCEPT"));
			btnAccept.setIcon(GUIButton.ICON_ACCEPT);
			btnAccept.setType(GUIButton.ACCEPT_BUTTON);
			btnAccept.setName("btnAccept");
			btnAccept.setBounds(getpnlDetail().getWidth() - 10 - btnWidth, getpnlDetail().getHeight() / 2 - btnHeigth, btnWidth, btnHeigth);
		}

		return btnAccept;
	}

	/**
	 * Botón de Añadir registro
	 * 
	 * @return
	 */
	protected GUIButton getbtnAdd() {
		if (btnAdd == null) {
			btnAdd = new GUIButton(Language.getString("BTN_ADD"));
			btnAdd.setIcon(GUIButton.ICON_ADD);
			btnAdd.setType(GUIButton.NORMAL_BUTTON);
			btnAdd.setName("btnAdd");
			btnAdd.setBounds(getpnlResult().getWidth() - 10 - btnWidth, getpnlResult().getHeight() / 2 - (int) (btnHeigth * 1.5), btnWidth, btnHeigth);

		}

		return btnAdd;
	}

	/**
	 * Botón de Cancelación de Edición
	 * 
	 * @return
	 */
	protected GUIButton getbtnCancel() {
		if (btnCancel == null) {
			btnCancel = new GUIButton(Language.getString("BTN_CANCEL"));
			btnCancel.setIcon(GUIButton.ICON_CANCEL);
			btnCancel.setType(GUIButton.NORMAL_BUTTON);
			btnCancel.setName("btnCancel");
			btnCancel.setBounds(getpnlDetail().getWidth() - 10 - btnWidth, getpnlDetail().getHeight() / 2, btnWidth, btnHeigth);
		}

		return btnCancel;
	}

	/**
	 * Botón de Cierre sin Guardar los cambios
	 * 
	 * @return
	 */
	protected GUIButton getbtnClose() {
		if (btnClose == null) {
			btnClose = new GUIButton(Language.getString("BTN_CLOSE"));
			btnClose.setIcon(GUIButton.ICON_CLOSE);
			btnClose.setType(GUIButton.CLOSE_BUTTON);
			btnClose.setName("btnClose");
			btnClose.setBounds(getWResolution() - 10 - btnWidth, getpnlQuery().getHeight() + getpnlDetail().getHeight() + getpnlResult().getHeight() - 5,
					btnWidth, btnHeigth);
		}

		return btnClose;
	}

	/**
	 * Botón de Editar registro
	 * 
	 * @return
	 */
	protected GUIButton getbtnEdit() {
		if (btnEdit == null) {
			btnEdit = new GUIButton(Language.getString("BTN_EDIT"));
			btnEdit.setIcon(GUIButton.ICON_EDIT);
			btnEdit.setType(GUIButton.NORMAL_BUTTON);
			btnEdit.setName("btnEdit");
			btnEdit.setBounds(getpnlResult().getWidth() - 10 - btnWidth, getpnlResult().getHeight() / 2 - btnHeigth / 2, btnWidth, btnHeigth);
			btnEdit.setEnabled(false);
		}

		return btnEdit;
	}

	/**
	 * Botón de Eliminar registro
	 * 
	 * @return
	 */
	protected GUIButton getbtnRemove() {
		if (btnRemove == null) {
			btnRemove = new GUIButton(Language.getString("BTN_DEL"));
			btnRemove.setIcon(GUIButton.ICON_WARNING);
			btnRemove.setType(GUIButton.NORMAL_BUTTON);
			btnRemove.setName("btnRemove");
			btnRemove.setBounds(getpnlResult().getWidth() - 10 - btnWidth, getpnlResult().getHeight() / 2 + btnHeigth / 2, btnWidth, btnHeigth);
			btnRemove.setEnabled(false);
		}

		return btnRemove;
	}

	/**
	 * Boton de Salvado y Aceptación de los cambios
	 * 
	 * @return
	 */
	protected GUIButton getbtnSave() {
		if (btnSave == null) {
			btnSave = new GUIButton(Language.getString("BTN_ACCEPT"));
			btnSave.setIcon(GUIButton.ICON_ACCEPT);
			btnSave.setType(GUIButton.ACCEPT_BUTTON);
			btnSave.setName("btnSave");
			btnSave.setBounds(getWResolution() - 20 - btnWidth * 2, getpnlQuery().getHeight() + getpnlDetail().getHeight() + getpnlResult().getHeight()
					- 5, btnWidth, btnHeigth);
		}

		return btnSave;
	}

	/**
	 * Botón de Busqueda con Criterios de Consulta
	 * 
	 * @return
	 */
	protected GUIButton getbtnSearch() {
		if (btnSearch == null) {
			btnSearch = new GUIButton(Language.getString("BTN_SEARCH"));
			btnSearch.setIcon(GUIButton.ICON_WARNING);
			btnSearch.setType(GUIButton.ACCEPT_BUTTON);
			btnSearch.setName("btnSearch");
			btnSearch.setBounds(getpnlQuery().getWidth() - 10 - btnWidth, getpnlQuery().getHeight() / 2 - btnHeigth / 2, btnWidth, btnHeigth);
		}

		return btnSearch;
	}

	protected abstract Container getDetailData();

	/**
	 * Bloque de Modificación
	 * 
	 * @return
	 */
	protected GUIPanel getpnlDetail() {
		if (pnlDetail == null) {
			pnlDetail = new GUIPanel();
			pnlDetail.setName("pnlDetail");
			pnlDetail.setBounds(5, getpnlQuery().getHeight(), getWResolution() - 10, (int) (getHResolution() * 0.30));
			pnlDetail.setBorder(new TitledBorder(null, Language.getString("PNL_DETAIL"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

			// Botones del panel
			// Para mantener el orden del foco, los botones los ha de añadir la Vista Final
			//pnlDetail.add(getbtnAccept());
			//pnlDetail.add(getbtnCancel());
		}

		return pnlDetail;
	}

	/**
	 * Bloque de Consulta
	 * 
	 * @return
	 */
	protected GUIPanel getpnlQuery() {
		if (pnlQuery == null) {
			pnlQuery = new GUIPanel();
			pnlQuery.setName("pnlQuery");
			pnlQuery.setBounds(5, 5, getWResolution() - 10, (int) (getHResolution() * 0.25));
			pnlQuery.setBorder(new TitledBorder(null, Language.getString("PNL_SEARCH"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

			// Botones del panel
			// Para mantener el orden del foco, los botones los ha de añadir la Vista Final
			//pnlQuery.add(getbtnSearch());
		}

		return pnlQuery;
	}

	/**
	 * Bloque de Resultados
	 * 
	 * @return
	 */
	protected GUIPanel getpnlResult() {
		if (pnlResult == null) {
			pnlResult = new GUIPanel();
			pnlResult.setName("pnlResult");
			pnlResult.setBounds(5, getpnlQuery().getHeight() + getpnlDetail().getHeight() - 5, getWResolution() - 10, (int) (getHResolution() * 0.40));
			pnlResult.setBorder(new TitledBorder(null, Language.getString("PNL_RESULT"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

			// Tabla de resultados
			pnlResult.add(getscrTable());

			// Botones del panel
			pnlResult.add(getbtnAdd());
			pnlResult.add(getbtnEdit());
			pnlResult.add(getbtnRemove());
		}

		return pnlResult;
	}

	/**
	 * Obtención de los criterios de consulta
	 * 
	 * @return Conjunto de criterios de consulta
	 */
	protected abstract Container getQueryData();

	/**
	 * Panel de Tabla
	 * 
	 * @return
	 */
	protected GUIScrollPane getscrTable() {
		if (scrTable == null) {
			scrTable = new GUIScrollPane();
			scrTable.setName("scrTable");
			scrTable.setBackground(new Color(240, 240, 240));
			scrTable.setBounds(15, 30, getpnlResult().getWidth() - btnWidth - 30, getpnlResult().getHeight() - 50);
			scrTable.setViewportView(getTable());
		}

		return scrTable;
	}

	/**
	 * Obtiene la última fila seleccionada
	 * 
	 * @return
	 */
	protected int getSelectedRow() {
		return lastRowSelected;
	}

	/**
	 * Tabla de datos
	 * 
	 * @return
	 */
	protected GUITable getTable() {
		if (table == null) {
			table = new GUITable();
			// Modelo por defecto
			table.setModel(new GUITableModel());
		}

		return table;
	}

	/**
	 * Devuelve el modelo de la tabla
	 * 
	 * @return
	 */
	protected GUITableModel getTableModel() {
		return (GUITableModel) getTable().getModel();
	}

	/**
	 * Inicialización de los eventos necesarios
	 */
	private void initBasicConnections() {
		// Eventos de botón
		getbtnSearch().addActionListener(this);
		getbtnAccept().addActionListener(this);
		getbtnCancel().addActionListener(this);
		getbtnAdd().addActionListener(this);
		getbtnEdit().addActionListener(this);
		getbtnRemove().addActionListener(this);
		getbtnSave().addActionListener(this);
		getbtnClose().addActionListener(this);

		// Evento de tabla
		listSelectionModel = getTable().getSelectionModel();
		getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(this);
	}

	/**
	 * Creación de la vista de 3 bloques y sus botones
	 */
	private void initView() {
		lastRowSelected = 0;

		// Tamaño del panel
		setSize(getWResolution(), getHResolution());

		// Paneles
		add(getpnlQuery());
		add(getpnlDetail());
		add(getpnlResult());

		// Botones
		add(getbtnSave());
		add(getbtnClose());
	};

	/**
	 * Acción de pulsar el botón de Aceptar la modficación
	 */
	protected void pulseAccept() {
		switch (getStatus()) {
		case ADD:
			execute(Controller.INSERT, getDetailData());
			break;
		case EDT:
			execute(Controller.UPDATE, getDetailData());
			break;
		case DEL:
			execute(Controller.DELETE, getDetailData());
			break;
		}

		// Se limpia el panel de detalle
		getpnlDetail().clearComponents();
	}

	/**
	 * Acción de pulsar botón Añadir Registro
	 */
	protected void pulseAdd() {
		changeStatus(ADD);
		getpnlDetail().clearComponents();
	};

	/**
	 * Acción de pulsar el botón de cancelar
	 */
	protected void pulseCancel() {
		changeStatus(QRY);
		getpnlDetail().clearComponents();
		selectRow();
	}

	protected void pulseClose() {
		execute(Controller.EXIT);
	};

	/**
	 * Acción de pulsar el botón Modificar
	 */
	protected void pulseEdit() {
		changeStatus(EDT);
	};

	/**
	 * Acción de pulsar el botoón de eliminar
	 */
	protected void pulseRemove() {
		changeStatus(DEL);
	}

	protected void pulseSave() {
		execute(Controller.EXIT_AND_SAVE);
	}

	/**
	 * Acción de consultar con los criterios de consulta
	 */
	protected void pulseSearch() {
		// Borrado de los resultados anteriores
		clearTable();

		// Consulta con los parámetros
		Container pQuery = getQueryData();
		execute(Controller.SEARCH, pQuery);
	}

	@Override
	public void refresh(int iAction, Container pResult) {
		switch (iAction) {
		/* Acción Inicial de la vista, nada más pintarla */
		case Controller.INIT:
			changeStatus(QRY);
			break;
		/* Pulsado de Botón Buscar Mostrar la información recuperada */
		case Controller.SEARCH:
			showQuery(pResult);
			showMessage("Consulta realizada");
			break;
		case Controller.INSERT:
			showInsert(pResult);
			changeStatus(QRY);
			showMessage("Inserción realizada");
			break;
		case Controller.UPDATE:
			showUpdate(pResult);
			changeStatus(QRY);
			showMessage("Actualización realizada");
			break;
		case Controller.DELETE:
			showDelete(pResult);
			changeStatus(QRY);
			showMessage("Borrado realizado");
			break;
		}
	}

	/**
	 * Selección de una fila
	 */
	protected void selectRow() {
		// Si la tabla está vacía se desactivan los botones de modificar y eliminar
		if (!listSelectionModel.getValueIsAdjusting() && getStatus() == QRY) {
			if (table.getRowCount() == 0 || getSelectedRow() == -1) {
				getbtnEdit().setEnabled(false);
				getbtnRemove().setEnabled(false);
			}
			else {
				getbtnEdit().setEnabled(true);
				getbtnRemove().setEnabled(true);
			}

			lastRowSelected = table.getSelectedRow();

			// Se muestra el registro detalle
			showDetail();
		}
	}

	/**
	 * Muestra el resultado del borrado
	 * 
	 * @param pResult
	 */
	protected abstract void showDelete(Container pResult);

	/**
	 * Muestra el registro seleccionado de la tabla
	 */
	protected abstract void showDetail();

	/**
	 * Muestra el resultado de la inserción
	 * 
	 * @param pResult
	 */
	protected abstract void showInsert(Container pResult);

	/**
	 * Muestra el resultado de la consulta
	 * 
	 * @param pResult
	 */
	protected abstract void showQuery(Container pResult);

	/**
	 * Muestra el resultado de la actualización
	 * 
	 * @param pResult
	 */
	protected abstract void showUpdate(Container pResult);

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource().equals(listSelectionModel) && !listSelectionModel.getValueIsAdjusting())
			selectRow();
	}
}
