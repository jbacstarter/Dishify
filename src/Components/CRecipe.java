package Components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Classes.RecipeTitle;

public class CRecipe extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img= null;
	private int imgX = 50, imgY = 50, imgW = 150, imgH = 150;
	private String color1 = "#98D7C2", color2 = "#C5FFBF";
	private RecipeTitle recipeTitle = new RecipeTitle();
	public CRecipe(BufferedImage image) {
		if(image == null) {

			try {
				img = ImageIO.read(new File("src/Resources/DishifyLogo.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			img = image;
		}
		initialize();
	}
	
	private void initialize() {
		setLocation(30, 45);
		setBackground(new Color(255, 255, 255));
		setSize(517, 236);
		setLayout(null);
		setPreferredSize(new Dimension(530,250));
		setBorder(new LineBorder(Color.BLACK, 1));
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				color1 = "#98D7C2";
				color2 = "#167D7F";
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				setImgW(160);
				setImgH(160);
				repaint();
				
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setImgW(150);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				setImgH(150);
				color1 = "#98D7C2";
				color2 = "#C5FFBF";
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		drawRecipeTitle(g2);
	}
	private void drawRecipeTitle(Graphics2D g2) {
		g2.setFont(recipeTitle.getFontTitle());
		g2.setColor(recipeTitle.getTitleForeground());
		g2.drawString(recipeTitle.getRecipeTitle(), recipeTitle.getxTitle(), recipeTitle.getyTitle());
	}
	private void drawBackground(Graphics2D g2) {
	    
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    int w = getWidth();
	    int h = getHeight();

	    // Define a gradient that transitions diagonally from top-left to middle-bottom
	    GradientPaint gra = new GradientPaint(0, 0, Color.decode(color1), w/2, 0, Color.decode(color2));
	    g2.setPaint(gra);
	    
	    // Fill the panel with the gradient
//	    g2.fillRect(0, 0, w, h);
	    g2.fillRect(0, 0, w, h);
	    
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(imgX, imgY, imgW, imgH);
		g2.drawImage(img,imgX, imgY, imgW, imgH, null);
	}
	public String getColor1() {
		return color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public RecipeTitle getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(RecipeTitle recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getImgX() {
		return imgX;
	}

	public void setImgX(int imgX) {
		this.imgX = imgX;
	}

	public int getImgY() {
		return imgY;
	}

	public void setImgY(int imgY) {
		this.imgY = imgY;
	}

	public int getImgW() {
		return imgW;
	}

	public void setImgW(int imgW) {
		this.imgW = imgW;
	}

	public int getImgH() {
		return imgH;
	}

	public void setImgH(int imgH) {
		this.imgH = imgH;
	}
}
