import java.time.LocalDateTime;

public class PlannerRecordatorios implements Planner {
    private final MiMinHeap<Recordatorio> heap;
    private final MiHashMap<String, Recordatorio> byId;

    public PlannerRecordatorios() {
        this.heap = new MiMinHeap<>();
        this.byId = new MiHashMap<>(16);
    }

    @Override
    public void programar(Recordatorio r) {
        if (r == null) return;
        heap.insert(r);
        byId.put(r.getId(), r);
    }

    @Override
    public Recordatorio proximo() {
        Recordatorio r = heap.extractMin();
        if (r != null) {
            byId.remove(r.getId());
        }
        return r;
    }

    @Override
    public boolean reprogramar(String id, LocalDateTime nuevaFecha) {
        if (id == null || nuevaFecha == null) return false;
        Recordatorio r = byId.get(id);
        if (r == null) return false;
        // Remove old from heap: simplest approach is to rebuild heap without the old one.
        // Since MiMinHeap doesn't support decrease-key or arbitrary removal, rebuild.
        byId.remove(id);
        r.setFecha(nuevaFecha);
        // rebuild heap from byId values
        MiMinHeap<Recordatorio> newHeap = new MiMinHeap<>();
        for (Recordatorio rec : this.byIdValues()) {
            newHeap.insert(rec);
        }
        // replace heap content
        // We can't assign to private final field, so we copy contents: reflect by creating new heap and inserting
        // To swap, we'll reassign internal state by creating a new PlannerRecordatorios? Simpler: replace heap contents by
        // clearing original via repeated extractMin (but we don't have clear). Instead, we'll reinsert into current heap by
        // creating a new heap variable and copying via reflection is overkill. Simpler approach: create a fresh heap and
        // insert all including updated record, then replace contents by extracting all from current and inserting from new.

        // Extract all from current heap to temp list
        java.util.List<Recordatorio> temp = new java.util.ArrayList<>();
        Recordatorio e;
        while ((e = heap.extractMin()) != null) {
            // skip the one being reprogrammed (it was removed from byId already)
            if (!e.getId().equals(id)) temp.add(e);
        }
        // insert updated record into temp
        temp.add(r);
        // rebuild heap by inserting all
        for (Recordatorio rec : temp) heap.insert(rec);
        // put back into byId map
        for (Recordatorio rec : temp) byId.put(rec.getId(), rec);

        return true;
    }

    private java.util.Collection<Recordatorio> byIdValues() {
        // No direct values() in MiHashMap; we can reconstruct by extracting keys via naive approach: use toString hack or keep auxiliary map.
        // Simpler: perform extract from heap to list and then reinsert — but caller uses byId as source. Here we return current byId map by
        // traversing heap elements which we can't access. So we will build by iterating extracting from heap (destructive) then reinserting.
        java.util.List<Recordatorio> list = new java.util.ArrayList<>();
        Recordatorio r;
        while ((r = heap.extractMin()) != null) list.add(r);
        for (Recordatorio rec : list) heap.insert(rec);
        return list;
    }
}
