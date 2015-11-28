package gestock.window;

import gestock.Free;
import gestock.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.GroupLayout.Alignment.*;

/**
 * Created by rogerxaic on 11/28/2015.
 */
public class SettingsWindow extends JFrame {
    protected User user;
    protected Interface main;
    protected String initName;
    protected String initEmail;
    protected String initPassword;
    protected String initFreeUser;
    protected String initFreeSecret;
    protected JTextField nameField;
    protected JTextField emailField;
    protected JTextField passwordField;
    protected JTextField freeUserField;
    protected JTextField freeSecretField;
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel buttonsPanel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel freeUserLabel;
    private JLabel freeSecretLabel;
    private JButton importButton;
    private JButton exportButton;
    private JButton saveButton;

    public SettingsWindow(Interface main, User user) {
        super("Gestock - Settings");

        this.main = main;
        this.user = user;

        this.initName = user.getName();
        this.initEmail = user.getEmail();
        this.initPassword = "";
        this.initFreeUser = user.getFree().getUser();
        this.initFreeSecret = user.getFree().getSecret();

        nameLabel = new JLabel("Name");
        emailLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        freeUserLabel = new JLabel("Free User Name");
        freeSecretLabel = new JLabel("Free Secret");
        nameField = new JTextField(this.initName, 20);
        emailField = new JTextField(this.initEmail, 20);
        passwordField = new JPasswordField(this.initPassword, 20);
        freeUserField = new JTextField(this.initFreeUser, 20);
        freeSecretField = new JTextField(this.initFreeSecret, 20);

        infoPanel = new JPanel();
        GroupLayout layout = new GroupLayout(infoPanel);
        //getContentPane().setLayout(layout);
        infoPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(TRAILING)
                                .addComponent(nameLabel)
                                .addComponent(emailLabel)
                                .addComponent(passwordLabel)
                                .addComponent(freeUserLabel)
                                .addComponent(freeSecretLabel))
                        .addGroup(layout.createParallelGroup(LEADING)
                                .addComponent(nameField)
                                .addComponent(emailField)
                                .addComponent(passwordField)
                                .addComponent(freeUserField)
                                .addComponent(freeSecretField)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(emailField)
                        .addComponent(emailLabel))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(passwordField)
                        .addComponent(passwordLabel))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(freeUserLabel)
                        .addComponent(freeUserField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(freeSecretLabel)
                        .addComponent(freeSecretField))
        );

        importButton = new JButton("Import");
        exportButton = new JButton("Export");
        saveButton = new JButton("Save user");
        saveButton.addActionListener(e1 -> {
            updateUser();
        });
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(importButton);
        buttonsPanel.add(exportButton);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(saveButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                main.setEnabled(true);
                if (hasChanged()) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Do you want to save changes?",
                            "Save changes", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        updateUser();
                    }
                }
            }
        });

        try {
            Image img = ImageIO.read(getClass().getResource("../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSize(600, 600);
        pack();
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //pack();
        setVisible(true);
    }

    private void updateUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String freeUser = freeUserField.getText();
        String freeSecret = freeSecretField.getText();

        user.setName(name);
        user.setEmail(email);
        Free free = new Free(freeUser, freeSecret);
        user.setFree(free);

        user.update();
        main.refresh();
    }

    private boolean hasChanged() {
        //boolean changed = true;
        boolean changed = user.getName().equals(nameField.getText());
        changed &= user.getEmail().equals(emailField.getText());
        changed &= initPassword.equals(passwordField.getText());
        changed &= user.getFree().getUser().equals(freeUserField.getText());
        changed &= user.getFree().getSecret().equals(freeSecretField.getText());

        return !changed;
    }
}
