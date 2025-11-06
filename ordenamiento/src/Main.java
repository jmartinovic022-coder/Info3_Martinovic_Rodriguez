package src;

public class Main {
    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria();
        Ordenador ordenador = new Ordenador();

        // Agregar algunos pedidos
        pizzeria.agregarPedido(new Pedido("Ana", 15, 2500));
        pizzeria.agregarPedido(new Pedido("Luis", 10, 1800));
        pizzeria.agregarPedido(new Pedido("Carlos", 20, 3000));
        pizzeria.agregarPedido(new Pedido("Beatriz", 12, 2200));

        System.out.println("Pedidos originales:");
        pizzeria.mostrarPedidos();

        // Ordenar por tiempo de preparación (inserción)
        TiempoOrdenamiento.medirTiempo(() -> ordenador.insercionPorTiempo(pizzeria.getPedidos()), "Inserción (Tiempo)");
        System.out.println("\nPedidos ordenados por tiempo:");
        pizzeria.mostrarPedidos();

        // Ordenar por precio (shellsort)
        TiempoOrdenamiento.medirTiempo(() -> ordenador.shellsortPorPrecio(pizzeria.getPedidos()), "Shellsort (Precio)");
        System.out.println("\nPedidos ordenados por precio:");
        pizzeria.mostrarPedidos();

        // Ordenar por nombre (quicksort)
        TiempoOrdenamiento.medirTiempo(() -> ordenador.quicksortPorNombre(pizzeria.getPedidos(), 0, pizzeria.getPedidos().size() - 1), "Quicksort (Nombre)");
        System.out.println("\nPedidos ordenados por nombre:");
        pizzeria.mostrarPedidos();
    }
}
