package arbolrojinegro;

import java.util.ArrayList;
import java.util.List;

/**
 * RBTree con las implementaciones pedidas en el práctico.
 * Contiene métodos para los ejercicios 1..10.
 */
public class RBTree<K extends Comparable<K>, V> {
    // Ejercicio 1: Nodo y NIL sentinel
    public final RBNode<K,V> NIL;
    public RBNode<K,V> root;

    public RBTree() {
        // Crear NIL único y sus punteros a sí mismo
        NIL = new RBNode<>(null, null, RBNode.Color.NEGRO);
        NIL.left = NIL.right = NIL.parent = NIL;
        root = NIL; // raíz = NIL
    }

    /* ---------------------------
       EJERCICIO 2: Rotación izquierda
       rotateLeft(x) actualiza punteros y root si corresponde.
       --------------------------- */
    public void rotateLeft(RBNode<K,V> x) {
        if (x == NIL) return;
        RBNode<K,V> y = x.right;
        if (y == NIL) return; // no es posible
        x.right = y.left;
        if (y.left != NIL) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == NIL) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    /* ---------------------------
       EJERCICIO 3: Rotación derecha
       rotateRight(y) simétrico.
       --------------------------- */
    public void rotateRight(RBNode<K,V> y) {
        if (y == NIL) return;
        RBNode<K,V> x = y.left;
        if (x == NIL) return;
        y.left = x.right;
        if (x.right != NIL) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == NIL) root = x;
        else if (y == y.parent.left) y.parent.left = x;
        else y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    /* ---------------------------
       EJERCICIO 4: Inserción como ABB (sin balance)
       insertBST: inserta como BST y devuelve el nodo nuevo (rojo).
       --------------------------- */
    public RBNode<K,V> insertBST(K key, V val) {
        RBNode<K,V> z = new RBNode<>(key, val, RBNode.Color.ROJO, NIL); // hijos/padre = NIL
        RBNode<K,V> y = NIL;
        RBNode<K,V> x = root;
        while (x != NIL) {
            y = x;
            if (z.key.compareTo(x.key) < 0) x = x.left;
            else x = x.right;
        }
        z.parent = y;
        if (y == NIL) root = z;
        else if (z.key.compareTo(y.key) < 0) y.left = z;
        else y.right = z;
        
        // left/right ya apuntan a NIL por constructor
        return z;
    }

    /* ---------------------------
       Helpers para fixInsert (clasificador)
       EJERCICIO 5: Clasificador de caso para fixInsert
       Devuelve: "TIO_ROJO", "LL", "RR", "LR", "RL"
       --------------------------- */
    public enum Caso { TIO_ROJO, LL, RR, LR, RL, NONE }

    public Caso clasificarCaso(RBNode<K,V> z) {
        if (z == NIL || z.parent == NIL || z.parent.parent == NIL) return Caso.NONE;
        
        // Solo clasificamos si hay violación rojo-rojo
        if (z.color != RBNode.Color.ROJO || z.parent.color != RBNode.Color.ROJO) {
            return Caso.NONE;
        }
        
        RBNode<K,V> p = z.parent;
        RBNode<K,V> g = p.parent;
        RBNode<K,V> tio = (p == g.left) ? g.right : g.left;
        
        System.out.println("\nAnálisis de caso:");
        System.out.println("- Nodo z: " + z.key + " (color: " + (z.color == RBNode.Color.ROJO ? "ROJO" : "NEGRO") + ")");
        System.out.println("- Padre: " + p.key + " (color: " + (p.color == RBNode.Color.ROJO ? "ROJO" : "NEGRO") + ")");
        System.out.println("- Abuelo: " + g.key + " (color: " + (g.color == RBNode.Color.ROJO ? "ROJO" : "NEGRO") + ")");
        System.out.println("- Tío: " + (tio == NIL ? "NIL" : tio.key + " (color: " + (tio.color == RBNode.Color.ROJO ? "ROJO" : "NEGRO") + ")"));
        
        if (tio != NIL && tio.color == RBNode.Color.ROJO) {
            System.out.println("=> Caso TIO_ROJO: padre y tío son rojos");
            return Caso.TIO_ROJO;
        }
        
        // si tío es negro o NIL, decidir entre LL, LR, RR, RL
        Caso caso;
        if (p == g.left) {
            if (z == p.left) caso = Caso.LL;
            else caso = Caso.LR;
        } else {
            if (z == p.right) caso = Caso.RR;
            else caso = Caso.RL;
        }
        
        System.out.println("=> Caso " + caso + ": nodo está en posición " + 
                          (z == p.left ? "izquierda" : "derecha") + " del padre, y padre está en posición " +
                          (p == g.left ? "izquierda" : "derecha") + " del abuelo");
        return caso;
    }

    /* ---------------------------
       EJERCICIO 6: Recoloreo por tío rojo
       Si padre y tío son rojos, ponerlos negros, abuelo rojo y subir z=g.
       --------------------------- */
    private RBNode<K,V> recolorUncleRed(RBNode<K,V> z) {
        RBNode<K,V> p = z.parent;
        RBNode<K,V> g = p.parent;
        RBNode<K,V> tio = (p == g.left) ? g.right : g.left;
        if (p.color == RBNode.Color.ROJO && tio != NIL && tio.color == RBNode.Color.ROJO) {
            System.out.println("  > Recoloreando: padre y tío a negro, abuelo a rojo");
            p.color = RBNode.Color.NEGRO;
            tio.color = RBNode.Color.NEGRO;
            g.color = RBNode.Color.ROJO;
            System.out.println("\nDespués de recolorear:");
            mostrarArbol();
            return g; // subir z = g
        }
        return z; // no cambio
    }

    /* ---------------------------
       EJERCICIO 7: Rotación simple vs doble (un lado)
       Implementación parcial dentro de fixInsert: maneja LL, LR, RR, RL.
       --------------------------- */
    public void fixInsert(RBNode<K,V> z) {
        // Algoritmo clásico: reparar violaciones hacia la raíz.
        while (z.parent != NIL && z.parent.color == RBNode.Color.ROJO) {
            RBNode<K,V> p = z.parent;
            RBNode<K,V> g = p.parent;
            if (g == NIL) break;
            RBNode<K,V> tio = (p == g.left) ? g.right : g.left;

            if (tio != NIL && tio.color == RBNode.Color.ROJO) {
                // Ejercicio 6: tío rojo -> recolorear (usar helper)
                System.out.println("\n=== Caso TÍO ROJO detectado ===");
                System.out.println("- Padre (" + p.key + ") y tío (" + tio.key + ") son rojos");
                System.out.println("- Cambiaremos: padre y tío a negro, abuelo a rojo");
                z = recolorUncleRed(z);
            } else {
                // Distinguimos casos LL/LR y RR/RL
                if (p == g.left) {
                    if (z == p.right) { // LR
                        // Ejercicio 7: LR -> rotateLeft(p)
                        System.out.println("\n=== Caso LR detectado ===");
                        System.out.println("- Nodo (" + z.key + ") está a la derecha de su padre (" + p.key + ")");
                        System.out.println("- Realizando rotación izquierda en " + p.key);
                        rotateLeft(p);
                        z = p; // tras rotateLeft, z apunta al antiguo p
                        p = z.parent;
                    }
                    // LL -> rotateRight(g) y recolorear
                    // Ejercicio 7: LL (o convertido a LL)
                    System.out.println("\nDespués de rotación izquierda:");
                    mostrarArbol();
                    
                    System.out.println("\n=== Caso LL detectado ===");
                    System.out.println("- Realizando rotación derecha en " + g.key);
                    System.out.println("- " + p.key + " subirá y será negro");
                    System.out.println("- " + g.key + " bajará y será rojo");
                    rotateRight(g);
                    p = g.parent; // ahora p es la nueva sub-raíz
                    p.color = RBNode.Color.NEGRO;
                    g.color = RBNode.Color.ROJO;
                    z = p;
                    System.out.println("\nDespués de rotación derecha y recoloreo:");
                    mostrarArbol();
                } else {
                    if (z == p.left) { // RL
                        // Ejercicio 7: RL -> rotateRight(p)
                        System.out.println("\n=== Caso RL detectado ===");
                        System.out.println("- Nodo (" + z.key + ") está a la izquierda de su padre (" + p.key + ")");
                        System.out.println("- Realizando rotación derecha en " + p.key);
                        rotateRight(p);
                        z = p;
                        p = z.parent;
                        System.out.println("\nDespués de rotación derecha:");
                        mostrarArbol();
                    }
                    // RR -> rotateLeft(g) y recolorear
                    System.out.println("\n=== Caso RR detectado ===");
                    System.out.println("- Realizando rotación izquierda en " + g.key);
                    System.out.println("- " + p.key + " subirá y será negro");
                    System.out.println("- " + g.key + " bajará y será rojo");
                    rotateLeft(g);
                    p = g.parent;
                    p.color = RBNode.Color.NEGRO;
                    g.color = RBNode.Color.ROJO;
                    z = p;
                    System.out.println("\nDespués de rotación izquierda y recoloreo:");
                    mostrarArbol();
                }
            }
        }
        
        if (root.color != RBNode.Color.NEGRO) {
            System.out.println("\n=== Asegurando raíz negra ===");
            root.color = RBNode.Color.NEGRO;
            mostrarArbol();
        }
    }

    /* ---------------------------
       EJERCICIO 8: successor y predecessor
       successor: menor > x.key
       predecessor: mayor < x.key
       --------------------------- */
    public RBNode<K,V> minimum(RBNode<K,V> node) {
        while (node.left != NIL) node = node.left;
        return node;
    }

    public RBNode<K,V> maximum(RBNode<K,V> node) {
        while (node.right != NIL) node = node.right;
        return node;
    }

    // successor de un nodo
    public RBNode<K,V> successor(RBNode<K,V> x) {
        if (x == NIL) return NIL;
        if (x.right != NIL) return minimum(x.right);
        RBNode<K,V> y = x.parent;
        while (y != NIL && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // predecessor de un nodo
    public RBNode<K,V> predecessor(RBNode<K,V> x) {
        if (x == NIL) return NIL;
        if (x.left != NIL) return maximum(x.left);
        RBNode<K,V> y = x.parent;
        while (y != NIL && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /* ---------------------------
       EJERCICIO 9: Consulta por rango [a,b]
       inOrder acotado que devuelve lista de claves en orden.
       --------------------------- */
    public List<K> rangeQuery(K a, K b) {
        List<K> resultado = new ArrayList<>();
        rangeQueryRec(root, a, b, resultado);
        return resultado;
    }

    private void rangeQueryRec(RBNode<K,V> node, K a, K b, List<K> out) {
        if (node == NIL) return;
        if (node.key.compareTo(a) < 0) {
            // nodo.key < a => ir a derecha
            rangeQueryRec(node.right, a, b, out);
        } else if (node.key.compareTo(b) > 0) {
            // nodo.key > b => ir a izquierda
            rangeQueryRec(node.left, a, b, out);
        } else {
            // en rango
            rangeQueryRec(node.left, a, b, out);
            out.add(node.key);
            rangeQueryRec(node.right, a, b, out);
        }
    }

    /* ---------------------------
       EJERCICIO 10: Verificadores de invariantes
       - raizNegra()
       - sinRojoRojo()
       - alturaNegra() -> devuelve -1 si no es consistente
       --------------------------- */
    public boolean raizNegra() {
        return root == NIL || root.color == RBNode.Color.NEGRO;
    }

    // si un nodo es rojo, ambos hijos deben ser negros (o NIL)
    public boolean sinRojoRojo() {
        return sinRojoRojoRec(root);
    }

    private boolean sinRojoRojoRec(RBNode<K,V> node) {
        if (node == NIL) return true;
        if (node.color == RBNode.Color.ROJO) {
            if (node.left.color != RBNode.Color.NEGRO || node.right.color != RBNode.Color.NEGRO) return false;
        }
        return sinRojoRojoRec(node.left) && sinRojoRojoRec(node.right);
    }

    // altura negra: devuelve black-height si todos los caminos tienen la misma, -1 si no
    public int alturaNegra() {
        return alturaNegraRec(root);
    }

    private int alturaNegraRec(RBNode<K,V> node) {
        if (node == NIL) return 0;
        int left = alturaNegraRec(node.left);
        if (left == -1) return -1;
        int right = alturaNegraRec(node.right);
        if (right == -1) return -1;
        if (left != right) return -1;
        return left + (node.color == RBNode.Color.NEGRO ? 1 : 0);
    }

    /* ---------------------------
       Métodos auxiliares útiles para testing:
       - buscar por clave
       - recorrido inorder
       --------------------------- */
    public RBNode<K,V> search(K key) {
        RBNode<K,V> cur = root;
        while (cur != NIL) {
            int cmp = key.compareTo(cur.key);
            if (cmp == 0) return cur;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return NIL;
    }

    public List<K> inorderKeys() {
        List<K> out = new ArrayList<>();
        inorderRec(root, out);
        return out;
    }

    private void inorderRec(RBNode<K,V> node, List<K> out) {
        if (node == NIL) return;
        inorderRec(node.left, out);
        out.add(node.key);
        inorderRec(node.right, out);
    }

    // insert balanced (util) para tests: inserta y luego fixInsert
    public RBNode<K,V> insert(K key, V val) {
        System.out.println("\n=== Insertando nodo " + key + " ===");
        RBNode<K,V> z = insertBST(key, val);
        
        System.out.println("\nIniciando proceso de balanceo...");
        Caso caso = clasificarCaso(z);
        if (caso != Caso.NONE) {
            System.out.println("Caso detectado: " + caso);
            System.out.println("\nArbol antes de balancear):");
            mostrarArbol();

        }else {
            System.out.println("No se detectaron violaciones de propiedades.");
        }
        
        fixInsert(z);
        return z;
    }


/* -------------------------------------------------
   Visualización horizontal del árbol (valores mayores arriba)
   ------------------------------------------------- */
    public void mostrarArbol() {
        if (root == NIL) {
            System.out.println("(vacío)");
        } else {
            mostrar(root, 0);
        }
    }

    private void mostrar(RBNode<K,V> nodo, int nivel) {
        if (nodo == NIL) return;

        // primero imprimir rama derecha (valores mayores)
        mostrar(nodo.right, nivel + 1);

        // margen según nivel
        for (int i = 0; i < nivel; i++) System.out.print("        ");
        System.out.println("(" + nodo.key + ":" + (nodo.color == RBNode.Color.ROJO ? "R" : "N") + ")");

        // luego rama izquierda (valores menores)
        mostrar(nodo.left, nivel + 1);
    }
}
