package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.recipes.Recipe;
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
    JLabel results;


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

        JPanel resultWrapper = new JPanel();
        resultWrapper.setSize(50,150);
        results = new JLabel("No Results Yet");
        resultWrapper.add(results);
        this.add(resultWrapper, BorderLayout.CENTER);
        this.add(initMenu(), BorderLayout.NORTH);
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
                String resString = "";

                for (Recipe r : timeResults){
                    resString += r.getName()+"\n";
                }
                results.setText(resString);

            }else if (selected.equals("Tag")){
                //search by tag
            }else{
                //search by name
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
