package p2Grafos;

import java.text.DecimalFormat;

/**
 * Clase Graph
 * @author MariaTeresaFernandezCoro - UO263728
 * @param <T>, clase de tipo generico
 */
public class Graph<T> {  
	
	protected T[] nodes; // Vector de nodos
	protected boolean[][] edges; // Matriz de aristas
	protected double[][] weights; // Matriz de pesos
	protected int numNodes; // Número de elementos en un momento dado
	
	protected double[] D; // Vector de costes minimos
	protected int[] P; // Vector de rutas de costes minimos
	protected boolean[] S; // Vector que indica si se conoce el coste minimo
	
	private static final double Inf = Double.POSITIVE_INFINITY; // Variable con valor infinito

	protected double[][] aFloyd; // Matriz de costes minimos
	protected int[][] pFloyd; // Matriz de rutas de costes minimos
	
	private boolean[] visited; // Vector de nodos visitados para el metodo recorridoProfundidad
	private String recorrido; // Recorrido en profundidad desde un nodo del grafo
	
	/**
	 * Constructor de la clase
	 * Complejidad: O(1)
	 * @param tam, numero maximo de nodos del grafo, Integer
	 */
	@SuppressWarnings("unchecked")   
	public Graph(int tam) {   
		nodes = (T[])new Object[tam];
		edges = new boolean[tam][tam];
		weights = new double[tam][tam];
	}
	
	/**
	 * Constructor de la clase
	 * Complejidad: O(n)
	 * @param tam, Integer
	 * @param initialNodes, T[]
	 * @param initialEdges, boolean[][]
	 * @param initialWeights, double[][]
	 */
	public Graph(int tam, T[] initialNodes, boolean[][] initialEdges, double[][] initialWeights) {
		// Llama al constructor original
		this(tam); 
		// Pero modifica los atributos con los parametros para hacer pruebas...
		numNodes = initialNodes.length;
		for (int i=0;i<numNodes;i++) {
			nodes[i]=initialNodes[i];
			for (int j=0;j<numNodes;j++){
				edges[i][j]=initialEdges[i][j];
				weights[i][j]=initialWeights[i][j];
			}
		}
	}
	
	/**
	 * Constructor de la clase
	 * Complejidad: O(n)
	 * @param tam, Integer
	 * @param initialNodes, T[]
	 * @param initialEdges, boolean[][]
	 * @param initialWeights, double[][]
	 * @param initialAfloyd, double[][]
	 * @param initialPfloyd, int[][]
	 */
    public Graph (int tam, T[] initialNodes, boolean[][] initialEdges, double[][] initialWeights, double[][] initialAfloyd, int[][] initialPfloyd) { 
        // Llama al constructor anterior de inicialización 
        this(tam, initialNodes,initialEdges,initialWeights);  
        // Pero modifica los atributos que almacenan las matrices de Floyd con los parámetros para hacer pruebas... 
        if (initialAfloyd!=null && initialPfloyd!=null){ 
            aFloyd=initialAfloyd; 
            pFloyd=initialPfloyd; 
        } 
    }

	/**  
	* Inserta un nuevo nodo que se le pasa como parámetro 
	* Complejidad: O(n)
	* @param node, T
	* @return 0, si lo inserta correctamente,
	* si ya existe y además no cabe, no lo inserta y devuelve -3,
	* si ya existe, pero sí cabría, no lo inserta y devuelve -1,
	* si no existe, pero no cabe, no lo inserta y devuelve -2,
	* si el nodo a insertar no es válido, devuelve –4,
	* si además de no ser válido no cabría, devuelve -6
	*/   
	public int addNode(T node) {
		if (node != null) {
			if (existsNode(node)) {
				if (numNodes < nodes.length)
					return -1;
				return -3;
			}
			if (numNodes < nodes.length) {
				nodes[numNodes] = node;
				numNodes++;
				if (getAFloyd() != null || getPFloyd() != null) { // Invalido matrices de Floyd
					aFloyd = null;
					pFloyd = null;
				}
				return 0;
			}
			return -2;
		}
		if (numNodes < nodes.length)
			return -4;
		return -6;
	}
	
	/**
	 * Obtiene el indice de un nodo en el vector de nodos
	 * Complejidad: O(n)
	 * @param node, T
	 * @return i, indice del nodo,
	 * –1, si no existe
	 * ¡¡¡ OJO que es privado porque depende de la implementación !!!  
     */  
	private int getNode(T node) {
		if (node != null) {
			for (int i = 0; i < numNodes; i++) {
				if (nodes[i] == node)
					return i;
			}
		}
		return -1;
	}  

	/**  
	 * Indica si existe o no el nodo en el grafo
	 * Complejidad: O(n)
	 * @param node, T
	 * @return true, si existe,
	 * false, si no existe
	 */  
	public boolean existsNode(T node) {
		if(getNode(node) != -1)
			return true;
		return false;
	}
	  
	/**
	 * Inserta una arista con el peso indicado (menor que 0) entre dos nodos, uno origen y
	 * otro destino. Si la arista existe, no la inserta.
	 * Complejidad: O(n)
	 * @param source, T
	 * @param target, T
	 * @param edgeWeight, double
	 * @return 0, si la inserta,
	 * -1, -2 o -3, si no existe nodos origen, destino o ambos (suma de los anteriores) respectivamente,
	 * -4, si ya existe,
	 * -8, si el peso no es válido
	 */   
	public int addEdge(T source, T target, double edgeWeight) {
		if (edgeWeight < 0)
			return -8;
		if (!existsNode(source) && !existsNode(target))
			return -3;
		if (!existsNode(source))
			return -1;
		if (!existsNode(target))
			return -2;
		if (existsEdge(source, target))
			return -4;
		edges[getNode(source)][getNode(target)] = true;
		weights[getNode(source)][getNode(target)] = edgeWeight;
		if (getAFloyd() != null || getPFloyd() != null) { // Invalido matrices de Floyd
			aFloyd = null;
			pFloyd = null;
		}
		return 0;
	}  

	/**  
	 * Comprueba si existe una arista entre dos nodos que se pasan como parámetro
	 * si alguno de los nodos no existe, no existe la arista
	 * Complejidad: O(n)
	 * @param source, T
	 * @param target, T
	 * @return true, si existe,
	 * false, si no existe
	 */  
	public boolean existsEdge(T source, T target) {
		if (existsNode(source) && existsNode(target))
			return edges[getNode(source)][getNode(target)];
		return false;
	}  

	/** 
	 * Devuelve el peso de la arista que conecta dos nodos
	 * Complejidad: O(n)
	 * @param source, T
	 * @param target, T
	 * @return peso de la arista,
	 * –1, -2 o –3, si no existe origen, destino o ambos,  
	 * –4, si no existe la arista pero sí los nodos origen y destino
	 */  
	public double getEdge(T source, T target) {
		if (!existsNode(source) && !existsNode(target))
			return -3;
		if (!existsNode(source))
			return -1;
		if (!existsNode(target))
			return -2;
		if(!existsEdge(source, target))
			return -4;
		return weights[getNode(source)][getNode(target)];
	}

	/**  
	 * Borra la arista del grafo que conecta dos nodos  
	 * Complejidad: O(n)
	 * @param source, T
	 * @param target, T
	 * @return 0, si lo borra,
	 * –1, -2 o –3, si no existe origen, destino o ambos,  
	 * –4, si no existe la arista pero sí los nodos origen y destino
	 */ 
	public int removeEdge(T source, T target) {
		if (!existsNode(source) && !existsNode(target))
			return -3;
		if (!existsNode(source))
			return -1;
		if (!existsNode(target))
			return -2;
		if(!existsEdge(source, target))
			return -4;
		edges[getNode(source)][getNode(target)] = false;
		if (getAFloyd() != null || getPFloyd() != null) { // Invalido matrices de Floyd
			aFloyd = null;
			pFloyd = null;
		}
		return 0;
	}

	/**  
	 * Borra el nodo deseado del vector de nodos así como las aristas de las que  
	 * forma parte
	 * Complejidad: O(n)
	 * @param node, T
	 * @return 0, si lo borra,
	 * -1, si no lo borra
	 */  
	public int removeNode(T node) {
		if (!existsNode(node))
			return -1;
		int indice = getNode(node);
		numNodes--;
		if (indice != numNodes) {
			nodes[indice] = nodes[numNodes];
			for (int i = 0; i < numNodes; i++) {
				if (existsEdge(nodes[i], nodes[numNodes])) {
					edges[i][indice] = true;
					weights[i][indice] = weights[i][numNodes];
					edges[i][numNodes] = false;
				}
				else
					edges[i][indice] = false;
				if (existsEdge(nodes[numNodes], nodes[i])) {
					edges[indice][i] = true;
					weights[indice][i] = weights[numNodes][i];
					edges[numNodes][i] = false;
				}
				else
					edges[indice][i] = false;
			}
			if (existsEdge(nodes[numNodes], nodes[numNodes])) {
				edges[indice][indice] = true;
				weights[indice][indice] = weights[numNodes][numNodes];
				edges[numNodes][numNodes] = false;
			}
			else
				edges[indice][indice] = false;
		}
		else { // ultimo nodo del grafo
			for (int i = 0; i < numNodes; i++) {
				edges[i][indice] = false;
				edges[indice][i] = false;
			}
			edges[indice][indice] = false;
		}
		if (getAFloyd() != null || getPFloyd() != null) { // Invalido matrices de Floyd
			aFloyd = null;
			pFloyd = null;
		}
		return 0;
	}
	
	/**
	 * Algoritmo de Dijkstra para encontrar el camino de coste mínimo desde nodoOrigen   
	 * hasta el resto de nodos. 
	 * Complejidad: O(n^2)
	 * @param nodoOrigen, T
	 * @return D, vector de costes minimos de Dijkstra
	 * null, si no existe el nodoOrigen (o es inválido como parámetro)
	 */  
	public double[] dijkstra(T nodoOrigen) {
		if (!existsNode(nodoOrigen) || nodoOrigen == null)
			return null;
		inicializarDijkstra(nodoOrigen);
		int comprobados = 1; // Parto de conocer el coste de ir a si mismo
		S[getNode(nodoOrigen)] = true;
		int indice = 0;
		while (comprobados < D.length) { // n-1 iteraciones
			indice = minCost(D, S); // Selecciono coste minimo
			if (indice == -1)
				return D; // No sigo aplicando Dijkstra
			S[indice] = true;
			comprobados++;
			for (int m = 0; m < D.length; m++) {
				if (!S[m] && existsEdge(nodes[indice], nodes[m])) {
					if (D[indice] + weights[indice][m] < D[m] || D[m] == 0) {
						D[m] = D[indice] + weights[indice][m];
						P[m] = indice;
					}
				}
			}
		}
		return D;
	}
	
	/**
	 * Inicializa los vectores D, P y S de Dijkstra
	 */
	private void inicializarDijkstra(T nodoOrigen) {
		D = new double[numNodes];
		P = new int[numNodes];
		S = new boolean[numNodes];
		for (int i = 0; i < D.length; i++) { // Evaluo costes desde nodoOrigen
			if (existsEdge(nodoOrigen, nodes[i]) && nodoOrigen != nodes[i])
				D[i] = weights[getNode(nodoOrigen)][i]; // Camino directo
			else if (nodoOrigen == nodes[i])
				D[i] = 0; // Camino a si mismo
			else
				D[i] = Inf; // No hay camino directo
			P[i] = -1;
		}
	}

	/**
	 * Busca el nodo con distancia minima en D al resto de nodos
	 * Complejidad: O(n)
	 * @param vectorD, double[], vector D de dijkstra
	 * @param visited, boolean[], conjunto de visitados
	 * @return el indice del nodo buscado, int 
	 * Si hay varios con mismo coste devuelve el que tenga indice más bajo en el vector de nodos 
	 * o -1 si el grafo es no conexo o no quedan nodos sin visitar  
	 */  
	private int minCost(double[] vectorD, boolean[] visited) {
		double minCost = Inf;
		int indice = -1;
		for (int i = 0; i < D.length; i++) {
			if (minCost > D[i] && D[i] != 0 && !S[i]) {
				minCost = D[i];
				indice = i;
			}
		}
		return indice;
	}
	
	/**
	 * Aplica el algoritmo de Floyd al grafo
	 * Complejidad: O(n^3)
	 * n iteraciones
	 * @return 0, si lo aplica y genera las matrices A y P
	 * –1, si no consigue generar las matrices correctamente 
	 */  
	public int floyd() {
		inicializarFloyd();
		for (int k = 0; k < numNodes; k++) {
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < numNodes; j++) {
					if (aFloyd[i][k] + aFloyd[k][j] < aFloyd[i][j]) {
						aFloyd[i][j] = aFloyd[i][k] + aFloyd[k][j];
						pFloyd[i][j] = k;
					}
				}
			}
		}
		if (aFloyd == null || pFloyd == null)
			return -1;
		return 0;
	}
	
	/**
	 * Inicializa las matrices A y P de Floyd
	 */
	private void inicializarFloyd() {
		aFloyd = new double[numNodes][numNodes];
		pFloyd = new int[numNodes][numNodes];
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				if (i == j)
					aFloyd[i][j] = 0;
				else if (weights[i][j] > 0)
					aFloyd[i][j] = weights[i][j];
				else
					aFloyd[i][j] = Inf;
				pFloyd[i][j] = -1;
			}
		}
	}
	
	/**   
     * Obtiene la matriz A de Floyd
	 * @return aFloyd, double[][]   
	 * Si no se ha invocado a Floyd retornara null, no lo invoca  
	 */   
	protected double[][] getAFloyd() {
		return this.aFloyd;
	}   

	/**   
	 * Obtiene la matriz P de Floyd
	 * @return pFloyd, int[][]   
	 * Si no se ha invocado a Floyd retornara null, no lo invoca
	 */   
	protected int[][] getPFloyd() {
		return this.pFloyd;
	}   

	/**
	 * Calcula el coste del camino de coste minimo entre los nodos pasados como parametros.
	 * Si no están generadas las matrices de Floyd, las genera.
	 * @param source, T
	 * @param target, T
	 * @return el coste minimo, valor de la matriz A de Floyd  
	 *  -1, si no puede obtener el valor por alguna razón  
	 */  
	public double minCostPath(T source, T target) {
		if (!existsNode(source) || !existsNode(target))
			return -1;
		if (getAFloyd() == null || getPFloyd() == null)
			floyd();
		if (getAFloyd()[getNode(source)][getNode(target)] == Inf)
			return -1;	// No existe camino entre dichos nodos
		return getAFloyd()[getNode(source)][getNode(target)];
	}
	
	/**  
	 * Indica el camino entre los nodos que se le pasan como parámetros en un String de esta forma:  
	 * 		Origen\t(coste0)\tIntermedio1\t(coste1)\t…\t(costeN)\tDestino\t  
	 * 		Si no hay camino: Origen\t(Infinity)\tDestino\t 
	 * 		Si Origen y Destino coinciden: Origen\t
	 * 		Si no existen los 2 nodos devuelve una cadena vacía  
	 * @param origen, T
	 * @param destino, T
	 * @return el camino entre los nodos, String
	 */  
	public String path(T origen, T destino) {
		String path = "";
		if (!existsNode(origen) || !existsNode(destino))
			return path;
		if (getAFloyd() == null || getPFloyd() == null)
			floyd();
		int posOrigen = getNode(origen);
		int posDestino = getNode(destino);
		if (origen == destino)
			path += origen + "\t";
		else if (getAFloyd()[posOrigen][posDestino] == Inf)
			path += origen + "\t(Infinity)\t" + destino + "\t";
		else if (getPFloyd()[posOrigen][posDestino] == -1)	// existe camino directo
			path += origen + "\t(" + getAFloyd()[posOrigen][posDestino] + ")\t" + destino + "\t";
		else {
			int posNodoIntermedio = getPFloyd()[getNode(origen)][getNode(destino)];
			path += origen + "\t(" + getAFloyd()[posOrigen][posNodoIntermedio] + ")\t" + 
						nodes[posNodoIntermedio] + "\t(" + getAFloyd()[posNodoIntermedio][posDestino] +
						")\t" + destino + "\t";
		}
		return path;
	}  

	/**  
	 * Calcula el recorrido en profundidad de un grafo desde un nodo determinado.
	 * No necesariamente recorre todos los nodos.
	 * Al recorrer cada nodo añade el toString del nodo y un tabulador  
	 * Se recorren vecinos empezando por el principio del vector de nodos (antes índices bajos) 
	 * @param origen, T
	 * @return recorrido, String 
	 * Si no existe el nodo devuelve una cadena vacia
	 */  
	public String recorridoProfundidad(T origen) {
		if (getAFloyd() == null || getPFloyd() == null)
			floyd();
		visited = new boolean[numNodes];
		recorrido = "";
		if (existsNode(origen))
			recorrido(origen);
		return recorrido;
	}
	
	/**
	 * Metodo recursivo que calcula el recorrido en profundidad desde un nodo
	 * Ha de comprobar el caso base desde el nodo pasado como parametro
	 * siempre que este se encuentre en el grafo
	 * @param origen, T
	 * @return recorrido, String
	 */
	private String recorrido(T origen) {
		recorrido += origen + "\t";
		visited[getNode(origen)] = true;
		if (casoBase(origen))
			recorrido += "";
		else {
			for (int i = 0; i < numNodes; i++) {
				if (existsEdge(origen, nodes[i]) && !visited[i])
					recorrido(nodes[i]);
			}
		}
		return recorrido;
	}
	
	/**
	 * Realiza una comprobacion base del nodo pasado como parametro
	 * @param origen, T
	 * @return true si ya recorrio todos los posibles nodos
	 * false en otro caso
	 */
	private boolean casoBase(T origen) {
		for (int i = 0; i < numNodes; i++) {
			if (existsEdge(origen, nodes[i]) && !visited[i])
				return false;
		}
		return true;
	}
	
	/**  
	 * Devuelve un String con la informacion del grafo
	 * @return la informacion del grafo
	 */
	public String toString1() {  
	     DecimalFormat df = new DecimalFormat("#.##");  
	     String cadena = "";  
	     cadena += "NODES\n";  
	     for (int i = 0; i < numNodes; i++) {  
	         cadena += nodes[i].toString() + "\t";  
	     }  
	     cadena += "\n\nEDGES\n";  
	     for (int i = 0; i < numNodes; i++) {  
	         for (int j = 0; j < numNodes; j++) {  
	             if (edges[i][j])  
	                 cadena += "T\t";  
	             else  
	                 cadena += "F\t";  
	         }
	         cadena += "\n";  
	     }  
	     cadena += "\nWEIGHTS\n";  
	     for (int i = 0; i < numNodes; i++) {  
	         for (int j = 0; j < numNodes; j++) {  
	             cadena += (edges[i][j]?df.format(weights[i][j]):"-") + "\t";  
	         }  
	         cadena += "\n";  
	     }  
	     return cadena;  
	}
	
	/** 
     * Devuelve un String con la informacion del grafo (incluyendo matrices de Floyd) 
     * @return la informacion del grafo
	 */
    public String toString() { 
        DecimalFormat df = new DecimalFormat("#.##"); 
        String cadena = ""; 
  
        cadena += "NODES\n"; 
        for (int i = 0; i < numNodes; i++) { 
            cadena += nodes[i].toString() + "\t"; 
        } 
        cadena += "\n\nEDGES\n"; 
        for (int i = 0; i < numNodes; i++) { 
            for (int j = 0; j < numNodes; j++) { 
                if (edges[i][j]) 
                    cadena += "T\t"; 
                else 
                    cadena += "F\t"; 
            } 
            cadena += "\n"; 
        } 
        cadena += "\nWEIGHTS\n"; 
        for (int i = 0; i < numNodes; i++) { 
            for (int j = 0; j < numNodes; j++) { 
  
                cadena += (edges[i][j]?df.format(weights[i][j]):"-") + "\t"; 
            } 
            cadena += "\n"; 
        } 
  
        double[][] aFloyd = getAFloyd(); 
        if (aFloyd != null) { 
            cadena += "\nAFloyd\n"; 
            for (int i = 0; i < numNodes; i++) { 
                for (int j = 0; j < numNodes; j++) { 
                    cadena += df.format(aFloyd[i][j]) + "\t"; 
                } 
                cadena += "\n"; 
            } 
        } 
  
        int[][] pFloyd = getPFloyd(); 
        if (pFloyd != null) { 
                cadena += "\nPFloyd\n"; 
            for (int i = 0; i < numNodes; i++) { 
                for (int j = 0; j < numNodes; j++) { 
                    cadena += (pFloyd[i][j]>=0?df.format(pFloyd[i][j]):"-") + "\t"; 
                } 
                cadena += "\n"; 
            } 
        } 
        return cadena; 
    }
	
}
