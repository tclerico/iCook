package edu.ithaca.goosewillis.icook;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTest {

    public static JsonObject getJsonFromUrl(String s) throws IOException{
        HttpURLConnection urlConnection = null;
        String json = "";
        try{
            URL url = new URL(s);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(bufferedReader).getAsJsonObject();
            return root;
        }catch(IOException e){
            throw new IOException("There was an error reading from the url. Check your parameters, and that the endpoint is online.");
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }
    public static void main(String[] args) {
        try {
            //can use this one to front load ingredients
            String str = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
            JsonObject test = getJsonFromUrl(str);
            System.out.println(test);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
