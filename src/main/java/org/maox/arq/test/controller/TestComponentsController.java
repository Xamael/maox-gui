package org.maox.arq.test.controller;

import java.sql.SQLException;

import org.maox.arq.control.Controller;
import org.maox.arq.infra.Container;

/**
 * Controlador de la Vista de Mantenimiento de Entidades
 * 
 * @author Alex Orgaz
 * @version 00.01
 */
public class TestComponentsController extends Controller {

	/**
	 * Constructor
	 */
	public TestComponentsController() {
		super();
		viewName = "org.maox.arq.test.view.TestComponentsView";
	}

	@Override
	protected void closeConnection() {
	}

	@Override
	protected void commit() throws SQLException {
	}

	@Override
	public void execute(int iAction, Container param) {
		// Primero se chequean las acciones basicas de todos los controladores
		super.execute(iAction, param);

		try {
			// Acciones especificas
			switch (iAction) {
			}

		} catch (Exception e) {
			handleException(e);
		}
	}

	@Override
	public boolean isWaitingForCommit() {
		return false;
	}

	@Override
	protected void rollback() throws SQLException {
	}

}
