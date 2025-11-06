// ArbolAVL.java
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ArbolAVL {
    private NodoAVL raiz;

    // Conjunto de nodos críticos donde surgió FE = ±2 durante inserciones
    private final Set<Integer> nodosCriticos = new HashSet<>();

    // Contadores de rotaciones para pruebas (encapsulados)
    private int rotacionesDerecha = 0;
    private int rotacionesIzquierda = 0;
    private int rotacionesLR = 0;
    private int rotacionesRL = 0;

    private int altura(NodoAVL n) {
        return (n == null) ? -1 : n.altura;
    }

    private void actualizarAltura(NodoAVL n) {
        if (n != null) {
            n.altura = 1 + Math.max(altura(n.izq), altura(n.der));
            n.fe = altura(n.izq) - altura(n.der);
        }
    }

    private NodoAVL rotacionDerecha(NodoAVL x) {
        rotacionesDerecha++;
        NodoAVL y = x.izq;
        x.izq = y.der;
        y.der = x;
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private NodoAVL rotacionIzquierda(NodoAVL y) {
        rotacionesIzquierda++;
        NodoAVL x = y.der;
        y.der = x.izq;
        x.izq = y;
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    // Inserción (igual que antes)
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private NodoAVL insertarRecursivo(NodoAVL nodo, int valor) {
        if (nodo == null)
            return new NodoAVL(valor);

        if (valor < nodo.valor)
            nodo.izq = insertarRecursivo(nodo.izq, valor);
        else if (valor > nodo.valor)
            nodo.der = insertarRecursivo(nodo.der, valor);
        else
            return nodo;

        actualizarAltura(nodo);

        // Casos LL, RR, LR, RL
        if (nodo.fe > 1 && valor < nodo.izq.valor) {
            nodosCriticos.add(nodo.valor);
            System.out.println("-> Desbalance en " + nodo.valor + " (LL). Rotación Derecha.");
            return rotacionDerecha(nodo);
        }
        if (nodo.fe < -1 && valor > nodo.der.valor) {
            nodosCriticos.add(nodo.valor);
            System.out.println("-> Desbalance en " + nodo.valor + " (RR). Rotación Izquierda.");
            return rotacionIzquierda(nodo);
        }
        if (nodo.fe > 1 && valor > nodo.izq.valor) {
            nodosCriticos.add(nodo.valor);
            rotacionesLR++;
            System.out.println("-> Desbalance en " + nodo.valor + " (LR). Rotación Izquierda + Derecha.");
            nodo.izq = rotacionIzquierda(nodo.izq);
            return rotacionDerecha(nodo);
        }
        if (nodo.fe < -1 && valor < nodo.der.valor) {
            nodosCriticos.add(nodo.valor);
            rotacionesRL++;
            System.out.println("-> Desbalance en " + nodo.valor + " (RL). Rotación Derecha + Izquierda.");
            nodo.der = rotacionDerecha(nodo.der);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // -------------------
    // Eliminación pública
    // -------------------
    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
    }

    private NodoAVL eliminarRec(NodoAVL nodo, int valor) {
        if (nodo == null) return null;

        if (valor < nodo.valor) {
            nodo.izq = eliminarRec(nodo.izq, valor);
        } else if (valor > nodo.valor) {
            nodo.der = eliminarRec(nodo.der, valor);
        } else {
            // encontrado
            if (nodo.izq == null && nodo.der == null) {
                return null; // hoja
            } else if (nodo.izq == null) {
                return nodo.der;
            } else if (nodo.der == null) {
                return nodo.izq;
            } else {
                // dos hijos: reemplazar con sucesor inorder (mínimo en subárbol derecho)
                NodoAVL sucesor = minNodo(nodo.der);
                nodo.valor = sucesor.valor;
                nodo.der = eliminarRec(nodo.der, sucesor.valor);
            }
        }

        // actualizar y balancear igual que en inserción
        actualizarAltura(nodo);

        if (nodo.fe > 1 && getFE(nodo.izq) >= 0) {
            System.out.println("-> Desbalance en " + nodo.valor + " (LL tras borrado). Rotación Derecha.");
            return rotacionDerecha(nodo);
        }
        if (nodo.fe > 1 && getFE(nodo.izq) < 0) {
            System.out.println("-> Desbalance en " + nodo.valor + " (LR tras borrado). Rotación Izquierda + Derecha.");
            nodo.izq = rotacionIzquierda(nodo.izq);
            return rotacionDerecha(nodo);
        }
        if (nodo.fe < -1 && getFE(nodo.der) <= 0) {
            System.out.println("-> Desbalance en " + nodo.valor + " (RR tras borrado). Rotación Izquierda.");
            return rotacionIzquierda(nodo);
        }
        if (nodo.fe < -1 && getFE(nodo.der) > 0) {
            System.out.println("-> Desbalance en " + nodo.valor + " (RL tras borrado). Rotación Derecha + Izquierda.");
            nodo.der = rotacionDerecha(nodo.der);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL minNodo(NodoAVL n) {
        NodoAVL curr = n;
        while (curr.izq != null) curr = curr.izq;
        return curr;
    }

    private int getFE(NodoAVL n) {
        return (n == null) ? 0 : n.fe;
    }

    // -------------------
    // Recorridos y dibujo
    // -------------------
    public void inorden() {
        inordenRecursivo(raiz);
        System.out.println();
    }

    private void inordenRecursivo(NodoAVL nodo) {
        if (nodo != null) {
            inordenRecursivo(nodo.izq);
            String marcador = nodosCriticos.contains(nodo.valor) ? " [CRIT]" : "";
            System.out.print(nodo.valor + "(FE:" + nodo.fe + ", h:" + nodo.altura + ")" + marcador + " ");
            inordenRecursivo(nodo.der);
        }
    }

    // Permite limpiar la lista de nodos críticos antes de una secuencia de inserciones
    public void limpiarNodosCriticos() {
        nodosCriticos.clear();
    }

    // Devuelve una copia inmodificable del conjunto de nodos críticos detectados
    public Set<Integer> getNodosCriticos() {
        return Collections.unmodifiableSet(new HashSet<>(nodosCriticos));
    }

    public void dibujar() {
        System.out.println("\nRepresentación del Árbol (horizontal):");
        dibujarHorizontal(raiz, 0);
        System.out.println();
    }

    private void dibujarHorizontal(NodoAVL nodo, int nivel) {
        if (nodo != null) {
            dibujarHorizontal(nodo.der, nivel + 1);
            for (int i = 0; i < nivel; i++)
                System.out.print("    ");
            System.out.println("↓ " + nodo.valor + " (h:" + nodo.altura + ", FE:" + nodo.fe + ")");
            dibujarHorizontal(nodo.izq, nivel + 1);
        }
    }

    // -------------------
    // Comprobador esAVL
    // -------------------
    // Clase auxiliar para devolver (esAVL, altura)
    public static class ResultadoAVL {
        public final boolean es;
        public final int altura;
        public ResultadoAVL(boolean es, int altura) {
            this.es = es; this.altura = altura;
        }
    }

    // Método solicitado: esAVL(Nodo r) -> devuelve (esAVL, altura)
    public ResultadoAVL esAVL(NodoAVL r) {
        return esAVLRec(r);
    }

    // Wrapper público que verifica todo el árbol actual
    public ResultadoAVL esAVL() {
        return esAVL(raiz);
    }

    private ResultadoAVL esAVLRec(NodoAVL nodo) {
        if (nodo == null) return new ResultadoAVL(true, -1);

        ResultadoAVL izq = esAVLRec(nodo.izq);
        ResultadoAVL der = esAVLRec(nodo.der);

        // Si alguna subrama no es AVL, propagar falso
        if (!izq.es || !der.es) return new ResultadoAVL(false, 0);

        // verificar FE
        if (Math.abs(izq.altura - der.altura) > 1) return new ResultadoAVL(false, 0);

        // verificar propiedad de ABB
        if (nodo.izq != null && nodo.izq.valor > nodo.valor) return new ResultadoAVL(false, 0);
        if (nodo.der != null && nodo.der.valor < nodo.valor) return new ResultadoAVL(false, 0);

        int h = 1 + Math.max(izq.altura, der.altura);
        return new ResultadoAVL(true, h);
    }

    // Exponer raíz para pruebas (opcional)
    public NodoAVL getRaiz() {
        return raiz;
    }

    // Resetear contadores de rotaciones
    public void resetRotaciones() {
        rotacionesDerecha = 0;
        rotacionesIzquierda = 0;
        rotacionesLR = 0;
        rotacionesRL = 0;
    }

    // Getters para contadores (lectura segura)
    public int getRotacionesDerecha() { return rotacionesDerecha; }
    public int getRotacionesIzquierda() { return rotacionesIzquierda; }
    public int getRotacionesLR() { return rotacionesLR; }
    public int getRotacionesRL() { return rotacionesRL; }
    /**
     * Devuelve el total de rotaciones simples ejecutadas (izquierda + derecha).
     * Los contadores rotacionesLR/rotacionesRL representan eventos compuestos
     * (una rotación doble) y NO se suman aquí para evitar doble conteo.
     */
    public int getTotalRotaciones() { return rotacionesDerecha + rotacionesIzquierda; }
}
