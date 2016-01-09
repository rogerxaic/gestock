package gestock.resources.views;


import gestock.Gestock;
import gestock.controller.BaseProductSearchController;
import gestock.controller.JustBoughtController;
import gestock.entity.BoughtProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Stack;

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
            BaseProductSearchController bpsc = new BaseProductSearchController(model);
            bpsc.addObserver(controller);
        });
        Addor = new JButton("Ajouter au garde-manger");
        Addor.addActionListener((ActionEvent ae) -> controller.addToPantry());
        DeleteLast = new JButton("Effacer Dernier");
        DeleteLast.addActionListener((ActionEvent ae) -> controller.deleteLast());
        DeleteAll = new JButton("Effacer Tout");
        DeleteAll.addActionListener((ActionEvent ae) -> controller.deleteAll());
        List = new JTextPane();

        Panel1 = new JPanel();
        Panel2 = new JPanel();
        Panel2.setBackground(Color.WHITE);
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
        PanelMain.setBackground(Color.WHITE);

        setContentPane(PanelMain);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void fill(Stack<BoughtProduct> toAdd) {
        StringBuilder sb = new StringBuilder();
        for (BoughtProduct bp : toAdd) {
            sb.append(bp.toString());
            sb.append("\n");
        }
        this.List.setText(sb.toString());
    }
}
