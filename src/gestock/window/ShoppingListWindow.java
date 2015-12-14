/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.io.*;
import java.lang.Object;
import gestock.baseProducts.*;
import gestock.Gestock;

/**
 *
 * @author lsudre
 */
public class ShoppingListWindow extends JFrame {

    // Attributs panels

    private JPanel panelPrincipal;
    private JPanel panelEntete;
    private JPanel panelListe;
    private JPanel panelOptions;
    private JPanel panelSuggestions;
    private JPanel panelPanier;

	// Attributs widgets
    // entete
    private JTextField element;
    private JButton boutonAjouter;
    private JButton boutonChercher;
		//liste
    //panier
    private JComboBox<String> maListePanier;
    //suggestions
    private JComboBox<String> maListeSuggestions;
    //options
    private JButton boutonEffacer;
    private JButton boutonEnvoyer;
    private JButton boutonCopier;
    private JLabel prixPanier;

    //Attributs de gestion de l'application
    private LinkedList<BaseProduct> listeProduits = new LinkedList();

    //ecouteurs
    private EcouteurAjout ecouteurAjouter;
    private EcouteurRecherche ecouteurRechercher;
    private EcouteurEffacer ecouteurEffacer;
    private EcouteurEnvoie ecouteurEnvoyer;
    private EcouteurCopie ecouteurCopier;

    public ShoppingListWindow(Gestock app) {
        super("Gestock - Liste d'achats");
        setSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        element = new JTextField("", 50);
        boutonAjouter = new JButton("Ajouter");
        boutonChercher = new JButton("Chercher");
        boutonEffacer = new JButton("Effacer selection");
        boutonEnvoyer = new JButton("Envoyer");
        boutonCopier = new JButton("Copier texte");
        prixPanier = new JLabel("Prix panier : ");

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        //panelPrincipal = new JPanel(new VerticalFlowLayout());
        panelEntete = new JPanel();
        panelListe = new JPanel();
        panelPanier = new JPanel();
        panelPanier.setLayout(new BoxLayout(panelPanier, BoxLayout.Y_AXIS));
        panelSuggestions = new JPanel();
        panelSuggestions.setLayout(new BoxLayout(panelSuggestions, BoxLayout.Y_AXIS));
        panelOptions = new JPanel();
        
        panelEntete.add(boutonAjouter);
        panelEntete.add(boutonChercher);
        panelEntete.add(element);
        
        panelPanier.add(maListePanier);
        panelSuggestions.add(maListeSuggestions);
        
        panelListe.add(panelPanier);
        panelListe.add(panelSuggestions);
        
        panelOptions.add(boutonEffacer);
        panelOptions.add(boutonEnvoyer);
        panelOptions.add(boutonCopier);
        panelOptions.add(prixPanier);
        
        panelPrincipal.add(panelEntete);
        panelPrincipal.add(panelListe);
        panelPrincipal.add(panelOptions);
        
        //ecouteurs
        ecouteurAjouter = new EcouteurAjout(this);
        ecouteurRechercher = new EcouteurRecherche(this);
        ecouteurEffacer = new EcouteurEffacer(this);
        ecouteurEnvoyer = new EcouteurEnvoie(this);
        ecouteurCopier = new EcouteurCopie(this);
        

        setVisible(true);
    }

}
