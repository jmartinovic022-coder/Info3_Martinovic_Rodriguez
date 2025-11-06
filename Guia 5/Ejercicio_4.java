public class Ejercicio_4 {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        int[] inserciones = {50, 30, 70, 20, 40, 60, 80, 65, 75};

        System.out.println("Ejercicio 4: Eliminación con rebalanceo");
        System.out.println("Inserciones iniciales: 50, 30, 70, 20, 40, 60, 80, 65, 75");
        System.out.println("-----------------------------------------------------------");

        for (int valor : inserciones)
            arbol.insertar(valor);

        System.out.println("\nÁrbol inicial (AVL completamente balanceado):");
        arbol.dibujar();

        System.out.print("Inorden inicial: ");
        arbol.inorden();
        System.out.println("-----------------------------------------------------------");

        // Paso 1: eliminar 20
        System.out.println("\n--- Paso 1: Eliminando 20 ---");
        arbol.eliminar(20);
        arbol.dibujar();
        System.out.print("Inorden tras eliminar 20: ");
        arbol.inorden();
        System.out.println("\n");

        System.out.println("📘 Nodos afectados tras eliminar 20:");
        System.out.println("- Nodo 30: pierde su hijo izquierdo → su FE pasa de 0 a -1.");
        System.out.println("- Nodo 50: su subárbol izquierdo disminuye altura → FE(50) pasa de 0 a -1.");
        System.out.println("👉 No se requiere rotación, ya que |FE| ≤ 1.\n");

        // Paso 2: eliminar 70
        System.out.println("--- Paso 2: Eliminando 70 ---");
        arbol.eliminar(70);
        arbol.dibujar();
        System.out.print("Inorden tras eliminar 70: ");
        arbol.inorden();
        System.out.println("\n");

        System.out.println("📘 Nodos afectados tras eliminar 70:");
        System.out.println("- Nodo 75 reemplaza a 70 (caso con hijo derecho).");
        System.out.println("- Nodo 60: ahora tiene subárbol derecho más alto → FE(60) = -1.");
        System.out.println("- Nodo 50: su subárbol derecho se reequilibra tras rotación derecha en 70.");
        System.out.println("👉 Se aplica **rotación derecha simple (RR inversa)** sobre el nodo 60-75 para restaurar el balance.\n");

        System.out.println("✅ Árbol final balanceado:");
        arbol.dibujar();
        System.out.print("Inorden final: ");
        arbol.inorden();

        System.out.println("\n-----------------------------------------------------------");
        System.out.println("Fin del Ejercicio 4 ✅");
    }
}
