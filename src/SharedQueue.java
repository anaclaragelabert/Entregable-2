import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SharedQueue {
    // Cambiamos LinkedList a PriorityQueue para gestionar la prioridad de los pedidos
    private final Queue<Pedido> queue = new PriorityQueue<>();
    private final int MAX_SIZE = 10;

    // Añadir un pedido a la cola (productores)
    public synchronized void addPedido(Pedido pedido) throws InterruptedException {
        while (queue.size() == MAX_SIZE) {
            wait();  // Espera si la cola está llena
        }
        queue.add(pedido);
        notifyAll();  // Notificar a los consumidores
    }

    // Consumir un pedido de la cola (consumidores)
    public synchronized Pedido consumePedido() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();  // Espera si no hay pedidos
        }
        Pedido pedido = queue.poll();
        notifyAll();  // Notificar a los productores
        return pedido;
    }

    // Consumir múltiples pedidos a la vez (limitado a 3 en este caso)
    public synchronized List<Pedido> consumeMultiplePedidos() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        List<Pedido> pedidos = new LinkedList<>();
        while (!queue.isEmpty() && pedidos.size() < 3) {  // Procesar hasta 3 pedidos
            pedidos.add(queue.poll());
        }
        notifyAll();
        return pedidos;
    }
}
