package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.user.UserSerializer;
import edu.ithaca.goosewillis.icook.util.FileUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;

public class AppStateControllerIT {


    @Test
    public void ControllerTest() throws IOException {
        try {
            CookBook cb = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbookS1.json"));
            User user = new UserSerializer().deserialize(FileUtil.readFromJson("test.json"));
            AppGUI gui = new AppGUI();

            AppStateController controller = new AppStateController(gui);
            controller.setCookBook(cb);
            controller.setUser(user);

            assertEquals(1, controller.cookBook.getRecipes().size());
            assertEquals("test", controller.user.getUsername());

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
