package pila_cola.Ejercicio_8;
import pila_cola.ColaArreglo;
import java.util.Scanner;

public class LlamadasTelefonicas {
    public static void main(String[] args) throws Exception {
        ColaArreglo<String> llamadas = new ColaArreglo<>(5);
        Scanner sc = new Scanner(System.in);
        int opc, id_llamada=1;
        do {
            System.out.println("1- Nueva llamada");
            System.out.println("2- Atender llamada");
            System.out.println("3- Mostrar llamadas en proceso");
            System.out.println("4- Salir");
            System.out.print("Ingrese una opción: ");
            opc=Integer.parseInt(sc.nextLine());
            switch(opc){
                case 1:
                    if(llamadas.isFull()) {
                        String aux=llamadas.dequeue();
                        System.out.println("Se colgo la "+ aux);
                    }
                    String llamada = "Llamada_"+id_llamada;
                    llamadas.enqueue(llamada);
                    System.out.println("Se ha agregado: " + llamada);
                    id_llamada++;
                    break;
                case 2:
                    llamadas.dequeue();
                    break;
                case 3:
                    llamadas.size();
                    llamadas.mostrar();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (opc != 4);
        sc.close();
    }
}
