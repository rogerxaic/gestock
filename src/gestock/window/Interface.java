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


        JPanel menuUp = new JPanel();
        menuUp.setLayout(new BoxLayout(menuUp, BoxLayout.X_AXIS));
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
        bottomButton.setBackground(Color.WHITE);
        bottomButton.setContentAreaFilled(false);
        bottomButton.setOpaque(true);
        bottomButton.setPressedIcon(new ImageIcon());
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
        catalogue.setBackground(Color.WHITE);
        catalogue.setContentAreaFilled(false);
        catalogue.setOpaque(true);
        catalogue.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/data110.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            catalogue.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        catalogue.setVerticalTextPosition(SwingConstants.BOTTOM);
        catalogue.setHorizontalTextPosition(SwingConstants.CENTER);


        AbstractButton gardeManger = new JButton("Garde-manger");
        menuUp.add(gardeManger);
        gardeManger.setBackground(Color.WHITE);
        gardeManger.setContentAreaFilled(false);
        gardeManger.setOpaque(true);
        gardeManger.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/cutlery23.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            gardeManger.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        gardeManger.setVerticalTextPosition(SwingConstants.BOTTOM);
        gardeManger.setHorizontalTextPosition(SwingConstants.CENTER);


        AbstractButton listeAchats = new JButton("Liste achats");
        listeAchats.setBackground(Color.WHITE);
        listeAchats.setContentAreaFilled(false);
        listeAchats.setOpaque(true);
        listeAchats.setPressedIcon(new ImageIcon());
        menuUp.add(listeAchats);
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/shopping122.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            listeAchats.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        listeAchats.setVerticalTextPosition(SwingConstants.BOTTOM);
        listeAchats.setHorizontalTextPosition(SwingConstants.CENTER);

        AbstractButton chercher = new JButton("Chercher");
        chercher.setBackground(Color.WHITE);
        chercher.setContentAreaFilled(false);
        chercher.setOpaque(true);
        chercher.setPressedIcon(new ImageIcon());
        menuUp.add(chercher);
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/search100.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            chercher.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        chercher.setVerticalTextPosition(SwingConstants.BOTTOM);
        chercher.setHorizontalTextPosition(SwingConstants.CENTER);

        menuUp.add(Box.createHorizontalGlue());

        AbstractButton parametres = new JButton("Parametres");
        parametres.setBackground(Color.WHITE);
        parametres.setContentAreaFilled(false);
        parametres.setOpaque(true);
        parametres.setPressedIcon(new ImageIcon());
        menuUp.add(parametres);
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/network60.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            parametres.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        parametres.setVerticalTextPosition(SwingConstants.BOTTOM);
        parametres.setHorizontalTextPosition(SwingConstants.CENTER);

        JPanel log = new JPanel();
        log.setLayout(new BoxLayout(log, BoxLayout.Y_AXIS));
        menuUp.add(log);
        log.setBackground(Color.white);

        JLabel user = new JLabel("User");
        log.add(user);


        AbstractButton logout = new JButton("Logout");
        logout.setBackground(Color.WHITE);
        logout.setContentAreaFilled(false);
        logout.setOpaque(true);
        logout.setPressedIcon(new ImageIcon());
        logout.setBorderPainted(true);
        log.add(logout);

        try {
            Image img = ImageIO.read(getClass().getResource("../resources/network60.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            logout.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
        }
        logout.setVerticalTextPosition(SwingConstants.CENTER);
        logout.setHorizontalTextPosition(SwingConstants.RIGHT);


        createMenuBar();
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
        }
        setSize(700, 700);
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
