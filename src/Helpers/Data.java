package Helpers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class Data {
	public static String token = "1ba9a2e6d8ce4a47a01f1e6507270924";
	public static boolean fillIngredients = false;
	public static int limit = 10;
	public static int offset = 0;
	public static String ingredients = "";
	public static String url;
	public static String getData() {
		url = "https://api.spoonacular.com/recipes/findByIngredients?"
				+ "ingredients=" + ingredients
				+ "&number=" + limit
				+"&offset=" + (offset*limit)
				+ "&fillIngredients=" + fillIngredients
				+"&apiKey="+token;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return response.body();
	}
	

}
