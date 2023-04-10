//  NO MODIFICAR NOMBRE DE ESTE PAQUETE, crear (si no existe ya) un paquete nuevo en vuestro proyecto que se llame asi: "evalNestor"
//  y meted en el esta clase
package evalNestor;

import p2Grafos.Graph;

public class EvalGraph<T> extends Graph<T> {
	
	Alumno alum=new Alumno();

	public EvalGraph(int i) {
		super(i);
	}

	public EvalGraph(int i,T initialNodes[], boolean[][] initialEdges, double [][] initialWeights){
		super(i,initialNodes,initialEdges,initialWeights);
	}

	// nuevo constructor en EvalGraph 
    public EvalGraph(int i,T initialNodes[], boolean[][] initialEdges, double [][] initialWeights, double [][] initialAfloyd, int [][] initialPfloyd){ 
        super(i,initialNodes,initialEdges,initialWeights,initialAfloyd,initialPfloyd); 
    } 

	public String getNombreFicheroAlumno(){
		return alum.getNombreFicheroAlumno();
	}
}


/*  //  deben estar incluidos estos constructores en la clase Graph 
	public Graph (int tam, T initialNodes[], boolean[][] initialEdges, double [][] initialWeights) {
		// Llama al constructor original
		this(tam); 
		
		// Pero modifica los atributos con los parametros para hacer pruebas...
		numNodes = initialNodes.length;
		
		for (int i=0;i<numNodes;i++) {
			// Si el vector de nodos se llama de otra forma (distinto de "nodes"), cambiad el nombre en la linea de abajo
			nodes[i]=initialNodes[i];
			for (int j=0;j<numNodes;j++){
				// Si la matriz de aristas se llama de otra forma (distinto de "edges"), cambiad el nombre en la linea de abajo
				edges[i][j]=initialEdges[i][j];
				// Si la matriz de pesos se llama de otra forma (distinto de "weights"), cambiad el nombre en la linea de abajo
				weights[i][j]=initialWeights[i][j];
			}
		}

	} 
	
	// Nuevo constructor en vuestro Graph 
    public Graph (int tam, T initialNodes[], boolean[][] initialEdges, double [][] initialWeights, double [][] initialAfloyd, int [][] initialPfloyd) { 
        // Llama al constructor anterior de inicializacion 
        this(tam, initialNodes,initialEdges,initialWeights);  
         
        // Pero modifica los atributos que almacenan las matrices de Floyd con los parametros para hacer pruebas... 
         
        if (initialAfloyd!=null && initialPfloyd!=null){ 
            // NO llameis a la matriz A de floyd de otra forma que "aFloyd" 
            aFloyd=initialAfloyd; 
            // NO llameis a la matriz P de floyd de otra forma que "pFloyd" 
            pFloyd=initialPfloyd; 
        } 
  
    } 
 */
