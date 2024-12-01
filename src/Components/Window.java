package Components;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


import Helpers.Data;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Font;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println(Toolkit.getDefaultToolkit().getScreenSize().height);
		setSize(600, Toolkit.getDefaultToolkit().getScreenSize().height);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBounds(0, 0, 586, 720);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		ListForm listForm = new ListForm();
		listForm.setBounds(0, 0, 586, 622);
		contentPanel.add(listForm);
		
		RecipesForm recipeForm = new RecipesForm();
		recipeForm.setBounds(0, 0, 600, 620);
		recipeForm.setLayout(null);
		//contentPanel.add(recipeForm);
		
		JPanel designPanel2 = new JPanel();
		designPanel2.setBackground(new Color(46, 139, 87));
		designPanel2.setBounds(0, 586, 586, 36);
		listForm.add(designPanel2);
		
		JButton homeButton = new JButton("H o m e");
		homeButton.setFont(new Font("Showcard Gothic", Font.PLAIN, 24));
		homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		homeButton.setBorderPainted(false);
		homeButton.setBackground(new Color(255, 255, 255));
		homeButton.setBounds(0, 621, 285, 65);
		homeButton.setFocusPainted(false);
		contentPanel.add(homeButton);
		
		JButton recipesButton = new JButton("R e c i p e s");
		recipesButton.setFont(new Font("Showcard Gothic", Font.PLAIN, 24));
		recipesButton.setFocusPainted(false);
		recipesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		recipesButton.setBorderPainted(false);
		recipesButton.setBackground(new Color(255, 255, 255));
		recipesButton.setBorder(null);
		recipesButton.setBounds(285, 621, 301, 65);
		contentPanel.add(recipesButton);
		
		recipesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				recipesButton.setBackground(new Color(46, 139, 87));
				recipesButton.setForeground(Color.WHITE);
				homeButton.setForeground(Color.BLACK);
				homeButton.setBackground(Color.WHITE);
				

				contentPanel.remove(listForm);
				contentPanel.repaint();
				contentPanel.add(recipeForm);
				contentPanel.repaint();
				contentPanel.revalidate();
				// Clear current ingredients
				Data.ingredients = "";
				String ingredients = listForm.getIngredients();
				Data.ingredients = ingredients;
				
				recipeForm.addRecipes();
				System.out.println(Data.ingredients);
				}
		});
	
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homeButton.setBackground(new Color(46, 139, 87));
				homeButton.setForeground(Color.WHITE);
				recipesButton.setBackground(Color.WHITE);
				recipesButton.setForeground(Color.BLACK);
				
				
				contentPanel.remove(recipeForm);
				contentPanel.repaint();
				contentPanel.add(listForm);
				contentPanel.repaint();
				contentPanel.revalidate();
			}
		});
	}

}
