import java.util.Random;

import java.util.*;


public class periodoSmart {
	
	
    private interface GenerationString{
        String generaStringa(int n, int len);
    }
	
    private interface PeriodAlgorithm{
        int calculaPeriodo(String s);
    }
	
    /**
     * Procedura che restituisce la lunghezza della più piccola sottostringa
     * di s che si ripete nella stringa s iniziale
     * Tempo atteso: crescita lineare
     * @param s stringa data in input
     * @return lunghezza della sottostringa
     */
    public static int periodSmart(String s){
        if(s.equals("")){
            return 0;
        }
        int lengthS = s.length();
        int[] a = new int[lengthS];
        a[0] = 0;
        int z;
        for(int i=1; i<lengthS; i++){
            z = a[i-1];
            while(s.charAt(i) != s.charAt(z) && z > 0){
                z = a[z-1];
            }
            if(s.charAt(i) == s.charAt(z)){
                a[i] = z + 1;
            }else{
                a[i] = 0;
            }
        }
        return lengthS - a[lengthS-1];
    }
	
    /**
     * Algoritmo per generare una stringa casuale di lunghezza len e con n caratteri 
     * @param n indica il numero di caratteri appartenenti alla stringa
     * @param len indica la lunghezza della stringa in output
     * @return s stringa casuale
     */
	
	public static String stringGenerator(int n, int len) {
		Random r = new Random();
		StringBuilder s = new StringBuilder(len);
		for(int i = 0; i < len; i++) {
			s.append((char)(r.nextInt(n) + 97));
		}
		return s.toString();
	}
	
    /*public static String generaStringaCasualeConPeriodo(int numeroCaratteri ,int lunghezza_stringa){
        //genera una stringa di lunghezza lunghezza_stringa, la stringa in questione è mutabile
        StringBuilder str = new StringBuilder(lunghezza_stringa);
        Random r = new Random();
        int q = r.nextInt(lunghezza_stringa);
        for(int i=0; i<=q; i++){
            str.append((char)(r.nextInt(numeroCaratteri)+97));
        }
        for (int i = q+1; i < lunghezza_stringa; i++) {
            str.append(str.charAt(i % (q + 1)));
        }
        return str.toString();
    }*/
	
    //Algoritmo che da la risoluzione minima misurabile dal calcolatore
	private static double getResolution() {
		double start = System.nanoTime();
		double end;
		do {
			end = System.nanoTime();
		} while (start == end);
		return end - start;
	}
	

    /**
     * Stima i tempi di un algoritmo per il calcolo del periodo minimo di una stringa
     * @param min minimo valore da cui deve partire la stima
     * @param max massimo valore a cui deve arrivare la stima
     * @param gen algoritmo di generazione delle stringhe
     * @param alg algoritmo Period da analizzare
     */
    private static void misuraTempi(int min, int max, GenerationString genera, PeriodAlgorithm algoritmo){

        double errore_massimo = 1.d/1000.d;  //calcolo errore massimo ammissibile pari a 0.001
        double R = getResolution();
        double tempo_minimo = R * (1.d/errore_massimo + 1.d);   //calcolo tempo minimo in base all'errore massimo e alla risoluzione

        double A, B;   
        A = min;
        B = Math.pow(((double)max)/A, 1.d/99.d);
        
        double start;
        double end;
        int lungh;
        int k;
        String s;
        
        double[] array = new double[10];
        double tempoMedio = 0;
        
		for(int i = 0; i < 100; i++) {
            
            //ricavo la lunghezza della stringa da analizzare e la stampo a video
            lungh = (int)(A*Math.pow(B, i));
            System.out.print(" " + lungh + " ");
            
            //Eseguo 10 iterazioni, ciascuna delle quali  misurata in modo da ottenere
            //la precisione voluta, in questo modo la media di tutte le esecuzioni sarà a sua volta in
            //quell'ordine di precisione
            for(int j = 0; j < 10; j++){
                k = 0;
                s = genera.generaStringa(2, lungh);
                start = System.nanoTime();
			do {
				algoritmo.calculaPeriodo(s);
				end  = System.nanoTime();
				k = k + 1;
			} while((end - start) < tempo_minimo);
			array[j] = (end-start)/(double)k;
		}
        
            //calcolo la media dei tempi ottenuti per la data lunghezza
            
            for(int j=0; j < 10; j++){
            	tempoMedio = tempoMedio + array[j];
            }
            tempoMedio = tempoMedio/10;   //10 = numero iterazioni eseguite          
            System.out.println("\t"+tempoMedio);
		}
    }
    
    public static void main(String args[]){
    	
    	GenerationString genera = (int a, int b) -> stringGenerator(a, b);
    	PeriodAlgorithm algoritmo = (String s) -> periodSmart(s);;
    	
        System.out.println("\nlunghezza\ttempoMedio\n"); 
        misuraTempi(1000, 500000, genera, algoritmo); 
    }
}