import java.util.Objects;

public class Pedido implements Comparable<Pedido> {
    private final int id;
    private final boolean esUrgente;
    private EstadoPedido estado;

    // Constructor
    public Pedido(int id, boolean esUrgente) {
        this.id = id;
        this.esUrgente = esUrgente;
        this.estado = EstadoPedido.PAGO_PENDIENTE; // Estado inicial por defecto
    }

    // Getters
    public int getId() {
        return id;
    }

    public boolean esUrgente() {
        return esUrgente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    // Setter para el estado del pedido
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    // Método toString para imprimir el pedido
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", esUrgente=" + esUrgente +
                ", estado=" + estado +
                '}';
    }

    // Sobrescribir equals y hashCode para comparar pedidos por ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Implementación de compareTo para priorizar pedidos urgentes
    @Override
    public int compareTo(Pedido otroPedido) {
        // Los pedidos urgentes tienen mayor prioridad (devuelve negativo si el pedido actual es más urgente)
        return Boolean.compare(otroPedido.esUrgente, this.esUrgente);
    }
}
