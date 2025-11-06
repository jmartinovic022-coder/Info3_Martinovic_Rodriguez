import java.util.*;

public class Ejercicio_10 {
	// Verifica que el recorrido inorden es estrictamente creciente
	private static boolean esInordenCreciente(NodoAVL raiz) {
		List<Integer> vals = new ArrayList<>();
		inorden(raiz, vals);
		for (int i = 1; i < vals.size(); i++) {
			if (vals.get(i) <= vals.get(i-1)) return false;
		}
		return true;
	}

	private static void inorden(NodoAVL n, List<Integer> vals) {
		if (n != null) {
			inorden(n.izq, vals);
			vals.add(n.valor);
			inorden(n.der, vals);
		}
	}

	// Verifica que todas las alturas están bien calculadas
	private static boolean alturasCorrectas(NodoAVL n) {
		if (n == null) return true;
		int hIzq = (n.izq == null) ? -1 : n.izq.altura;
		int hDer = (n.der == null) ? -1 : n.der.altura;
		int hEsperada = 1 + Math.max(hIzq, hDer);
		if (n.altura != hEsperada) return false;
		return alturasCorrectas(n.izq) && alturasCorrectas(n.der);
	}

	// Genera secuencias
	private static int[] creciente(int n) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) arr[i] = i+1;
		return arr;
	}
	private static int[] decreciente(int n) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) arr[i] = n-i;
		return arr;
	}
	private static int[] pseudoaleatoriaConRepetidos(int n) {
		Random rnd = new Random(42);
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) arr[i] = 1 + rnd.nextInt(n/2+2); // muchos repetidos
		return arr;
	}

	// Testea una secuencia y reporta resultados
	private static void testSecuencia(String nombre, int[] secuencia) {
		ArbolAVL arbol = new ArbolAVL();
		arbol.resetRotaciones();
		int exitos = 0, fallos = 0;
		for (int i = 0; i < secuencia.length; i++) {
			int v = secuencia[i];
			arbol.insertar(v);
			NodoAVL raiz = arbol.getRaiz();
			boolean esAVL = arbol.esAVL().es;
			boolean alturas = alturasCorrectas(raiz);
			boolean inorden = esInordenCreciente(raiz);
			boolean pasoOk = esAVL && alturas && inorden;
			if (pasoOk) exitos++; else fallos++;
		}
		int totalRot = arbol.getTotalRotaciones();
		System.out.printf("Secuencia: %-15s | Tests OK: %2d/%2d | Fallos: %2d | Rotaciones: %2d (D:%d, I:%d, LR:%d, RL:%d)\n",
				nombre, exitos, secuencia.length, fallos, totalRot, arbol.getRotacionesDerecha(), arbol.getRotacionesIzquierda(), arbol.getRotacionesLR(), arbol.getRotacionesRL());
		if (fallos > 0) System.out.println("  [!] Fallo en alguna verificación tras inserción");
	}

	public static void main(String[] args) {
		System.out.println("Ejercicio 10: Secuencias estresantes y pruebas unitarias\n");
		int n = 20;
		testSecuencia("Creciente", creciente(n));
		testSecuencia("Decreciente", decreciente(n));
		testSecuencia("Pseudoaleatoria", pseudoaleatoriaConRepetidos(n));
		System.out.println("\nFin de las pruebas.");
	}
}
