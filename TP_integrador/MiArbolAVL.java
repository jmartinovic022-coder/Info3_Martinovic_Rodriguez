public class MiArbolAVL<T extends Comparable<T>> {

    private class NodoAVL {
        T dato;
        NodoAVL izquierdo, derecho;
        int altura;

        NodoAVL(T dato) {
            this.dato = dato;
            this.izquierdo = null;
            this.derecho = null;
            this.altura = 1; // altura de un nodo hoja = 1
        }
    }

    private NodoAVL raiz;

    // Helpers
    private int getAltura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int getBalance(NodoAVL nodo) {
        if (nodo == null) return 0;
        return getAltura(nodo.izquierdo) - getAltura(nodo.derecho);
    }

    // Rotaciones
    private NodoAVL rotacionSimpleDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        // rotación
        x.derecho = y;
        y.izquierdo = T2;

        // actualizar alturas
        y.altura = Math.max(getAltura(y.izquierdo), getAltura(y.derecho)) + 1;
        x.altura = Math.max(getAltura(x.izquierdo), getAltura(x.derecho)) + 1;

        // nueva raíz
        return x;
    }

    private NodoAVL rotacionSimpleIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        // rotación
        y.izquierdo = x;
        x.derecho = T2;

        // actualizar alturas
        x.altura = Math.max(getAltura(x.izquierdo), getAltura(x.derecho)) + 1;
        y.altura = Math.max(getAltura(y.izquierdo), getAltura(y.derecho)) + 1;

        // nueva raíz
        return y;
    }

    private NodoAVL rotacionDobleIzquierdaDerecha(NodoAVL z) {
        // LR: rotación simple izquierda en hijo izquierdo, luego rotación simple derecha en z
        z.izquierdo = rotacionSimpleIzquierda(z.izquierdo);
        return rotacionSimpleDerecha(z);
    }

    private NodoAVL rotacionDobleDerechaIzquierda(NodoAVL x) {
        // RL: rotación simple derecha en hijo derecho, luego rotación simple izquierda en x
        x.derecho = rotacionSimpleDerecha(x.derecho);
        return rotacionSimpleIzquierda(x);
    }

    // Insert
    public void insert(T dato) {
        this.raiz = insertRecursivo(this.raiz, dato);
    }

    private NodoAVL insertRecursivo(NodoAVL nodo, T dato) {
        if (nodo == null) {
            return new NodoAVL(dato);
        }

        int cmp = dato.compareTo(nodo.dato);
        if (cmp < 0) {
            nodo.izquierdo = insertRecursivo(nodo.izquierdo, dato);
        } else if (cmp > 0) {
            nodo.derecho = insertRecursivo(nodo.derecho, dato);
        } else {
            // valores iguales: no insertamos duplicados (puedes cambiar la política si lo deseas)
            return nodo;
        }

        // actualizar altura
        nodo.altura = 1 + Math.max(getAltura(nodo.izquierdo), getAltura(nodo.derecho));

        // balance
        int balance = getBalance(nodo);

        // casos de desbalance
        // Izquierda - Izquierda
        if (balance > 1 && dato.compareTo(nodo.izquierdo.dato) < 0) {
            return rotacionSimpleDerecha(nodo);
        }

        // Derecha - Derecha
        if (balance < -1 && dato.compareTo(nodo.derecho.dato) > 0) {
            return rotacionSimpleIzquierda(nodo);
        }

        // Izquierda - Derecha
        if (balance > 1 && dato.compareTo(nodo.izquierdo.dato) > 0) {
            return rotacionDobleIzquierdaDerecha(nodo);
        }

        // Derecha - Izquierda
        if (balance < -1 && dato.compareTo(nodo.derecho.dato) < 0) {
            return rotacionDobleDerechaIzquierda(nodo);
        }

        return nodo;
    }

    // Remove
    public void remove(T dato) {
        this.raiz = removeRecursivo(this.raiz, dato);
    }

    private NodoAVL getMinValueNode(NodoAVL nodo) {
        NodoAVL current = nodo;
        while (current != null && current.izquierdo != null) {
            current = current.izquierdo;
        }
        return current;
    }

    private NodoAVL removeRecursivo(NodoAVL nodo, T dato) {
        if (nodo == null) return null;

        int cmp = dato.compareTo(nodo.dato);
        if (cmp < 0) {
            nodo.izquierdo = removeRecursivo(nodo.izquierdo, dato);
        } else if (cmp > 0) {
            nodo.derecho = removeRecursivo(nodo.derecho, dato);
        } else {
            // encontramos el nodo a eliminar
            if (nodo.izquierdo == null || nodo.derecho == null) {
                NodoAVL temp = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;

                // no hijos
                if (temp == null) {
                    nodo = null;
                } else {
                    // un hijo
                    nodo = temp;
                }
            } else {
                // dos hijos: tomar sucesor in-order (mínimo del subárbol derecho)
                NodoAVL temp = getMinValueNode(nodo.derecho);
                nodo.dato = temp.dato;
                nodo.derecho = removeRecursivo(nodo.derecho, temp.dato);
            }
        }

        if (nodo == null) return null;

        // actualizar altura
        nodo.altura = 1 + Math.max(getAltura(nodo.izquierdo), getAltura(nodo.derecho));

        // balance
        int balance = getBalance(nodo);

        // aplicar rotaciones según casos (usar balances de hijos cuando corresponda)
        // Izquierda - Izquierda
        if (balance > 1 && getBalance(nodo.izquierdo) >= 0) {
            return rotacionSimpleDerecha(nodo);
        }

        // Izquierda - Derecha
        if (balance > 1 && getBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionSimpleIzquierda(nodo.izquierdo);
            return rotacionSimpleDerecha(nodo);
        }

        // Derecha - Derecha
        if (balance < -1 && getBalance(nodo.derecho) <= 0) {
            return rotacionSimpleIzquierda(nodo);
        }

        // Derecha - Izquierda
        if (balance < -1 && getBalance(nodo.derecho) > 0) {
            nodo.derecho = rotacionSimpleDerecha(nodo.derecho);
            return rotacionSimpleIzquierda(nodo);
        }

        return nodo;
    }

    // (Opcional) método para recorrido en orden — útil para pruebas
    public void inorder() {
        inorderRec(this.raiz);
        System.out.println();
    }

    private void inorderRec(NodoAVL nodo) {
        if (nodo != null) {
            inorderRec(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            inorderRec(nodo.derecho);
        }
    }

    /**
     * Retorna el menor elemento >= key, o null si no existe.
     */
    public T ceiling(T key) {
        NodoAVL current = raiz;
        NodoAVL candidate = null;
        while (current != null) {
            int cmp = current.dato.compareTo(key);
            if (cmp == 0) {
                return current.dato;
            } else if (cmp > 0) {
                candidate = current;
                current = current.izquierdo;
            } else {
                current = current.derecho;
            }
        }
        return (candidate == null) ? null : candidate.dato;
    }

    /**
     * Retorna el mayor elemento < key, o null si no existe.
     */
    public T lower(T key) {
        NodoAVL current = raiz;
        NodoAVL candidate = null;
        while (current != null) {
            int cmp = current.dato.compareTo(key);
            if (cmp < 0) {
                candidate = current;
                current = current.derecho;
            } else {
                current = current.izquierdo;
            }
        }
        return (candidate == null) ? null : candidate.dato;
    }

    /**
     * Devuelve una lista con los elementos en orden ascendente.
     */
    public java.util.List<T> toListInorder() {
        java.util.List<T> out = new java.util.ArrayList<>();
        toListRec(this.raiz, out);
        return out;
    }

    private void toListRec(NodoAVL nodo, java.util.List<T> out) {
        if (nodo == null) return;
        toListRec(nodo.izquierdo, out);
        out.add(nodo.dato);
        toListRec(nodo.derecho, out);
    }
}
