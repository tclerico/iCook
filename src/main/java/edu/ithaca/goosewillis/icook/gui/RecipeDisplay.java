/*
Created by Tim Clerico
 */
package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDisplay extends JFrame {

   //JFrame frame = new JFrame();

    public RecipeDisplay(Recipe toDisplay){
        super(toDisplay.getName());
        List<String> instructions = toDisplay.getInstructions();
        List<Ingredient> ingredients= toDisplay.getIngredients();
        List<String> realIngredients = new ArrayList<>();
        for (Ingredient i : ingredients){
            realIngredients.add(i.getName());
        }

        JPanel displayPanel = new JPanel();
        JPanel instructionPanel = initListThing(instructions);
        JPanel itemsNeeded =  initListThing(realIngredients);

        displayPanel.add(instructionPanel);
        displayPanel.add(itemsNeeded);
        this.add(displayPanel);
    }


    public JPanel initListThing(List<String> instructs){
        DefaultListModel<String> dlist = new DefaultListModel<>();
        for (String i : instructs){
            dlist.addElement(i);
        }
        JList list = new JList(dlist);
        list.setVisibleRowCount(15);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollmeup = new JScrollPane(list);
        scrollmeup.setSize(50,100);

        JPanel theSteps = new JPanel();
        theSteps.add(scrollmeup);
        return theSteps;
    }


}
