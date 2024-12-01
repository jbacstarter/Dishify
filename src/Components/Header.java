package Components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Header extends JPanel {

	private static final long serialVersionUID = 1L;
	private Color color1 = Color.decode("#006A4E");
	private Color color2 = Color.decode("#ACE1AF");
	/**
	 * Create the panel.
	 */
	public Header() {
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getWidth();
		int h = getHeight();
		
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gra = new GradientPaint(0, 0, color1, w/2, h, color2);
		g2.setPaint(gra);
		g2.fillRect(0, 0, w, h);
		g.drawImage(img, 0, 0, null);
	}

}
