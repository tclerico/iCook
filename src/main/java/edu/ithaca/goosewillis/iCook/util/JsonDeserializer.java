package edu.ithaca.goosewillis.icook.util;

import com.google.gson.JsonObject;

public interface JsonDeserializer<T> {

    T deserialize(JsonObject root);

}
