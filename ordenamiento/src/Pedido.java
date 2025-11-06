package src;
public class Pedido {
    private String nombreCliente;
    private int tiempoPreparacion; // en minutos
    private double precioTotal;

    public Pedido(String nombreCliente, int tiempoPreparacion, double precioTotal) {
        this.nombreCliente = nombreCliente;
        this.tiempoPreparacion = tiempoPreparacion;
        this.precioTotal = precioTotal;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "Cliente='" + nombreCliente + '\'' +
                ", Tiempo=" + tiempoPreparacion + " min" +
                ", Precio=$" + precioTotal +
                '}';
    }
}
