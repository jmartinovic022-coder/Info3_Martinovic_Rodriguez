public interface AgendaConHistorialInterface {
    boolean agendar(Turno t);
    boolean cancelar(String idTurno);
    void undo();
    void redo();
}
