package edu.ithaca.goosewillis.icook;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import org.junit.jupiter.api.Test;
import edu.ithaca.goosewillis.icook.fridge.Fridge;

import static org.junit.jupiter.api.Assertions.*;

public class CookBookTest {

    @Test
    void getRecommendationsTest(){
        try {
            CookBook cookBook = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbook.json"));
            Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridge.json"));

            assertTrue(!cookBook.getRecipeRecommendations(fridge).contains(cookBook.getSpecificRecipe("Can't Make in User Test")));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
