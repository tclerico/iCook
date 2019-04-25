package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.fridge.FridgeSerializer;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;
import edu.ithaca.goosewillis.icook.util.FileUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        register.addActionListener(new RegistrationListener());
        this.add(register);

    }


    private class RegistrationListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            //Create new account and login user -> redirect to user page
            String uname = username.getText();
            String pass = password.getText();
            try{
                Fridge fridge = new FridgeSerializer().deserialize(FileUtil.readFromJson("defaultFridge.json"));
                User user = new User(uname, pass, fridge, new ArrayList<Ingredient>(), new ArrayList<Recipe>(), new ArrayList<DietType>(), new ArrayList<Recipe>());
                controller.changePage(new UserPanel(user, controller));
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }



}
