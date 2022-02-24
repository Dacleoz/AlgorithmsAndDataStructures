//Classe per inizializzare un nodo di un RedBlackTree (Albero Rosso-Nero)

public class NodeRB {
	
	/*RBT: Ogni nodo oltre a contenere una chiave numerica (di tipo intero) e 
	un valore alfanumerico (di tipo stringa) è caratterizzato da un colore (o rosso o nero), inizialmente rosso*/
	int key;
	String value;
	NodeRB parent, left, right;
	Color color;
		
	public NodeRB (int k, String value) {
		key = k;
		parent = null;
		left = null;
		right = null;
		color = Color.Red;
		this.value = value;
	}
	
	public boolean isEmpty() {
		return (this == null);
	}
	

	public void paste() {
		System.out.println(this.key);
	}
}

//la variabile Color può assumere solo due valore: o Red o Black
enum Color {
	Red, Black
}
