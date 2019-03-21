package edu.ithaca.goosewillis.icook.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class FileUtil {

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static File createFile(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) {
            return null;
        }
        return file;
    }

    public static boolean fileExists(String name) {
        File file = new File(name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static void deleteFile(String name) {
        if (fileExists(name)) {
            File file = new File(name);
            file.delete();
        }
    }

    public static JsonObject readFromJson(String name) throws Exception {
        if (!fileExists(name)) throw new Exception("File to read from does not exist");
        File file = new File(name);
        Reader reader = new InputStreamReader(new FileInputStream(file));
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(reader).getAsJsonObject();
        return root;
    }

}
