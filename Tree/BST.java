import java.util.*;

public class BST {
		
	/**
	 * Algoritmo per la ricerca di un nodo
	 * @param x nodo 
	 * @param a chiave del nodo x
	 */
	public static Node searchNode (Node x, int k) {
		if (x == null || k == x.key) {
			return x;
		} 
		if (k < x.key) {
			return searchNode(x.left, k);
		} else {
			return searchNode(x.right, k);
		}
	}
	
	/**
	 * Algoritmo per l'inserimento di un nodo
	 * @param tree albero in cui inserire
	 * @param node nodo da inserire
	 */
	public static Tree insertNode(Tree T, Node x) {
		Node y = T.nil;
		if (T.root == null) {
			T.root = x;
		} else {
			Node z = T.root;
			while (z != null) {
				y = z;
				if (x.key < z.key) {
					z = z.left;
				} else {
					z = z.right;
				}
			}
			x.parent = y;
			if (y == null) {
				T.root = x;
			} else if (x.key < y.key) {
				y.left = x;
			} else {
				y.right = x;
			}
		}
		//System.out.println("Il nodo " + x.keyNode + " è stato inserito nell'albero");
		return T;
	}
	
	//Algoritmo per visita Pre-Order dell'albero
	public static void preOrder(Node x) {
		if (x == null) {
			System.out.print("Null");
		} else {
			System.out.print(x.key + ":" + x.value + ":");
			preOrder(x.left);
			preOrder(x.right);
		}
	}
	
	
    //Algoritmo per la stima dei tempi     
    public static void stimaTempi() {
   
        double start;
        double end;
		int num_elementi;
		int esecuzioni;
		int a = 0;
		Random rd = new Random();

        
        double tempoMedio, tempoAmmortizzato, standardDeviation;
        
                
		for(int i = 0; i < 100; i++) {     
			
			tempoMedio = 0;
			tempoAmmortizzato = 0;
			esecuzioni = (int) (100.0 * Math.pow(Math.exp (Math.log(5000000.0 / 100.0) / 200.0), i));
			System.out.print(" " + esecuzioni + " ");
			double[] memoria_esecuzioni = new double[esecuzioni];
			
			for(int j = 0; j < esecuzioni; j++){
				num_elementi = 0;
				start = System.nanoTime();
                do {
                	Tree T = new Tree();
            		if (rd.nextInt(500) >= 0) {
            			a = rd.nextInt(500);
            		}
                	for(int z = 0; z < num_elementi; z++) {
                		if(searchNode(T.root, a) == null) {
                			Node x = new Node(a, "");
                			T = insertNode(T, x);		
                		}
                	}
                	end  = System.nanoTime();
            		num_elementi = num_elementi + 1;
                } while((end - start) < (100 * (1/(0.01/2))+1)); //tempo_minimo
	        	memoria_esecuzioni[j] = (end - start) / (double) num_elementi;
            }
			
			//calcolo la media
			for(int j = 0; j < esecuzioni; j++) {
				tempoMedio += memoria_esecuzioni[j];
			}
            tempoMedio = tempoMedio/(double) esecuzioni;
            
            //calcolo il tempo ammortizzato
            for(int j = 0; j < esecuzioni; j++) {
				tempoAmmortizzato += Math.pow((memoria_esecuzioni[j] - tempoMedio), 2);
			}
            tempoAmmortizzato = tempoMedio/esecuzioni;
			
            //calcolo lo scarto quadratico del tempo ammortizzato
            standardDeviation = Math.sqrt(tempoAmmortizzato);
			System.out.println("\t"+tempoMedio+"\t"+tempoAmmortizzato+ "\t" + standardDeviation);       
		}
    }
    
	
    public static void main (String[] args) {
    	
    	Tree T = new Tree();
    	/*
    	Scanner scan = new Scanner(System.in);
		while (true) {
			String input = scan.nextLine();
			String[] a = input.split(" ");
			String[] array = new String[a.length];
			for (int y = 0; y < array.length; y++) {
				array[y] = a[y];
			}
			if (array[0].equals("Insert")) {
				int key = Integer.valueOf(array[1]);
				String value = array[2];
				Node x = new Node(key, value);
				insertNode(T, x);
				
			} else if(array[0].equals("Search")) {
				int key = Integer.valueOf(array[1]);
				Node z = searchNode(T.root, key);
				
				if (z == null) {
					System.out.println("Null");
				} else {
					System.out.print(z.value + " ");
				}
			} else if(array[0].equals("Stop")) {
				break;
			}
		}*/
    	
        System.out.println("\ntempoMedio\ttempoAmmortizzato\tstandardDeviation\n"); 
        stimaTempi();  
	
	}
}
