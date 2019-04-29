/*
Created by Tim Clerico
 */
package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserPanel extends JPanel {

    User user;
    AppStateController controller;
    JTextArea newItem;
    JTextArea delItem;
    JPanel fridge;
    List<Ingredient>fridgeItems;
    JPanel favorites;
    JList favlist;
    JPanel favePanel;
    JPanel recommendations;

    public UserPanel(User user, AppStateController controller){
        //Initialize data members
        this.user = user;
        this.controller = controller;
        this.controller.setUser(user);
        try {
            CookBook cb = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbook.json"));
            this.controller.setCookBook(cb);
        }catch (Exception e){
            e.printStackTrace();
        }
        fridgeItems = this.user.getFridge().getIngredients();

        //Set layout for panel
        this.setLayout(new BorderLayout());

        //Create 'wrapper' panels for items on the page
        JPanel title = new JPanel();
        JMenuBar mb = initMenu();
        title.add(new JLabel("User: "+user.getUsername()));
        this.add(mb, BorderLayout.NORTH);
        this.add(title, BorderLayout.WEST);

        //Setup Fridge Display
        JPanel fridgePanel = new JPanel();
        fridge = setupFridge(readInItems());
        JLabel fridgeLabel = new JLabel("Fridge:");
        fridgePanel.add(fridgeLabel);
        fridgePanel.add(fridge);

        //Setup panel for editing items in fridge
        JPanel editFridgePanel = new JPanel();
        editFridgePanel.setLayout(new BorderLayout());
        newItem = new JTextArea(1,15);
        JButton addButton = new JButton("Add to Fridge");
        addButton.addActionListener(new AddIngredientAction());
        delItem = new JTextArea(1,15);
        JButton remove = new JButton("Remove");
        remove.addActionListener(new RemoveIngredientAction());

        JPanel addItems = new JPanel();
        addItems.add(newItem);
        addItems.add(addButton);

        JPanel removeItems = new JPanel();
        removeItems.add(delItem);
        removeItems.add(remove);

        editFridgePanel.add(addItems, BorderLayout.NORTH);
        editFridgePanel.add(removeItems, BorderLayout.CENTER);

        this.add(fridge, BorderLayout.CENTER);
        this.add(editFridgePanel, BorderLayout.EAST);


        //Setup the 'extras' favorite recipes and recommended recipes
        JPanel extras = new JPanel();
        extras.setLayout(new BorderLayout());

        favorites = initFavorites();
        favePanel =  new JPanel();
        JLabel favs = new JLabel("Favorites:");
        JButton removeFavorite = new JButton("Remove Favorite");
        removeFavorite.addActionListener(new RemoveFavorite());

        favePanel.add(favs);
        favePanel.add(removeFavorite);
        favePanel.add(favorites);
        extras.add(favePanel, BorderLayout.EAST);


        recommendations = initRecommendations();
        extras.add(recommendations, BorderLayout.CENTER);

        this.add(extras, BorderLayout.SOUTH);


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
        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(new SearchAction());
        JMenuItem oneTray = new JMenuItem("One Tray");
        oneTray.addActionListener(new OneTrayAction());
        options.add(logout);
        options.add(search);
        options.add(oneTray);
        menuBar.add(options);
        return menuBar;
    }

    /**
     * Reads in recommendations for a user based on the items in their fridge
     * @return A JPanel containing a scrollable pane of recipes, to be added to the 'extras' panel
     */
    public JPanel initRecommendations(){
        List<Recipe> recs = controller.cookBook.recommendRecipes(user.getFridge());
        DefaultListModel<String> recNames = new DefaultListModel<>();
        for (Recipe r : recs){
            recNames.addElement(r.getName());
        }
        JList list = new JList(recNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);
        JScrollPane scrollPane = new JScrollPane(list);
        JPanel recPanel = new JPanel();
        recPanel.add(scrollPane);
        return recPanel;
    }

    /**
     * Takes a list of Ingredient names to create the scrollable view of the users fridge
     * @param items list of strings
     * @return a JPanel containing a scrollable list of ingredients to be used in main panel
     */
    public JPanel setupFridge(DefaultListModel items){
        JPanel fPanel = new JPanel();
        JList fridge = new JList(items);
        fridge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fridge.setVisibleRowCount(8);
        JScrollPane fridgeScroller = new JScrollPane(fridge);
        fridgeScroller.setSize(30,50);
        fPanel.add(fridgeScroller);
        return fPanel;
    }

    /**
     * Reads in users fridge and puts them in a DefaultListModel to be used in a JList
     * @return DefaultListModel of ingredient names to be used in a JList
     */
    public DefaultListModel<String> readInItems(){
        DefaultListModel<String> items = new DefaultListModel<>();
        for (Ingredient i : fridgeItems){
            items.addElement(i.getName());
        }

        return items;
    }

    /**
     * Updates the display for the users fridge for when an item is added or removed
     * @param newFridge takes the new panel that will replace the current one
     */
    public void resetFridge(JPanel newFridge){
        this.remove(fridge);
        fridge = newFridge;
        this.add(fridge, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Reads in users Favorites and creates the panel to display them -> to be added to 'extras' panel
     * @return JPanel containing a scrollable pane with a list of recipes.
     */
    public JPanel initFavorites(){
        List<Recipe> userFaves = user.getFavoriteRecipes();
        //System.out.println(userFaves);
        DefaultListModel<String> favs = new DefaultListModel<>();
        for (Recipe r : userFaves){
            favs.addElement(r.getName());
        }
        favlist = new JList(favs);
        favlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        favlist.setVisibleRowCount(10);
        JScrollPane scoller = new JScrollPane(favlist);
        JPanel favoritePanel = new JPanel();
        favoritePanel.add(scoller);
        return favoritePanel;
    }

    /**
     * Updates the favorites panel for when a recipe is removed
     */
    public void refreshFavorites(){
        favePanel.remove(favorites);
        favorites = initFavorites();
        favePanel.add(favorites);
        favePanel.revalidate();
        favePanel.repaint();
    }


    // ACTION LISTENERS

    private class RemoveIngredientAction implements  ActionListener{
         @Override
        public void actionPerformed(ActionEvent e){
             String toRem = delItem.getText();
             int j = -1;
             for (int i=0; i<fridgeItems.size(); i++){
                 if (fridgeItems.get(i).getName().equals(toRem)){
                     j=i;
                 }
             }
             try{
                 fridgeItems.remove(j);
                 DefaultListModel<String> update = new DefaultListModel<>();
                 for (Ingredient i : fridgeItems){
                     update.addElement(i.getName());
                 }
                 JPanel updatedF = setupFridge(update);
                 resetFridge(updatedF);
             }catch (Exception ex){
                 ex.printStackTrace();
             }

         }
    }

    private class AddIngredientAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String ingName = newItem.getText();
            DefaultListModel<String> items = readInItems();
            items.addElement(ingName);
            fridgeItems.add(new Ingredient(ingName,1,1));
            JPanel nfridge = setupFridge(items);
            resetFridge(nfridge);

        }
    }

    private class RemoveFavorite implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String selected = favlist.getSelectedValue().toString();
            Recipe toRem = controller.cookBook.getSpecificRecipe(selected);
            int j = 0;
            for (int i=0; i<user.getFavoriteRecipes().size(); i++){
                if (user.getFavoriteRecipes().get(i).getName().equals(toRem.getName())){
                    j=i;
                }
            }
            user.getFavoriteRecipes().remove(j);
            refreshFavorites();

        }
    }

    private class RecommendAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Fridge uf = user.getFridge();
            System.out.println("Fridge size: "+uf.getIngredients().size());
            List<Recipe> rec = controller.cookBook.recommendRecipes(uf);
            System.out.println("Num Recommend: "+rec.size());
        }
    }

    private class SearchAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            //redirect to search panel
            controller.changePage(new SearchPanel(user, controller));
        }
    }

    private class OneTrayAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            controller.changePage(new OneTrayPanel(controller, user));
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

}
