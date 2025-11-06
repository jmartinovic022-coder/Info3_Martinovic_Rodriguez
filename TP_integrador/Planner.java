import java.time.LocalDateTime;

public interface Planner {
    void programar(Recordatorio r);
    Recordatorio proximo();
    boolean reprogramar(String id, LocalDateTime nuevaFecha);
}
