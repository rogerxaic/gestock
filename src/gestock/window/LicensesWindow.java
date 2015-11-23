/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 *
 * @author Roger
 */
public class LicensesWindow extends JFrame implements ActionListener {

    private HashMap<String, String> licenceText;
    private JButton closeButton;
    private JPanel panel;
    private JPanel closePanel;
    private JScrollPane scrollLicences;
    private JTextPane licences;

    public LicensesWindow() {

        super("Gestock - Licences");

        licences = new JTextPane();
        licences.setEditable(false);
        initText();
        addText();

        scrollLicences = new JScrollPane(licences);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closePanel = new JPanel();
        closePanel.add(closeButton);

        panel = new JPanel(new BorderLayout());
        panel.add(scrollLicences, BorderLayout.CENTER);
        panel.add(closePanel, BorderLayout.SOUTH);

        setContentPane(panel);

        try {
            Image img = ImageIO.read(getClass().getResource("../resources/gestock-blue.png"));
            setIconImage(img);
        } catch (Exception e) {
        }
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void initText() {
        this.licenceText = new HashMap();
        this.licenceText.put("JSON in Java [package org.json]\n| (https://github.com/douglascrockford/JSON-java)", " Copyright (c) 2002 JSON.org\n\n"
                + " Permission is hereby granted, free of charge, to any person obtaining a copy\n"
                + " of this software and associated documentation files (the \"Software\"), to deal\n"
                + " in the Software without restriction, including without limitation the rights\n"
                + " to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n"
                + " copies of the Software, and to permit persons to whom the Software is\n"
                + " furnished to do so, subject to the following conditions:\n\n"
                + " The above copyright notice and this permission notice shall be included in all\n"
                + " copies or substantial portions of the Software.\n\n"
                + " The Software shall be used for Good, not Evil.\n\n"
                + " THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n"
                + " IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n"
                + " FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n"
                + " AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n"
                + " LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n"
                + " OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n"
                + " SOFTWARE.");
        this.licenceText.put("Icons made by Freepik (http://www.freepik.com)\n| (http://www.flaticon.com)",
                "Licensed under CC BY 3.0 (Creative Commons BY 3.0) \n http://creativecommons.org/licenses/by/3.0/");
    }

    private void addText() {
        String text = "";
        for (HashMap.Entry<String, String> entry : licenceText.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            text += "+------------------------------------------------------\n"
                    + "| " + key + "\n"
                    + "+------------------------------------------------------\n"
                    + value + "\n\n\n";
        }
        this.licences.setText(text);
    }
}
