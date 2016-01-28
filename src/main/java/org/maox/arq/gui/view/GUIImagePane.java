package org.maox.arq.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Panel para mostrar una imagen
 * 
 * @author Alex Orgaz
 * 
 */
@SuppressWarnings("serial")
public class GUIImagePane extends JPanel {

	/* Imagen a mostrar */
	private BufferedImage image;
	/* Flag que indica si la imagen se ha de adaptar al panel automáticamente */
	private boolean autoResize = false;
	/* Flag que indica si la imagen ha de mantener el aspect ratio en los resize */
	private boolean mantainRatio = false;

	/**
	 * Constructor
	 */
	public GUIImagePane() {
	}

	/**
	 * Redimensiona la imagen
	 */
	public void autoResizeImage() {
		if (image == null)
			return;

		int wOld = image.getWidth();
		int hOld = image.getHeight();
		int wNew = getWidth();
		int hNew = getHeight();

		if (wOld != wNew || hOld != hNew) {
			// Si hay que mantener el ratio se comprueba y se vuelve a obtener la dimensión
			if (mantainRatio) {
				double ratio = (double) wOld / hOld;

				if (ratio != (double) wNew / hNew) {
					wNew = (int) (hNew * ratio);
					if (wNew > getWidth()) {
						wNew = getWidth();
						hNew = (int) (wNew * ((double) hOld / wOld));
					}
				}
			}

			BufferedImage after = new BufferedImage(wNew, hNew, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(wNew / (double) wOld, hNew / (double) hOld);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			after = scaleOp.filter(image, after);

			image = after;
		}
	}

	/**
	 * @return La imagen que se está mostrando
	 */
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public Dimension getPreferredSize() {
		BufferedImage img = getImage();
		return img == null ? new Dimension(320, 240) : new Dimension(img.getWidth(), img.getHeight());
	}

	/**
	 * Determina si la imagen se ha de redimensionar automáticamente
	 * 
	 * @return the autoResize
	 */
	public boolean isAutoResize() {
		return autoResize;
	}

	/**
	 * @return the mantainRatio
	 */
	public boolean isMantainRatio() {
		return mantainRatio;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		BufferedImage img = getImage();
		if (autoResize) {
			autoResizeImage();
		}

		if (img != null) {
			int x = (getWidth() - img.getWidth()) / 2;
			int y = (getHeight() - img.getHeight()) / 2;

			g2d.drawImage(img, x, y, this);
		}
		g2d.dispose();
	}

	/**
	 * Establece si la imagen se ha de adaptar automáticamente al tamaño del panel
	 * 
	 * @param autoResize
	 */
	public void setAutoResize(boolean autoResize) {
		this.autoResize = autoResize;
	}

	/**
	 * @param image Imagen a mostrar
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Establece si la imagen a de mantener el aspect ratio aunque se redimensione
	 * 
	 * @param mantainRatio the mantainRatio to set
	 */
	public void setMantainRatio(boolean mantainRatio) {
		this.mantainRatio = mantainRatio;
	}
}
