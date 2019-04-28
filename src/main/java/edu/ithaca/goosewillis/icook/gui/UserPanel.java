package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.cookbook.CookbookSerializer;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
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

    public UserPanel(User user, AppStateController controller){
        //TODO Add 'recommend' to user panel??
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
        this.setLayout(new BorderLayout());
        JPanel fridgePanel = new JPanel();
        JPanel title = new JPanel();

        JMenuBar mb = initMenu();
        title.add(new JLabel("User: "+user.getUsername()));

        //Setup Fridge Display
        fridge = setupFridge(readInItems());

        this.add(mb, BorderLayout.NORTH);
        this.add(title, BorderLayout.WEST);
        this.add(fridge, BorderLayout.CENTER);

        JPanel editFridgePanel = new JPanel();
        editFridgePanel.setLayout(new BorderLayout());
        newItem = new JTextArea(1,15);
        JButton addButton = new JButton("Add to Fridge");
        addButton.addActionListener(new AddIngredientAction());
        //TODO add a 'remove item' for fridge?
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
        this.add(editFridgePanel, BorderLayout.EAST);


    }


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


    public JPanel setupFridge(DefaultListModel items){
        JPanel fPanel = new JPanel();
        JList fridge = new JList(items);
        fridge.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fridge.setVisibleRowCount(8);
        JScrollPane fridgeScroller = new JScrollPane(fridge);
        fridgeScroller.setSize(30,50);
        fPanel.add(fridgeScroller);
        return fPanel;
    }


    public DefaultListModel<String> readInItems(){
        DefaultListModel<String> items = new DefaultListModel<>();
        for (Ingredient i : fridgeItems){
            items.addElement(i.getName());
        }

        return items;
    }

    public void resetFridge(JPanel newFridge){
        this.remove(fridge);
        fridge = newFridge;
        this.add(fridge, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


    private class RemoveIngredientAction implements  ActionListener{
         @Override
        public void actionPerformed(ActionEvent e){
             String toRem = delItem.getText();
             int j = -1;
             for (int i=0; i<fridgeItems.size(); i++){
                 System.out.println(fridgeItems.get(i).getName());
                 if (fridgeItems.get(i).getName().equals(toRem)){
                     System.out.println("Found");
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
        //TODO LOGOUT NEEDS TO SAVE ALL THINGS TO FILES / DB
        @Override
        public void actionPerformed(ActionEvent e){
            user.saveToFile();
            user = null;
            controller.changePage(new LoginPanel(controller));

        }
    }

}
