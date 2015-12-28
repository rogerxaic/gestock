package gestock.window.catalogue;

import gestock.Gestock;
import gestock.entity.BaseProduct;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Roger on 12/19/2015.
 */
public class Catalogue extends JFrame {

    protected Gestock app;
    private JPanel main;
    private JPanel header;
    private JScrollPane container;
    private JPanel content;

    public Catalogue(Gestock app) {
        super("Gestock - Catalogue");
        this.app = app;
        setSize(600, 600);
        header = new JPanel();
        AbstractButton add = new JButton("Ajouter");
        add.setBackground(Color.white);
        header.add(add);
        add.setBackground(Color.WHITE);
        add.setContentAreaFilled(false);
        add.setOpaque(true);
        add.setFont(new Font("Arial", Font.BOLD, 12));
        add.setPressedIcon(new ImageIcon());
        try {
            Image img = ImageIO.read(getClass().getResource("../../resources/add64.png"));
            Image newImg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            add.setIcon(new ImageIcon(newImg));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        add.setVerticalTextPosition(SwingConstants.BOTTOM);
        add.setHorizontalTextPosition(SwingConstants.CENTER);
        add.addActionListener((ActionEvent ae) -> {
            new Product(app, new BaseProduct(), false); //Gestock app, BaseProduct baseProduct, boolean update
        });
        header.add(new JLabel("Add"));
        header.add(new JLabel("Magasins"));

        int rows = (int) Math.ceil((double) app.getCatalogue().size() / 5);
        //content = new JPanel(new GridLayout(rows, 5));
        content = new JPanel();
        container = new JScrollPane();
        container.getViewport().add(content);
        for (int i = 0; i < app.getCatalogue().size(); i++) {
            BaseProduct bp = (BaseProduct) app.getCatalogue().get(i);
            ProductPanel productPanel = new ProductPanel(bp, this);
            content.add(productPanel);
            //System.out.println(bp.getName());
        }

        main = new JPanel(new BorderLayout());
        main.add(header, BorderLayout.NORTH);
        main.add(container, BorderLayout.CENTER);
        setContentPane(main);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            Image img = ImageIO.read(getClass().getResource("../../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();
        setVisible(true);
    }
}
