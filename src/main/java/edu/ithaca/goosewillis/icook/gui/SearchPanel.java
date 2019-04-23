package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel{

    User user;
    AppStateController controller;
    JComboBox dropDown;
    JTextArea input;


    public SearchPanel(User user, AppStateController controller){
        this.user = user;
        this.controller = controller;

        String [] options = {"Time", "Tag", "Name"};
        dropDown = new JComboBox(options);

        //add listener to button
        JButton search = new JButton("Search");

        JLabel inputLabel = new JLabel("Search:");
        input = new JTextArea(1, 20);

        this.add(inputLabel);
        this.add(input);
        this.add(dropDown);
        this.add(search);

    }




    private class SearchAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            //gets options
            String selected = dropDown.getSelectedItem().toString();
            //gets input to search

            if (selected.equals("Time")){
                //search by time
            }else if (selected.equals("Tag")){
                //search by tag
            }else{
                //search by name
            }


        }
    }



}
