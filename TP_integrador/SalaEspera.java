public class SalaEspera {
    private final MiColaCircular<String> cola;

    public static final int LLEGA_OK = 0;
    public static final int LLEGA_INVALID_DNI = 1;
    public static final int LLEGA_SALA_LLENA = 2;
    public static final int LLEGA_DUPLICADO = 3;

    public SalaEspera(int capacidad) {
        this.cola = new MiColaCircular<>(capacidad);
    }

    /**
     * Intenta agregar un paciente identificado por DNI a la sala.
     * Retorna LLEGA_OK (0) si se agregó correctamente,
     * LLEGA_INVALID_DNI (1) si el DNI no es válido (no numérico o longitud inesperada),
     * LLEGA_SALA_LLENA (2) si la sala está llena y no se puede aceptar más gente.
     *
     * Suposición razonable: DNI válido = sólo dígitos y longitud entre 7 y 8 (inclusive).
     */
    public int llega(String dni) {
        if (dni == null) return LLEGA_INVALID_DNI;
        String s = dni.trim();
        if (!s.matches("\\d{7,8}")) {
            return LLEGA_INVALID_DNI;
        }
        // Si el DNI ya está en la sala, no lo aceptamos
        if (cola.contains(s)) {
            return LLEGA_DUPLICADO;
        }
        if (cola.isFull()) {
            return LLEGA_SALA_LLENA;
        }
        cola.llega(s);
        return LLEGA_OK;
    }

    public String atiende() {
        return cola.atiende();
    }

    public String peek() {
        return cola.peek();
    }

    public int size() {
        return cola.size();
    }
}
