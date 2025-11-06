import java.time.LocalDateTime;

public class Turno implements Comparable<Turno> {
    private String id;
    private String dniPaciente;
    private String matriculaMedico;
    private LocalDateTime fechaHora;
    private int duracionMin;
    private String motivo;

    public Turno(String id, String dniPaciente, String matriculaMedico, LocalDateTime fechaHora, int duracionMin, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.matriculaMedico = matriculaMedico;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.motivo = motivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getMatriculaMedico() {
        return matriculaMedico;
    }

    public void setMatriculaMedico(String matriculaMedico) {
        this.matriculaMedico = matriculaMedico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public int compareTo(Turno other) {
        if (this.fechaHora == null && other.fechaHora == null) return 0;
        if (this.fechaHora == null) return -1;
        if (other.fechaHora == null) return 1;
        return this.fechaHora.compareTo(other.fechaHora);
    }

    @Override
    public String toString() {
        return "Turno{id='" + id + "', dniPaciente='" + dniPaciente + "', matriculaMedico='" + matriculaMedico + "', fechaHora=" + fechaHora + ", duracionMin=" + duracionMin + ", motivo='" + motivo + "'}";
    }
}