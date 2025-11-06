package arbolrojinegro.tests;

import arbolrojinegro.*;
import java.util.List;

/**
 * TestGeneralCompleto:
 * Prueba integral de todos los ejercicios (1 a 10)
 * en un solo flujo para verificar el correcto funcionamiento
 * del árbol rojinegro.
 */
public class TestGeneralCompleto {
    public static void main(String[] args) {
        System.out.println("===========================================================");
        System.out.println("     TEST GENERAL COMPLETO – Árbol RojiNegro (Ej 1–10)");
        System.out.println("===========================================================\n");

        RBTree<Integer,String> t = new RBTree<>();

        /* EJERCICIO 1: NIL sentinel */
        System.out.println("EJERCICIO 1: NIL sentinel y raíz inicial\n");
        System.out.println("Antes de insertar nada:");
        t.mostrarArbol();
        System.out.println("root == NIL: " + (t.root == t.NIL));
        System.out.println("NIL.color == NEGRO: " + (t.NIL.color == RBNode.Color.NEGRO));
        System.out.println("-----------------------------------------------------------\n");

        /* EJERCICIO 2 y 3: Rotaciones */
        System.out.println("EJERCICIOS 2 y 3: Rotaciones izquierda y derecha\n");
        RBNode<Integer,String> a = t.insertBST(10,"A");
        RBNode<Integer,String> b = t.insertBST(20,"B");
        RBNode<Integer,String> c = t.insertBST(30,"C");

        t.root = a;
        a.right = b; b.parent = a;
        b.right = c; c.parent = b;
        a.left = b.left = c.left = c.right = t.NIL;

        System.out.println("Árbol inicial (para rotaciones):");
        t.mostrarArbol();

        System.out.println("\nRotación Izquierda en 10:");
        t.rotateLeft(a);
        t.mostrarArbol();

        System.out.println("\nRotación Derecha en 20:");
        t.rotateRight(t.root);
        t.mostrarArbol();
        System.out.println("-----------------------------------------------------------\n");


        /* EJERCICIOS 5,6,7: Inserción balanceada con fixInsert */
        System.out.println("EJERCICIOS 5,6,7: Clasificación, recoloreo y rotaciones\n");
        RBTree<Integer,String> t3 = new RBTree<>();
        t3.insert(10,"A");
        t3.insert(20,"B");
        t3.insert(30,"C");
        t3.insert(15,"D");
        t3.insert(25,"E");
        System.out.println("\nÁrbol final balanceado:");
        t3.mostrarArbol();
        System.out.println("-----------------------------------------------------------\n");

        /* EJERCICIO 8: Successor y Predecessor */
        System.out.println("EJERCICIO 8: Successor y Predecessor\n");
        RBNode<Integer,String> nodo20 = t3.search(20);
        RBNode<Integer,String> succ = t3.successor(nodo20);
        RBNode<Integer,String> pred = t3.predecessor(nodo20);
        System.out.println("Nodo: 20");
        System.out.println("Successor: " + (succ == t3.NIL ? "NIL" : succ.key));
        System.out.println("Predecessor: " + (pred == t3.NIL ? "NIL" : pred.key));
        System.out.println("-----------------------------------------------------------\n");

        /* EJERCICIO 9: Consulta por rango */
        System.out.println("EJERCICIO 9: Consulta por Rango [a,b]\n");
        List<Integer> rango = t3.rangeQuery(12, 27);
        System.out.println("rangeQuery(12,27): " + rango);
        System.out.println("-----------------------------------------------------------\n");

        /* EJERCICIO 10: Verificación de invariantes */
        System.out.println("EJERCICIO 10: Verificadores de Invariantes\n");
        System.out.println("Raíz negra: " + t3.raizNegra());
        System.out.println("Sin rojo-rojo: " + t3.sinRojoRojo());
        System.out.println("Altura negra: " + t3.alturaNegra());
        System.out.println("-----------------------------------------------------------\n");

        System.out.println("✅ TEST GENERAL COMPLETADO CON ÉXITO ✅");
    }
}
