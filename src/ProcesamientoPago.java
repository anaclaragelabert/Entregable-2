public class ProcesamientoPago implements Runnable {
    private final Pedido pedido;
    private final SharedQueue empaquetadoQueue;

    public ProcesamientoPago(Pedido pedido, SharedQueue empaquetadoQueue) {
        this.pedido = pedido;
        this.empaquetadoQueue = empaquetadoQueue;
    }

    @Override
    public void run() {
        try {
            // Verificar si el pedido está en estado PAGO_PENDIENTE
            if (pedido.getEstado() != EstadoPedido.PAGO_PENDIENTE) {
                throw new IllegalStateException("El pedido no está en estado PAGO_PENDIENTE.");
            }

            // Simular el procesamiento de pago
            Thread.sleep(1000);
            pedido.setEstado(EstadoPedido.PAGO_VERIFICADO);
            System.out.println("Pago procesado para el pedido: " + pedido.getId());

            // Enviar el pedido a la cola de empaquetado
            empaquetadoQueue.addPedido(pedido);
        } catch (InterruptedException | IllegalStateException e) {
            System.err.println("Error en el procesamiento del pago: " + e.getMessage());
        }
    }
}


/* 
public class ProcesamientoPago implements Runnable{
    private final Pedido pedido;

    public ProcesamientoPago(Pedido pedido) {
        this.pedido = pedido;
    }
    @Override
    public void run() {
        try {
            // Verificar si el pedido está empaquetado antes de enviarlo
            if (pedido.getEstado() != EstadoPedido.PAGO_PENDIENTE) {
                throw new IllegalStateException("El pedido ya está pago.");
            }
            
            // Lógica de procesamiento de pago
            System.out.println("Procesamiento de pago para orden: " + pedido.getId());
            // Simulación del proceso de envío
            procesarPago(pedido);

            // Cambiar el estado del pedido a ENVIADO
            pedido.setEstado(EstadoPedido.PAGO_VERIFICADO);
            Thread.sleep(100); // Simulación de tiempo de procesamiento
            
        } catch (Exception e) {
            // Manejo de errores en el envío
            System.err.println("Error al procesar el procesamiento de pago del pedido: " + e.getMessage());

        }
    }

    // Método para procesar el envío, puede incluir integración con sistemas externos
    private void procesarPago(Pedido pedido) {
        // Simular una operación que toma tiempo
        try {
            Thread.sleep(2000); // Simula tiempo de procesamiento de envío
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Reestablecer el estado de interrupción
        }
    }
}
*/