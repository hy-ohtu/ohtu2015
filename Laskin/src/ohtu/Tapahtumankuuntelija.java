package ohtu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JTextField;
 
public class Tapahtumankuuntelija implements ActionListener {
    private JButton undo;
    private JButton nollaa;
    private JTextField tuloskentta;
    private JTextField syotekentta;
    private Sovelluslogiikka sovellus;
    private Map<JButton, Komento> komennot;
    private Komento edellinen;
 
    public Tapahtumankuuntelija(JButton plus, JButton miinus, JButton nollaa, JButton undo, JTextField tuloskentta, JTextField syotekentta) {
        this.undo = undo;
        this.tuloskentta = tuloskentta;
        this.syotekentta = syotekentta;
        this.sovellus = new Sovelluslogiikka();
        this.nollaa = nollaa;
        
        komennot = new HashMap<>();
        
        komennot.put(plus, new Summa(sovellus, syotekentta, tuloskentta));
        komennot.put(miinus, new Erotus(sovellus, syotekentta, tuloskentta));
        komennot.put(nollaa, new Nollaus(sovellus, syotekentta, tuloskentta));
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Komento komento = komennot.get((JButton) ae.getSource());
        if (komento != null) {
            komento.suorita();
            edellinen = komento;
            undo.setEnabled(true);  
        } else if (edellinen != null) {
            edellinen.peru();
            edellinen = null;
            undo.setEnabled(false);
        }
        nollaa.setEnabled(sovellus.tulos()!=0);
    }
 
}