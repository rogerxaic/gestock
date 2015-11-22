package gestock;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Interface extends JFrame{

    public Interface(){
        super("gestock");
        setDefaultCloseOperation(super.EXIT_ON_CLOSE);

        // The main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setSize(500,500);

        JPanel menuUp = new JPanel(new FlowLayout());
        mainPanel.add(menuUp, BorderLayout.NORTH);
        menuUp.setBackground(Color.white);

        JPanel tables = new JPanel(new FlowLayout());
        mainPanel.add(tables, BorderLayout.CENTER);
        tables.setBackground(Color.white);

        JPanel bottomButtonPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        bottomButtonPanel.setBackground(Color.white);

        JPanel space = new JPanel();
        bottomButtonPanel.add(space, BorderLayout.SOUTH);
        space.setBackground(Color.white);

        AbstractButton bottomButton = new JButton("Je viens d'acheter");
        bottomButton.setBackground(Color.white);
        bottomButtonPanel.add(bottomButton, BorderLayout.WEST);
        try {
            Image img = ImageIO.read(getClass().getResource("resources/plus.png"));
            bottomButton.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        bottomButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        bottomButton.setHorizontalTextPosition(SwingConstants.CENTER);



        setVisible(true);


    }
}
