public class AgendaConHistorial extends AgendaMedicoImpl implements AgendaConHistorialInterface {
    private final MiStack<Comando> pilaUndo;
    private final MiStack<Comando> pilaRedo;

    public AgendaConHistorial() {
        super();
        this.pilaUndo = new MiStack<>();
        this.pilaRedo = new MiStack<>();
    }

    // Comando interface
    public interface Comando {
        boolean execute();
        boolean unexecute();
    }

    // Helper methods to call superclass implementations directly
    protected boolean doAgendar(Turno t) {
        return super.agendar(t);
    }

    protected boolean doCancelar(String id) {
        return super.cancelar(id);
    }

    // AgendarComando
    private class AgendarComando implements Comando {
        private final Turno t;

        AgendarComando(Turno t) { this.t = t; }

        @Override
        public boolean execute() { return doAgendar(t); }

        @Override
        public boolean unexecute() { return doCancelar(t.getId()); }
    }

    // CancelarComando
    private class CancelarComando implements Comando {
        private final Turno t;

        CancelarComando(Turno t) { this.t = t; }

        @Override
        public boolean execute() { return doCancelar(t.getId()); }

        @Override
        public boolean unexecute() { return doAgendar(t); }
    }

    @Override
    public boolean agendar(Turno t) {
        if (t == null) return false;
        Comando c = new AgendarComando(t);
        boolean ok = c.execute();
        if (ok) {
            pilaUndo.push(c);
            // clear redo
            while (!pilaRedo.isEmpty()) pilaRedo.pop();
        }
        return ok;
    }

    @Override
    public boolean cancelar(String idTurno) {
        if (idTurno == null) return false;
        // find turno by id
        Turno found = null;
        for (Turno t : this.avl.toListInorder()) {
            if (idTurno.equals(t.getId())) { found = t; break; }
        }
        if (found == null) return false;
        Comando c = new CancelarComando(found);
        boolean ok = c.execute();
        if (ok) {
            pilaUndo.push(c);
            while (!pilaRedo.isEmpty()) pilaRedo.pop();
        }
        return ok;
    }

    @Override
    public void undo() {
        if (pilaUndo.isEmpty()) return;
        Comando c = pilaUndo.pop();
        boolean ok = c.unexecute();
        if (ok) pilaRedo.push(c);
    }

    @Override
    public void redo() {
        if (pilaRedo.isEmpty()) return;
        Comando c = pilaRedo.pop();
        boolean ok = c.execute();
        if (ok) pilaUndo.push(c);
    }
}
