import java.util.*;

public class App {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenido al sistema de gestión de tareas\n\n");
        int opc, id=1, id_aux;
        String descripcion;
        Tarea tarea;
        GestorTarea gt = new GestorTarea();
        Scanner sc = new Scanner(System.in);
        gt.crearArchivo("tareas.txt");
        gt.leerArchivo("tareas.txt");
        do{
            System.out.println("1- Crear tarea");
            System.out.println("2- Listar tareas");
            System.out.println("3- Marcar tarea como completada");
            System.out.println("4- Eliminar tareas completadas");
            System.out.println("5- Aplicar cambios a archivo");
            System.out.println("6- Salir");
            System.out.println("Opcion: ");
            opc=Integer.parseInt(sc.nextLine());
            switch (opc) {
                case 1:
                    do{
                        System.out.println("\nIngrese la descripción de la tarea: ");
                        descripcion=sc.nextLine();
                        if(descripcion.isEmpty()){
                            System.out.println("\nLa descripción no puede estar vacía. Intente nuevamente.\n");
                        }
                    }while (descripcion.isEmpty());
                        
                    
                    tarea=new Tarea(id, descripcion);
                    gt.agregarTarea(tarea);
                    id++;
                    break;
                case 2:
                    gt.mostrarTareas();
                    break;
                case 3: 
                    
                    if(!gt.verificarListaVacia()){
                        gt.mostrarTareas();
                        System.out.println("Ingrese el id de la tarea que ha completado");
                        id_aux=Integer.parseInt(sc.nextLine());
                        gt.tareaCompletada(id_aux);
                    }else{
                        System.out.println("\nNo hay tareas para marcar como completadas\n");
                    }
                    break;
                case 4:
                    gt.eliminarTareasCompletadas();
                    break;
                case 5:
                    gt.guardarTareasEnArchivo("tareas.txt");
                    break;
                case 6: 
                    System.out.println("\n\nHasta luego :)");
                    break;
                default:
                    System.out.println("\nValor incorrecto! Intente nuevamente.");
                    break;
            }
        }while(opc!=6);
        sc.close();
    }
}
