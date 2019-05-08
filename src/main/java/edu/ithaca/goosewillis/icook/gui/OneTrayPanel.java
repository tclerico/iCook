
package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OneTrayPanel extends JPanel {

    AppStateController controller;
    User user;
    JComboBox options;
    JPanel ingredientList = new JPanel();
    DefaultListModel ingList = new DefaultListModel();
    JPanel oneTray = new JPanel();
    JTextArea ingInput;
    HashMap<String, String> oneTrayPairing = new HashMap<>();

    public OneTrayPanel(AppStateController controller, User user){
        this.controller = controller;
        this.user = user;
        this.setLayout(new BorderLayout());
        this.add(initMenu(), BorderLayout.NORTH);
        ingredientList.setSize(20,50);

        String [] oneTrayOptions = {"Beef", "Pork", "Chicken", "Vegetable"};
        options = new JComboBox(oneTrayOptions);

        JButton addToMeal = new JButton("Add");
        addToMeal.addActionListener(new AddItemAction());

        JButton create = new JButton("Create");
        create.addActionListener(new GenerateMealAction());

        JLabel input = new JLabel("Ingredient");
        ingInput = new JTextArea(1,15);
        JLabel type = new JLabel("Food Type");

        JPanel inputWrapper = new JPanel();
        inputWrapper.add(input);
        inputWrapper.add(ingInput);
        inputWrapper.add(type);
        inputWrapper.add(options);
        inputWrapper.add(addToMeal);
        inputWrapper.add(create);
        this.add(inputWrapper, BorderLayout.WEST);


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
        JMenuItem oneTray = new JMenuItem("Account");
        oneTray.addActionListener(new AccountAction());
        options.add(logout);
        options.add(search);
        options.add(oneTray);
        menuBar.add(options);
        return menuBar;
    }

    /**
     * Updates a panel displaying the selected ingredients
     * @param newPanel panel to use in update
     */
    public void refreshIngredients(JPanel newPanel){
        this.remove(ingredientList);
        this.add(newPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Displays the recipe that was generated using input ingredients
     * @param instructions the List of steps for the recipe
     */
    public void displayOneTray(List<String> instructions){
        DefaultListModel<String> steps = new DefaultListModel<>();
        for (String i : instructions){
            steps.addElement(i);
        }
        JList list = new JList(steps);
        list.setVisibleRowCount(10);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroller = new JScrollPane(list);
        scroller.setSize(40,70);
        JPanel results = new JPanel();
        results.add(scroller);

        this.remove(oneTray);
        this.add(results, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();

    }


    // ACTION LISTENERS

    private class AddItemAction implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String ingredient = ingInput.getText();
            String type = options.getSelectedItem().toString();
            oneTrayPairing.put(ingredient,type);
            ingList.addElement(ingredient);

            JList list = new JList(ingList);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setVisibleRowCount(10);
            JScrollPane ingToUse = new JScrollPane(list);
            ingToUse.setSize(20,50);
            JPanel newResPanel = new JPanel();
            newResPanel.setLayout(new BorderLayout());
            newResPanel.add(ingToUse);
            refreshIngredients(newResPanel);
        }
    }

    private class GenerateMealAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            Recipe oneTray = controller.cookBook.generateOneTray(oneTrayPairing);
            List<String> instructs = oneTray.getInstructions();
            displayOneTray(instructs);
        }
    }

    private class SearchAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            //redirect to search panel
            controller.changePage(new SearchPanel(user, controller));
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

    private class AccountAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changePage(new UserPanel(user, controller));
        }
    }


}
