//  NO MODIFICAR NOMBRE DE ESTE PAQUETE, crear (si no existe ya) un paquete nuevo en vuestro proyecto que se llame así: "evalNestor"
//  y meted en él esta clase
package evalNestor;

public class Alumno {
	// Poned vuestros apellidos, nombre y UO; sustituyendolos en las asignaciones de debajo...
	String nombre="MariaTeresa", // Primera en mayúsculas y si es compuesto, sin espacios
			apellido1="Fernandez", // Primera en mayúsculas y si es compuesto, sin espacios
			apellido2="Coro", // Primera en mayúsculas y si es compuesto, sin espacios
			uo="UO263728"; // El UO en mayúsculas
	
	public String getNombreFicheroAlumno(){
		return apellido1+apellido2+nombre+"-"+uo;
	}

	public String email() {
		return uo+"@uniovi.es";
	}
	
}
