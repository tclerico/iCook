package edu.ithaca.goosewillis.icook.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    AppStateController controller;
    JTextArea username;
    JTextArea password;

    public RegisterPanel(AppStateController controller){
        this.controller = controller;

        JLabel ulabel = new JLabel("Username:");
        JLabel plabel = new JLabel("Password:");
        username = new JTextArea(1,15);
        password = new JTextArea(1,15);

        this.add(ulabel);
        this.add(username);
        this.add(plabel);
        this.add(password);

        JButton register = new JButton("Create Account");
        this.add(register);

    }



    private class RegistrationListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            //Create new account and login user -> redirect to user page


        }
    }



}
