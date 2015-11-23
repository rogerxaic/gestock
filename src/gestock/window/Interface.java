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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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


        createMenuBar();
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
