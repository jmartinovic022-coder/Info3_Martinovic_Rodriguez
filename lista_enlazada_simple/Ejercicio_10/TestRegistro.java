package lista_enlazada_simple.Ejercicio_9;

public class TestRegistro {
    public static void main(String[] args) {
        RegistroAlumnos registro = new RegistroAlumnos();

        // Simular tres alumnos
        registro.agregarAlumno("Juan Pérez", 101);
        registro.agregarAlumno("Ana López", 102);
        registro.agregarAlumno("Carlos Gómez", 103);

        System.out.println("Registro inicial:");
        registro.imprimirRegistro();

        // Buscar alumno
        int buscarLegajo = 102;
        Alumno encontrado = registro.buscarAlumno(buscarLegajo);
        System.out.println("\nBuscando legajo " + buscarLegajo + ": " +
                (encontrado != null ? encontrado : "No encontrado"));

        // Eliminar alumno
        int eliminarLegajo = 101;
        System.out.println("\nEliminando legajo " + eliminarLegajo + "...");
        registro.eliminarAlumno(eliminarLegajo);

        System.out.println("\nRegistro final:");
        registro.imprimirRegistro();
    }
}

