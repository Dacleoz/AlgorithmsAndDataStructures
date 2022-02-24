public class Node {
	
	/*BST: Ogni nodo deve contenere una chiave numerica (di tipo intero) e 
	un valore alfanumerico (di tipo stringa)*/
	int key;
	String value;
	Node parent, left, right;
	
	//AVL: ogni nodo ha anche un'altezza (di tipo intero)
	int height;
	
	
	public Node (int k, String value) {
		key = k;
		parent = null;
		left= null;
		right = null;
		height = 1;
		this.value = value;
	}
	
	public boolean isEmpty() {
		return (this == null);
	}
	

	public void paste() {
		System.out.println(this.key);
	}
}