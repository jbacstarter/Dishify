package Components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class ListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String color1 = "#98D7C2", color2 = "#DDFFE7";

	/**
	 * Create the panel.
	 */
	public ListPanel() {
		setLayout(new WrapLayout(WrapLayout.CENTER, 10,30));
		setBackground(new Color(169, 218, 167));
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D) g;
		drawBackground(g2);
	}
	private void drawBackground(Graphics2D g2) {
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    int w = getWidth();
	    int h = getHeight();

	    // Define a gradient that transitions diagonally from top-left to middle-bottom
	    GradientPaint gra = new GradientPaint(0, 0, Color.decode(color1), 0, h-100, Color.decode(color2));
	    g2.setPaint(gra);
	    
	    // Fill the panel with the gradient
	    g2.fillRect(0, 0, w, h);
	}

}
