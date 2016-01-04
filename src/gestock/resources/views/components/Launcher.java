package gestock.resources.views.components;

import gestock.resources.views.GFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roger on 1/2/2016.
 */
public class Launcher extends GFrame {

    private JLabel text;

    public Launcher() {
        super("");
        setSize(300, 200);
        text = new JLabel("");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(new JLabel("Loading"), BorderLayout.NORTH);
        panel.add(text, BorderLayout.SOUTH);

        setContentPane(panel);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
