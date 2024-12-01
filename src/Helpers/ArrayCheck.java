package Helpers;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import org.json.JSONArray;

public class ArrayCheck {

	public static boolean checkDuplicates(String value, DefaultListModel<String> arr) {
		boolean res = false;
		res = arr.contains(value);
        	
		return res;
		
	}
}
