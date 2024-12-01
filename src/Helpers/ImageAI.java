package Helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import Components.ListForm;

public class ImageAI {

	public static  String token = "37f81e84220e73aa778e2a73656c93ab860bc71f";
    // Create HttpClient instance
    public static HttpClient client = HttpClient.newHttpClient();
	public static int IdentifyImage(File selectedFile, JPanel parent) {
		int imageId = -1;
	    try {
	        // Compress the image if needed
	        File fileToUpload = compressImage(selectedFile);
	       
	        // Boundary for multipart/form-data
	        String boundary = "----WebKitFormBoundary" + UUID.randomUUID().toString();



	        // Build the multipart body
	        StringBuilder bodyHeader = new StringBuilder();
	        bodyHeader.append("--").append(boundary).append("\r\n");
	        bodyHeader.append("Content-Disposition: form-data; name=\"image\"; filename=\"")
	                  .append(fileToUpload.getName()).append("\"\r\n");
	        bodyHeader.append("Content-Type: application/octet-stream\r\n\r\n");

	        String bodyFooter = "\r\n--" + boundary + "--\r\n";

	        byte[] headerBytes = bodyHeader.toString().getBytes();
	        byte[] footerBytes = bodyFooter.getBytes();
	        byte[] fileBytes = Files.readAllBytes(fileToUpload.toPath());

	        // Combine all parts into one byte array
	        byte[] fullBody = new byte[headerBytes.length + fileBytes.length + footerBytes.length];
	        System.arraycopy(headerBytes, 0, fullBody, 0, headerBytes.length);
	        System.arraycopy(fileBytes, 0, fullBody, headerBytes.length, fileBytes.length);
	        System.arraycopy(footerBytes, 0, fullBody, headerBytes.length + fileBytes.length, footerBytes.length);

	        // Create HttpRequest
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("https://api.logmeal.com/v2/image/segmentation/complete"))
	                .header("Authorization", "Bearer " + token)
	                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
	                .POST(HttpRequest.BodyPublishers.ofByteArray(fullBody))
	                .build();

	        // Send the request and get the response
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


	        if (response.statusCode() == 200) {
	            // Parse the response JSON to get the imageId
	            JSONObject jsonResponse = new JSONObject(response.body());
	            imageId = jsonResponse.getInt("imageId");
	        } else {
	        	JOptionPane.showMessageDialog(parent.getParent().getParent(), "Failed to scan image.","Error", JOptionPane.ERROR_MESSAGE);
	           
	        }
	    } catch (IOException | InterruptedException e) {
	       JOptionPane.showMessageDialog(parent.getParent(), "Connection Error..", "Error Found", JOptionPane.ERROR_MESSAGE);
	    }
	    return imageId;
	}
	public static JSONArray retrieveIngredients(HttpClient client, String token, int imageId, JPanel parent) {
	    JSONArray arr = null;
		try {
	        // Create the JSON body for the second API call
	        JSONObject jsonBody = new JSONObject();
	        jsonBody.put("imageId", imageId);

	        // Create HttpRequest for the second API call
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("https://api.logmeal.com/v2/recipe/ingredients"))
	                .header("Authorization", "Bearer " + token)
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
	                .build();

	        // Send the request and get the response
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        // Handle the response for the second API call
	        
	        JSONObject resJ = new JSONObject(response.body());
	        arr =  resJ.getJSONArray("foodName");
	        if(arr.length() <= 0) {
	        	arr = null;
	        	JOptionPane.showMessageDialog(parent.getParent().getParent(), "No Ingredient Found", "Image Recognition Error", JOptionPane.ERROR_MESSAGE);
	        }
	        if (response.statusCode() != 200) {
	        	JOptionPane.showMessageDialog(parent.getParent(), "Connection error", "Status code error", JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }
	    return arr;
	}
	
	public static File compressImage(File originalFile) throws IOException {
	    BufferedImage image = ImageIO.read(originalFile);
	    File compressedFile = new File("compressed_" + originalFile.getName());

	    // Use ImageIO to write a compressed JPEG
	    FileImageOutputStream output = new FileImageOutputStream(compressedFile);
	    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
	    writer.setOutput(output);

	    ImageWriteParam params = writer.getDefaultWriteParam();
	    if (params.canWriteCompressed()) {
	        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        params.setCompressionQuality(0.75f); // Adjust compression quality (0.0 to 1.0)
	    }

	    writer.write(null, new IIOImage(image, null, null), params);
	    writer.dispose();
	    output.close();

	    return compressedFile;
	}
   
	public static boolean ImageFileChecker(String fName) {
		String[] fileName = fName.split("\\.");
		String fileExt = fileName[fileName.length-1];
		String[] okFileExtensions = new String[] { "jpg", "jpeg"};
		boolean isImage = false;
		for(String extensions : okFileExtensions) {
			if(fileExt.contains(extensions)) {
				isImage = true;
			}

		}
		return isImage;
	}

}
