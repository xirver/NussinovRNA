package it.unicam.cs.asdl1819.project1;

import com.sun.codemodel.internal.JForEach;

import java.util.*;

/**
 * Un oggetto di questa classe rappresenta una struttura secondaria di RNA.
 * 
 * @author Luca Tesei
 *
 */
public class SecondaryStructure{

    private final String primarySequence;

    private Set<WeakBond> bonds;

    /**
     * Costruisce una struttura secondaria con un insieme vuoto di legami
     * deboli.
     * 
     * @param primarySequence
     *                            la sequenza di nucleotidi
     * 
     * @throws IllegalArgumentException
     *                                      se la primarySequence contiene dei
     *                                      codici di nucleotidi sconosciuti
     * @throws NullPointerException
     *                                      se la sequenza di nucleotidi è nulla
     */
    public SecondaryStructure(String primarySequence) {
        if (primarySequence == null)
            throw new NullPointerException(
                    "Tentativo di costruire un solutore Nussinov a partire da una sequenza nulla");
        String seq = primarySequence.toUpperCase().trim();
        // check bases in the primary structure
        for (int i = 0; i < seq.length(); i++)
            switch (seq.charAt(i)) {
            case 'A':
            case 'U':
            case 'C':
            case 'G':
                break;
            default:
                throw new IllegalArgumentException(
                        "INPUT ERROR: primary structure contains an unkwnown nucleotide code at position "
                                + (i + 1));
            }
        this.primarySequence = seq;
        this.bonds = new HashSet<WeakBond>();
    }

    /**
     * Costruisce una struttura secondaria con un insieme dato di legami deboli.
     * 
     * @param primarySequence
     *                            la sequenza di nucleotidi
     * @param bonds
     *                            l'insieme dei legami deboli presenti nella
     *                            struttura
     * 
     * @throws IllegalArgumentException
     *                                       se la primarySequence contiene dei
     *                                       codici di nucleotidi sconosciuti
     * @throws NullPointerException
     *                                       se la sequenza di nucleotidi è
     *                                       nulla
     * @throws NullPointerException
     *                                       se l'insieme dei legami è nullo
     * @throws IndexOutOfBoundsException
     *                                       se almeno uno dei due indici di uno
     *                                       dei legami deboli passati esce
     *                                       fuori dai limiti della sequenza
     *                                       primaria di questa struttura
     * @throws IllegalArgumentException
     *                                       se almeno uno dei legami deboli
     *                                       passati connette due nucleotidi a
     *                                       formare una coppia non consentita.
     * 
     */
    public SecondaryStructure(String primarySequence, Set<WeakBond> bonds) {
        if (primarySequence == null)
            throw new NullPointerException(
                    "Tentativo di costruire un solutore Nussinov a partire da una sequenza nulla");
        String seq = primarySequence.toUpperCase().trim();
        // check bases in the primary structure
        for (int i = 0; i < seq.length(); i++)
            switch (seq.charAt(i)) {
            case 'A':
            case 'U':
            case 'C':
            case 'G':
                break;
            default:
                throw new IllegalArgumentException(
                        "INPUT ERROR: primary structure contains an unkwnown nucleotide code at position "
                                + (i + 1));
            }
        this.primarySequence = seq;
        this.bonds = new HashSet<WeakBond>();
        for (WeakBond b : bonds)
            this.addBond(b);
    }

    /**
     * Restituisce la sequenza di nucleotidi di questa struttura secondaria.
     * 
     * @return la sequenza di nucleotidi di questa struttura secondaria
     */
    public String getPrimarySequence() {
        return this.primarySequence;
    }

    /**
     * Restituisce l'insieme dei legami deboli di questa struttura secondaria.
     * 
     * @return l'insieme dei legami deboli di questa struttura secondaria
     */
    public Set<WeakBond> getBonds() {
        return this.bonds;
    }

    /**
     * Determina se questa struttura contiene pseudonodi.
     * 
     * @return true, se in questa struttura ci sono almeno due legami deboli che
     *         si incrociano, false altrimenti
     */
    public boolean isPseudoknotted() {
        //if i<i'<j<j' == true ci sono pseudonodi controllare se ne sono presenti almeno 2
        int count = 0;  //dichiaro il contatore
        for(WeakBond bond : bonds){ //scorro tutte le coppie
            for (WeakBond bondCopy : bonds){
                //faccio un confronto tra le coppie presenti nella lista e controllo se gli indici si incrociano
                if(bond.getI()<bondCopy.getI() && bond.getJ()<bondCopy.getJ()){
                    count++;    //se gli indici si incrociano il contatore aumenta
                }
            }
        }
        if (count >= 2) {   //se sono presenti almeno 2 pseudonodi o più ritorno true
            return true;
        }else {
            return false;   //se sono presenti meno di 2 pseudonodi ritorno false
        }
    }

    /**
     * Aggiunge un legame debole a questa struttura.
     * 
     * @param b
     *              il legame debole da aggiungere
     * @return true se il legame è stato aggiunto, false se era già presente
     * 
     * @throws NullPointerException
     *                                       se il legame passato è nullo
     * @throws IndexOutOfBoundsException
     *                                       se almeno uno uno dei due indici
     *                                       del legame debole passato esce
     *                                       fuori dai limiti della sequenza
     *                                       primaria di questa struttura
     * @throws IllegalArgumentException
     *                                       se il legame debole passato
     *                                       connette due nucleotidi a formare
     *                                       una coppia non consentita.
     */
    public boolean addBond(WeakBond b) {
        // TODO implementare
        int N = primarySequence.toCharArray().length;   //prendo la lunghezza della stringa
        //faccio i dovuti controlli sui valori che vogliono essere inseriti

        if (b==null) throw new NullPointerException("Il legame inserito è nullo");
        if (b.getI()<=0 && b.getJ()>N || b.getI()>N && b.getJ()<=0) throw new IndexOutOfBoundsException("I valori inseriti non sono validi");
        if (!(this.primarySequence.charAt(b.getI()-1) + this.primarySequence.charAt(b.getJ()-1) == 138
                || this.primarySequence.charAt(b.getI()-1) + this.primarySequence.charAt(b.getJ()-1) == 150
                || this.primarySequence.charAt(b.getI()-1) + this.primarySequence.charAt(b.getJ()-1) == 156)) throw new IllegalArgumentException("I valori inseriti connettono due nucleotidi non accoppiabili");


        for(WeakBond bond: bonds){  //scorro le coppie di valori nella lista
            for(int k=0; k<=N; k++){
                if(bond.getI()==b.getI() || bond.getJ()==b.getJ()){ //controllo se sono già presenti nella lista, se lo sono ritorno false
                    return false;
                }
            }
        }
        bonds.add(new WeakBond(b.getI(),b.getJ())); //altrimenti aggiungo gli indici alla lista e ritorno true
        return true;
    }

    public void add(WeakBond b){
        this.bonds.add(b);
    }


    /**
     * Restituisce il numero di legami deboli presenti in questa struttura.
     * 
     * @return il numero di legami deboli presenti in questa struttura
     */
    public int getCardinality() {
        // TODO implementare
        int count=0;

        for(WeakBond bond: bonds){  //scorro la lista
            count++;    //aumento il contatore per ogni coppia nella lista
        }

        return count;   //ritorno il numero di coppie presenti nella lista
    }

    /**
     * Restituisce una stringa contenente la rappresentazione nella notazione
     * dot-bracket di questa struttura secondaria.
     * 
     * @return una stringa contenente la rappresentazione nella notazione
     *         dot-bracket di questa struttura secondaria
     * 
     * @throws IllegalStateException
     *                                   se questa struttura secondaria contiene
     *                                   pseudonodi
     */
    public String getDotBracketNotation() {
        // TODO implementare
        // Ai valori getI() verrà asseggnata la stampa "("
        // mentre ai valori getJ() la stampa ")"
        // tutti gli altri indici non compresi nel Set verrà assegnata la stampa "."
        int N = primarySequence.toCharArray().length; //prendo la lunghezza della stringa
        char [] dotBracket; //dichiaro l'array
        int i;  //inizializzo il contatore
        char [] Ps; //dichiaro un array
        Ps = primarySequence.toCharArray(); //inserisco la stringa di nucleotidi nell'array

        dotBracket = new char [N+1]; //inizializzo l'array

        if(isPseudoknotted()) { //controllo se ci sono pseudonodi nella stringa
            throw new IllegalStateException("Questa struttura contiene pseudonodi");
        }

        for(WeakBond bond : bonds){ //scorro tutte le coppie
            for (i = 1; i <= N; i++) { //per ogni coppia faccio un confronto con gli indici della coppia con un contatore
                if (bond.getI() == i) { //se l'indice di sinistra è uguale al contatore inserisco l'arco
                    dotBracket[i] = '(';
                }
                if (bond.getJ() == i) {   //se l'indice di destra è uguale al contatore inserisco l'arco
                    dotBracket[i] = ')';
                }
                //se all'interno della cella non sono presenti già i caratteri degli archi inserisco i punti
                if (!(dotBracket[i] == '(' || dotBracket[i] == ')')) {
                    dotBracket[i] = '.';
                }
            }
        }

        for(i=1;i<=N;i++){  //controllo se la stringa è maggiore di 50 caratteri se lo è vado a capo
            if(i%51==0){
                dotBracket[i]='\n'; //la dotbracket inizia da 1 per via degli indici degli accoppiamenti che iniziano da 1
                Ps[i-1]='\n'; //la stringa dei nucleotidi inizia da 0
            }
        }

        String dotBracketNotation = new String(dotBracket); //converto l'array di char in stringa
        String NucletodiString = new String(Ps);
        //aggiungo il carattere a capo alla fine della stringa dei
        // nucleotidi per far si che non si sovrapponga alla dotBracketNotation
        NucletodiString = NucletodiString+'\n';

        String result = new String();   //creo una nuova stringa
        //concateno le due stringhe precedentemente create, la seconda stringa và da 1 a N+1
        // perchè i valori della stringa partono da 0, per non avere problemi di stampe nulle
        result = NucletodiString + dotBracketNotation.substring(1,N+1);


        return result; //ritorno la stringa finale
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bonds == null) ? 0 : bonds.hashCode());
        result = prime * result
                + ((primarySequence == null) ? 0 : primarySequence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SecondaryStructure))
            return false;
        SecondaryStructure other = (SecondaryStructure) obj;
        if (bonds == null) {
            if (other.bonds != null)
                return false;
        } else if (!bonds.equals(other.bonds))
            return false;
        if (primarySequence == null) {
            if (other.primarySequence != null)
                return false;
        } else if (!primarySequence.equals(other.primarySequence))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        Iterator<WeakBond> i = this.bonds.iterator();
        if (i.hasNext()) {
            WeakBond current = i.next();
            while (i.hasNext()) {
                WeakBond next = i.next();
                sb.append(current.toString() + ", ");
                if (!i.hasNext()) {
                    sb.append(next.toString());
                    break;
                }
                current = next;
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
