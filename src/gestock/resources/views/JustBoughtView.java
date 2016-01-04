package gestock.resources.views;


import gestock.Gestock;
import gestock.controller.BaseProductSearchController;
import gestock.controller.JustBoughtController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author smihalkova
 */
public class JustBoughtView extends GFrame {

    private Gestock model;
    private JustBoughtController controller;

    private JPanel Panel1;
    private JPanel Panel2;
    private JPanel Panel3;
    private JPanel Panel4;
    private JPanel order1;
    private JPanel order2;
    private JPanel order3;


    private JLabel Add;
    private JButton FirstTime;
    private JButton NotFirstTime;
    private JButton Addor;
    private JButton DeleteLast;
    private JButton DeleteAll;
    private JTextPane List;


    public JustBoughtView(Gestock app, JustBoughtController justBoughtController) {
        super("Gestock - Je viens d'acheter");
        this.model = app;
        this.controller = justBoughtController;
        setSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Add = new JLabel("Ajouter produit");
        FirstTime = new JButton("Premiere fois");
        NotFirstTime = new JButton("Deja achete");
        NotFirstTime.addActionListener((ActionEvent ae) -> {
            new BaseProductSearchController(model);
        });
        Addor = new JButton("Ajouter au garde-manger");
        DeleteLast = new JButton("Effacer Dernier");
        DeleteAll = new JButton("Effacer Tout");
        List = new JTextPane();

        Panel1 = new JPanel();
        Panel2 = new JPanel();
        Panel3 = new JPanel();
        Panel4 = new JPanel();

        JPanel PanelMain = new JPanel(new BorderLayout());
        order1 = new JPanel(new BorderLayout());
        order2 = new JPanel(new BorderLayout());
        order3 = new JPanel(new BorderLayout());

        Panel1.add(Add);
        Panel1.add(FirstTime);
        Panel1.add(NotFirstTime);
        Panel2.add(List);
        Panel3.add(Addor);
        Panel3.add(DeleteLast);
        Panel3.add(DeleteAll);

        PanelMain.add(Panel1, BorderLayout.NORTH);
        PanelMain.add(Panel2, BorderLayout.CENTER);
        PanelMain.add(Panel3, BorderLayout.SOUTH);

        setContentPane(PanelMain);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
