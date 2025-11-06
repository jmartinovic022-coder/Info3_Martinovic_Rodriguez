public class NodoAVL {
    int valor;
    int altura; // Altura del nodo (distancia a la hoja más lejana)
    int fe;     // Factor de Equilibrio
    NodoAVL izq, der;

    public NodoAVL(int valor) {
        this.valor = valor;
        this.altura = 0; // Inicialmente, una hoja tiene altura 0
        this.fe = 0;
        this.izq = null;
        this.der = null;
    }
}