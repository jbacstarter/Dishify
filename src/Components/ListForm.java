package Components;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONObject;

import Helpers.ArrayCheck;
import Helpers.Data;
import Helpers.ImageAI;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.JLabel;

import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class ListForm extends JPanel {

	private static final long serialVersionUID = 1L;
	public DefaultListModel<String> listModel = new DefaultListModel<String>();

	/**
	 * Create the panel.
	 */
	public ListForm() {
		setLayout(null);
		
		JPanel ListPanel = new JPanel();
		ListPanel.setBackground(new Color(255, 255, 255));
		ListPanel.setBounds(0, 0, 600, 586);
		add(ListPanel);
		ListPanel.setLayout(null);
		
		JPanel designPanel1 = new Header();
		designPanel1.setBounds(0, 0, 600, 110);
		ListPanel.add(designPanel1);
		//designPanel1.setBackground(new Color(46, 139, 87));
		//designPanel1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("D i s h i f y");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("FZShuTi", Font.BOLD | Font.ITALIC, 75));
		lblNewLabel.setBounds(112, 11, 382, 88);
		designPanel1.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(null);
		scrollPane.setBounds(159, 121, 275, 367);
		scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());;
		ListPanel.add(scrollPane);
		
		
		JList list = new JList(listModel);

		list.setBorder(new LineBorder(Color.BLACK, 2, true));
		list.setFont(new Font("STKaiti", Font.PLAIN, 23));
		scrollPane.setViewportView(list);
		JButton uploadButton = new JButton("Upload image");
		uploadButton.setFocusPainted(false);
		uploadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uploadButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		uploadButton.setBackground(new Color(255, 255, 255));
		uploadButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				jf.setFileFilter(new FileNameExtensionFilter("JPEG file", new String[] {"jpg", "jpeg"}));
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = jf.showOpenDialog(ListPanel.getParent().getParent());
				boolean isImage = ImageAI.ImageFileChecker(jf.getSelectedFile().getName());
				if(i == JFileChooser.APPROVE_OPTION && isImage) {
						
						Thread.startVirtualThread((new Runnable() {
								
								@Override
								public void run() {
									try {
										int imageId = ImageAI.IdentifyImage(ImageAI.compressImage(jf.getSelectedFile()) , ListPanel);
										JSONArray arr = ImageAI.retrieveIngredients(ImageAI.client, ImageAI.token, imageId, ListPanel);
										
										arr.forEach(el ->{
											if(!(ArrayCheck.checkDuplicates(((String)el).toUpperCase(), listModel))) {
												listModel.addElement(((String)el).toUpperCase());
												list.repaint();
											}
										});
									} catch (IOException e) {
										
									}
										
									
								}
							}));
					
				}
			}
		});
		uploadButton.setBounds(235, 496, 117, 56);
		ListPanel.add(uploadButton);
		
		JButton addButton = new JButton("+");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = JOptionPane.showInputDialog(getParent().getParent(), "Enter ingredient: ", "",JOptionPane.PLAIN_MESSAGE);
				if(!(ArrayCheck.checkDuplicates(value.toUpperCase(), listModel))) {
					listModel.addElement(value.toUpperCase());
				}else {
					JOptionPane.showMessageDialog(getParent().getParent(), "Ingredient already exists.", "Input error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				addButton.setForeground(Color.BLACK);
			}
		});
		addButton.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				addButton.setForeground(Color.GREEN);
			}
		});
		
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
		addButton.setContentAreaFilled(false);
		addButton.setBorderPainted(false);
		addButton.setFocusPainted(false);
		addButton.setBounds(101, 127, 55, 24);
		ListPanel.add(addButton);
		
		JButton removeButton = new JButton("-");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getLeadSelectionIndex();
				listModel.remove(index);
			}
		});
		removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeButton.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				removeButton.setForeground(Color.RED);
			}
		});
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				removeButton.setForeground(Color.BLACK);
			}
		});
		
		removeButton.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
		removeButton.setFocusPainted(false);
		removeButton.setContentAreaFilled(false);
		removeButton.setBorderPainted(false);
		removeButton.setBounds(101, 173, 55, 24);
		ListPanel.add(removeButton);

	}
	
	public String getIngredients() {
		 String str = "";
		 if(listModel.size() !=0) {
			 
			 for(int i = 0; i< listModel.size(); i++) {
				 String ingredient = listModel.get(i).toLowerCase().replace(" ", "");
				 System.out.println(ingredient);
				if(i ==0) {
					str = str.concat(ingredient);
				}else {
					str= str.concat(",+" + ingredient);
				}
			 }
		 }else {
			 str = "apples,+flour,+sugar";
		 }
		 return str;
	}

}
