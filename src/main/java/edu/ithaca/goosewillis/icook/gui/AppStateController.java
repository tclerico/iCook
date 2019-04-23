package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.user.User;

import javax.swing.*;

public class AppStateController {

    AppGUI gui;
    User user;

    public AppStateController(AppGUI gui){
        this.gui = gui;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void changePage(JPanel panel){
        gui.changePanel(panel);
    }

}
