import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades para agendas.
 */
public class UtilsAgendas {

    /**
     * Merge de dos listas ordenadas de Turno (por fecha). Retorna una nueva lista ordenada C.
     * Si un turno aparece en ambas listas (mismo id o mismo medico y misma fechaHora) se añade solo una
     * instancia y se registra el conflicto en stderr.
     */
    public static List<Turno> merge(List<Turno> A, List<Turno> B) {
        List<Turno> C = new ArrayList<>();
        if (A == null) A = new ArrayList<>();
        if (B == null) B = new ArrayList<>();

        int i = 0, j = 0;
        while (i < A.size() && j < B.size()) {
            Turno a = A.get(i);
            Turno b = B.get(j);
            if (a == null) { i++; continue; }
            if (b == null) { j++; continue; }

            int cmp = a.compareTo(b);
            if (cmp < 0) {
                C.add(a);
                i++;
            } else if (cmp > 0) {
                C.add(b);
                j++;
            } else {
                // mismas fechaHora (compareTo == 0)
                if (isSameTurno(a, b)) {
                    // duplicado exacto
                    System.err.println("Conflicto: turno duplicado id=" + a.getId() + " (misma fecha/medico)");
                    C.add(a);
                } else if (a.getId() != null && a.getId().equals(b.getId())) {
                    System.err.println("Conflicto: mismo id en A y B id=" + a.getId());
                    C.add(a);
                } else {
                    // si tienen misma fecha pero no son el mismo turno según criterios, añadir ambos en orden consistente
                    C.add(a);
                    C.add(b);
                }
                i++; j++;
            }
        }

        while (i < A.size()) {
            Turno a = A.get(i++);
            if (a != null) C.add(a);
        }
        while (j < B.size()) {
            Turno b = B.get(j++);
            if (b != null) C.add(b);
        }

        // Deduplicación adicional: si existieran duplicados con fechas diferentes pero mismo id, eliminar extras keeping first occurrence.
        // Esto pasa raramente pero lo manejamos en un pase final O(n) con un set de ids.
        java.util.Set<String> seenIds = new java.util.HashSet<>();
        List<Turno> finalList = new ArrayList<>();
        for (Turno t : C) {
            if (t == null) continue;
            String id = t.getId();
            if (id != null) {
                if (seenIds.contains(id)) {
                    System.err.println("Conflicto: turno con id repetido, ignorando id=" + id);
                    continue;
                }
                seenIds.add(id);
            }
            finalList.add(t);
        }

        return finalList;
    }

    private static boolean isSameTurno(Turno a, Turno b) {
        if (a == null || b == null) return false;
        if (a.getMatriculaMedico() == null || b.getMatriculaMedico() == null) return false;
        if (!a.getMatriculaMedico().equals(b.getMatriculaMedico())) return false;
        if (a.getFechaHora() == null || b.getFechaHora() == null) return false;
        return a.getFechaHora().equals(b.getFechaHora());
    }
}
