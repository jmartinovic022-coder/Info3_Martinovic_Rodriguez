public class SolicitudCirugia {
    private final String matriculaMedico;
    private final int duracionMin;

    public SolicitudCirugia(String matriculaMedico, int duracionMin) {
        this.matriculaMedico = matriculaMedico;
        this.duracionMin = duracionMin;
    }

    public String getMatriculaMedico() {
        return matriculaMedico;
    }

    public int getDuracionMin() {
        return duracionMin;
    }
}
