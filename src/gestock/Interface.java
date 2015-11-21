package gestock;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame{

    public Interface(){
        super("gestock");
        setDefaultCloseOperation(super.EXIT_ON_CLOSE);

        // The main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        setContentPane(mainPanel);



    }
}
