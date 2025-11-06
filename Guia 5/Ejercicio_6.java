public class Ejercicio_6 {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();

        // Limpiar marcadores de nodos críticos antes de la secuencia de inserciones
        arbol.limpiarNodosCriticos();

        int[] valores = {10, 100, 20, 80, 40, 70};

        System.out.println("Insertando secuencia: 10, 100, 20, 80, 40, 70\n");
        for (int v : valores) {
            System.out.println("Insertar: " + v);
            arbol.insertar(v);
        }

        System.out.println("\nÁrbol final (representación horizontal):");
        arbol.dibujar();

        System.out.println("Recorrido Inorden (valor(FE, h) [CRIT si apareció FE=±2 durante el proceso]):");
        arbol.inorden();

        System.out.println("\nNodos críticos detectados durante inserciones (valores con FE=±2):");
        System.out.println(arbol.getNodosCriticos());

        // Comprobar si el árbol final es AVL
        ArbolAVL.ResultadoAVL res = arbol.esAVL();
        System.out.println("\n¿Árbol es AVL? " + res.es + ", altura = " + res.altura);
    }
}
