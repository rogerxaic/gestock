package gestock.window;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Login extends JFrame {

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
    private JTextField Pass;
    private JTextArea maZoneTexte;

    public Login() {
        super();
        setSize(new Dimension(250, 250));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maZoneTexte = new JTextArea();
        Forget = new JLabel("Forgot Password?(online)");
        Email = new JLabel("Email:");
        Password = new JLabel("Password:");
        Mail = new JTextField(10);
        Pass = new JTextField(10);
        btnSignup = new JButton("Signup");
        btnSignin = new JButton("Signin");

        
      
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
        
        
        
        info1.add(Panel1,BorderLayout.NORTH);
        info1.add(Panel2,BorderLayout.CENTER);
        info2.add(Panel3,BorderLayout.CENTER);
        info2.add(Panel4,BorderLayout.SOUTH);
        PanelMain.add(info1, BorderLayout.NORTH);
        PanelMain.add(info2,BorderLayout.SOUTH);
        
        setContentPane(PanelMain);
        
        setVisible(true);
    }

}
