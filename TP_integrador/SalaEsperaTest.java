public class SalaEsperaTest {
    public static void main(String[] args) {
        SalaEspera s = new SalaEspera(2);
        System.out.println("Llega 'abc' -> inválido? -> " + s.llega("abc"));
        System.out.println("Llega '12345678' -> " + s.llega("12345678"));
        System.out.println("Llega '12345678' (duplicado) -> " + s.llega("12345678"));
        System.out.println("Llega '87654321' -> " + s.llega("87654321"));
        System.out.println("Llega '00000001' cuando está llena -> " + s.llega("00000001"));
        System.out.println("Contenido y tamaño -> size=" + s.size() + ", peek=" + s.peek());
    }
}
