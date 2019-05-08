package edu.ithaca.goosewillis.icook.gui;

import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.user.User;

import javax.swing.*;

public class AppStateController {

    AppGUI gui;
    User user;
    CookBook cookBook;

    public AppStateController(AppGUI gui){
        this.gui = gui;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){return this.user;}

    public void setCookBook(CookBook cb){this.cookBook = cb;}

    public CookBook getCookBook() {
        return cookBook;
    }

    public void changePage(JPanel panel){
        gui.changePanel(panel);
    }

}
