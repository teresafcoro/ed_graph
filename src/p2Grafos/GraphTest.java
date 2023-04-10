package p2Grafos;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Clase GraphTest
 * Pruebas con JUnit de los algoritmos de la clase Graph
 * @author MariaTeresaFernandezCoro-UO263728
 * @param <T>, clase de tipo generico
 */
class GraphTest<T> {
	
	private static final double Inf = Double.POSITIVE_INFINITY; // Variable con valor infinito
	
	/**
	 * Test de prueba para los metodos de la clase Graph
	 */
	@Test
	void test() {
		Graph<Integer> g = new Graph<Integer>(2);
		
		// addNode
		assertEquals(g.addNode(7), 0);	// se inserta correctamente
		assertEquals(g.addNode(7), -1);	// ya existe, pero sí cabría
		assertEquals(g.addNode(null), -4);	// el nodo a insertar no es válido
		assertEquals(g.addNode(5), 0);	// se añade correctamente
		assertEquals(g.addNode(5), -3);	// ya existe el nodo y además no cabe
		assertEquals(g.addNode(null), -6);	// además de no ser válido no cabría
		assertEquals(g.addNode(2), -2);	// no existe, pero no cabe
		
		// existsNode
		assertTrue(g.existsNode(7));	// existe
		assertFalse(g.existsNode(2));	// no existe
		
		// addEdge
		assertEquals(g.addEdge(7, 5, 10), 0);	// se inserte correctamente
		assertEquals(g.addEdge(2, 5, 10), -1);	// no existe el nodo origen
		assertEquals(g.addEdge(7, 2, 10), -2);	// no existe el nodo destino
		assertEquals(g.addEdge(2, 2, 10), -3);	// no existe ninguno de los dos nodos
		assertEquals(g.addEdge(7, 5, 10), -4);	// ya existe
		assertEquals(g.addEdge(7, 5, -10), -8);	// el peso no es valido
		
		// existsEdge
		assertTrue(g.existsEdge(7, 5));	// existe
		assertFalse(g.existsEdge(5, 7));	// no existe
		
		// getEdge
		assertEquals(g.getEdge(7, 5), 10);	// existe la arista entre los nodos
		assertEquals(g.getEdge(2, 5), -1);	// no existe el nodo origen
		assertEquals(g.getEdge(7, 2), -2);	// no existe el nodo destino
		assertEquals(g.getEdge(2, 2), -3);	// no existe ninguno de los dos nodos
		assertEquals(g.getEdge(5, 7), -4);	// no existe la arista
		
		// removeEdge
		assertEquals(g.removeEdge(7, 5), 0);	// se elimino correctamente
		assertEquals(g.removeEdge(2, 5), -1);	// no existe el nodo origen
		assertEquals(g.removeEdge(7, 2), -2);	// no existe el nodo destino
		assertEquals(g.removeEdge(2, 2), -3);	// no existe ninguno de los dos nodos
		assertEquals(g.removeEdge(7, 5), -4);	// no existe la arista
		
		// removeNode
		assertEquals(g.removeNode(7), 0);	// se elimino el nodo correctamente
		assertEquals(g.removeNode(7), -1);	// ya no existe dicho nodo
		assertTrue(g.existsNode(5));	// sigue existiendo el otro nodo
	}
	
	/**
	 * Test de prueba de Nestor para los metodos de la clase Graph
	 */
	@Test
	void testNestor() {
		Graph<Integer> g = new Graph<Integer>(3);
		
		assertFalse(g.existsNode(1));
		assertFalse(g.existsNode(2));
		assertEquals(g.addNode(1), 0);
		assertEquals(g.addNode(2), 0);
		assertTrue(g.existsNode(1));
		assertTrue(g.existsNode(2));
		assertEquals(g.addNode(1), -1);
		assertEquals(g.addNode(2), -1);
		assertEquals(g.addEdge(1,2, 1.2), 0);
		assertEquals(g.addEdge(2,1, 2.1), 0);
		assertEquals(g.addEdge(1,1, 1.1), 0);
		assertEquals(g.getEdge(1,2), 1.2);
		assertEquals(g.getEdge(2,1), 2.1);
		assertEquals(g.getEdge(1,1), 1.1);
		assertEquals(g.getEdge(2,3), -2.0);
		assertTrue(g.existsEdge(1,2));
		assertTrue(g.existsEdge(2,1));
		assertTrue(g.existsEdge(1,1));
		assertFalse(g.existsEdge(2,2));
		assertEquals(g.removeEdge(2,2), -4);
		assertEquals(g.addEdge(2,2, 2.2), 0);
		assertEquals(g.getEdge(2,2), 2.2);
		assertTrue(g.existsEdge(2,2));
		assertEquals(g.removeEdge(2,2), 0);
		assertEquals(g.removeNode(3), -1);
		assertEquals(g.addNode(3), 0);
		assertEquals(g.addNode(4), -2);
		assertEquals(g.getEdge(1, 3), -4.0);
		assertEquals(g.addEdge(1,3, 1.3), 0);
		assertTrue(g.existsEdge(1, 3));
		assertEquals(g.getEdge(1, 3), 1.3);
		assertEquals(g.getEdge(1, 4), -2.0);
		assertEquals(g.getEdge(5,1), -1.0);
		assertEquals(g.getEdge(5,4), -3.0);
		assertEquals(g.removeNode(3), 0);
		assertEquals(g.removeNode(3), -1);
		assertFalse(g.existsEdge(1, 3));
		assertEquals(g.getEdge(1, 3), -2);
		assertEquals(g.removeNode(2), 0);
		assertEquals(g.removeNode(2), -1);
		assertTrue(g.existsEdge(1, 1));
		assertEquals(g.removeNode(1), 0);
		assertFalse(g.existsNode(1));
		assertFalse(g.existsNode(2));
		assertFalse(g.existsNode(3));
		assertFalse(g.existsNode(4));
		assertEquals(g.addNode(4), 0);
		assertTrue(g.existsNode(4));
		assertEquals(g.addEdge(4,4, 4.4), 0);
		assertTrue(g.existsEdge(4, 4));
		assertEquals(g.getEdge(4, 4), 4.4);
		assertEquals(g.removeNode(4), 0);
		assertEquals(g.removeNode(4), -1);
		assertFalse(g.existsEdge(4, 4));
		assertEquals(g.addNode(7), 0);
		assertEquals(g.removeNode(2), -1);
		assertEquals(g.removeNode(3), -1);
		assertEquals(g.removeNode(4), -1);
		assertFalse(g.existsEdge(7, 7));
		assertEquals(g.addEdge(7,7, 7.7), 0);
		assertEquals(g.addEdge(7,7, 17.17), -4);
		assertEquals(g.getEdge(7, 7), 7.7);
		assertEquals(g.addNode(8), 0);
		assertEquals(g.addNode(9), 0);
		assertFalse(g.existsEdge(7, 8));
		assertFalse(g.existsEdge(8, 7));
		assertFalse(g.existsEdge(8, 8));
		assertFalse(g.existsEdge(8, 9));
		assertFalse(g.existsEdge(9, 8));
		assertFalse(g.existsEdge(9, 9));
		assertEquals(g.addEdge(7,8, 7.8), 0);
		assertEquals(g.addEdge(8,7, 8.7), 0);
		assertEquals(g.addEdge(8,8, 8.8), 0);
		assertEquals(g.addEdge(8,9, 8.9), 0);
		assertEquals(g.addEdge(9,8, 9.8), 0);
		assertEquals(g.addEdge(9,9, 9.9), 0);
		assertEquals(g.addEdge(7,9, 7.9), 0);
		assertEquals(g.addEdge(9,7, 9.7), 0);
		assertEquals(g.getEdge(7, 7), 7.7); 
		assertEquals(g.getEdge(7, 8), 7.8); 
		assertEquals(g.getEdge(7, 9), 7.9); 
		assertEquals(g.getEdge(8, 7), 8.7); 
		assertEquals(g.getEdge(8, 8), 8.8);
		assertEquals(g.getEdge(8, 9), 8.9);
		assertEquals(g.getEdge(9, 7), 9.7);
		assertEquals(g.getEdge(9, 8), 9.8); 
		assertEquals(g.getEdge(9, 9), 9.9); 
		assertEquals(g.removeNode(7), 0);
		assertEquals(g.removeNode(7), -1);
		assertEquals(g.getEdge(7, 7), -3); 
		assertEquals(g.getEdge(7, 8), -1); 
		assertEquals(g.getEdge(7, 9), -1); 
		assertEquals(g.getEdge(8, 7), -2); 
		assertEquals(g.getEdge(8, 8), 8.8); 
		assertEquals(g.getEdge(8, 9), 8.9);
		assertEquals(g.getEdge(9, 7), -2);
		assertEquals(g.getEdge(9, 8), 9.8); 
		assertEquals(g.getEdge(9, 9), 9.9);
		assertEquals(g.removeNode(null), -1);
		assertEquals(g.addNode(null), -4);
		assertEquals(g.addNode(10), 0);
		assertEquals(g.getEdge(8, 10), -4);
		assertEquals(g.getEdge(10, 9), -4); 
		assertEquals(g.addNode(null), -6);
		assertEquals(g.addEdge(null, 8, 0.8), -1);
		assertEquals(g.addEdge(8, null, 0.8), -2);
		assertEquals(g.addEdge(null, null, 0.8), -3);
		assertEquals(g.getEdge(null, 10), -1);
		assertEquals(g.getEdge(10, null), -2);
		assertEquals(g.getEdge(null, null), -3);
		assertFalse(g.existsNode(null));
		assertFalse(g.existsEdge(null, null));
		assertEquals(g.addEdge(8, 8, -8.8), -8);
		assertEquals(g.addEdge(7, 8, -7.8), -8);
		assertEquals(g.addEdge(8, 7, -8.7), -8);
		assertEquals(g.addEdge(7, 7, -7.7), -8);
	}
	
	/**
	 * Test de prueba para el metodo Dijkstra de la clase Graph
	 * Desde todos los nodos del grafo
	 */
	@Test
	void testDijkstra() {
		Graph<Integer> g = new Graph<Integer>(4);
		
		assertEquals(g.addNode(0), 0);
		assertEquals(g.addNode(1), 0);
		assertEquals(g.addNode(2), 0);
		assertEquals(g.addNode(3), 0);
		assertEquals(g.addEdge(0, 1, 1), 0);
		assertEquals(g.addEdge(0, 3, 3), 0);
		assertEquals(g.addEdge(1, 2, 5), 0);
		assertEquals(g.addEdge(1, 3, 4), 0);
		assertEquals(g.addEdge(2, 0, 8), 0);
		assertEquals(g.addEdge(2, 3, 1), 0);
		assertEquals(g.addEdge(3, 2, 2), 0);
		
		assertArrayEquals("Dijkstra[9]:", null, g.dijkstra(9), 0); // no existe el nodoOrigen
		assertArrayEquals("Dijkstra[null]:", null, g.dijkstra(null), 0); // no es valido el nodoOrigen
		// Dijkstra con nodoOrigen valido 
		assertArrayEquals("Dijkstra[0]:", new double[] {0, 1, 5, 3}, g.dijkstra(0), 0);
		assertArrayEquals("Dijkstra[1]:", new double[] {13, 0, 5, 4}, g.dijkstra(1), 0);
		assertArrayEquals("Dijkstra[2]:", new double[] {8, 9, 0, 1}, g.dijkstra(2), 0);
		assertArrayEquals("Dijkstra[3]:", new double[] {10, 11, 2, 0}, g.dijkstra(3), 0);
	}
	
	/**
	 * Test de prueba para el metodo Dijkstra de la clase Graph
	 * Desde todos los nodos del grafo
	 */
	@Test
	void testDijkstra1() {
		Graph<Integer> g = new Graph<Integer>(5);
		
		assertEquals(g.addNode(1), 0);
		assertEquals(g.addNode(2), 0);
		assertEquals(g.addNode(3), 0);
		assertEquals(g.addNode(4), 0);
		assertEquals(g.addNode(5), 0);
		assertEquals(g.addEdge(1, 2, 1), 0);
		assertEquals(g.addEdge(1, 4, 3), 0);
		assertEquals(g.addEdge(1, 5, 10), 0);
		assertEquals(g.addEdge(2, 3, 5), 0);
		assertEquals(g.addEdge(3, 5, 1), 0);
		assertEquals(g.addEdge(4, 3, 2), 0);
		assertEquals(g.addEdge(4, 5, 6), 0);
		
		assertArrayEquals("Dijkstra[9]:", null, g.dijkstra(9), 0); // no existe el nodoOrigen
		assertArrayEquals("Dijkstra[null]:", null, g.dijkstra(null), 0); // no es valido el nodoOrigen
		// Dijkstra con nodoOrigen valido 
		assertArrayEquals("Dijkstra[1]:", new double[] {0, 1, 5, 3, 6}, g.dijkstra(1), 0);
		assertArrayEquals("Dijkstra[2]:", new double[] {Inf, 0, 5, Inf, 6}, g.dijkstra(2), 0);
		assertArrayEquals("Dijkstra[3]:", new double[] {Inf, Inf, 0, Inf, 1}, g.dijkstra(3), 0);
		assertArrayEquals("Dijkstra[4]:", new double[] {Inf, Inf, 2, 0, 3}, g.dijkstra(4), 0);
		assertArrayEquals("Dijkstra[5]:", new double[] {Inf, Inf, Inf, Inf, 0}, g.dijkstra(5), 0);
	}
	
	/**
	 * Test de prueba para el metodo Floyd de la clase Graph
	 */
	@Test
	void testFloyd() {
		Graph<Integer> g = new Graph<Integer>(6);
		assertEquals(g.addNode(1), 0);
		assertEquals(g.addNode(2), 0);
		assertEquals(g.addNode(3), 0);
		assertEquals(g.addNode(4), 0);
		assertEquals(g.addNode(5), 0);
		assertEquals(g.addNode(6), 0);
		assertEquals(g.addEdge(1, 2, 3), 0);
		assertEquals(g.addEdge(1, 3, 4), 0);
		assertEquals(g.addEdge(1, 5, 8), 0);
		assertEquals(g.addEdge(2, 5, 5), 0);
		assertEquals(g.addEdge(3, 5, 3), 0);
		assertEquals(g.addEdge(5, 4, 7), 0);
		assertEquals(g.addEdge(5, 6, 3), 0);
		assertEquals(g.addEdge(6, 4, 2), 0);
		
		// Floyd no se ha generado
		assertEquals(null, g.getAFloyd());
		assertEquals(null, g.getPFloyd());
		// Genero Floyd y compruebo la matriz A resultante
		assertEquals(0, g.floyd());
		assertArrayEquals(new double[][] {{0,3,4,12,7,10}, {Inf,0,Inf,10,5,8}, {Inf,Inf,0,8,3,6},
							{Inf,Inf,Inf,0,Inf,Inf}, {Inf,Inf,Inf,5,0,3}, {Inf,Inf,Inf,2,Inf,0}},
								g.getAFloyd());
	}
	
	/**
	 * Test1 de prueba para el metodo Floyd, MinCostPath, Path y RecorridoProfundidad de la clase Graph
	 */
	@Test
	void test1() {
		Graph<Integer> g = new Graph<Integer>(5);
		assertEquals(g.addNode(1), 0);
		assertEquals(g.addNode(2), 0);
		assertEquals(g.addNode(3), 0);
		assertEquals(g.addNode(4), 0);
		assertEquals(g.addNode(5), 0);
		assertEquals(g.addEdge(1, 2, 1), 0);
		assertEquals(g.addEdge(1, 4, 3), 0);
		assertEquals(g.addEdge(1, 5, 10), 0);
		assertEquals(g.addEdge(2, 3, 5), 0);
		assertEquals(g.addEdge(3, 5, 1), 0);
		assertEquals(g.addEdge(4, 3, 2), 0);
		assertEquals(g.addEdge(4, 5, 6), 0);
		
		// Floyd no se ha generado
		assertEquals(null, g.getAFloyd());
		assertEquals(null, g.getPFloyd());
		
		// Genero Floyd y compruebo la matriz A resultante
		assertEquals(0, g.floyd());
		assertArrayEquals(new double[][] {{0,1,5,3,6}, {Inf,0,5,Inf,6}, {Inf,Inf,0,Inf,1},
							{Inf,Inf,2,0,3}, {Inf,Inf,Inf,Inf,0}}, g.getAFloyd());
		
		// MinCostPath
		assertEquals(5, g.minCostPath(1, 3)); // Existe un camino entre los nodos y el coste mínimo es 5
		assertEquals(-1, g.minCostPath(0, 1)); // No existe nodo source
		assertEquals(-1, g.minCostPath(1, 0)); // No existe nodo target
		assertEquals(-1, g.minCostPath(0, 0)); // No existe ni nodo source ni target
		assertEquals(-1, g.minCostPath(2, 1)); // No existe camino entre dichos nodos
				
		// Path
        assertEquals("", g.path(0, 3)); // No existe nodo origen
        assertEquals("", g.path(5, 0)); // No existe nodo destino
        assertEquals("", g.path(0, 0)); // No existe ni nodo origen ni destino
     	assertEquals("5\t", g.path(5, 5)); // Nodos origen y destino son los mismos
     	assertEquals("2\t(Infinity)\t1\t", g.path(2, 1)); // No existe camino directo
        assertEquals("1\t(1.0)\t2\t", g.path(1, 2)); // Existe camino directo entre los dos nodos
        // Existe camino entre los dos nodos pasando por otro
        assertEquals("1\t(3.0)\t4\t(2.0)\t3\t", g.path(1, 3));
        
        // RecorridoProfundidad
        assertEquals("1\t2\t3\t5\t4\t", g.recorridoProfundidad(1));
		assertEquals("2\t3\t5\t", g.recorridoProfundidad(2));
		assertEquals("3\t5\t", g.recorridoProfundidad(3));
		assertEquals("4\t3\t5\t", g.recorridoProfundidad(4));
		assertEquals("5\t", g.recorridoProfundidad(5));
		assertEquals("", g.recorridoProfundidad(0));	// no existe el nodo
	}
	
}
