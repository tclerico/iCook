/*
Created by Tim Clerico
 */
package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.user.User;

import javax.swing.*;
import java.awt.*;
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
    JPanel results;
    JPanel resWrapper;
    JList resList;


    public SearchPanel(User user, AppStateController controller){
        this.user = user;
        this.controller = controller;
        this.setLayout(new BorderLayout());

        String [] options = {"Time", "Tag", "Name"};
        dropDown = new JComboBox(options);

        //add listener to button
        JButton search = new JButton("Search Recipes");
        search.addActionListener(new SearchAction());

        JLabel inputLabel = new JLabel("Search:");
        input = new JTextArea(1, 20);

        JPanel searchWrapper = new JPanel();

        JButton favorite = new JButton("Favorite Recipe");
        favorite.addActionListener(new FavoriteAction());

        searchWrapper.add(inputLabel);
        searchWrapper.add(input);
        searchWrapper.add(dropDown);
        searchWrapper.add(search);
        searchWrapper.add(favorite);

        this.add(searchWrapper, BorderLayout.WEST);

        results = new JPanel();
        resWrapper = new JPanel();
        resWrapper.setLayout(new BorderLayout());
        resWrapper.add(results, BorderLayout.CENTER);

        this.add(resWrapper, BorderLayout.CENTER);
        this.add(initMenu(), BorderLayout.NORTH);



    }

    /**
     * takes result from one of the different searches and updates the results display
     * @param results
     */
    public void fillSearchResults(Set<Recipe> results){
        DefaultListModel<String> items = new DefaultListModel<>();
        for (Recipe r : results){
            items.addElement(r.getName());
        }
        resList = new JList(items);
        resList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resList.setVisibleRowCount(10);
        JScrollPane searchResults = new JScrollPane(resList);
        refreshSearchResults(searchResults);
    }

    /**
     * Used to display a recipe found when searching for a specific recipe by name
     * @param r Recipe to be displayed
     */
    public void displayRecipe(Recipe r){
        DefaultListModel<String> items = new DefaultListModel<>();
        for (String s : r.getInstructions()){
            items.addElement(s);
        }
        JList instructs = new JList(items);
        instructs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        instructs.setVisibleRowCount(10);
        JScrollPane pane = new JScrollPane(instructs);
        refreshSearchResults(pane);
    }

    /**
     * Updates the results display with new search results
     * @param searchResults the JScrollPane that has the new resultsw
     */
    public void refreshSearchResults(JScrollPane searchResults){
        JPanel newResPan = new JPanel();
        newResPan.setLayout(new BorderLayout());
        //newResPan.setSize(50,150);
        newResPan.add(searchResults, BorderLayout.CENTER);
        resWrapper.remove(this.results);
        this.results = newResPan;
        resWrapper.add(this.results, BorderLayout.CENTER);
        resWrapper.revalidate();
        resWrapper.repaint();
    }

    /**
     * Sets up the menu bar with all possible redirects for this page
     * @return A JMenuBar object to be added to the panel in the 'NORTH' section of layout
     */
    public JMenuBar initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Options");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(new LogoutAction());
        JMenuItem oneTray = new JMenuItem("One Tray");
        oneTray.addActionListener(new OneTrayAction());
        JMenuItem account = new JMenuItem("Account");
        account.addActionListener(new AccountAction());
        options.add(logout);
        options.add(oneTray);
        options.add(account);
        menuBar.add(options);
        return menuBar;
    }

    private class FavoriteAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String selected = resList.getSelectedValue().toString();
            Recipe favoriteRecipe = controller.cookBook.getSpecificRecipe(selected);
            user.favoriteRecipe(favoriteRecipe);
        }
    }

    private class SearchAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            //gets options
            String selected = dropDown.getSelectedItem()+"";

            if (selected.equals("Time")){
                //search by time
                Double time = Double.valueOf(input.getText());
                Set<Recipe> timeResults = controller.cookBook.getRecipesByTime(time);
                fillSearchResults(timeResults);
            }
            else if (selected.equals("Tag")){
                //search by tag
                String type = input.getText();
                for (DietType d : DietType.values()){
                    if (d.getName().equals(type)){
                        Set<Recipe> res = controller.cookBook.getRecipesByTag(d);
                        fillSearchResults(res);
                    }
                }
            }else{
                //search by name
                String name = input.getText();
                Recipe result = controller.cookBook.getSpecificRecipe(name);
                displayRecipe(result);
            }
        }
    }

    private class LogoutAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            user.saveToFile();
            user = null;
            controller.changePage(new LoginPanel(controller));
        }
    }

    private class OneTrayAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            controller.changePage(new OneTrayPanel(controller, user));
        }
    }

    private class AccountAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changePage(new UserPanel(user, controller));
        }
    }



}
