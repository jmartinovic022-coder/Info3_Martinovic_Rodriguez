package lista_enlazada_simple.Ejercicio_9;

import lista_enlazada_simple.ListaEnlazada;
import lista_enlazada_simple.Nodo;

public class RegistroAlumnos {
    private ListaEnlazada<Alumno> lista = new ListaEnlazada<>();

    // Agregar alumno
    public void agregarAlumno(String nombre, int legajo) {
        Alumno nuevo = new Alumno(nombre, legajo);
        lista.insertarFinal(nuevo); // lo agregamos al final
    }

    // Buscar alumno por legajo
    public Alumno buscarAlumno(int legajo) {
        Nodo<Alumno> aux = getCabeza();
        while (aux != null) {
            if (aux.getDato().getLegajo() == legajo) {
                return aux.getDato();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }

    // Eliminar alumno por legajo
    public void eliminarAlumno(int legajo) {
        Nodo<Alumno> aux = getCabeza();

        if (aux == null) return;

        // Si el primero es el que hay que borrar
        if (aux.getDato().getLegajo() == legajo) {
            setCabeza(aux.getSiguiente());
            return;
        }

        // Buscar en el resto
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getDato().getLegajo() == legajo) {
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                return;
            }
            aux = aux.getSiguiente();
        }
    }

    // Método para imprimir todos
    public void imprimirRegistro() {
        lista.imprimir();
    }

    // Acceso interno al primer nodo (hack amigable para reusar)
    @SuppressWarnings("unchecked")
    private Nodo<Alumno> getCabeza() {
        try {
            java.lang.reflect.Field f = ListaEnlazada.class.getDeclaredField("cabeza");
            f.setAccessible(true);
            return (Nodo<Alumno>) f.get(lista);
        } catch (Exception e) {
            return null;
        }
    }

    private void setCabeza(Nodo<Alumno> nuevo) {
        try {
            java.lang.reflect.Field f = ListaEnlazada.class.getDeclaredField("cabeza");
            f.setAccessible(true);
            f.set(lista, nuevo);
        } catch (Exception e) {
            // ignoramos
        }
    }
}
