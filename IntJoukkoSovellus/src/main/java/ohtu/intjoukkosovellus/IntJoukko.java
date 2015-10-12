
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] luvut;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        luvut = new int[KAPASITEETTI];
        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        luvut = new int[kapasiteetti];
        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;

    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti pitää olla epänegatiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoko pitää olla epänegatiivinen");
        }
        luvut = new int[kapasiteetti];
        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public void lisaa(int[] luvut) {
        for (int i=0; i<luvut.length; i++) {
            lisaa(luvut[i]);
        }
    }
    
    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0) {
            luvut[0] = luku;
            alkioidenLkm++;
            return true;
        } 
        if (!kuuluu(luku)) {
            luvut[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm % luvut.length == 0) {
                kasvata();
            }
            return true;
        }
        return false;
    }

    private void kasvata() {
        int[] vanhatLuvut = new int[luvut.length];
        kopioiTaulukko(luvut, vanhatLuvut);
        luvut = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(vanhatLuvut, luvut);
    }

    public boolean kuuluu(int luku) {
        int on = 0;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == luvut[i]) {
                on++;
            }
        }
        return on > 0;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == luvut[i]) {
                luvut[i] = 0;
                tiivista(i);
                alkioidenLkm--;
                return true;
            }
        }
        return false;
    }

    private void tiivista(int kohta) {
        int apu;
        for (int j = kohta; j < alkioidenLkm; j++) {
            apu = luvut[j];
            luvut[j] = luvut[j + 1];
            luvut[j + 1] = apu;
        }
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        switch (alkioidenLkm) {
            case 0:
                return "{}";
            case 1:
                return "{" + luvut[0] + "}";
            default:
                return moniAlkioString();
        }
    }

    private String moniAlkioString() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += luvut[i];
            tuotos += ", ";
        }
        tuotos += luvut[alkioidenLkm - 1];
        tuotos += "}";
        return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(luvut, 0, taulu, 0, taulu.length);
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        x.lisaa(a.toIntArray());
        x.lisaa(b.toIntArray());
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        z.lisaa(a.toIntArray());
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }

        return z;
    }
        
}