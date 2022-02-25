package StringPeriod;

import java.util.*;


public class periodNaive {
	
    private interface GenerationString{
        String generaStringa(int n, int len);
    }
	
    private interface PeriodAlgorithm{
        int calculaPeriodo(String s);
    }
		
	/**
	 * Algoritmo che restituisce la lunghezza della più piccola sottostringa di s che si ripete
	 * Tempo atteso: O(n^2)
	 * @param s Stringa data in input
	 * @return periodo 
	 */    
    public static int periodNaive(String s){
        int len = s.length();
        int k;
        for(int p=1; p<len; p++){
            k = p;
            while(k < len && s.charAt(k) == s.charAt(k%p)){
                k++;
            }
            if(k == len){
                return p;
            }
        }
        return len;
    }

    /**
     * Algoritmo per generare una stringa casuale di lunghezza len e con n caratteri 
     * @param n numero caratteri appartenenti alla stringa
     * @param len lunghezza stringa in output
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
     * @param genera algoritmo di generazione delle stringhe
     * @param algoritmo algoritmo Period da analizzare
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
    	PeriodAlgorithm algoritmo = (String s) -> periodNaive(s);
    	
        System.out.println("\nlunghezza\ttempoMedio\n"); 
        misuraTempi(1000, 500000, genera, algoritmo); 
    }
}
