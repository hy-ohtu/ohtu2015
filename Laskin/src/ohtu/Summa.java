/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import javax.swing.JTextField;

/**
 *
 * @author hylje
 */
class Summa implements Komento {
    
    private Sovelluslogiikka sovellus;
    private JTextField syote;
    private JTextField tulos;
    private int arvo = 0;

    public Summa(
            Sovelluslogiikka sovellus, 
            JTextField syotekentta, 
            JTextField tuloskentta) {
        this.sovellus = sovellus;
        this.syote = syotekentta;
        this.tulos = tuloskentta;
    }
    
    @Override
    public void suorita() {
        try {
            arvo = Integer.parseInt(syote.getText());
        } catch (Exception e) {
        }
        
        sovellus.plus(arvo);
        tulos.setText("" + sovellus.tulos());
        syote.setText("");
    }
    
    @Override
    public void peru() {
        sovellus.miinus(arvo);
        tulos.setText("" + sovellus.tulos());
    }
}
