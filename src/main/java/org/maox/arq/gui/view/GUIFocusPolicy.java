package org.maox.arq.gui.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.ContainerOrderFocusTraversalPolicy;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JViewport;

import org.maox.arq.gui.component.GUIPanel;
import org.maox.arq.gui.component.GUIScrollPane;

@SuppressWarnings("serial")
public class GUIFocusPolicy extends ContainerOrderFocusTraversalPolicy {

	@Override
	public Component getComponentAfter(Container aContainer, Component aComponent) {
		Component nextComp = super.getComponentAfter(aContainer, aComponent);

		// Si el componente es un contenedor de componentes se tendrá que ir al primer componente focuseable
		if (nextComp instanceof GUIPanel || nextComp instanceof GUIScrollPane || nextComp instanceof GUIDesktop || nextComp instanceof JViewport
				|| nextComp instanceof JRootPane || nextComp instanceof JPanel || nextComp instanceof JLayeredPane)
			nextComp = getComponentAfter(aContainer, nextComp);

		return nextComp;
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component aComponent) {
		Component nextComp = super.getComponentBefore(aContainer, aComponent);

		// Si el componente es un contenedor de componentes se tendrá que ir al primer componente focuseable
		if (nextComp instanceof GUIPanel || nextComp instanceof GUIScrollPane || nextComp instanceof GUIDesktop || nextComp instanceof JViewport
				|| nextComp instanceof JRootPane || nextComp instanceof JPanel || nextComp instanceof JLayeredPane)
			nextComp = getComponentBefore(aContainer, nextComp);

		return nextComp;

	}
}
