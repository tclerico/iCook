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
    JPanel fridge;

    public UserPanel(User user, AppStateController controller){
        this.user = user;
        this.controller = controller;
        this.controller.setUser(user);
        try {
            CookBook cb = new CookbookSerializer().deserialize(FileUtil.readFromJson("cookbookS1.json"));
            this.controller.setCookBook(cb);
        }catch (Exception e){
            e.printStackTrace();
        }
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

        //TODO ADD A 'Add to fridge' section positioned East
        JPanel addIngPanel = new JPanel();
        newItem = new JTextArea(1,15);
        JButton addButton = new JButton("Add to Fridge");
        addButton.addActionListener(new AddIngredientAction());

        addIngPanel.add(newItem);
        addIngPanel.add(addButton);
        this.add(addIngPanel, BorderLayout.EAST);


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
        Fridge uf = user.getFridge();
        DefaultListModel<String> items = new DefaultListModel<>();
        for (Ingredient i : uf.getIngredients()){
            items.addElement(i.toString());
        }

        return items;
    }

    public void resetFridge(JPanel newFridge){
        this.remove(fridge);
        this.add(newFridge, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private class AddIngredientAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String ingName = newItem.getText();
            DefaultListModel<String> items = readInItems();
            items.addElement(ingName);
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
            //redirect to onetray generation panel
        }
    }


    private class LogoutAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            user = null;
            controller.changePage(new LoginPanel(controller));
        }
    }

}
