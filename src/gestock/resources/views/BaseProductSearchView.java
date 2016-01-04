package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.BaseProductSearchController;
import gestock.entity.BaseProduct;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Roger on 1/4/2016.
 */
public class BaseProductSearchView extends GFrame {

    private final Dimension ORIGINAL_SIZE;
    private final Point ORIGINAL_LOCATION;
    private BaseProductSearchController controller;
    private HashMap<Object, BaseProduct> obp;
    private Gestock model;
    private JPanel main;
    private JScrollPane jScrollPane;
    private JPanel content;
    private JTextField searchField;

    public BaseProductSearchView(Gestock app, BaseProductSearchController baseProductSearchController) {
        super("Gestock - Chercher produit dans le catalogue");
        this.model = app;
        this.controller = baseProductSearchController;
        setSize(300, 600);

        searchField = new JTextField(20);
        searchField.addKeyListener(controller);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        jScrollPane = new JScrollPane();
        jScrollPane.getViewport().add(content);

        main = new JPanel(new BorderLayout());
        main.add(searchField, BorderLayout.NORTH);
        main.add(jScrollPane, BorderLayout.CENTER);

        setResizable(false);
        setContentPane(main);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        ORIGINAL_SIZE = new Dimension(300, 600);
        ORIGINAL_LOCATION = this.getLocation();
        obp = new HashMap<>();
    }

    public String getSearchTerm() {
        return searchField.getText();
    }

    public void fill(List<BaseProduct> l) {
        content.removeAll();
        obp = new HashMap<>();
        for (Object o : l) {
            BaseProduct bp = (BaseProduct) o;
            JButton b = new JButton(bp.getName());
            obp.put(b, bp);
            b.addActionListener(controller);
            content.add(b);
        }
    }

    public void putOriginalSize() {
        setSize(this.ORIGINAL_SIZE);
    }

    public void putInOriginalLocation() {
        setLocation(this.ORIGINAL_LOCATION);
    }

    public HashMap<Object, BaseProduct> getObp() {
        return obp;
    }
}
