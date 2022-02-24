import java.util.*;

/*Si ricordi che un BST AVL deve soddisfare anche la seguente proprietà: per ogni nodo x, 
le altezze dei sottoalberi di sinistra e di destra nel nodo radicati in x differiscono al più di 1. */

public class AVL {
	
	//Procedura per calcolare l'altezza di un nodo
	public static int heightNode(Node x) {
		if (x == null) {
			return 0;
		} else {
			return x.height;
		}
	}
	
	//Procedura per trovare il valore massimo tra due interi
	public static int maxValue(int x, int y) {
		if(x > y) {
			return x;
		} else {
			return y;
		}
	}
	
	//Procedura per trovare il fattore di equilibrio
	public static int getBalanced(Node x) {
		if (x == null) {
			return 0;
		} else {
			return heightNode(x.left) - heightNode(x.right);
		}
	}
	
	//Metodo per eseguire una rotazione verso sinistra
	public static Node rotationLeft(Node x) {
		Node y = x.right;
		Node z = y.left;
		
		y.left = x;
		x.right = z;
		
		x.height = maxValue(heightNode(x.left), heightNode(x.right)) +1;
		y.height = maxValue(heightNode(y.left), heightNode(y.right)) +1;
		
		return y;
	}
	
	//Metodo per eseguire una rotazione verso destra
	public static Node rotationRight(Node y) {
		Node x = y.left;
		Node z = x.right;
		
		x.right = y;
		y.left = z;
		
		y.height = maxValue(heightNode(y.left), heightNode(y.right)) +1;
		x.height = maxValue(heightNode(x.left), heightNode(x.right)) +1;
		
		return x;
	}	
	
	//Procedura per eseguire la visita Pre Order
	public static void preOrder(Node n) {
		if (n == null) {
			System.out.print("Null");
		} else {
			System.out.print(n.key + ":" + n.value + ":" + n.height + "");
			preOrder(n.left);
			preOrder(n.right);
		}
	}
	
	//Procedura per eseguire la riceca di un nodo
	public static Node searchNodeAVL (Node x, int key) {
		if (x == null || key == x.key) {
			return x;
		} 
		if (key < x.key) {
			return searchNodeAVL(x.left, key);
		} else {
			return searchNodeAVL(x.right, key);
		}
	}
	
	//Procedura per eseguire l'inserimento di un nodo
	static Node insertNodeAVL(Node node, int key, String value, Tree T) {
		if (node == null) {
			return new Node(key, value);
		} else if (key < node.key) {
			node.left = insertNodeAVL(node.left, key, value, T);
		} else if (key > node.key) {
			node.right = insertNodeAVL(node.right, key, value, T);
		} else {
			return node;
		}
		
		node.height = 1 + maxValue(heightNode(node.left), heightNode(node.right));
		int balance = getBalanced(node);
		if (balance > 1 && key < node.left.key) {
			return rotationRight(node);
		} else if (balance < -1 && key > node.right.key) {
			return rotationLeft(node);
		} else if (balance > 1 && key > node.left.key) {
			node.left = rotationLeft(node.left);
			return rotationRight(node);
		} else if (balance < -1 && key < node.right.key) {
			node.right = rotationRight(node.right);
			return rotationLeft(node);
		}
		return node;
	}
	
    //Procedura per eseguire la stima dei tempi     
    public static void stimaTempi() {
    	        
        double start;
        double end;
		int num_elementi = 0;
		int esecuzioni;
		int a = 0;
		Random rd = new Random();

      
        double tempoMedio, tempoAmmortizzato, standardDeviation;
                
		for(int i = 0; i < 100; i++) {    
			
			tempoMedio = 0;
			tempoAmmortizzato = 0;
			esecuzioni = (int)(100.0 * Math.pow(Math.exp (Math.log(5000000.0 / 100.0) / 200.0), i));
	        double[] memoria_esecuzioni = new double[(int) esecuzioni];
            
	        for(int j = 0; j < esecuzioni; j++){
                start = System.nanoTime();
                do {
                	Tree T = new Tree();
            		if (rd.nextInt(500) >= 0) {
            			a = rd.nextInt(500);
            		}
                	for(int z = 0; z < num_elementi; z++) {
                		if(searchNodeAVL(T.root, a) == null) {  //NODO CHIAVE
                			Node x = new Node(a, "");  //CHIAVE VALORE
                			T = insertNodeAVL(x, a, "", T);			//NODO CHIAVE VALORE ALBERO
                		}
                	}
                	num_elementi = num_elementi + 1;
                	end  = System.nanoTime();
                } while((end - start) < (100 * (1/(0.01/2))+1)); //tempo_minimo
    	        memoria_esecuzioni[j] = (end - start) / (double) num_elementi;
	        }
	        
	        //calcolo la media
			for(int j = 0; j < esecuzioni; j++){
				tempoMedio += memoria_esecuzioni[j];
			}
			tempoMedio = tempoMedio/(double) esecuzioni;		
			
			//calcolo il tempo ammortizzato
            for(int j=0; j < esecuzioni; j++){
                tempoAmmortizzato += Math.pow((memoria_esecuzioni[j] - tempoMedio), 2);
            }
            tempoAmmortizzato = tempoAmmortizzato/esecuzioni;
            
            //calcolo lo scarto quadratico del tempo ammortizzato    
            standardDeviation = Math.sqrt(tempoAmmortizzato);
			System.out.println("\t"+tempoAmmortizzato+"\t"+ standardDeviation);       
		}
    }
	
	
	public static void main(String[] args) {
		RBT T = new RBT();
		
		Scanner scan = new Scanner(System.in);
        String input = "";
		while (true) {
			input = scan.nextLine();
			String[] a = input.split("");
			String[] array = new String[a.length];
			
			for(int i = 0; i < array.length; i++) {
				array[i] = a[i];
			}
			if(array[0].equals("Inserisci")) {
				int key = Integer.valueOf(array[1]);
				String value = array[2];
				NodeRB z = new NodeRB(key, value);
				T = insertNodeAVL(T, z);
			} else if(array[0].equals("Cerca")) {
				int key = Integer.valueOf(array[1]);
				Node z = searchNodeAVL(T.root, key);
				
				if (z == null) {
					System.out.println("Null");
				} else {
					System.out.print(z.value + " ");
				}
			} else if(array[0].equals("Stop")) {
				break;
			}
		}
        System.out.println("\nlungh\ttempoAmmortizzato\tstandardDeviation\n");  
        stimaTempi();
        
    }
	}