/**
 * 
 */
package it.unicam.cs.asdl1819.project1;


import java.util.*;


/**
 * Implementazione dell'algoritmo di Nussinov per trovare, data una sequenza di
 * nucleotidi, una struttura secondaria senza pseudonodi che ha un numero
 * massimo di legami deboli.
 * 
 * @author Luca Tesei
 *
 */
public class NussinovFolder implements FoldingAlgorithm {

    private final String primarySequence;
    private final int[][] dp;   //matrice

    private List<WeakBond> pairList = new ArrayList<>();    //lista delle coppie

    private int N; //lunghezza stringa RNA
    private boolean folded; //flag per controllo sul folding
    // TODO inserire altre variabili istanza

    /**
     * Costruisce un solver che utilizza l'algoritmo di Nussinov.
     * 
     * @param primarySequence
     *                            la sequenza di nucleotidi di cui fare il
     *                            folding
     * 
     * @throws IllegalArgumentException
     *                                      se la primarySequence contiene dei
     *                                      codici di nucleotidi sconosciuti
     * @throws NullPointerException
     *                                      se la sequenza di nucleotidi è nulla
     */
    public NussinovFolder(String primarySequence) {
        if (primarySequence == null)
            throw new NullPointerException(
                    "Tentativo di costruire un solutore Nussinov a partire da una sequenza nulla");
        String seq = primarySequence.toUpperCase().trim();
        // check bases in the primary structure - IUPAC nucleotide codes
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
        N = primarySequence.toCharArray().length; //prendo la lunghezza dells stringa
        this.dp = new int[N+1][N+2]; //creo una matrice con le dimensioni n+1 x (n+2) per poi non avere problemi nei controlli delle caselle sul traceback


        // TODO continuare le operazioni del costruttore
    }

    public String getName() {
        return "NussinovFolder";
    }

    @Override
    public String getSequence() {
        return this.primarySequence;
    }

    @Override
    public SecondaryStructure getOneOptimalStructure() {

        SecondaryStructure s = new SecondaryStructure(primarySequence);

        if (!isFolded()) throw new IllegalStateException("Il folding sulla sequenza ancora non è stato eseguito");

        pairList.forEach((coppia)->{//scorro la lista
            s.add(coppia);  //aggiungo gli indici dell'arco
        });
        return s;
    }

    public void traceback(int i, int j){

        if (!isFolded()) throw new IllegalStateException("Il folding sulla sequenza ancora non è stato eseguito");

        if(j<=i){
            return;
            //controllo la casella a sinistra e vedo se i due valori sono uguali se true ricorro
        }else if(dp[i][j] == dp[i][j-1]){
            traceback(i,j-1);
            return;
        }else{
            for(int k = i; k < j; k++){
                if (this.primarySequence.charAt(k-1) + this.primarySequence.charAt(j-1) == 138
                        || this.primarySequence.charAt(k-1) + this.primarySequence.charAt(j-1) == 150
                        || this.primarySequence.charAt(k-1) + this.primarySequence.charAt(j-1) == 156){
                    //se la somma di questi tre valori sono uguali al valore della posizione corrente allora aggiungo l'arco
                    if (dp[i][j] == dp[i][k - 1] + dp[k + 1][j - 1] + 1) {
                        pairList.add(new WeakBond(k,j));
                        traceback(i, k - 1);
                        traceback(k + 1, j - 1);
                        return;
                    }
                }

            }
        }
    }

    @Override
    public void fold(){
        try {
            for (int d = 1; d <= N; d++) { //d valore che tiene conto del numero della diagonale
                for (int i = 1; i <= (N - d); i++) { //per riempimento celle
                    int j = i + d;
                    for (int k = i; k <= j; k++) { //for per scorrere la sequenza e trovare il maggior numero di legami della sotto sequenza
                        //i-1 e j-1 vengono decrementati di 1 perchè la stringa parte da 0,N-1 mentre la matrice utilizza delle indicizzazioni diverse
                        //controllo degli accoppiamenti AU-UA GC-CG ( == 138 e == 150 è riferito al valore ASCII) e anche i legami deboli GU-UG
                        if (this.primarySequence.charAt(i - 1) + this.primarySequence.charAt(j - 1) == 138
                                || this.primarySequence.charAt(i - 1) + this.primarySequence.charAt(j - 1) == 150
                                || this.primarySequence.charAt(i - 1) + this.primarySequence.charAt(j - 1) == 156) {
                            dp[i][j] = 1 + dp[i + 1][j - 1]; //se l'accoppiamento è buono controllo il valora sulla diagonale e lo sommo a 1
                        } else {
                            //se l'accoppiamento non è buono controllo il valore massimo tra le tre caselle adiacenti e lo inserisco nella casella corrente
                            dp[i][j] = Math.max(dp[i + 1][j - 1], Math.max(dp[i][j - 1], dp[i + 1][j]));
                        }
                    }
                }
            }
            folded=true;    //se tutto è andato bene folded viene messo a true
        }catch (Exception e){
            folded=false;   //se c'è un errore folded viene messo a false
        }
    }


    @Override
    public boolean isFolded() {
        // TODO implementare

        if(!folded){  //controllo se il folding è stato eseguito correttamente o no
            return false;   //folding non eseguito
        }else{
            return true;    //folding eseguito
        }
    }

    public int[][] getDp(){ //metodo utilizzato per un test in NussinovFolderTest
        return dp;
    }

}