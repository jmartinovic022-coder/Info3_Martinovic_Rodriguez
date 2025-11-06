import java.util.ArrayList;
import java.io.*;

public class GestorTarea {
    private ArrayList<Tarea> listaTareas;

    public void agregarTarea(Tarea tarea){
        listaTareas.add(tarea);
    }

    public GestorTarea(){
        listaTareas=new ArrayList<>();
    }

    public void mostrarTareas(){
        if(verificarListaVacia()){
            System.out.println("\nNo hay tareas para mostrar\n");
            return;
        }
        for(int i =0; i<listaTareas.size(); i++){
            Tarea aux;
            aux=listaTareas.get(i);
            System.out.println(aux);
            System.out.println("-------------------");
        }
        
    }
    public void tareaCompletada(int id){
        int aux=0;
        if(id<1 || id>listaTareas.size()){
            System.out.println("\nEl id ingresado no es válido\n");
            return;
        }
        for (int i =0; i<listaTareas.size(); i++){
            if (listaTareas.get(i).getId()==id) {
                aux++;
                break;
            }
            if(listaTareas.get(i).getEstado()=="Completada" && listaTareas.get(i).getId()==id){
                System.out.println("\nLa tarea ya ha sido marcada como completada\n");
                return;
            }
        }
        if(aux==0){
            System.out.println("\nEl id ingresado no es válido\n");
            return;
        }
        listaTareas.get(id-1).setEstado("Completada");
        System.out.println("\nLa tarea ha sido marcada como completada\n");
    }

    public void eliminarTareasCompletadas(){
        int aux=0;
        if (verificarListaVacia()){
            System.out.println("\nNo hay tareas para eliminar\n");
            return;
        }
        for (int i =0; i<listaTareas.size(); i++){
            if(listaTareas.get(i).getEstado().equals("Completada")){
                listaTareas.remove(i);
                aux++;
            }
        }
        if(aux==0){
            System.out.println("\nNo hay tareas completadas para eliminar\n");
        }else{
            System.out.println("\nSe han eliminado "+aux+" tareas completadas\n");
        }
    }
    public boolean verificarListaVacia(){
        if(listaTareas.size()==0){
            return true;
        }
        return false;
    }

    public void guardarTareasEnArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        borrarContenidoArchivo(nombreArchivo);
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(archivo,true));
            for (int i = 0; i < listaTareas.size(); i++) {
                Tarea tarea = listaTareas.get(i);
                pw.println(tarea.getId() + "," + tarea.getDescripcion() + "," + tarea.getEstado());
            }
            pw.close();
            System.out.println("\nCambios en el archivo efectuados correctamente.\n");
        } catch (IOException e) {
            System.out.println("\nError al guardar las tareas en el archivo: " + e.getMessage() + "\n");
        }
    }

    public void borrarContenidoArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        try (PrintWriter pw = new PrintWriter(archivo)) {
            // Al abrir el archivo con PrintWriter sin el modo append, se borra su contenido
            pw.print("");
        } catch (IOException e) {
            System.out.println("\nError al borrar el contenido del archivo: " + e.getMessage() + "\n");
        }
    }

    public void crearArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if(archivo.exists()){
            return;
        }
        try {
            PrintWriter pw = new PrintWriter(archivo);
            pw.close();
            System.out.println("\nEl Archivo para guardar tareas se creo correctamente, con el nombre: " + nombreArchivo + "\n");
        } catch (IOException e) {
            System.out.println("\nError al crear el archivo: " + e.getMessage() + "\n");
        }
    }

    public void leerArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine())!= null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String descripcion = partes[1];
                    String estado = partes[2].trim();
                    Tarea tarea = new Tarea(id, descripcion);
                    tarea.setEstado(estado);
                    listaTareas.add(tarea);
                }
            }
        } catch (IOException e) {
            System.out.println("\nError al leer las tareas desde el archivo: " + e.getMessage() + "\n");
        }
    }
}
