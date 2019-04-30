package edu.ithaca.goosewillis.icook;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.function.DoubleBinaryOperator;

import edu.ithaca.goosewillis.icook.commands.Command;
import edu.ithaca.goosewillis.icook.commands.CommandManager;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.user.UserSerializer;
import edu.ithaca.goosewillis.icook.util.FileUtil;

public class CLI {
    //TODO CHECK ALL USER INPUT -> VALID INPUT ONLY | currently assuming all input is ok

    public static State cliState = State.RUNNING;

    public static CommandManager manager;

    public static User createUser(String username, String password) throws Exception{
        Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("defaultFridge.json"));
        User user = new User(username, password, fridge, new ArrayList<Ingredient>(), new ArrayList<Recipe>(), new ArrayList<DietType>(), new ArrayList<Recipe>());
        return user;
    }

    public static User login(String username, String password){
        try{
            User user = new UserSerializer().deserialize(FileUtil.readFromJson(username+".json"));
            if (user.getPassword().equals(password)){
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("User doesn't exist");
        return null;
    }

    public static void logout(User user){
        System.out.println("being logged out");
        user.saveToFile();
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        String action = "";
        System.out.println("Welcome to iCook\n" +
                "type 'help' for available commands");

        try{
            CookBook cookBook = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbookS1.json"));
            //Read in from file to check against log-ins
            User user = null;

            //Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridge.json"));
            manager = new CommandManager(cookBook);
            manager.loadCommands();

            while(cliState == State.RUNNING){
                action = reader.nextLine();
                String inputCommand = action.split(" ")[0];
                Command nextCommand = manager.getCommand(inputCommand);
                if (nextCommand == null) {
                    //Doesnt exist keep prompting
                    System.out.println("That command doesn't exist!");
                } else {
                    nextCommand.execute(action);
                }
            }
            //fridge.saveFridgeToFile();
            cookBook.saveToFile();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
