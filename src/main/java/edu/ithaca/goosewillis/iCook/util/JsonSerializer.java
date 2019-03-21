package edu.ithaca.goosewillis.icook.util;

import com.google.gson.JsonObject;

public interface JsonSerializer<T> {

    JsonObject serialize(T toSerialize);

}
