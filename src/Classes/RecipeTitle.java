package Classes;

import java.awt.Color;
import java.awt.Font;

public class RecipeTitle {

	private String recipeTitle = "NAN";
	private Font fontTitle = new Font("Times New Roman", Font.BOLD , 20);
	private int xTitle = 25, yTitle = 30;
	private Color titleForeground = Color.decode("#2E765E");
	
	public Color getTitleForeground() {
		return titleForeground;
	}
	public void setTitleForeground(Color titleForeground) {
		this.titleForeground = titleForeground;
	}
	public String getRecipeTitle() {
		return recipeTitle;
	}
	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}
	public Font getFontTitle() {
		return fontTitle;
	}
	public void setFontTitle(Font fontTitle) {
		this.fontTitle = fontTitle;
	}
	public int getxTitle() {
		return xTitle;
	}
	public void setxTitle(int xTitle) {
		this.xTitle = xTitle;
	}
	public int getyTitle() {
		return yTitle;
	}
	public void setyTitle(int yTitle) {
		this.yTitle = yTitle;
	}
	
}
