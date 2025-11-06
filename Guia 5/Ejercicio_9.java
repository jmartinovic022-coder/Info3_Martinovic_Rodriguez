public class Ejercicio_9 {
    public static void main(String[] args) {
        System.out.println("9. Costos y altura (respuestas breves):\n");
        System.out.println("a) La altura de un AVL es O(log n) porque el balanceo impide que el árbol se vuelva muy desbalanceado: cada vez que la diferencia de alturas entre hijos supera 1, se reestructura. Así, el número de niveles crece logarítmicamente con la cantidad de nodos.");
        System.out.println("\nb) Como la altura es O(log n), las operaciones de búsqueda, inserción y eliminación recorren a lo sumo esa cantidad de niveles, por lo que su costo es O(log n).\n");
        System.out.println("c) Comparación conceptual:\n   - ABB sin balance puede tener altura hasta n (muy lento en el peor caso).\n   - AVL siempre O(log n) (más rápido en búsquedas).\n   - Rojinegros también O(log n), pero permiten un poco más de desbalance y suelen ser más eficientes en inserciones/borrados masivos.\n");
    }
}
