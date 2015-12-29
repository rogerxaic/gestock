package gestock.resources.views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Roger on 12/29/2015.
 */
public class GFrame extends JFrame {
    public GFrame(String title) {
        super(title);

        try {
            Image img = ImageIO.read(getClass().getResource("../gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh(boolean relocate) {
        revalidate();
        repaint();
        pack();
        if (relocate) setLocationRelativeTo(null);
        setVisible(true);
    }

    public void refresh() {
        refresh(true);
    }
}
