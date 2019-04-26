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


    public SearchPanel(User user, AppStateController controller){
        this.user = user;
        this.controller = controller;
        this.setLayout(new BorderLayout());

        String [] options = {"Time", "Tag", "Name"};
        dropDown = new JComboBox(options);

        //add listener to button
        JButton search = new JButton("Search");
        search.addActionListener(new SearchAction());

        JLabel inputLabel = new JLabel("Search:");
        input = new JTextArea(1, 20);

        JPanel searchWrapper = new JPanel();

        searchWrapper.add(inputLabel);
        searchWrapper.add(input);
        searchWrapper.add(dropDown);
        searchWrapper.add(search);

        this.add(searchWrapper, BorderLayout.WEST);

        results = new JPanel();
        //results.setSize(50,150);

        this.add(results, BorderLayout.CENTER);
        this.add(initMenu(), BorderLayout.NORTH);
    }


    public void fillSearchResults(Set<Recipe> results){
        DefaultListModel<String> items = new DefaultListModel<>();
        for (Recipe r : results){
            items.addElement(r.getName());
        }
        JList resList = new JList(items);
        resList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        resList.setVisibleRowCount(10);
        JScrollPane searchResults = new JScrollPane(resList);
        refreshSearchResults(searchResults);
    }

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

    public void refreshSearchResults(JScrollPane searchResults){
        JPanel newResPan = new JPanel();
        newResPan.setLayout(new BorderLayout());
        //newResPan.setSize(50,150);
        newResPan.add(searchResults, BorderLayout.CENTER);
        this.remove(this.results);
        this.add(newResPan, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
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
                Set<Recipe> timeResults = controller.cookBook.getRecipesByTime(time);
                fillSearchResults(timeResults);

            }else if (selected.equals("Tag")){
                //search by tag
                String type = input.getText();
                //TODO Convert string to diet type
                for (DietType d : DietType.values()){
                    if (d.getName().equals(type)){
                        Set<Recipe> res = controller.cookBook.getRecipesByTag(d);
                        fillSearchResults(res);
                    }
                }


            }else{
                //TODO THIS WORKS, But formatting needs to be fixed
                //search by name
                String name = input.getText();
                Recipe result = controller.cookBook.getSpecificRecipe(name);
                displayRecipe(result);
            }



        }
    }


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
            //redirect to onetray generation panel
        }
    }

    private class AccountAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changePage(new UserPanel(user, controller));
        }
    }



}
