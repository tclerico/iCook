package edu.ithaca.icook.util;

import com.google.gson.JsonObject;

public interface JsonDeserializer<T> {

    T deserialized(JsonObject root);

}
