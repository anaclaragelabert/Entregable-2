import java.util.List;

public class EmpaquetadoPedidos implements Runnable {
    private final SharedQueue empaquetadoQueue;
    private final SharedQueue envioQueue;

    public EmpaquetadoPedidos(SharedQueue empaquetadoQueue, SharedQueue envioQueue) {
        this.empaquetadoQueue = empaquetadoQueue;
        this.envioQueue = envioQueue;
    }

    @Override
    public void run() {
        try {
            // Extraemos varios pedidos para procesarlos en paralelo
            List<Pedido> pedidos = empaquetadoQueue.consumeMultiplePedidos(); // Extraer varios pedidos al mismo tiempo
            
            // Empaquetamos los pedidos usando parallelStream
            pedidos.parallelStream().forEach(pedido -> {
                try {
                    empaquetar(pedido);
                    envioQueue.addPedido(pedido);  // Añadimos el pedido empaquetado a la cola de envíos
                } catch (InterruptedException e) {
                    System.err.println("Error en empaquetado de pedido " + pedido.getId() + ": " + e.getMessage());
                }
            });
        } catch (InterruptedException e) {
            System.err.println("Error en empaquetado: " + e.getMessage());
        }
    }

    private void empaquetar(Pedido pedido) throws InterruptedException {
        Thread.sleep(1000);  // Simulación del empaquetado
        pedido.setEstado(EstadoPedido.EMPAQUETADO);
        System.out.println("Pedido empaquetado: " + pedido.getId());
    }
}
