/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestock.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
/**
 *
 * @author lsudre
 */
public class EcouteurAjout implements ActionListener{
    public ShoppingListWindow fen;
	
	public EcouteurAjout(ShoppingListWindow fg) {
		fen = fg;
	}
	public void actionPerformed(ActionEvent e ) {
		System.out.println("ajout produit");
	}
}
