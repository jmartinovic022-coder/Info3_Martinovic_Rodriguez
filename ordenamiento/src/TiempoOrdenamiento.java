package src;

public class TiempoOrdenamiento {

    public static void medirTiempo(Runnable algoritmo, String nombreAlgoritmo) {
        long inicio = System.nanoTime();
        algoritmo.run();
        long fin = System.nanoTime();
        System.out.println("Tiempo de " + nombreAlgoritmo + ": " + (fin - inicio) / 1_000_000.0 + " ms");
    }
}
