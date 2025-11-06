public class Ejercicio_8 {
	public static void main(String[] args) {
		System.out.println("Demostración: rotación doble LR usando la API de ArbolAVL");
		System.out.println("Justificación: LR == rotación izquierda en hijo izquierdo + rotación derecha en el nodo\n");

		ArbolAVL arbol = new ArbolAVL();
		int[] secuencia = {30, 10, 20, 40, 35, 37};
		int paso = 1;

		for (int v : secuencia) {
			System.out.println("\n--- Paso " + paso + ": Insertando " + v + " ---");

			// Si el próximo valor es el que provoca LR (20 en esta secuencia), mostrar antes
			if (v == 20) {
				System.out.println("Subárbol ANTES de insertar " + v + " (posible LR):");
				arbol.dibujar();
			}

			arbol.insertar(v); // ArbolAVL imprimirá si detecta rotaciones (incluye mensaje LR)

			System.out.println("Árbol DESPUÉS de la inserción:");
			arbol.dibujar();

			System.out.print("Inorden: "); arbol.inorden();
			paso++;
		}

		System.out.println("\nValidación completa con la secuencia del Ejercicio 2 usando ArbolAVL.");
	}
}
