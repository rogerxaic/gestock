package gestock.window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Interface extends JFrame implements ActionListener {

    public Interface(){
        super("gestock");

        // The main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JPanel menuUp = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
            Image img = ImageIO.read(getClass().getResource("../resources/add64.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            bottomButton.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        bottomButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        bottomButton.setHorizontalTextPosition(SwingConstants.CENTER);

        AbstractButton catalogue = new JButton("Catalogue");
        menuUp.add(catalogue);
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/data110.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            catalogue.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        catalogue.setVerticalTextPosition(SwingConstants.BOTTOM);
        catalogue.setHorizontalTextPosition(SwingConstants.CENTER);


        createMenuBar();
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
        }
        setSize(500, 500);
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
