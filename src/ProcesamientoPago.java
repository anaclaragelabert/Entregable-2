
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