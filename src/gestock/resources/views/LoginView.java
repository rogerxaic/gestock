package gestock.resources.views;

import gestock.Gestock;
import gestock.controller.LoginController;
import gestock.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends GFrame {

    private JPanel monPanelCommande;
    private JPanel Panel1;
    private JPanel Panel2;
    private JPanel Panel3;
    private JPanel Panel4;
    private JPanel info1;
    private JPanel info2;

    private JButton btnSignup;
    private JButton btnSignin;
    private JLabel Email;
    private JLabel Password;
    private JLabel Forget;
    private JTextField Mail;
    private JPasswordField Pass;
    private JTextArea maZoneTexte;

    private Gestock model;
    private LoginController controller;

    public LoginView(Gestock gestock, LoginController loginController) {
        super(gestock.messages.getString("app.title")+" - " + gestock.messages.getString("login.title"));
        this.model = gestock;
        this.controller = loginController;
        setSize(new Dimension(250, 250));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        maZoneTexte = new JTextArea();
        Forget = new JLabel("Forgot Password?(online)");
        Email = new JLabel(model.messages.getString("settings.email"));
        Password = new JLabel(model.messages.getString("settings.password"));
        Mail = new JTextField(10);
        Pass = new JPasswordField(10);
        btnSignup = new JButton(model.messages.getString("login.signup"));
        btnSignup.addActionListener(controller);
        btnSignin = new JButton(model.messages.getString("login.signin"));
        btnSignin.addActionListener((ActionEvent ae) -> {
            User u = model.getUser();
            u.setEmail(Mail.getText());
            u.setPassword(String.valueOf(Pass.getPassword()));
            u.login();
            if (u.isLoggedIn()) {
                u.setUpdated();
                dispose();
            }
        });

        Panel1 = new JPanel();
        Panel2 = new JPanel();
        Panel3 = new JPanel();
        Panel4 = new JPanel();
        info1 = new JPanel(new BorderLayout());
        info2 = new JPanel(new BorderLayout());
        JPanel PanelMain = new JPanel(new BorderLayout());


        Panel1.add(Email);
        Panel1.add(Mail);
        Panel2.add(Password);
        Panel2.add(Pass);
        Panel3.add(btnSignup);
        Panel3.add(btnSignin);
        Panel4.add(Forget);


        info1.add(Panel1, BorderLayout.NORTH);
        info1.add(Panel2, BorderLayout.CENTER);
        info2.add(Panel3, BorderLayout.CENTER);
        info2.add(Panel4, BorderLayout.SOUTH);
        PanelMain.add(info1, BorderLayout.NORTH);
        PanelMain.add(info2, BorderLayout.SOUTH);

        setContentPane(PanelMain);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getEmail() {
        return Mail.getText();
    }

    public String getPassword() {
        return String.valueOf(Pass.getPassword());
    }
}
