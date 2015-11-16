/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Roger
 */
public class MainWindow extends JFrame implements ActionListener {

    public MainWindow() {
        super("Gestock");
        createMenuBar();

        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem licences = new JMenuItem("Licences");
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        licences.addActionListener((ActionEvent e) -> {
            new LicensesWindow();
        });

        fileMenu.add(exit);
        helpMenu.add(licences);

        menubar.add(fileMenu);
        menubar.add(helpMenu);

        setJMenuBar(menubar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
    }

}
