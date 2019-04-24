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
        //this.setLayout(new FlowLayout());
        JPanel menuPanel = new JPanel();
        JPanel fridgePanel = new JPanel();
        JPanel title = new JPanel();
        menuPanel.setBounds(350,5,700,10);
        fridgePanel.setBounds(350, 100, 500,100);
        title.setBounds(350,25,500,10);



        JMenuBar mb = initMenu();
        menuPanel.add(mb);
        title.add(new JLabel("User: "+user.getUsername()));

        JList fridgeItems = initFridge();
        JScrollPane listScroller = new JScrollPane(fridgeItems);
        listScroller.setSize(new Dimension(15,20));
        fridgePanel.add(listScroller);
        this.add(menuPanel);
        this.add(title);
        this.add(fridgePanel);


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



    public JList initFridge(){
        Fridge uf = user.getFridge();
        DefaultListModel<String> items = new DefaultListModel<>();
        for (Ingredient i : uf.getIngredients()){
            items.addElement(i.toString());
        }
        JList list = new JList(items);

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        return list;
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
