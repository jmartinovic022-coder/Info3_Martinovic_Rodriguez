package monticulobinario;

import java.util.Map;
import java.util.HashMap;

/**
 * Simulación de una cola de prioridad médica usando MinHeap.
 * 
 * Prioridad: 1 = Alta, 2 = Media, 3 = Baja.
 */
public class ColaPacientes {
    private final MinHeap heap; // usamos el MinHeap de enteros
    private final Map<Integer, Paciente> mapaPacientes; // vincula prioridad única con paciente
    private int contadorId; // para diferenciar pacientes con misma prioridad

    public ColaPacientes() {
        heap = new MinHeap();
        mapaPacientes = new HashMap<>();
        contadorId = 0;
    }

    static class Paciente {
        private final String nombre;
        private final int prioridad; // 1 = alta, 2 = media, 3 = baja

        public Paciente(String nombre, int prioridad) {
            if (prioridad < 1 || prioridad > 3) {
                throw new IllegalArgumentException("La prioridad debe ser 1 (Alta), 2 (Media) o 3 (Baja)");
            }
            this.nombre = nombre;
            this.prioridad = prioridad;
        }

        @Override
        public String toString() {
            String textoPrioridad = switch (prioridad) {
                case 1 -> "Alta";
                case 2 -> "Media";
                case 3 -> "Baja";
                default -> "Desconocida";
            };
            return nombre + " (Prioridad " + textoPrioridad + ")";
        }
    }

    // Ingresar paciente a la cola
    public void ingresar(Paciente p) {
        if (p == null) {
            throw new IllegalArgumentException("El paciente no puede ser null");
        }
        // combinamos prioridad con un contador para evitar empates exactos
        int clave = p.prioridad * 100 + contadorId++;
        mapaPacientes.put(clave, p);
        System.out.println("\n📥 Ingresando paciente: " + p);
        heap.add(clave);
    }

    // Verificar si hay pacientes en espera
    public boolean hayPacientes() {
        return !heap.isEmpty();
    }

    // Atender al paciente con mayor prioridad (menor número)
    public Paciente atender() {
        if (!hayPacientes()) {
            System.out.println("No hay pacientes en espera.");
            return null;
        }
        int clave = heap.poll();
        Paciente atendido = mapaPacientes.remove(clave);
        System.out.println("✅ Atendiendo a: " + atendido);
        return atendido;
    }

    // Simulación
    public static void main(String[] args) {
        ColaPacientes cola = new ColaPacientes();

        try {
            // Crear pacientes
            Paciente p1 = new Paciente("Ana", 2);
            Paciente p2 = new Paciente("Carlos", 1);
            Paciente p3 = new Paciente("Beatriz", 3);
            Paciente p4 = new Paciente("Diego", 1);
            Paciente p5 = new Paciente("Elena", 2);

            // Ingresar pacientes
            cola.ingresar(p1);
            cola.ingresar(p2);
            cola.ingresar(p3);
            cola.ingresar(p4);
            cola.ingresar(p5);

            System.out.println("\n--- 🏥 Orden de atención ---");
            while (cola.hayPacientes()) {
                cola.atender();
            }

            // Probar manejo de errores
            System.out.println("\n--- 🧪 Pruebas de validación ---");
            cola.ingresar(new Paciente("Inválido", 4)); // Debería lanzar excepción
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        }
    }
}
