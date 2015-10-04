package org.maox.arq.gui.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Barra de estaus que se mostrara en el escritorio, utilizada para mostrar mensajes de información o errores
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
@SuppressWarnings("serial")
public class GUIStatusBar extends GUIPanel {
	GUILabel	lblEstado	= null;
	GUILabel	lblNumreg	= null;

	/**
	 * Constructor
	 */
	public GUIStatusBar() {
		// Caracteristicas del Panel
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		// Layout
		initLayout();

		// Componentes del Panel
		initComponents();

		setMessage("");
	}

	/**
	 * Inicializa las distintas partes de la barra de estado
	 */
	private void initComponents() {
		JSeparator SeparatorInicial = new JSeparator();
		GridBagConstraints gbc_SeparatorInicial = new GridBagConstraints();
		gbc_SeparatorInicial.insets = new Insets(0, 0, 0, 5);
		gbc_SeparatorInicial.gridx = 0;
		gbc_SeparatorInicial.gridy = 0;
		add(SeparatorInicial, gbc_SeparatorInicial);

		// Se inicializan las partes de la barra de estado
		lblEstado = new GUILabel("Status");
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.anchor = GridBagConstraints.WEST;
		gbc_lblEstado.insets = new Insets(0, 0, 0, 5);
		gbc_lblEstado.gridx = 1;
		gbc_lblEstado.gridy = 0;
		add(lblEstado, gbc_lblEstado);

		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator1 = new GridBagConstraints();
		gbc_separator1.fill = GridBagConstraints.VERTICAL;
		gbc_separator1.insets = new Insets(0, 0, 0, 5);
		gbc_separator1.gridx = 2;
		gbc_separator1.gridy = 0;
		add(separator1, gbc_separator1);

		lblNumreg = new GUILabel("Num Reg");
		GridBagConstraints gbc_lblNumreg = new GridBagConstraints();
		gbc_lblNumreg.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumreg.anchor = GridBagConstraints.EAST;
		gbc_lblNumreg.gridx = 3;
		gbc_lblNumreg.gridy = 0;
		add(lblNumreg, gbc_lblNumreg);

		JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator2 = new GridBagConstraints();
		gbc_separator2.fill = GridBagConstraints.VERTICAL;
		gbc_separator2.insets = new Insets(0, 0, 0, 5);
		gbc_separator2.gridx = 4;
		gbc_separator2.gridy = 0;
		add(separator2, gbc_separator2);

		GUILabel lblFlags = new GUILabel("Flags");
		GridBagConstraints gbc_lblFlags = new GridBagConstraints();
		gbc_lblFlags.insets = new Insets(0, 0, 0, 5);
		gbc_lblFlags.gridx = 5;
		gbc_lblFlags.gridy = 0;
		add(lblFlags, gbc_lblFlags);

		JSeparator separatorFinal = new JSeparator();
		GridBagConstraints gbc_separatorFinal = new GridBagConstraints();
		gbc_separatorFinal.gridx = 6;
		gbc_separatorFinal.gridy = 0;
		add(separatorFinal, gbc_separatorFinal);
	}

	/**
	 * Creación del Layout
	 */
	private void initLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 20 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);
	}

	@Override
	public boolean isFocusable() {
		return false;
	}

	/**
	 * Muestra un mensaje en la barra de estado
	 * 
	 * @param strMessage Mensaje
	 */
	public void setMessage(String strMessage) {
		lblEstado.setForeground(Color.BLACK);
		lblEstado.setText(strMessage);
	}

	/**
	 * Muestra un mensaje de error en la barra de estado
	 * 
	 * @param strMessage Mensaje
	 */
	public void setMessageError(String strMessage) {
		lblEstado.setForeground(Color.RED);
		lblEstado.setText(strMessage);
	}
}
