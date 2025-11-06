public class Ejercicio_3 {
    public static void main(String[] args) {
        ArbolAVL arbol;

        // ------------------
        // Ejercicio 3
        // ------------------
        System.out.println("=== Ejercicio 3: Secuencia ordenada / efecto peinar ===");
        int[] sec3 = {5,10,15,20,25,30,35};
        arbol = new ArbolAVL();
        int paso = 1;
        for (int v : sec3) {
            System.out.println("\n--- Paso " + paso + ": Insertando " + v + " ---");
            arbol.insertar(v);
            arbol.dibujar();
            System.out.print("Inorden: ");
            arbol.inorden();
            paso++;
        }
        System.out.println("\nExplicación breve:");
        System.out.println("a) Un ABB puro con datos crecientes se degrada a una lista (altura O(n)).");
        System.out.println("b) El AVL aplica 4 rotaciones izquierdas para mantener altura O(log n).\n");
    }
}
