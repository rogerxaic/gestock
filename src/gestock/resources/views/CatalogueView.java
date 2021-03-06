package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.CatalogueController;
import gestock.controller.ProductController;
import gestock.controller.ShopController;
import gestock.entity.BaseProduct;
import gestock.resources.views.components.ProductPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by Roger on 12/19/2015.
 */
public class CatalogueView extends GFrame {

    protected Gestock model;
    protected CatalogueController catalogueController;
    protected List<BaseProduct> products;
    private JPanel main;
    private JPanel header;
    private JScrollPane container;
    private JPanel content;

    public CatalogueView(Gestock app, CatalogueController catalogueController, List<BaseProduct> products) {
        super(app.messages.getString("app.title") + " - " + app.messages.getString("catalogue.title"));
        this.model = app;
        this.catalogueController = catalogueController;
        this.products = products;

        setSize(600, 600);
        header = new JPanel();
        AbstractButton add = new JButton(model.messages.getString("baseproduct.title"));
        add.setBackground(Color.white);
        header.add(add);
        add.setBackground(Color.WHITE);
        add.setContentAreaFilled(false);
        add.setOpaque(true);
        add.setFont(new Font("Arial", Font.BOLD, 12));
        add.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/add64.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            add.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        add.setVerticalTextPosition(SwingConstants.BOTTOM);
        add.setHorizontalTextPosition(SwingConstants.CENTER);
        add.addActionListener((ActionEvent ae) -> new ProductController(app, new BaseProduct()));
        AbstractButton mag = new JButton(model.messages.getString("shop.title"));
        mag.setBackground(Color.white);
        header.add(mag);
        mag.setBackground(Color.WHITE);
        mag.setContentAreaFilled(false);
        mag.setOpaque(true);
        mag.setFont(new Font("Arial", Font.BOLD, 12));
        mag.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("/gestock/resources/add64.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            mag.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mag.setVerticalTextPosition(SwingConstants.BOTTOM);
        mag.setHorizontalTextPosition(SwingConstants.CENTER);
        mag.addActionListener((ActionEvent ae) -> new ShopController(model));

        container = new JScrollPane();

        this.fillCatalogue(true);

        main = new JPanel(new BorderLayout());
        main.add(header, BorderLayout.NORTH);
        main.add(container, BorderLayout.CENTER);
        setContentPane(main);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void fillCatalogue(boolean inverse) {
        int rows = (int) Math.ceil((double) this.products.size() / 5);
        content = new JPanel(new GridLayout(rows, 5));
        container.getViewport().removeAll();
        container.getViewport().add(content);
        if (inverse) {
            for (int i = this.products.size() - 1; i >= 0; i--) {
                BaseProduct baseProduct = this.products.get(i);
                ProductPanel productPanel = new ProductPanel(baseProduct, this);
                content.add(productPanel);
            }
        } else {
            for (int i = 0; i < this.products.size(); i++) {
                BaseProduct baseProduct = this.products.get(i);
                ProductPanel productPanel = new ProductPanel(baseProduct, this);
                content.add(productPanel);
            }
        }
    }

    public Gestock getModel() {
        return this.model;
    }
}
