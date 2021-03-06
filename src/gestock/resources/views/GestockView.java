package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.*;
import gestock.entity.BaseProduct;
import gestock.entity.ShoppingList;
import gestock.entity.User;
import gestock.resources.views.components.MyRenderer;
import gestock.resources.views.components.TableModel;
import gestock.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GestockView extends GFrame {

    private Gestock model;
    private User user;

    private JLabel userName;

    private AbstractButton bottomButton;
    private AbstractButton parametres;
    private AbstractButton chercher;
    private AbstractButton catalogue;
    private AbstractButton gardeManger;
    private AbstractButton listeAchats;
    private AbstractButton consommer;
    private AbstractButton loginout;
    private JButton addToShoppingList;

    private JPanel peuPanel;
    private JPanel perimPanel;

    private JTable perim;
    private String[] expiryColumns;
    private JTable peu;
    private String[] fewColumns;

    public GestockView(Gestock app, GestockController controller, List<BaseProduct> expireSoonProducts, List<BaseProduct> fewProducts) {
        super(app.messages.getString("app.title"));
        setSize(1120, 750);

        this.model = app;
        this.user = model.getUser();
        user.addObserver((o, arg) -> {
            this.userName.setText(user.getName());
        });

        expiryColumns = new String[]{model.messages.getString("app.table.expiresoon.name"),
                model.messages.getString("app.table.expiresoon.quantity"),
                model.messages.getString("app.table.expiresoon.date")};
        fewColumns = new String[]{model.messages.getString("app.table.fewproducts.name"),
                model.messages.getString("app.table.fewproducts.quantity")};

        // The main panel
        JPanel mainPanel = new JPanel(new BorderLayout(5, 30));
        setContentPane(mainPanel);
        mainPanel.setBackground(Color.WHITE);

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


        bottomButton = new JButton(model.messages.getString("justbought.title"));
        bottomButton.setBackground(Color.white);
        bottomButtonPanel.add(bottomButton, BorderLayout.WEST);
        bottomButton.setBackground(Color.WHITE);
        bottomButton.setContentAreaFilled(false);
        bottomButton.setOpaque(true);
        bottomButton.setFont(new Font("Arial", Font.BOLD, 12));
        bottomButton.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/add64.png"));
            //Image img = ImageIO.read(getClass().getResource("gestock.resources.add64.png"));

            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            bottomButton.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        bottomButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        bottomButton.setHorizontalTextPosition(SwingConstants.CENTER);
        bottomButton.addActionListener((ActionEvent ae) -> new JustBoughtController(app));


        catalogue = new JButton(model.messages.getString("catalogue.title"));
        menuUp.add(catalogue);
        catalogue.setBackground(Color.WHITE);
        catalogue.setContentAreaFilled(false);
        catalogue.setOpaque(true);
        catalogue.setFont(new Font("Arial", Font.BOLD, 12));
        catalogue.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/data110.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            catalogue.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catalogue.setVerticalTextPosition(SwingConstants.BOTTOM);
        catalogue.setHorizontalTextPosition(SwingConstants.CENTER);
        catalogue.addActionListener((ActionEvent ae) -> new CatalogueController(app));


        gardeManger = new JButton(model.messages.getString("pantry.title"));
        menuUp.add(gardeManger);
        gardeManger.setBackground(Color.WHITE);
        gardeManger.setContentAreaFilled(false);
        gardeManger.setOpaque(true);
        gardeManger.setFont(new Font("Arial", Font.BOLD, 12));
        gardeManger.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/cutlery23.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            gardeManger.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        gardeManger.setVerticalTextPosition(SwingConstants.BOTTOM);
        gardeManger.setHorizontalTextPosition(SwingConstants.CENTER);
        gardeManger.addActionListener((ActionEvent ae) -> new PantryController(app));


        listeAchats = new JButton(model.messages.getString("shoppinglist.title"));
        listeAchats.setBackground(Color.WHITE);
        listeAchats.setContentAreaFilled(false);
        listeAchats.setOpaque(true);
        listeAchats.setFont(new Font("Arial", Font.BOLD, 12));
        listeAchats.setPressedIcon(new ImageIcon());
        menuUp.add(listeAchats);
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/shopping122.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            listeAchats.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        listeAchats.setVerticalTextPosition(SwingConstants.BOTTOM);
        listeAchats.setHorizontalTextPosition(SwingConstants.CENTER);
        listeAchats.addActionListener((ActionEvent ae) -> new ShoppingListController(app));

        chercher = new JButton(model.messages.getString("search.title"));
        chercher.setBackground(Color.WHITE);
        chercher.setContentAreaFilled(false);
        chercher.setOpaque(true);
        chercher.setFont(new Font("Arial", Font.BOLD, 12));
        chercher.setPressedIcon(new ImageIcon());
        menuUp.add(chercher);
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/search100.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            chercher.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        chercher.setVerticalTextPosition(SwingConstants.BOTTOM);
        chercher.setHorizontalTextPosition(SwingConstants.CENTER);
        chercher.addActionListener((ActionEvent ae) -> {
            new SearchController(model);
        });

        consommer = new JButton(model.messages.getString("consume.title"));
        bottomButtonPanel.add(consommer, BorderLayout.EAST);
        consommer.setBackground(Color.WHITE);
        consommer.setContentAreaFilled(false);
        consommer.setOpaque(true);
        consommer.setFont(new Font("Arial", Font.BOLD, 12));
        consommer.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/cutlery23.png")); //minus24.png
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            consommer.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        consommer.setVerticalTextPosition(SwingConstants.BOTTOM);
        consommer.setHorizontalTextPosition(SwingConstants.CENTER);
        consommer.addActionListener((ActionEvent ae) -> new JustConsumedController(model));

        menuUp.add(Box.createHorizontalGlue());

        parametres = new JButton(model.messages.getString("settings.title"));
        parametres.setBackground(Color.WHITE);
        parametres.setContentAreaFilled(false);
        parametres.setOpaque(true);
        parametres.setFont(new Font("Arial", Font.BOLD, 12));
        parametres.setPressedIcon(new ImageIcon());
        menuUp.add(parametres);
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/network60.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            parametres.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        parametres.setVerticalTextPosition(SwingConstants.BOTTOM);
        parametres.setHorizontalTextPosition(SwingConstants.CENTER);
        parametres.addActionListener((ActionEvent ae) -> {
            new SettingsView(model);
            //setEnabled(false);
        });

        JPanel log = new JPanel();
        log.setLayout(new BoxLayout(log, BoxLayout.Y_AXIS));
        menuUp.add(log);
        log.setBackground(Color.white);


        userName = new JLabel(user.getName());
        userName.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 5, 5));
        log.add(userName);
        userName.setAlignmentX(SwingConstants.CENTER);
        userName.setFont(new Font("Arial", Font.ITALIC, 14));

        String[] loginoutText = {model.messages.getString("user.state.login"),
                model.messages.getString("user.state.logout")};
        loginout = new JButton((user.isLoggedIn()) ? loginoutText[1] : loginoutText[0]);
        loginout.setBackground(Color.WHITE);
        loginout.setContentAreaFilled(false);
        loginout.setOpaque(true);
        loginout.setPressedIcon(new ImageIcon());
        loginout.setBorderPainted(true);
        log.add(loginout);

        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/logout20.png"));
            Image newImg = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            loginout.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loginout.setVerticalTextPosition(SwingConstants.CENTER);
        loginout.setHorizontalTextPosition(SwingConstants.RIGHT);
        loginout.addActionListener((ActionEvent ae) -> {
            if (user.isLoggedIn()) {
                user.logout();
            } else {
                new LoginController(app);
            }
        });
        user.addObserver((Observable o, Object arg) -> {
            if (arg != null && arg instanceof Integer) {
                if (arg.equals(Constants.OBSERVER_USER_UPDATED)) {
                    if (user.isLoggedIn()) {
                        loginout.setText(loginoutText[1]);
                    } else {
                        loginout.setText(loginoutText[0]);
                    }
                    refresh();
                }
            }
        });

        tables.add(Box.createRigidArea(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 30), 0)));

        MyRenderer renderer = new MyRenderer();

        perimPanel = new JPanel();
        perimPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                model.messages.getString("app.table.expiresoon.title")));
        tables.add(perimPanel);
        perimPanel.setBackground(Color.white);


        TableModel model1 = new TableModel(expiryColumns);
        perim = new JTable(model1);
        perimPanel.add(new JScrollPane(perim));
        perim.setEnabled(false);
        expireSoonProducts.forEach((k) -> {
            model1.addRow(new Object[]{k.toString(), k.getQuantityInPantry(), String.format("%1$td-%1$tm-%1$tY", k.getOldestAvailableBoughtProduct().getExpirationDay())});
        });
        perim.setGridColor(Color.black);
        perim.setDefaultRenderer(Integer.class, renderer);
        perim.setDefaultRenderer(String.class, renderer);
        perim.setShowHorizontalLines(false);
        perim.setRowHeight(25);
        perim.getColumnModel().getColumn(0).setPreferredWidth(200);
        perim.getColumnModel().getColumn(1).setPreferredWidth(70);
        perim.getColumnModel().getColumn(2).setPreferredWidth(500);
        perim.setAutoCreateRowSorter(true);
        perim.setFillsViewportHeight(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model1);
        sorter.setComparator(2, new Comparator<String>() {

            @Override
            public int compare(String date1, String date2) {
                if (date1.equals(date2)) {
                    return 0;
                } else if (Integer.parseInt(date1.substring(6)) < Integer.parseInt(date2.substring(6))) {
                    return 1;
                } else if (Integer.parseInt(date1.substring(6)) > Integer.parseInt(date2.substring(6))) {
                    return -1;
                } else if (Integer.parseInt(date1.substring(3, 5)) < Integer.parseInt(date2.substring(3, 5))) {
                    return 1;
                } else if (Integer.parseInt(date1.substring(3, 5)) > Integer.parseInt(date2.substring(3, 5))) {
                    return -1;
                } else if (Integer.parseInt(date1.substring(0, 2)) < Integer.parseInt(date2.substring(0, 2))) {
                    return 1;
                } else if (Integer.parseInt(date1.substring(0, 2)) > Integer.parseInt(date2.substring(0, 2))) {
                    return -1;
                } else {
                    return 0;
                }


            }
        });


        addToShoppingList = new JButton("Ajouter à une liste d'achats");
        addToShoppingList.addActionListener((ActionEvent ae) -> {


            ShoppingList s = new ShoppingList();
            s.setProducts(controller.getL());
            new ShoppingListController(model, s);
        });

        tables.add(Box.createRigidArea(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 30), 0)));

        peuPanel = new JPanel(new BorderLayout());
        peuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                model.messages.getString("app.table.fewproducts.title")));
        tables.add(peuPanel);
        peuPanel.setBackground(Color.white);


        TableModel model2 = new TableModel(fewColumns);
        peu = new JTable(model2);
        peuPanel.add(new JScrollPane(peu), BorderLayout.CENTER);
        peuPanel.add(addToShoppingList, BorderLayout.SOUTH);
        //peu.setEnabled(false);
        fewProducts.forEach((k) -> {
            JButton add = new JButton("");
            model2.addRow(new Object[]{k.toString(), k.getQuantityInPantry(), add});
        });
        peu.setShowHorizontalLines(false);
        peu.setGridColor(Color.black);
        peu.setDefaultRenderer(Integer.class, renderer);
        peu.setDefaultRenderer(String.class, renderer);
        peu.setRowHeight(25);
        peu.getColumnModel().getColumn(0).setPreferredWidth(200);
        peu.getColumnModel().getColumn(1).setPreferredWidth(70);
        peu.setFillsViewportHeight(true);
        TableRowSorter<TableModel> sorter1 = new TableRowSorter<TableModel>(model2);
        peu.setRowSorter(sorter1);
        sorter1.setComparator(0, new Comparator<String>() {

            @Override
            public int compare(String name1, String name2) {
                return name1.compareTo(name2);
            }
        });
        ListSelectionModel listMod = peu.getSelectionModel();
        listMod.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listMod.addListSelectionListener(controller);

        //peu.getColumn(model.messages.getString("app.table.fewproducts.add")).setCellRenderer(new ButtonRenderer());
        //peu.getColumn(model.messages.getString("app.table.fewproducts.add")).setCellEditor(
        //        new ButtonEditor(new JCheckBox()));


        tables.add(Box.createRigidArea(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 30), 0)));

        createMenuBar();
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                model.save();
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        JMenu fileMenu = new JMenu(model.messages.getString("app.menu.file"));
        JMenu helpMenu = new JMenu(model.messages.getString("app.menu.help"));
        JMenu toolsMenu = new JMenu(model.messages.getString("app.menu.tools"));

        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        toolsMenu.setMnemonic(KeyEvent.VK_T);

        JMenuItem exit = new JMenuItem(model.messages.getString("app.menu.file.exit"));
        JMenuItem licences = new JMenuItem(model.messages.getString("app.menu.help.licences"));
        JMenu languages = new JMenu(model.messages.getString("app.menu.tools.languages"));
        exit.addActionListener((ActionEvent e) -> {
            model.save();
            System.exit(0);
        });
        licences.addActionListener((ActionEvent e) -> new LicensesView());

        Set<ResourceBundle> resourceBundles = new HashSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                ResourceBundle rb = ResourceBundle.getBundle("gestock.resources.lang.MessagesBundle", locale);
                if (!rb.getLocale().getDisplayLanguage().equals(""))
                    resourceBundles.add(rb);
            } catch (MissingResourceException ex) {
                // ...
            }
        }
        resourceBundles.forEach((k) -> {
            Locale l = k.getLocale();
            JMenuItem jmi = new JMenuItem(l.getDisplayLanguage(l));
            languages.add(jmi);
            jmi.addActionListener((ActionEvent ae) -> {
                Locale.setDefault(l);
                model.messages = ResourceBundle.getBundle("gestock.resources.lang.MessagesBundle", l);
                model.getUser().setLocale(l);
                model.getUser().setUpdated();
                Thread t = new Thread(this::updateLocale);
                t.start();
            });
        });

        fileMenu.add(exit);
        helpMenu.add(licences);
        toolsMenu.add(languages);

        menubar.add(fileMenu);
        menubar.add(helpMenu);
        menubar.add(toolsMenu);

        setJMenuBar(menubar);
    }


    public void updateLocale() {
        bottomButton.setText(model.messages.getString("justbought.title"));
        parametres.setText(model.messages.getString("settings.title"));
        chercher.setText(model.messages.getString("search.title"));
        catalogue.setText(model.messages.getString("catalogue.title"));
        gardeManger.setText(model.messages.getString("pantry.title"));
        listeAchats.setText(model.messages.getString("shoppinglist.title"));
        String[] loginoutText = {model.messages.getString("user.state.login"),
                model.messages.getString("user.state.logout")};
        loginout.setText((user.isLoggedIn()) ? loginoutText[1] : loginoutText[0]);
        consommer.setText(model.messages.getString("consume.title"));

        peuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                model.messages.getString("app.table.fewproducts.title")));
        perimPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                model.messages.getString("app.table.expiresoon.title")));
        expiryColumns = new String[]{model.messages.getString("app.table.expiresoon.name"),
                model.messages.getString("app.table.expiresoon.quantity"),
                model.messages.getString("app.table.expiresoon.date")};
        fewColumns = new String[]{model.messages.getString("app.table.fewproducts.name"),
                model.messages.getString("app.table.fewproducts.quantity"),
                model.messages.getString("app.table.fewproducts.add")};
    }

    public void fillExpireSoon(List<BaseProduct> expireSoonProducts) {
        TableModel tm = new TableModel(expiryColumns);
        perim.setModel(tm);
        expireSoonProducts.forEach((k) -> {
            tm.addRow(new Object[]{k.toString(), k.getQuantityInPantry(), k.getOldestAvailableBoughtProduct().getExpirationDay()});
        });
    }

    public void fillFewProducts(List<BaseProduct> baseProducts) {
        TableModel tm = new TableModel(fewColumns);
        peu.setModel(tm);
        baseProducts.forEach((k) -> {
            try {
                tm.addRow(new Object[]{k.toString(), k.getQuantityInPantry()});
            } catch (Exception e) {
            }
        });
    }

    public JTable getTable() {
        return peu;
    }

    public JButton getEnableButton() {
        return this.addToShoppingList;
    }

    public void setEnabledButton(JButton b) {
        this.addToShoppingList = b;
    }
}


// Roger, regarde http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm  Moi, je n'arrive pas a le faire marcher. Cristina
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);

    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;

    private String label;

    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            //
            //
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}


