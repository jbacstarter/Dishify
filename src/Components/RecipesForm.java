package Components;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import java.awt.Font;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.awt.Dimension;
import javax.swing.JScrollPane;

import org.json.JSONArray;

import Helpers.Data;

public class RecipesForm extends JPanel {

	private static final long serialVersionUID = 1L;
	public JSONArray arrData =null;
	public JPanel listPanel = null;

	/**
	 * Create the panel.
	 */
	public RecipesForm() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(606, 720));
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
		recipeScrollPanel.setBorder(null);
		recipeScrollPanel.setBounds(25, 130, 573, 487);
		recipeScrollPanel.getVerticalScrollBar().setUI(new ModernScrollBarUI());
		add(recipeScrollPanel);
		
		listPanel = new JPanel();
		listPanel.setLayout(new WrapLayout(WrapLayout.CENTER, 10,30));
		listPanel.setBackground(Color.WHITE);
		recipeScrollPanel.setViewportView(listPanel);
		
	}
	public void addRecipes() {
		
		Thread.startVirtualThread(new Runnable() {
			
			@Override
			public void run() {
				listPanel.removeAll();
				JProgressBar bar = new JProgressBar(0,100);	
				bar.setStringPainted(true);
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
					String image=  arrData.getJSONObject(i).getString("image");
					
					if(image != null) {
						try {
							listPanel.add(new CRecipe(ImageIO.read(URI.create(image).toURL())));
							listPanel.repaint();
							listPanel.revalidate();
						} catch (MalformedURLException e) {
							listPanel.add(new CRecipe(null));
							listPanel.repaint();
							listPanel.revalidate();
						} catch (IOException e) {
							listPanel.add(new CRecipe(null));
							listPanel.revalidate();
							listPanel.repaint();
						}
						
					}else {
						listPanel.add(new CRecipe(null));
						listPanel.repaint();
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
}
