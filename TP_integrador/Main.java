import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando sistema de gestión de turnos...");

        GestorDeTurnos gestor = new GestorDeTurnos();
        System.out.println("Cargando datos iniciales (pacientes, medicos, turnos)...");
        gestor.cargarDatosIniciales();
        System.out.println("Carga finalizada.");

        Scanner scanner = new Scanner(System.in);
        PlannerRecordatorios planner = new PlannerRecordatorios();

        while (true) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            int opcion;
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Opción no válida.");
                scanner.nextLine(); // limpiar buffer
                continue;
            }
            scanner.nextLine(); // consumir fin de línea

            switch (opcion) {
                case 0:
                    System.out.println("Fin de ejecución.");
                    scanner.close();
                    return;

                case 1: {
                    // Agendar turno
                    System.out.print("ID del nuevo turno: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("DNI del paciente: ");
                    String dni = scanner.nextLine().trim();
                    if (gestor.getPaciente(dni) == null) {
                        System.out.println("Paciente no encontrado. Abortando.");
                        break;
                    }
                    System.out.print("Matrícula del médico: ");
                    String mat = scanner.nextLine().trim();
                    if (gestor.getAgenda(mat) == null) {
                        System.out.println("Médico no encontrado. Abortando.");
                        break;
                    }
                    System.out.print("Fecha y hora (YYYY-MM-DDTHH:MM): ");
                    String fechaStr = scanner.nextLine().trim();
                    LocalDateTime fecha;
                    try {
                        fecha = LocalDateTime.parse(fechaStr);
                    } catch (Exception e) {
                        System.out.println("Formato de fecha inválido. Abortando.");
                        break;
                    }
                    System.out.print("Duración en minutos: ");
                    int dur;
                    try {
                        dur = Integer.parseInt(scanner.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Duración inválida. Abortando.");
                        break;
                    }
                    System.out.print("Motivo: ");
                    String motivo = scanner.nextLine().trim();
                    Turno nuevo = new Turno(id, dni, mat, fecha, dur, motivo);
                    boolean ok = gestor.agregarTurno(nuevo);
                    if (ok) System.out.println("Turno agendado correctamente.");
                    else System.out.println("No se pudo agendar el turno (conflicto o id duplicado).");
                } break;

                case 2: {
                    // Cancelar turno
                    System.out.print("ID del turno a cancelar: ");
                    String idCan = scanner.nextLine().trim();
                    boolean ok = gestor.cancelarTurno(idCan);
                    if (ok) System.out.println("Turno cancelado.");
                    else System.out.println("No se encontró el turno o no pudo cancelarse.");
                } break;

                case 3: {
                    // Buscar turno por id
                    System.out.print("ID del turno a buscar: ");
                    String idBus = scanner.nextLine().trim();
                    Turno t = gestor.getTurnoById(idBus);
                    if (t == null) System.out.println("Turno no encontrado.");
                    else System.out.println(t);
                } break;

                case 4: {
                    // Ver agenda de un médico (usar AgendaMedico.getTurnosOrdenados)
                    System.out.print("Ingrese matrícula del médico: ");
                    String matList = scanner.nextLine().trim();
                    AgendaMedico agenda = gestor.getAgenda(matList);
                    if (agenda == null) {
                        System.out.println("No existe agenda para la matrícula proporcionada.");
                        break;
                    }
                    List<Turno> turnos = agenda.getTurnosOrdenados();
                    if (turnos == null || turnos.isEmpty()) {
                        System.out.println("La agenda está vacía.");
                    } else {
                        System.out.println("Agenda del médico " + matList + ":");
                        for (Turno t : turnos) {
                            System.out.println(String.format("ID=%s, Paciente=%s, Fecha=%s, Dur=%dmin", t.getId(), t.getDniPaciente(), t.getFechaHora(), t.getDuracionMin()));
                        }
                        System.out.println("[Operación O(log n) - Árbol AVL balanceado]");
                    }
                } break;

                case 5: {
                    // Mostrar sala de espera
                    System.out.print("Capacidad de la sala de espera: ");
                    int capacidad;
                    try { capacidad = Integer.parseInt(scanner.nextLine().trim()); }
                    catch (Exception e) { System.out.println("Capacidad inválida."); break; }
                    SalaEspera sala = new SalaEspera(capacidad);
                    System.out.println("Sala creada. Comandos: 'llega DNI', 'atiende', 'salir' para volver al menú.");
                    while (true) {
                        System.out.print("sala> ");
                        String cmd = scanner.nextLine().trim();
                        if (cmd.equalsIgnoreCase("salir")) break;
                        if (cmd.startsWith("llega ")) {
                            String dn = cmd.substring(6).trim();
                            int res = sala.llega(dn);
                            if (res == SalaEspera.LLEGA_OK) {
                                System.out.println("Paciente " + dn + " llegó. Tamaño actual: " + sala.size());
                            } else if (res == SalaEspera.LLEGA_INVALID_DNI) {
                                System.out.println("DNI inválido: '" + dn + "'. Use sólo dígitos (7-8 cifras).");
                            } else if (res == SalaEspera.LLEGA_SALA_LLENA) {
                                System.out.println("La sala está llena. No se puede aceptar al paciente: " + dn);
                            } else if (res == SalaEspera.LLEGA_DUPLICADO) {
                                System.out.println("El DNI '" + dn + "' ya se encuentra en la sala de espera.");
                            } else {
                                System.out.println("No se pudo agregar el paciente: " + dn);
                            }
                        } else if (cmd.equalsIgnoreCase("atiende")) {
                            String atendido = sala.atiende();
                            if (atendido == null) System.out.println("No hay pacientes en la sala.");
                            else System.out.println("Atendiendo paciente: " + atendido);
                        } else {
                            System.out.println("Comando desconocido.");
                        }
                    }
                } break;

                case 6: {
                    // Programar recordatorio
                    System.out.print("ID del recordatorio: ");
                    String idRec = scanner.nextLine().trim();
                    System.out.print("Fecha y hora (YYYY-MM-DDTHH:MM): ");
                    String fechaR = scanner.nextLine().trim();
                    LocalDateTime fechaRec;
                    try { fechaRec = LocalDateTime.parse(fechaR); } catch (Exception e) { System.out.println("Formato de fecha inválido."); break; }
                    System.out.print("DNI del paciente: ");
                    String dniRec = scanner.nextLine().trim();
                    System.out.print("Mensaje: ");
                    String msg = scanner.nextLine().trim();
                    Recordatorio rec = new Recordatorio(idRec, fechaRec, dniRec, msg);
                    planner.programar(rec);
                    System.out.println("Recordatorio programado: " + rec);
                } break;

                case 7: {
                    // Planificar quirófanos
                    System.out.print("Número de solicitudes a planificar: ");
                    int n;
                    try { n = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { System.out.println("Número inválido."); break; }
                    List<SolicitudCirugia> solicitudes = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Solicitud " + (i+1) + " - Matrícula médico: ");
                        String mat = scanner.nextLine().trim();
                        System.out.print("Duración (min): ");
                        int dur;
                        try { dur = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Duración inválida."); dur = 0; }
                        solicitudes.add(new SolicitudCirugia(mat, dur));
                    }
                    System.out.print("Número de quirófanos: ");
                    int numQ;
                    try { numQ = Integer.parseInt(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Número inválido."); break; }
                    System.out.print("Fecha inicio (YYYY-MM-DD): ");
                    LocalDate fi;
                    try { fi = LocalDate.parse(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Fecha inválida."); break; }
                    System.out.print("Fecha fin (YYYY-MM-DD): ");
                    LocalDate ff;
                    try { ff = LocalDate.parse(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Fecha inválida."); break; }
                    System.out.print("Hora inicio jornada (HH:MM): ");
                    LocalTime hi;
                    try { hi = LocalTime.parse(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Hora inválida."); break; }
                    System.out.print("Hora fin jornada (HH:MM): ");
                    LocalTime hf;
                    try { hf = LocalTime.parse(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Hora inválida."); break; }

                    PlanificadorQuirofano pl = new PlanificadorQuirofano();
                    MiHashMap<Integer, List<Turno>> result = pl.planificar(solicitudes, numQ, fi, ff, hi, hf);
                    System.out.println("Planificación:");
                    for (int room = 1; room <= numQ; room++) {
                        List<Turno> list = result.get(room);
                        if (list == null || list.isEmpty()) continue;
                        System.out.println("Quirófano " + room + ":");
                        for (Turno t : list) System.out.println(t);
                    }
                } break;

                case 8: {
                    // Generar reportes: medir tiempos de algoritmos de ordenamiento
                    System.out.println("Ejecutando medición de tiempos de ordenamiento (esto puede tardar unos segundos)...");
                    ReportGenerator.medirTiempos();
                } break;

                case 9: {
                    // Histórico undo/redo
                    System.out.print("Ingrese matrícula del médico (agenda con historial): ");
                    String mat = scanner.nextLine().trim();
                    AgendaMedico a = gestor.getAgenda(mat);
                    if (a == null) { System.out.println("Agenda no encontrada."); break; }
                    if (!(a instanceof AgendaConHistorialInterface)) { System.out.println("La agenda no soporta undo/redo."); break; }
                    AgendaConHistorialInterface hist = (AgendaConHistorialInterface) a;
                    while (true) {
                        System.out.println("1 - Undo\n2 - Redo\n0 - Volver");
                        System.out.print("-> ");
                        String op = scanner.nextLine().trim();
                        if (op.equals("0")) break;
                        if (op.equals("1")) { hist.undo(); System.out.println("Undo ejecutado."); }
                        else if (op.equals("2")) { hist.redo(); System.out.println("Redo ejecutado."); }
                        else System.out.println("Opción inválida.");
                    }
                } break;

                case 10: {
                    // Buscar próximo turno disponible
                    System.out.print("Matrícula del médico: ");
                    String matBus = scanner.nextLine().trim();
                    AgendaMedico agenda = gestor.getAgenda(matBus);
                    if (agenda == null) {
                        System.out.println("No existe agenda para la matrícula proporcionada.");
                        break;
                    }
                    System.out.print("Fecha y hora de inicio (AAAA-MM-DDTHH:MM): ");
                    String fechaInicioStr = scanner.nextLine().trim();
                    LocalDateTime inicio;
                    try {
                        inicio = LocalDateTime.parse(fechaInicioStr);
                    } catch (Exception e) {
                        System.out.println("Formato de fecha inválido.");
                        break;
                    }
                    java.util.Optional<Turno> prox = agenda.siguiente(inicio);
                    if (prox.isPresent()) System.out.println("Próximo turno: " + prox.get());
                    else System.out.println("No hay turnos disponibles.");
                } break;

                case 11: {
                    // Consultar índice de pacientes (Hash)
                    System.out.print("Ingrese DNI del paciente: ");
                    String dniConsulta = scanner.nextLine().trim();
                    Paciente p = gestor.getPaciente(dniConsulta);
                    if (p == null) System.out.println("Paciente no encontrado.");
                    else System.out.println(p);
                } break;

                case 12: {
                    // Consolidador de agendas (Merge) - ejemplo
                    java.util.List<Turno> listaA = new java.util.ArrayList<>();
                    java.util.List<Turno> listaB = new java.util.ArrayList<>();
                    // Ambas listas deben estar ordenadas por fecha
                    try {
                        listaA.add(new Turno("A1", "20111111", "M001", LocalDateTime.parse("2025-11-10T09:00"), 30, "Consulta A1"));
                        listaA.add(new Turno("A2", "20111112", "M001", LocalDateTime.parse("2025-11-10T10:00"), 30, "Consulta A2"));

                        listaB.add(new Turno("B1", "20121111", "M001", LocalDateTime.parse("2025-11-10T09:30"), 20, "Consulta B1"));
                        listaB.add(new Turno("A2", "20111112", "M001", LocalDateTime.parse("2025-11-10T10:00"), 30, "Duplicado A2"));
                    } catch (Exception e) {
                        System.out.println("Error creando turnos de ejemplo: " + e.getMessage());
                        break;
                    }

                    java.util.List<Turno> merged = UtilsAgendas.merge(listaA, listaB);
                    System.out.println("Lista unificada resultante:");
                    for (Turno t : merged) System.out.println(t);
                } break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1 - Agendar turno");
        System.out.println("2 - Cancelar turno");
        System.out.println("3 - Buscar turno por id");
    System.out.println("4 - Ver agenda de un médico");
        System.out.println("5 - Mostrar sala de espera");
        System.out.println("6 - Programar recordatorio");
        System.out.println("7 - Planificar quirófanos");
        System.out.println("8 - Generar reportes");
        System.out.println("9 - Histórico de acciones (undo/redo)");
        System.out.println("10 - Buscar próximo turno disponible");
        System.out.println("11 - Consultar índice de pacientes (Hash)");
        System.out.println("12 - Consolidador de agendas (Merge)");
        System.out.println("0 - Salir");
    }
}
