package edu.ithaca.goosewillis.icook;
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
        System.out.println("Welcome to iCook");

        //TODO LOAD ALL INFO HERE
        try{
            CookBook cookBook = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbook.json"));
            Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("fridge.json"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        while(!action.equals("quit")){
            action = reader.nextLine();

            if (action.equals("add ingredient")){
                System.out.println("Please enter the ingredient information ( name and amount separated by a comma )");
                action = reader.nextLine();
                String [] info = action.split(",");
                for (int i=0; i<info.length; i++){
                    info[i] = info[i].trim();
                }
                String name = info[0];
                //TODO Check user input here
                double amount = Double.parseDouble(info[1]);
                Ingredient toAdd = new Ingredient(name, amount);

                //Add to fridge


            }else if (action.equals("generate meal")){
                //one tray generation
            }


        }

    }




}
