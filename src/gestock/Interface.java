package gestock;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame{

    public Interface(){
        super("gestock");
        setDefaultCloseOperation(super.EXIT_ON_CLOSE);

        // The main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JPanel menuUp = new JPanel(new FlowLayout());
        mainPanel.add(menuUp, BorderLayout.NORTH);

        JPanel tables = new JPanel(new FlowLayout());
        mainPanel.add(tables, BorderLayout.CENTER);

        JPanel bottomButton = new JPanel(new FlowLayout());
        mainPanel.add(bottomButton, BorderLayout.SOUTH);



    }
}
