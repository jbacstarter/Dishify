package Components;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.awt.Dimension;
import javax.swing.JScrollPane;

import org.json.JSONArray;
import org.json.JSONObject;

import Classes.RecipeTitle;
import Helpers.Data;

public class RecipesForm extends JPanel {

	private static final long serialVersionUID = 1L;
	public JSONArray arrData =null;
	public JPanel listPanel = null;

	/**
	 * Create the panel.
	 */
	public RecipesForm() {
		setPreferredSize(new Dimension(606, 720));
		setBackground(Color.decode("#98D7C2"));
		setLayout(null);
		JPanel designPanel1 = new Header();
		designPanel1.setBounds(0, 0, 606, 87);
		add(designPanel1);
		
		JLabel lblNewLabel = new JLabel("D i s h i f y");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("FZShuTi", Font.BOLD | Font.ITALIC, 75));
		lblNewLabel.setBounds(117, 0, 399, 76);
		designPanel1.add(lblNewLabel);
		
		JScrollPane recipeScrollPanel = new JScrollPane();
		recipeScrollPanel.setBackground(new Color(169, 218, 167));
		recipeScrollPanel.setBorder(null);
		recipeScrollPanel.setBounds(25, 130, 573, 487);
		recipeScrollPanel.getVerticalScrollBar().setUI(new ModernScrollBarUI());
		add(recipeScrollPanel);
		
		listPanel = new ListPanel();
		recipeScrollPanel.setViewportView(listPanel);
		
	}
	public void addRecipes() {
		
		Thread.startVirtualThread(new Runnable() {
			
			@Override
			public void run() {
				listPanel.removeAll();
				JProgressBar bar = new JProgressBar(0,100);	
				bar.setStringPainted(true);
				bar.setValue(0);
				bar.setBackground(Color.WHITE);
				bar.setSize(200,20);
				bar.setLocation(200, 100);
				bar.setForeground(Color.GREEN);
				bar.setValue(0);
				add(bar);
				repaint();
				revalidate();
				String response =  Data.getData();
				bar.setValue(30);
				try {
					Thread.sleep(1000);
					bar.setValue(50);
				} catch (InterruptedException e) {
				}
				
				arrData = new JSONArray(response);
				bar.setValue(60);
				bar.repaint();
				for(int i = 0; i< arrData.length();i++) {
					JSONObject obj =arrData.getJSONObject(i);
					String image = obj.getString("image");
					String recipeTitle = obj.getString("title");
					if(image != null) {
						try {
							CRecipe rec = new CRecipe(ImageIO.read(URI.create(image).toURL()));
							RecipeTitle recTitle = rec.getRecipeTitle();
							recTitle.setRecipeTitle(recipeTitle);
							if(recTitle.getRecipeTitle().length() <= 8) {
								recTitle.setxTitle(70);
							}
							listPanel.add(rec);
							listUpdate();
						} catch(MalformedURLException e) {
							CRecipe rec = new CRecipe(null);
							rec.getRecipeTitle().setxTitle(80);
							listPanel.add(rec);
							listUpdate();
						} catch (IOException e) {
							CRecipe rec = new CRecipe(null);
							rec.getRecipeTitle().setxTitle(80);
							listPanel.add(rec);
							listUpdate();
						}
						
					}else {
						listPanel.add(new CRecipe(null));
						listUpdate();
					}
					bar.setValue(bar.getValue()+2);
				}
				bar.setValue(100);
				remove(bar);
				repaint();
				revalidate();
			}
		});
	}
	public void listUpdate() {
		listPanel.repaint();
		listPanel.revalidate();
	}
	public JSONArray getArrData() {
		return arrData;
	}
	public void setArrData(JSONArray arrData) {
		this.arrData = arrData;
	}
	public JPanel getListPanel() {
		return listPanel;
	}
	public void setListPanel(JPanel listPanel) {
		this.listPanel = listPanel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
