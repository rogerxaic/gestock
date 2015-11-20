/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock;

import gestock.window.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author rmiretgine
 */
public class Gestock {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {
        }

        EventQueue.invokeLater(MainWindow::new);
    }
}
