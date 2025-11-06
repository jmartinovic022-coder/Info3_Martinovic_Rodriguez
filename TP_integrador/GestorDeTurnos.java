import java.time.LocalDateTime;

public class GestorDeTurnos implements MapaPacientes {
    private static final int CAPACIDAD_INICIAL = 16;

    private MiHashMap<String, Paciente> indicePacientes;
    private MiHashMap<String, Medico> indiceMedicos;
    private MiHashMap<String, Turno> turnosPorId;
    private MiHashMap<String, AgendaMedico> agendasPorMedico;

    public GestorDeTurnos() {
        this.indicePacientes = new MiHashMap<>(CAPACIDAD_INICIAL);
        this.indiceMedicos = new MiHashMap<>(CAPACIDAD_INICIAL);
        this.turnosPorId = new MiHashMap<>(CAPACIDAD_INICIAL);
        this.agendasPorMedico = new MiHashMap<>(CAPACIDAD_INICIAL);
    }

    // MapaPacientes implementation (delegan a indicePacientes)
    @Override
    public void put(String dni, Paciente p) {
        indicePacientes.put(dni, p);
    }

    @Override
    public Paciente get(String dni) {
        return indicePacientes.get(dni);
    }

    @Override
    public boolean remove(String dni) {
        return indicePacientes.remove(dni);
    }

    @Override
    public boolean containsKey(String dni) {
        return indicePacientes.get(dni) != null;
    }

    @Override
    public int size() {
        return indicePacientes.size();
    }

    // Aquí se implementarán las operaciones del gestor (agendar/cancelar/buscar), usando las estructuras creadas.

    /**
     * Carga inicial de datos desde CSVs (pacientes.csv, medicos.csv, turnos.csv).
     * Se asume la existencia de una clase helper CSVParser.parse(String path) -> List<String[]>.
     */
    public void cargarDatosIniciales() {
        // Cargar pacientes
        try {
            java.util.List<String[]> filasPacientes = CSVParser.parse("pacientes.csv");
            for (String[] row : filasPacientes) {
                if (row == null || row.length < 2) continue;
                String dni = row[0].trim();
                String nombre = row[1].trim();
                Paciente p = new Paciente(dni, nombre);
                indicePacientes.put(dni, p);
            }
        } catch (Exception e) {
            System.err.println("Error leyendo pacientes.csv: " + e.getMessage());
        }

        // Cargar medicos y crear agendas
        try {
            java.util.List<String[]> filasMedicos = CSVParser.parse("medicos.csv");
            for (String[] row : filasMedicos) {
                if (row == null || row.length < 3) continue;
                String matricula = row[0].trim();
                String nombre = row[1].trim();
                String especialidad = row[2].trim();
                Medico m = new Medico(matricula, nombre, especialidad);
                indiceMedicos.put(matricula, m);
                // Inicializar agenda del médico
                agendasPorMedico.put(matricula, new AgendaMedicoImpl());
            }
        } catch (Exception e) {
            System.err.println("Error leyendo medicos.csv: " + e.getMessage());
        }

        // Cargar turnos con validaciones
        try {
            java.util.List<String[]> filasTurnos = CSVParser.parse("turnos.csv");
            for (String[] row : filasTurnos) {
                if (row == null || row.length < 6) continue;
                String id = row[0].trim();
                String dniPaciente = row[1].trim();
                String matriculaMedico = row[2].trim();
                String fechaHoraStr = row[3].trim();
                String duracionStr = row[4].trim();
                String motivo = row[5].trim();

                // Validaciones
                if (turnosPorId.get(id) != null) {
                    System.err.println("Turno " + id + " rechazado: id duplicado");
                    continue;
                }

                if (indicePacientes.get(dniPaciente) == null) {
                    System.err.println("Turno " + id + " rechazado: paciente no encontrado");
                    continue;
                }

                if (indiceMedicos.get(matriculaMedico) == null) {
                    System.err.println("Turno " + id + " rechazado: medico no encontrado");
                    continue;
                }

                LocalDateTime fechaHora = null;
                try {
                    fechaHora = LocalDateTime.parse(fechaHoraStr);
                } catch (Exception ex) {
                    System.err.println("Turno " + id + " rechazado: formato fecha invalido -> " + fechaHoraStr);
                    continue;
                }

                if (fechaHora.isBefore(LocalDateTime.now())) {
                    System.err.println("Turno " + id + " rechazado: fecha pasada");
                    continue;
                }

                int duracionMin = 0;
                try {
                    duracionMin = Integer.parseInt(duracionStr);
                    if (duracionMin <= 0) {
                        System.err.println("Turno " + id + " rechazado: duracion invalida");
                        continue;
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("Turno " + id + " rechazado: duracion no es numero");
                    continue;
                }

                // Si todo OK, crear Turno y guardarlo
                Turno t = new Turno(id, dniPaciente, matriculaMedico, fechaHora, duracionMin, motivo);
                turnosPorId.put(id, t);
                AgendaMedico agenda = agendasPorMedico.get(matriculaMedico);
                if (agenda == null) {
                    // debería no ocurrir si medicos.csv fue cargado correctamente, pero lo manejamos
                    agenda = new AgendaMedicoImpl();
                    agendasPorMedico.put(matriculaMedico, agenda);
                }
                boolean agendado = agenda.agendar(t);
                if (!agendado) {
                    System.err.println("Turno " + id + " no pudo agendarse en la agenda del medico " + matriculaMedico);
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo turnos.csv: " + e.getMessage());
        }
    }

    // Accesores públicos para uso por la UI (Main)
    public AgendaMedico getAgenda(String matricula) {
        return agendasPorMedico.get(matricula);
    }

    public Paciente getPaciente(String dni) {
        return indicePacientes.get(dni);
    }

    /**
     * Retorna la lista de turnos del médico (inorder) o null si no existe agenda.
     */
    public java.util.List<Turno> listarTurnosMedico(String matricula) {
        AgendaMedico agenda = agendasPorMedico.get(matricula);
        if (agenda == null) return null;
        if (agenda instanceof AgendaMedicoImpl) {
            AgendaMedicoImpl impl = (AgendaMedicoImpl) agenda;
            return impl.avl.toListInorder();
        }
        // si es otra implementación, intentar vía reflexión o devolver lista vacía
        return new java.util.ArrayList<>();
    }

    public boolean agregarTurno(Turno t) {
        if (t == null) return false;
        String id = t.getId();
        if (id == null || id.isEmpty()) return false;
        if (turnosPorId.get(id) != null) return false; // id duplicado
        AgendaMedico agenda = agendasPorMedico.get(t.getMatriculaMedico());
        if (agenda == null) {
            agenda = new AgendaMedicoImpl();
            agendasPorMedico.put(t.getMatriculaMedico(), agenda);
        }
        boolean ok = agenda.agendar(t);
        if (ok) {
            turnosPorId.put(id, t);
        }
        return ok;
    }

    public Turno getTurnoById(String id) {
        return turnosPorId.get(id);
    }

    public boolean cancelarTurno(String id) {
        if (id == null) return false;
        Turno t = turnosPorId.get(id);
        if (t == null) return false;
        AgendaMedico agenda = agendasPorMedico.get(t.getMatriculaMedico());
        if (agenda == null) return false;
        boolean ok = agenda.cancelar(id);
        if (ok) {
            turnosPorId.remove(id);
        }
        return ok;
    }
}
