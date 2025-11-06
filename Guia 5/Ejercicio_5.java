public class Ejercicio_5 {
    public static void main(String[] args) {
        System.out.println("=== Ejercicio 5: Comprobador esAVL (tests y gráficos) ===\n");

        ArbolAVL arbol = new ArbolAVL();

        // ---------- Caso A: árbol válido (usar inserciones en AVL)
        int[] validos = {30, 20, 40, 10, 25, 35, 45};
        for (int v : validos) arbol.insertar(v);

        System.out.println("A) Árbol válido (creado por ArbolAVL.insertar):");
        // usamos el dibujador de la clase para mostrar el árbol interno
        arbol.dibujar();
        // comprobamos usando tu método esAVL sobre la raiz expuesta
        ArbolAVL.ResultadoAVL rA = arbol.esAVL(arbol.getRaiz());
        System.out.println("Resultado esAVL: " + (rA.es ? "✅ Es AVL" : "❌ No es AVL") + " | Altura devuelta: " + rA.altura);
        System.out.println("-----------------------------------------------------------\n");

        // ---------- Caso B: ABB puro (efecto 'peine') construido manualmente
        int[] peine = {5, 10, 15, 20, 25};
        NodoAVL rootPeine = null;
        for (int v : peine) rootPeine = insertarABB(rootPeine, v);

        // Antes de dibujar, computamos alturas y FE para mostrar datos coherentes
        computeAlturasYFE(rootPeine);

        System.out.println("B) Árbol NO AVL (ABB puro - efecto 'peine'):");
        dibujarNodo(rootPeine, 0);
        // llamamos a tu esAVL pasando la raíz manual
        ArbolAVL.ResultadoAVL rB = arbol.esAVL(rootPeine);
        System.out.println("Resultado esAVL: " + (rB.es ? "✅ Es AVL" : "❌ No es AVL") + " | Altura devuelta: " + rB.altura);
        System.out.println("-----------------------------------------------------------\n");

        // ---------- Caso C: árbol que viola la propiedad ABB (nodo izquierdo mayor que la raíz)
        NodoAVL rootNoABB = new NodoAVL(30);
        rootNoABB.izq = new NodoAVL(40); // violación: hijo izquierdo > padre
        rootNoABB.der = new NodoAVL(20); // violación: hijo derecho < padre

        // compute heights/FE for clearer display (no importa que sean incoherentes respecto a ABB)
        computeAlturasYFE(rootNoABB);

        System.out.println("C) Árbol NO AVL (viola propiedad ABB):");
        dibujarNodo(rootNoABB, 0);
        ArbolAVL.ResultadoAVL rC = arbol.esAVL(rootNoABB);
        System.out.println("Resultado esAVL: " + (rC.es ? "✅ Es AVL" : "❌ No es AVL") + " | Altura devuelta: " + rC.altura);
        System.out.println("-----------------------------------------------------------\n");

        System.out.println("Fin Ejercicio 5.");
    }

    // -------------------------
    // Helpers (no tocan tus clases)
    // -------------------------

    // inserta como ABB normal (sin balancear), devuelve nueva raíz
    private static NodoAVL insertarABB(NodoAVL nodo, int valor) {
        if (nodo == null) return new NodoAVL(valor);
        if (valor < nodo.valor) nodo.izq = insertarABB(nodo.izq, valor);
        else nodo.der = insertarABB(nodo.der, valor);
        return nodo;
    }

    // Calcula recursivamente alturas y FE y asigna a los campos de NodoAVL
    // Convención: altura de hoja = 0; altura de null = -1
    private static int computeAlturasYFE(NodoAVL nodo) {
        if (nodo == null) return -1;
        int hIzq = computeAlturasYFE(nodo.izq);
        int hDer = computeAlturasYFE(nodo.der);
        nodo.altura = 1 + Math.max(hIzq, hDer);
        // en tu ArbolAVL usás FE = altura(izq) - altura(der)
        nodo.fe = hIzq - hDer;
        return nodo.altura;
    }

    // Dibuja cualquier NodoAVL (similar a dibujar de ArbolAVL pero para una raíz dada)
    private static void dibujarNodo(NodoAVL nodo, int nivel) {
        if (nodo == null) return;
        // pintar derecho primero para que aparezca a la derecha visualmente
        dibujarNodo(nodo.der, nivel + 1);

        for (int i = 0; i < nivel; i++) System.out.print("    ");
        System.out.println("↓ " + nodo.valor + " (h:" + nodo.altura + ", FE:" + nodo.fe + ")");

        dibujarNodo(nodo.izq, nivel + 1);
    }

}
