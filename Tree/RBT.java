import java.util.*;

/* Si ricordi che in un RBT, ogni nodo ha associato un colore associato, che può essere rosso o nero.
Un RBT deve soddisfare anche la seguente proprietà: per ogni nodo x, le altezze nere dei sotto-alberi di sinistra 
e di destra nel nodo x coincidono */
public class RBT {
	NodeRB root;	//il primo nodo è la radice dell'albero
	NodeRB NIL;
	
	public RBT( ) {
		NodeRB nilNode = new NodeRB(0, "");
		nilNode.color = Color.Black;
		this.NIL = nilNode; 
		this.root = this.NIL;
	}
	
	//Procedura che implementa la visita In Order di  un albero
	public void inOrder (NodeRB x) {
		if (x != this.NIL) {
			inOrder(x.left);
			System.out.println(x.key);
			inOrder(x.right);
		}
	}
	
	//Procedura che implementa la visita Pre Order di  un albero
	public void preOrder (NodeRB x) {
		if (x != this.NIL) {
			System.out.println(x.key);
			inOrder(x.left);
			inOrder(x.right);
		}
	}
	
	//Procedura che implementa la rotazione verso destra
	public static void rotateRight (RBT T, NodeRB x) {
		NodeRB a = x.left;
		x.left = a.right;
		if (a.right != T.NIL) {
			a.right.parent = x;
		}
		a.parent = x.parent;
		if (x.parent == T.NIL) {
			T.root = a;
		} else if (x == x.parent.right) {
			x.parent.right = a;
		} else {
			x.parent.left = a;
		}
		a.right = x;
		x.parent = a;
	}
	
	//Procedura che implementa la rotazione verso sinistra
	public static void rotateLeft (RBT T, NodeRB x) {
		NodeRB a = x.right;
		x.right = a.left;
		if (a.left != T.NIL) {
			a.left.parent = x;
		}
		a.parent = x.parent;
		if (x.parent == T.NIL) {
			T.root = a;
		} else if (x == x.parent.left) {
			x.parent.left = a;
		} else {
			x.parent.right = a;
		}
		a.left = x;
		x.parent = a;
	}
	
	//Procedura che implemeta la ricerca di un nodo
	public static NodeRB searchNodeRBT (NodeRB x, int a) {
		if (x == null || a == x.key) {
			return x;
		}
		if (a < x.key) {
			return searchNodeRBT(x.left, a);
		} else {
			return searchNodeRBT(x.right, a);
		}		
	}
	
	//Procedura che implemeta l'inserimento di un nodo: si ricordi che l'inserimento fa uso di una procedura ausiliaria
	public static RBT insertNodeRBT (RBT T, NodeRB x) {
		NodeRB a = T.NIL;
		NodeRB t = T.root;
		
		while (t != T.NIL) {
			a = t;
			if (x.key < t.key) {
				t = t.left;
			} else {
				t = t.right;
			}
		}
		x.parent = a;
		if (a == T.NIL) {
			T.root = x;
		} else if (x.key < a.key) {
			a.left = x;
		} else {
			a.right = x;
		}
		x.right = T.NIL;
		x.left = T.NIL;
		correctInsert(T, x);
		return T;
	}
	
	//Procedura che attraverso rotazioni e ricolorazione mantiene le proprietà del RBT
	public static void correctInsert (RBT T, NodeRB x) {
		while (x.parent.color == Color.Red) {
			if (x.parent == x.parent.parent.left) {
				NodeRB a = x.parent.parent.right;
				if (a.color == Color.Red) {
					x.parent.color = Color.Black;
					a.color = Color.Black;
					x.parent.parent.color = Color.Red;
					x= x.parent.parent;
				} else {
					if (x == x.parent.right) {
						x = x.parent;
						rotateLeft(T, x.parent.parent);
					}
					x.parent.color = Color.Black;
					x.parent.parent.color = Color.Red;
					rotateRight(T, x.parent.parent);
				}
			} else {
				NodeRB a = x.parent.parent.left;
				if (a.color == Color.Red) {
					x.parent.color = Color.Black;
					a.color = Color.Black;
					x.parent.parent.color = Color.Red;
					x = x.parent.parent;
				} else {
					if (x== x.parent.left) {
						x = x.parent;
						rotateRight(T, x);
					}
					x.parent.color = Color.Black;
					x.parent.parent.color = Color.Red;
					rotateLeft(T, x.parent.parent);
				}
			}
		}
		T.root.color = Color.Black;
	}
	
	//Procedura per stampare l'albero
	public static void printRBT(NodeRB x){
        	if (x.value != "") {
        	System.out.print(x.key + ":" + x.value + ":" + x.color.name() + " ");
            printRBT(x.left); 
            printRBT(x.right);
        } else {
        	System.out.print("NULL ");
        }
    }
	
    //Algoritmo per la stima dei tempi     
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
                		if(searchNodeRBT(T.root, a) == null) {
                			NodeRB x = new NodeRB(a, "");
                			T = insertNodeRBT(T, x);
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
				T = insertNodeRBT(T, z);
			} else if (array[0].equals("Cerca")) {
				int key = Integer.valueOf(array[1]);
				NodeRB n = searchNodeRBT(T.root, key);
				if (n == null) {
					System.out.println("Null");
				} else {
					System.out.print(n.value + " ");
				}	
			} else if (array[0].equals("Stampa")) {
				printRBT(T.root);
			} else if (array[0].equals("Stop")) {
				break;
			}	else break;
		}
        System.out.println("\nlungh\ttempoAmmortizzato\tstandardDeviation\n");  
        stimaTempi();
        
    }
}	

