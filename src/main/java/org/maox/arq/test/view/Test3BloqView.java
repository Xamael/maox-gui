package org.maox.arq.test.view;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.maox.arq.gui.component.GUILabel;
import org.maox.arq.gui.component.GUITextField;
import org.maox.arq.gui.events.DataEvent;
import org.maox.arq.gui.events.DataListener;
import org.maox.arq.gui.view.GUITableModel;
import org.maox.arq.gui.view.template.GUIView3Bloq;
import org.maox.arq.infra.Container;
import org.maox.arq.language.Language;
import org.maox.arq.test.model.ITestModel;
import org.maox.arq.test.model.TestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Vista para el mantenimiento de Prueba
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class Test3BloqView extends GUIView3Bloq implements DataListener {

	/* Log */
	private final Logger logger = LoggerFactory.getLogger(Test3BloqView.class);
	/* Etiquetas */
	private GUILabel lblTicket = null;
	private GUILabel lblDescrip = null;
	private GUILabel lblTicketM = null;
	private GUILabel lblDescripM = null;
	private GUILabel lblPaisM = null;
	/* Campos */
	private GUITextField txtTicket = null;
	private GUITextField txtDescripcion = null;
	private GUITextField txtTicketM = null;
	private GUITextField txtDescripcionM = null;
	private GUITextField txtPaisM = null;

	/**
	 * Constructor, creación de la vista
	 */
	public Test3BloqView() {
		super(SVGA);
		setTitle(Language.getString("VIEW_ENTITIES"));
		initView();
		initTable();
		initConnections();
	}

	@Override
	public void dataChanged(DataEvent event) {
		logger.debug("Lisening dataChangd");

		// Si los campos son de criterios de busqueda
		if (event.getSource() == gettxtTicket() || event.getSource() == gettxtDescripcion()) {
			clearTable();
		}
	}

	@Override
	protected Container getDetailData() {
		Container pQuery = new Container();
		pQuery.put(ITestModel.TICKET, gettxtTicketM().getData());
		pQuery.put(ITestModel.DESCRIPCION, gettxtDescripcionM().getData());
		pQuery.put(ITestModel.PAIS, gettxtPaisM().getData());

		return pQuery;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUILabel getlblDescrip() {
		if (lblDescrip == null) {
			lblDescrip = new GUILabel();
			lblDescrip.setText(Language.getString("LBL_DESCRIP"));
			lblDescrip.setBounds(20, 60, 71, 14);
		}
		return lblDescrip;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUILabel getlblDescripM() {
		if (lblDescripM == null) {
			lblDescripM = new GUILabel();
			lblDescripM.setText(Language.getString("LBL_DESCRIP"));
			lblDescripM.setBounds(20, 60, 71, 14);
		}
		return lblDescripM;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUILabel getlblPaisM() {
		if (lblPaisM == null) {
			lblPaisM = new GUILabel();
			lblPaisM.setBounds(20, 85, 71, 14);
			lblPaisM.setText(Language.getString("LBL_COUNTRY"));
		}
		return lblPaisM;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUILabel getlblTicket() {
		if (lblTicket == null) {
			lblTicket = new GUILabel();
			lblTicket.setText(Language.getString("LBL_TICKET"));
			lblTicket.setBounds(20, 35, 71, 14);
		}

		return lblTicket;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUILabel getlblTicketM() {
		if (lblTicketM == null) {
			lblTicketM = new GUILabel();
			lblTicketM.setText(Language.getString("LBL_TICKET"));
			lblTicketM.setBounds(20, 35, 71, 14);
		}

		return lblTicketM;
	}

	@Override
	protected Container getQueryData() {
		Container pQuery = new Container();
		pQuery.put(ITestModel.TICKET, gettxtTicket().getData());
		pQuery.put(ITestModel.DESCRIPCION, gettxtDescripcion().getData());

		return pQuery;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUITextField gettxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new GUITextField();
			txtDescripcion.setBounds(93, 56, 384, 22);
			txtDescripcion.setName("txtDescripcion");
		}

		return txtDescripcion;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUITextField gettxtDescripcionM() {
		if (txtDescripcionM == null) {
			txtDescripcionM = new GUITextField();
			txtDescripcionM.setRequired(true);
			txtDescripcionM.setBounds(93, 56, 384, 22);
			txtDescripcionM.setName("txtDescripcionM");
		}

		return txtDescripcionM;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUITextField gettxtPaisM() {
		if (txtPaisM == null) {
			txtPaisM = new GUITextField();
			txtPaisM.setRequired(true);
			txtPaisM.setBounds(93, 81, 100, 22);
			txtPaisM.setName("txtPaisM");
		}

		return txtPaisM;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUITextField gettxtTicket() {
		if (txtTicket == null) {
			txtTicket = new GUITextField();
			txtTicket.setRequired(true);
			txtTicket.setLocation(93, 32);
			txtTicket.setName("txtTicket");
		}

		return txtTicket;
	}

	/**
	 * Componente Visual
	 * 
	 * @return
	 */
	private GUITextField gettxtTicketM() {
		if (txtTicketM == null) {
			txtTicketM = new GUITextField();
			txtTicketM.setRequired(true);
			txtTicketM.setSize(100, 22);
			txtTicketM.setLocation(93, 32);
			txtTicketM.setName("txtTicketM");
		}

		return txtTicketM;
	}

	/**
	 * Inicialización de los eventos de pantalla
	 */
	private void initConnections() {

		// Campos
		gettxtTicket().addDataListener(this);
		gettxtDescripcion().addDataListener(this);
		gettxtTicketM().addDataListener(this);
		gettxtDescripcionM().addDataListener(this);
		gettxtPaisM().addDataListener(this);
	}

	/**
	 * Inicialización de las columnas de la tabla
	 */
	private void initTable() {
		GUITableModel model = (GUITableModel) getTable().getModel();
		model.addColumn(Language.getString("LBL_TICKET"));
		model.addColumn(Language.getString("LBL_DESCRIP"));
		model.addColumn(Language.getString("LBL_COUNTRY"));
	}

	/**
	 * Inicialización de la Vista
	 */
	public void initView() {
		// Etiquetas
		getpnlQuery().add(getlblTicket());
		getpnlQuery().add(getlblDescrip());
		getpnlDetail().add(getlblPaisM());
		getpnlDetail().add(getlblTicketM());
		getpnlDetail().add(getlblDescripM());

		// Campos Consulta
		getpnlQuery().add(gettxtTicket());
		getpnlQuery().add(gettxtDescripcion());
		getpnlQuery().add(getbtnSearch());

		// Campos Detalle
		getpnlDetail().add(gettxtTicketM());
		getpnlDetail().add(gettxtDescripcionM());
		getpnlDetail().add(gettxtPaisM());
		getpnlDetail().add(getbtnAccept());
		getpnlDetail().add(getbtnCancel());
	}

	@Override
	public void refresh(int iAction, Container pResult) {
		super.refresh(iAction, pResult);

		switch (iAction) {
		}
	}

	@Override
	protected void showDelete(Container pResult) {
		GUITableModel model = (GUITableModel) getTable().getModel();
		model.removeRow(getSelectedRow());
	}

	@Override
	protected void showDetail() {
		gettxtTicketM().setData(getTableModel().getValueAt(getSelectedRow(), 0));
		gettxtDescripcionM().setData(getTableModel().getValueAt(getSelectedRow(), 1));
		gettxtPaisM().setData(getTableModel().getValueAt(getSelectedRow(), 2));
	}

	@Override
	protected void showInsert(Container pResult) {
		GUITableModel model = (GUITableModel) getTable().getModel();
		TestModel ent = (TestModel) pResult.get("REG");

		Vector<String> vData = new Vector<String>();
		vData.add(ent.getTicket());
		vData.add(ent.getDescripcion());
		vData.add(ent.getPais());

		model.insertRow(0, vData);
	}

	@Override
	protected void showQuery(Container param) {
		GUITableModel model = (GUITableModel) getTable().getModel();

		@SuppressWarnings("unchecked")
		List<TestModel> list = (List<TestModel>) param.get("QUERY");
		Iterator<TestModel> iter = list.iterator();

		while (iter.hasNext()) {
			TestModel ent = iter.next();
			Vector<String> vData = new Vector<String>();
			vData.add(ent.getTicket());
			vData.add(ent.getDescripcion());
			vData.add(ent.getPais());
			model.addRow(vData);
		}
	}

	@Override
	protected void showUpdate(Container pResult) {
		GUITableModel model = (GUITableModel) getTable().getModel();
		TestModel ent = (TestModel) pResult.get("REG");

		model.setValueAt(ent.getTicket(), getSelectedRow(), 0);
		model.setValueAt(ent.getDescripcion(), getSelectedRow(), 1);
		model.setValueAt(ent.getPais(), getSelectedRow(), 2);
	}
}
