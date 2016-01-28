package org.maox.arq.test.view;

import javax.swing.border.TitledBorder;

import org.maox.arq.gui.component.GUIPanel;
import org.maox.arq.gui.events.DataListener;
import org.maox.arq.gui.view.GUIView;
import org.maox.arq.infra.Container;
import org.maox.arq.language.Language;

/**
 * Interfaces de prueba para los componentes visuales
 * 
 * @author Alex Orgaz
 * @version 00.01
 * 
 */
@SuppressWarnings("serial")
public class TestComponentsView extends GUIView implements DataListener {

	/* Panle contendor */
	private GUIPanel pnlPanel = null;

	/**
	 * Constructor
	 */
	public TestComponentsView() {
		setTitle(Language.getString("VIEW_COMPONENTS"));
		initView();
	}

	@Override
	protected void changeStatus(int status) {
		// TODO Auto-generated method stub

	}

	/**
	 * Bloque de Consulta
	 * 
	 * @return
	 */
	protected GUIPanel getpnlPanel() {
		if (pnlPanel == null) {
			pnlPanel = new GUIPanel();
			pnlPanel.setName("pnlPanel");
			pnlPanel.setBounds(5, 5, getWResolution() - 10, (int) (getHResolution() * 0.25));
			pnlPanel.setBorder(new TitledBorder(null, Language.getString("PNL"), TitledBorder.LEADING,
					TitledBorder.TOP, null, null));

		}

		return pnlPanel;
	}

	/**
	 * Generación de la vista de interfaz
	 */
	private void initView() {
		// Tamaño del panel
		setSize(getWResolution(), getHResolution());

		// Paneles
		add(getpnlPanel());

	}

	@Override
	public void refresh(int iAction, Container pResult) {
		// TODO Auto-generated method stub

	}

}
