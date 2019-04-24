package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SearchPanel extends JPanel{

    User user;
    AppStateController controller;
    JComboBox dropDown;
    JTextArea input;
    JLabel results;


    public SearchPanel(User user, AppStateController controller){
        this.user = user;
        this.controller = controller;

        String [] options = {"Time", "Tag", "Name"};
        dropDown = new JComboBox(options);

        //add listener to button
        JButton search = new JButton("Search");
        search.addActionListener(new SearchAction());

        JLabel inputLabel = new JLabel("Search:");
        input = new JTextArea(1, 20);

        this.add(inputLabel);
        this.add(input);
        this.add(dropDown);
        this.add(search);

        JPanel resultWrapper = new JPanel();
        resultWrapper.setSize(50,150);
        results = new JLabel("No Results Yet");
        resultWrapper.add(results);
        this.add(resultWrapper);
    }



    private class SearchAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            //gets options
            String selected = dropDown.getSelectedItem()+"";
            //results.setText(selected);
            //updateResultsPanel(new JLabel(selected));

            //gets input to search

            if (selected.equals("Time")){
                //search by time
                Double time = Double.valueOf(input.getText());
                System.out.println(time);
                Set<Recipe> timeResults = controller.cookBook.getRecipesByTime(time);
                System.out.println(timeResults.size());
                String resString = "";

                for (Recipe r : timeResults){
                    resString += r.getName()+"\n";
                }
                System.out.println(resString);
                results.setText(resString);

            }else if (selected.equals("Tag")){
                //search by tag
            }else{
                //search by name
            }



        }
    }

//    public void updateResultsPanel(JLabel newRes){
//        //replaces current results panel with new output
//        JPanel newPanel = new JPanel();
//        newPanel.setSize(50,150);
//        newPanel.add(newRes);
//        this.remove(results);
//        this.add(newPanel);
//    }



}
