package edu.ithaca.goosewillis.icook.APIQueries;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.p.spoonacularrecipefoodnutritionv1.SpoonacularAPIClient;
import com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController;


public class IngredientQueries {

    public static JsonObject getJsonFromUrl(String s) throws IOException {
        HttpURLConnection urlConnection = null;
        String json = "";
        try {
            URL url = new URL(s);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(reader).getAsJsonObject();
            return root;
        } catch (IOException e) {
            throw new IOException("There was an error reading from the url. Check your parameters, and that the endpoint is online.");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static ObjectMapper mapper = new ObjectMapper()
    {
        private static final long serialVersionUID = -174113593500315394L;
        {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    };

    // Configuration parameters
    String xMashapeKey = "xMashapeKey"; // The Mashape application you want to use for this session.
    public SpoonacularAPIClient client = new SpoonacularAPIClient(xMashapeKey);


    public void spoon(){



        APIController client = client.getClient();
    }

    public static void main(String[] args) {
        try {
            //can use this one to front load ingredients
            String str = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
            JsonObject test = getJsonFromUrl(str);
            System.out.println(test);
            System.out.println(test.get("meals"));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }



}
