package edu.ithaca.goosewillis.icook;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.DoubleBinaryOperator;

import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.util.FileUtil;

public class CLI {

    //TODO CHECK ALL USER INPUT -> VALID INPUT ONLY | currently assuming all input is ok

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        String action = "";
        System.out.println("Welcome to iCook\n" +
                "type 'help' for available commands");

        try{
            CookBook cookBook = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbook.json"));
            Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridge.json"));


            while(!action.equals("quit")){
                action = reader.nextLine();

                if (action.equals("add ingredient")){
                    System.out.println("Please enter the ingredient information ( name and amount separated by a comma )");
                    action = reader.nextLine();
                    String [] info = action.split(", ");

                    String name = info[0];
                    //TODO Check user input here
                    double amount = Double.parseDouble(info[1]);
                    Ingredient toAdd = new Ingredient(name, amount);
                    fridge.addIngredient(toAdd);

                }
                else if(action.equals("show ingredients")){
                    for(Ingredient i : fridge.getIngredients()){ System.out.println(i.toString()); }
                }
                else if (action.equals("generate meal")){
                    System.out.println("Please enter the ingredients you want to use separated by commas");
                    action = reader.nextLine();
                    String [] ings = action.split(", ");
                    ArrayList<Ingredient> toUse = new ArrayList<>();
                    for (int i=0; i< ings.length; i++){
                        toUse.add(fridge.searchIngredient(ings[i]));
                    }
                    Recipe oneTray = cookBook.generateOneTray(toUse);
                    cookBook.addRecipe(oneTray.getName(), oneTray);
                    System.out.println(oneTray.display());

                }
                else if(action.equals("help")){
                    System.out.println("Commands\n 'add ingredient' to add an ingredient to your fridge\n" +
                            "'show ingredients' to show all ingredients in your fridge\n" +
                            "'generate meal' to generate a onetray meal");
                }


            }
            fridge.saveFridgeToFile();
            cookBook.saveToFile();


        }
        catch (Exception e){
            e.printStackTrace();
        }



    }




}
