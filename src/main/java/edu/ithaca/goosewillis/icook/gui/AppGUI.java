package edu.ithaca.goosewillis.icook.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AppGUI extends JPanel{

    public AppGUI(){
        AppStateController controller = new AppStateController(this);
        this.setLayout(new BorderLayout());
        this.add(new LoginPanel(controller), BorderLayout.CENTER);
    }


    public void changePanel(JPanel panel){
        this.removeAll();
        this.add(panel, BorderLayout.CENTER);
        this.doLayout();
        this.update(getGraphics());
        this.revalidate();
        this.repaint();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("iCook");
        frame.setSize(800,400);
        AppGUI gt = new AppGUI();
        frame.add(gt);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}