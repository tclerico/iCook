package edu.ithaca.goosewillis.icook.fridge;

import com.google.gson.JsonObject;
import edu.ithaca.goosewillis.icook.util.JsonSerializer;
import edu.ithaca.goosewillis.icook.util.JsonDeserializer;

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
