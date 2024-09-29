import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SharedQueue {
    private final Queue<Pedido> queue = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public synchronized void addPedido(Pedido pedido) throws InterruptedException {
        while (queue.size() == MAX_SIZE) {
            wait();
        }
        queue.add(pedido);
        notifyAll();  // Notificar a los consumidores
    }

    public synchronized Pedido consumePedido() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        Pedido pedido = queue.poll();
        notifyAll();
        return pedido;
    }

    // Extrae múltiples pedidos al mismo tiempo
    public synchronized List<Pedido> consumeMultiplePedidos() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        List<Pedido> pedidos = new LinkedList<>();
        while (!queue.isEmpty() && pedidos.size() < 3) {  // Ajustar cantidad según capacidad
            pedidos.add(queue.poll());
        }
        notifyAll();
        return pedidos;
    }
}

