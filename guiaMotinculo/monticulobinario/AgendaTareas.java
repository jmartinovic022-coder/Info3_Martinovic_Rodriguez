package monticulobinario;

import java.util.*;

/**
 * Gestión de una agenda de tareas usando MinHeap.
 * 
 * Cada tarea tiene una prioridad (menor número = más urgente).
 * Se muestra el estado del heap visualmente después de cada operación.
 */
class Tarea {
    private final String descripcion;
    private final int prioridad; // menor número = más urgente

    public Tarea(String descripcion, int prioridad) {
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    @Override
    public String toString() {
        String nivel = switch (prioridad) {
            case 1 -> "alta";
            case 2 -> "media";
            case 3 -> "baja";
            default -> "desconocida";
        };
        return "\"" + descripcion + "\" (prioridad " + nivel + ")";
    }
}

public class AgendaTareas {
    private final MinHeap heap;
    private final Map<Integer, Tarea> mapaTareas;
    private int contadorId; // para evitar empates de prioridad

    public AgendaTareas() {
        heap = new MinHeap();
        mapaTareas = new HashMap<>();
        contadorId = 0;
    }

    // Definición de la clase Tarea
    public static class Tarea {
        private final String descripcion;
        private final int prioridad; // menor número = más urgente

        public Tarea(String descripcion, int prioridad) {
            this.descripcion = descripcion;
            this.prioridad = prioridad;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public int getPrioridad() {
            return prioridad;
        }

        @Override
        public String toString() {
            String nivel = switch (prioridad) {
                case 1 -> "alta";
                case 2 -> "media";
                case 3 -> "baja";
                default -> "desconocida";
            };
            return "\"" + descripcion + "\" (prioridad " + nivel + ")";
        }
    }
    // Método de ayuda para mostrar la estructura del heap
    private void mostrarEstructura() {
        if (heap.isEmpty()) {
            System.out.println("\n🌳 Montículo vacío");
            return;
        }
        System.out.println("\n🌳 Estado actual del montículo:");
        heap.printTree();
        System.out.println("\nLeyenda (números mostrados son las claves):");
        System.out.println("< 200: Prioridad Alta");
        System.out.println("200-299: Prioridad Media");
        System.out.println("≥ 300: Prioridad Baja");
    }

    // 1️⃣ Agregar tarea
    public void agregarTarea(Tarea t) {
        if (t == null) {
            throw new IllegalArgumentException("La tarea no puede ser null");
        }
        int clave = t.getPrioridad() * 100 + contadorId++;
        mapaTareas.put(clave, t);
        heap.add(clave);
        System.out.println("\n📝 Tarea #" + contadorId + ": " + t.getDescripcion());
        mostrarEstructura();
    }

    // 2️⃣ Ver próxima tarea urgente (sin eliminar)
    public Tarea proximaTarea() {
        if (heap.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
            return null;
        }
        int clave = heap.peek();
        Tarea t = mapaTareas.get(clave);
        System.out.println("\n👀 Próxima tarea urgente: " + t);
        mostrarEstructura();
        return t;
    }

    // 3️⃣ Completar la tarea más urgente
    public Tarea completarTarea() {
        if (heap.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
            return null;
        }
        int clave = heap.poll();
        Tarea t = mapaTareas.remove(clave);
        System.out.println("\n✅ Completando tarea: " + t);
        if (!heap.isEmpty()) {
            mostrarEstructura();
        }
        return t;
    }

    // 4️⃣ Mostrar todas las tareas en orden de prioridad
    public void mostrarPendientes() {
        if (mapaTareas.isEmpty()) {
            System.out.println("\n📂 No hay tareas pendientes.");
            return;
        }

        // Mostrar la estructura del montículo primero
        mostrarEstructura();

        // Luego mostrar la lista ordenada
        System.out.println("\n📋 Lista de tareas por prioridad:");
        mapaTareas.keySet().stream()
            .sorted()
            .forEach(clave -> {
                Tarea t = mapaTareas.get(clave);
                System.out.println("- " + t);
            });
    }

    // 🧭 Programa principal
    public static void main(String[] args) {
        AgendaTareas agenda = new AgendaTareas();
        System.out.println("=== 🗓️ AGENDA DE TAREAS (usando MinHeap) ===");
        
        try (Scanner sc = new Scanner(System.in)) {
            boolean salir = false;
            while (!salir) {
                System.out.println("""
                    \nSeleccione una opción:
                    1. Agregar tarea
                    2. Ver próxima tarea urgente
                    3. Completar tarea más urgente
                    4. Mostrar todas las tareas pendientes
                    5. Salir
                    """);
                System.out.print("Opción: ");
                
                try {
                    int opcion = Integer.parseInt(sc.nextLine().trim());
                    
                    switch (opcion) {
                        case 1 -> {
                            System.out.print("Descripción: ");
                            String desc = sc.nextLine().trim();
                            if (desc.isEmpty()) {
                                System.out.println("Error: La descripción no puede estar vacía.");
                                continue;
                            }
                            
                            System.out.print("Prioridad (1=alta, 2=media, 3=baja): ");
                            try {
                                int pr = Integer.parseInt(sc.nextLine().trim());
                                if (pr < 1 || pr > 3) {
                                    System.out.println("Error: La prioridad debe ser 1 (alta), 2 (media) o 3 (baja).");
                                    continue;
                                }
                                agenda.agregarTarea(new Tarea(desc, pr));
                            } catch (NumberFormatException e) {
                                System.out.println("Error: La prioridad debe ser un número (1, 2 o 3).");
                            }
                        }
                        case 2 -> agenda.proximaTarea();
                        case 3 -> agenda.completarTarea();
                        case 4 -> agenda.mostrarPendientes();
                        case 5 -> salir = true;
                        default -> System.out.println("Opción inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Por favor ingrese un número válido.");
                }
            }
            
            System.out.println("\n👋 Saliendo de la agenda. ¡Hasta luego!");
        }
    }
}
