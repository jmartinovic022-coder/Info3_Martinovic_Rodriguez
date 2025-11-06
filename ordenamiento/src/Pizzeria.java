package src;
import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private List<Pedido> pedidos = new ArrayList<>();

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void eliminarPedido(String nombreCliente) {
        pedidos.removeIf(p -> p.getNombreCliente().equalsIgnoreCase(nombreCliente));
    }

    public void actualizarPedido(String nombreCliente, int nuevoTiempo, double nuevoPrecio) {
        for (Pedido p : pedidos) {
            if (p.getNombreCliente().equalsIgnoreCase(nombreCliente)) {
                p.setTiempoPreparacion(nuevoTiempo);
                p.setPrecioTotal(nuevoPrecio);
                break;
            }
        }
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void mostrarPedidos() {
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }
}
