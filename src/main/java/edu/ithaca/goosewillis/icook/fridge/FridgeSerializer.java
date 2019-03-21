package edu.ithaca.goosewillis.icook.fridge;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;

public class FridgeSerializer implements JsonSerializer<Fridge>, JsonDeserializer<Fridge> {

    @Override
    public Fridge deserialize(JsonObject root) {
        return null;
    }

    @Override
    public JsonObject serialize(Fridge toSerialize) {
        return null;
    }

}
