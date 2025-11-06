import java.time.LocalDateTime;

public class Recordatorio implements Comparable<Recordatorio> {
    private String id;
    private LocalDateTime fecha;
    private String dni;
    private String mensaje;

    public Recordatorio(String id, LocalDateTime fecha, String dni, String mensaje) {
        this.id = id;
        this.fecha = fecha;
        this.dni = dni;
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public int compareTo(Recordatorio other) {
        if (this.fecha == null && other.fecha == null) return 0;
        if (this.fecha == null) return -1;
        if (other.fecha == null) return 1;
        return this.fecha.compareTo(other.fecha);
    }

    @Override
    public String toString() {
        return "Recordatorio{id='" + id + "', fecha=" + fecha + ", dni='" + dni + "', mensaje='" + mensaje + "'}";
    }
}
