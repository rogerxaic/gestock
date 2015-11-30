package gestock.window;

import gestock.Gestock;
import gestock.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class Interface extends JFrame implements ActionListener {

    private User user;

    private JLabel userName;

    public Interface(Gestock app) {
        super("gestock");
        setSize(700, 700);

        this.user = app.getUser();

        // The main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);


        JPanel menuUp = new JPanel();
        menuUp.setLayout(new BoxLayout(menuUp, BoxLayout.X_AXIS));
        mainPanel.add(menuUp, BorderLayout.NORTH);
        menuUp.setBackground(Color.white);


        JPanel tables = new JPanel();
        tables.setLayout(new BoxLayout(tables, BoxLayout.X_AXIS));
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        parametres.setVerticalTextPosition(SwingConstants.BOTTOM);
        parametres.setHorizontalTextPosition(SwingConstants.CENTER);
        parametres.addActionListener((ActionEvent ae) -> {
            new SettingsWindow(this, user);
            setEnabled(false);
        });

        JPanel log = new JPanel();
        log.setLayout(new BoxLayout(log, BoxLayout.Y_AXIS));
        menuUp.add(log);
        log.setBackground(Color.white);


        userName = new JLabel(user.getName());
        userName.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 30, 5, 0));
        log.add(userName);
        userName.setAlignmentX(SwingConstants.CENTER);


        AbstractButton logout = new JButton("Logout");
        logout.setBackground(Color.WHITE);
        logout.setContentAreaFilled(false);
        logout.setOpaque(true);
        logout.setPressedIcon(new ImageIcon());
        logout.setBorderPainted(true);
        log.add(logout);

        try {
            Image img = ImageIO.read(getClass().getResource("../resources/logout20.png"));
            Image newImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            logout.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        logout.setVerticalTextPosition(SwingConstants.CENTER);
        logout.setHorizontalTextPosition(SwingConstants.RIGHT);

        tables.add(Box.createRigidArea(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/20),0)));

        MyRenderer renderer = new MyRenderer();

        JTable perim = new JTable(8,3);
        tables.add(perim);
        perim.setEnabled(false);
        perim.setValueAt(this.getBounds().getWidth(),1,1);
        perim.setGridColor(Color.black);
        perim.setDefaultRenderer(Object.class, renderer);
        perim.setShowHorizontalLines(false);
        perim.setRowHeight(25);
        perim.getColumnModel().getColumn(1).setPreferredWidth(30);


        tables.add(Box.createRigidArea(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/20),0)));

        JTable peu = new JTable(8,3);
        tables.add(peu);
        peu.setEnabled(false);
        peu.setShowHorizontalLines(false);
        peu.setGridColor(Color.black);
        peu.setDefaultRenderer(Object.class, renderer);
        peu.setRowHeight(25);
        peu.getColumnModel().getColumn(1).setPreferredWidth(30);

        tables.add(Box.createRigidArea(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/20),0)));

        createMenuBar();
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                app.saveProperties();
                System.exit(0);
            }
        });
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
        licences.addActionListener((ActionEvent e) -> new LicensesWindow());

        fileMenu.add(exit);
        helpMenu.add(licences);

        menubar.add(fileMenu);
        menubar.add(helpMenu);

        setJMenuBar(menubar);
    }

    public void refresh() {
        this.userName.setText(user.getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
    }
}
