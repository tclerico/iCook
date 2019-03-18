package edu.ithaca.icook.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class FileUtil {

    public boolean createFile(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) {
            return false;
        }
        return file.createNewFile();
    }

    public boolean fileExists(String name) {
        File file = new File(name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public void deleteFile(String name) {
        if (fileExists(name)) {
            File file = new File(name);
            file.delete();
        }
    }

    public JsonObject readFromJson(String name) throws Exception {
        if (!fileExists(name)) throw new Exception("File to read from does not exist");
        File file = new File(name);
        Reader reader = new InputStreamReader(new FileInputStream(file));
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(reader).getAsJsonObject();
        return root;
    }

}
