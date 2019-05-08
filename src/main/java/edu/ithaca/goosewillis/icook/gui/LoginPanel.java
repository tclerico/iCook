package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.user.UserSerializer;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    JTextArea username;
    JTextArea password;
    AppStateController controller;

    public LoginPanel(AppStateController appController){
        //Set controller for this 'session'
        controller=appController;

        //Set up login text fields
        JLabel ulabel = new JLabel("Username:");
        JLabel plabel = new JLabel("Password:");
        username = new JTextArea(1,15);
        password = new JTextArea(1,15);

        //Add to panel
        this.add(ulabel);
        this.add(username);
        this.add(plabel);
        this.add(password);

        JButton login = new JButton("Login");
        login.addActionListener(new LoginAction());

        this.add(login);

        JButton register = new JButton("Register");
        register.addActionListener(new RegisterAction());
        this.add(register);

    }


    /*
     * Action listener for the Login button
     * Handles logging a user in and redirects to the user panel
     */
    private class LoginAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String uname = username.getText();
            String pass = password.getText();
            try{
                User user = new UserSerializer().deserialize(FileUtil.readFromJson(uname+".json"));
                if (user.getPassword().equals(pass)){
                    controller.changePage(new UserPanel(user, controller));
                }else{
                    System.out.println("Incorrect");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


    /*
    Redirects to the Registration page
     */
    private class RegisterAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changePage(new RegisterPanel(controller));
        }
    }



}
