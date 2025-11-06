public class Ejercicio_7 {
	// Implementación guiada: rotación simple a la izquierda y demo RR
	// Usamos la clase package-private NodoAVL del proyecto.

	// Devuelve la altura de un nodo (convención: hoja -> 0, nulo -> -1)
	private static int altura(NodoAVL n) {
		return (n == null) ? -1 : n.altura;
	}

	private static void actualizarAltura(NodoAVL n) {
		if (n != null) {
			n.altura = 1 + Math.max(altura(n.izq), altura(n.der));
			n.fe = altura(n.izq) - altura(n.der);
		}
	}

	// Rotación simple a la izquierda sobre y (caso RR)
	private static NodoAVL rotacionIzquierda(NodoAVL y) {
		if (y == null || y.der == null) return y;
		NodoAVL x = y.der;
		y.der = x.izq;
		x.izq = y;
		// actualizar alturas desde abajo hacia arriba
		actualizarAltura(y);
		actualizarAltura(x);
		return x;
	}

	// Inserción simplificada que solo aplica la rotación a la izquierda
	// en caso RR (desbalance por subárbol derecho-derecho).
	private static NodoAVL insertarSimple(NodoAVL nodo, int valor) {
		if (nodo == null) return new NodoAVL(valor);

		if (valor < nodo.valor) nodo.izq = insertarSimple(nodo.izq, valor);
		else if (valor > nodo.valor) nodo.der = insertarSimple(nodo.der, valor);
		else return nodo; // ya existe

		actualizarAltura(nodo);

		// Detectar RR -> FE < -1 y el valor insertado está en la rama derecha derecha
		if (nodo.fe < -1 && valor > nodo.der.valor) {
			System.out.println("\n*** Detectado caso RR en subárbol con raíz " + nodo.valor + " -> aplicar rotación izquierda ***");
			System.out.println("Subárbol ANTES de la rotación:");
			dibujarHorizontal(nodo, 0);
			NodoAVL nuevaRaiz = rotacionIzquierda(nodo);
			System.out.println("Subárbol DESPUÉS de la rotación:");
			dibujarHorizontal(nuevaRaiz, 0);
			return nuevaRaiz;
		}

		// Para este ejercicio no implementamos las otras rotaciones
		return nodo;
	}

	// Dibuja el subárbol horizontalmente (mismo estilo que en ArbolAVL)
	private static void dibujarHorizontal(NodoAVL nodo, int nivel) {
		if (nodo != null) {
			dibujarHorizontal(nodo.der, nivel + 1);
			for (int i = 0; i < nivel; i++) System.out.print("    ");
			System.out.println("↓ " + nodo.valor + " (h:" + nodo.altura + ", FE:" + nodo.fe + ")");
			dibujarHorizontal(nodo.izq, nivel + 1);
		}
	}

	private static void inorden(NodoAVL nodo) {
		if (nodo != null) {
			inorden(nodo.izq);
			System.out.print(nodo.valor + "(FE:" + nodo.fe + ",h:" + nodo.altura + ") ");
			inorden(nodo.der);
		}
	}

	public static void main(String[] args) {
		System.out.println("Demostración: rotación simple a la IZQUIERDA (caso RR)");

		// Secuencia que produce un caso RR clásico: 10 -> 20 -> 30
		int[] secuencia = {10, 20, 30};

		NodoAVL raiz = null;
		for (int v : secuencia) {
			System.out.println("\nInsertando: " + v);
			raiz = insertarSimple(raiz, v);
			System.out.println("Árbol (horizontal) tras la inserción:");
			dibujarHorizontal(raiz, 0);
			System.out.print("Recorrido inorden: "); inorden(raiz); System.out.println();
		}

		System.out.println("\nFin de la demostración.");
	}
}
