public class Tarea {
    private int id;
    private String descripcion;
    private String estado;

    public Tarea(int id, String descripcion){
        this.descripcion=descripcion;
        this.estado="Pendiente";
        this.id=id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getEstado() {
        return estado;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\n"+id+ "- Descripción: " + descripcion + "\nEstado: "+ estado+"\n";
    }
}
