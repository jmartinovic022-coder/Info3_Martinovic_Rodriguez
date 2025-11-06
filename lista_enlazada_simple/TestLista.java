package lista_enlazada_simple;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestLista {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        int opcion;
        do {
            System.out.println("\n===== MENÚ LISTA ENLAZADA =====");
            System.out.println("1. Insertar al inicio");
            System.out.println("2. Insertar al final");
            System.out.println("3. Insertar en posición");
            System.out.println("4. Eliminar por valor");
            System.out.println("5. Buscar un valor");
            System.out.println("6. Contar elementos");
            System.out.println("7. Invertir lista");
            System.out.println("8. Eliminar duplicados");
            System.out.println("9. Mostrar lista");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese valor a insertar al inicio: ");
                    int valor = leerEntero(sc);
                    lista.insertarInicio(valor);
                }
                case 2 -> {
                    System.out.print("Ingrese valor a insertar al final: ");
                    int valor = leerEntero(sc);
                    lista.insertarFinal(valor);
                }
                case 3 -> {
                    System.out.print("Ingrese valor a insertar: ");
                    int valor = leerEntero(sc);
                    System.out.print("Ingrese posición (0 = inicio): ");
                    int pos = leerEntero(sc);
                    lista.insertarEn(pos, valor);
                }
                case 4 -> {
                    if (lista.contar() == 0) {
                        System.out.println("La lista está vacía.");
                    } else {
                        System.out.print("Ingrese valor a eliminar: ");
                        int valor = leerEntero(sc);
                        lista.eliminar(valor);
                    }
                }
                case 5 -> {
                    if (lista.contar() == 0) {
                        System.out.println("La lista está vacía.");
                    } else {
                        System.out.print("Ingrese valor a buscar: ");
                        int valor = leerEntero(sc);
                        System.out.println(lista.buscar(valor) ? " Encontrado" : " No encontrado");
                    }
                }
                case 6 -> System.out.println("La lista tiene " + lista.contar() + " elementos.");
                case 7 -> {
                    if (lista.contar() == 0) {
                        System.out.println("No se puede invertir, la lista está vacía.");
                    } else {
                        lista.invertir();
                        System.out.println("Lista invertida.");
                    }
                }
                case 8 -> {
                    if (lista.contar() == 0) {
                        System.out.println("No hay duplicados, la lista está vacía.");
                    } else {
                        lista.eliminarDuplicados();
                        System.out.println("Duplicados eliminados.");
                    }
                }
                case 9 -> lista.imprimir();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Método seguro para leer enteros
    private static int leerEntero(Scanner sc) {
        int num;
        while (true) {
            try {
                num = sc.nextInt();
                sc.nextLine(); // limpiar buffer
                return num;
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Ingrese un número entero: ");
                sc.nextLine(); // descartar entrada incorrecta
            }
        }
    }
}
