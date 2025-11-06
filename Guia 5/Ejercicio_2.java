public class Ejercicio_2 {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        int[] secuencia = {30, 10, 20, 40, 35, 37};

        System.out.println("Ejercicio 2: Inserciones con rotación doble (casos LR y RL)");
        System.out.println("Secuencia: 30, 10, 20, 40, 35, 37");
        System.out.println("-----------------------------------------------------------");

        int paso = 1;

        for (int valor : secuencia) {
            System.out.println("\n--- Paso " + paso + ": Insertando " + valor + " ---");
            arbol.insertar(valor);
            arbol.dibujar();

            System.out.print("Inorden: ");
            arbol.inorden();

            System.out.println("-----------------------------------------------------------");
            paso++;
        }

        System.out.println("\nFin del Ejercicio 2");
    }
}
