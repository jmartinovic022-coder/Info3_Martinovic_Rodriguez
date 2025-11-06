import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ReportGenerator {

    public static void insertionSort(Turno[] arr, Comparator<Turno> comp) {
        for (int i = 1; i < arr.length; i++) {
            Turno key = arr[i];
            int j = i - 1;
            while (j >= 0 && comp.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void shellSort(Turno[] arr, Comparator<Turno> comp) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Turno temp = arr[i];
                int j = i;
                while (j >= gap && comp.compare(arr[j - gap], temp) > 0) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    public static void quickSort(Turno[] arr, Comparator<Turno> comp) {
        quickSortRec(arr, 0, arr.length - 1, comp);
    }

    private static void quickSortRec(Turno[] arr, int low, int high, Comparator<Turno> comp) {
        if (low < high) {
            int p = lomutoPartition(arr, low, high, comp);
            quickSortRec(arr, low, p - 1, comp);
            quickSortRec(arr, p + 1, high, comp);
        }
    }

    private static int lomutoPartition(Turno[] arr, int low, int high, Comparator<Turno> comp) {
        Turno pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(arr[j], pivot) <= 0) {
                i++;
                Turno tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        Turno tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;
        return i + 1;
    }

    public static void medirTiempos() {
        final int N = 20000; // tamaño razonable para medir
        Turno[] base = new Turno[N];
        Random rnd = new Random(123);
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < N; i++) {
            String id = "T" + i;
            String dni = "D" + (i % 1000);
            String mat = "M" + (i % 100);
            LocalDateTime fecha = now.plusSeconds(rnd.nextInt(3600 * 24 * 30));
            int dur = 15 + rnd.nextInt(60);
            base[i] = new Turno(id, dni, mat, fecha, dur, "motivo");
        }

        // comparator por fechaHora
        Comparator<Turno> byFecha = Comparator.comparing(Turno::getFechaHora);

        Turno[] a1 = Arrays.copyOf(base, base.length);
        Turno[] a2 = Arrays.copyOf(base, base.length);
        Turno[] a3 = Arrays.copyOf(base, base.length);

        long t0, t1;

        t0 = System.nanoTime();
        insertionSort(a1, byFecha);
        t1 = System.nanoTime();
        System.out.println("insertionSort: " + (t1 - t0) / 1e6 + " ms");

        t0 = System.nanoTime();
        shellSort(a2, byFecha);
        t1 = System.nanoTime();
        System.out.println("shellSort: " + (t1 - t0) / 1e6 + " ms");

        t0 = System.nanoTime();
        quickSort(a3, byFecha);
        t1 = System.nanoTime();
        System.out.println("quickSort: " + (t1 - t0) / 1e6 + " ms");
    }
}
