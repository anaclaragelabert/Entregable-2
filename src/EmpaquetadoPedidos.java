import java.util.ArrayList;
import java.util.List;

public class EmpaquetadoPedidos {

    private final List<Pedido> pedidosEnEspera = new ArrayList<>();
    private final List<Pedido> pedidosEnProceso = new ArrayList<>();
    private final int capacidadMaxima = 3; // Cantidad máxima de pedidos que pueden estar en proceso de empaquetado


    // Método para agregar un pedido a la lista de espera
    public void agregarPedido(Pedido pedido) {
        if (pedido.getEstado() != EstadoPedido.PAGO_VERIFICADO) {
                throw new IllegalStateException("El pedido no está pago. No se puede empaqurtar.");
            }
        pedidosEnEspera.add(pedido);
    }

    // Método para transferir pedidos de la lista de espera a la lista de empaquetado
    public void transferirPedidosAProceso() {
        // Ordenar la lista de espera de acuerdo con la prioridad de los pedidos (urgentes primero)
        pedidosEnEspera.sort(Pedido::compareTo);

        // Transferir pedidos a la lista de proceso hasta que esté llena o no haya más pedidos en espera
        while (pedidosEnProceso.size() < capacidadMaxima && !pedidosEnEspera.isEmpty()) {
            Pedido pedido = pedidosEnEspera.remove(0); // Remover de la lista de espera
            pedidosEnProceso.add(pedido);              // Agregar a la lista de proceso
            System.out.println("Pedido transferido a la lista de empaquetado: " + pedido);
        }
    }

    // Método para procesar los pedidos en paralelo
    public void procesarPedidosEnParalelo() {
        // Procesar los pedidos en la lista de empaquetado usando parallelStream
        pedidosEnProceso.parallelStream().forEach(this::empaquetar);

        // Verificar el estado de los pedidos después del empaquetado
        pedidosEnProceso.forEach(pedido -> System.out.println("Pedido " + pedido.getId() + " está en estado: " + pedido.getEstado()));
    }

    // Método que empaqueta el pedido
    private void empaquetar(Pedido pedido) {
        // Simular la lógica de empaquetado del pedido
        System.out.println("Empaquetando pedido " + pedido.getId());
        pedido.setEstado(EstadoPedido.EMPAQUETADO);
    }
}
