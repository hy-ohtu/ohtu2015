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
class Nollaus implements Komento {
    
    private Sovelluslogiikka sovellus;
    private JTextField syote;
    private JTextField tulos;
    private int vanhaArvo = 0;
    
    public Nollaus(Sovelluslogiikka sovellus, JTextField syotekentta, JTextField tuloskentta) {
        this.sovellus = sovellus;
        this.syote = syotekentta;
        this.tulos = tuloskentta;
    }
    
    @Override
    public void suorita() {
        vanhaArvo = sovellus.tulos();
        sovellus.nollaa();
        tulos.setText("" + sovellus.tulos());
        syote.setText("");
    }
    
    @Override
    public void peru() {
        sovellus.plus(vanhaArvo);
        tulos.setText("" + sovellus.tulos());
    }
    
}
