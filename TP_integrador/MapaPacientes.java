public interface MapaPacientes {
    void put(String dni, Paciente p);
    Paciente get(String dni);
    boolean remove(String dni);
    boolean containsKey(String dni);
    int size();
}
