package gestock.resources.views;

import gestock.Gestock;
import gestock.entity.Free;
import gestock.entity.User;
import gestock.util.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.GroupLayout.Alignment.*;

/**
 * Created by rogerxaic on 11/28/2015.
 */
public class SettingsView extends GFrame {
    protected Gestock model;
    protected User user;
    protected GestockView main;
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

    public SettingsView(Gestock app, GestockView main, User user) {
        super(app.messages.getString("app.title") + " - " + app.messages.getString("settings.title"));

        this.model = app;
        this.main = main;
        this.user = user;

        this.initName = user.getName();
        this.initEmail = user.getEmail();
        this.initPassword = "";
        this.initFreeUser = user.getFree().getUser();
        this.initFreeSecret = user.getFree().getSecret();

        nameLabel = new JLabel(model.messages.getString("settings.name"));
        emailLabel = new JLabel(model.messages.getString("settings.email"));
        passwordLabel = new JLabel(model.messages.getString("settings.password"));
        freeUserLabel = new JLabel(model.messages.getString("settings.free.user"));
        freeSecretLabel = new JLabel(model.messages.getString("settings.free.secret"));
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

        importButton = new JButton(model.messages.getString("settings.import"));
        exportButton = new JButton(model.messages.getString("settings.export"));
        exportButton.addActionListener((ActionEvent ae) -> {
            Thread save = new Thread(() -> {
                model.saveTemp();
            });
            save.run();
            JFileChooser c = new JFileChooser();
            int rVal = c.showSaveDialog(SettingsView.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                String dir = c.getCurrentDirectory().toString();
                String fs = System.getProperty("file.separator");
                System.out.println("filename: " + c.getSelectedFile().getName());
                System.out.println("dir : " + c.getCurrentDirectory().toString());
                System.out.println(c.getSelectedFile().toString());
                Tools.zip(model.getTemp(), dir + fs + "gestock.zip");
            }
        });
        saveButton = new JButton(model.messages.getString("settings.save.button"));
        saveButton.addActionListener(e1 -> updateUser());
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
                            null, model.messages.getString("settings.save.message"),
                            model.messages.getString("settings.save.title"), JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        updateUser();
                    }
                }
            }
        });

        setSize(600, 600);
        pack();
        setLocationRelativeTo(null);
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
        user.setPassword(password);

        user.setUpdated();
        main.refresh();
    }

    private boolean hasChanged() {
        boolean changed = user.getName().equals(nameField.getText());
        changed &= user.getEmail().equals(emailField.getText());
        changed &= initPassword.equals(passwordField.getText());
        changed &= user.getFree().getUser().equals(freeUserField.getText());
        changed &= user.getFree().getSecret().equals(freeSecretField.getText());

        return !changed;
    }
}
