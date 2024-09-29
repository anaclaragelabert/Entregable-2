public class Envios implements Runnable {
    private final SharedQueue envioQueue;

    public Envios(SharedQueue envioQueue) {
        this.envioQueue = envioQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pedido pedido = envioQueue.consumePedido();
                
                // Verificar si el pedido está en estado EMPAQUETADO
                if (pedido.getEstado() != EstadoPedido.EMPAQUETADO) {
                    throw new IllegalStateException("El pedido no está empaquetado. No se puede enviar.");
                }

                // Simular el envío
                Thread.sleep(1000);
                pedido.setEstado(EstadoPedido.ENVIADO);
                
                // Otras acciones, como generar un número de seguimiento o notificar al cliente
                String trackingNumber = generarNumeroSeguimiento();
                System.out.println("Pedido " + pedido.getId() + " enviado con número de seguimiento: " + trackingNumber);

            }
        } catch (InterruptedException e) {
            System.err.println("Error en el envío: " + e.getMessage());
        }
    }
    // Método simulado para generar un número de seguimiento
    private String generarNumeroSeguimiento() {
        return "TRACK" + (int)(Math.random() * 10000);
    }
}

/* 
public class Envios implements Runnable {
    private final Pedido pedido;

    public Envios(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void run() {
        try {
            // Verificar si el pedido está empaquetado antes de enviarlo
            if (pedido.getEstado() != EstadoPedido.EMPAQUETADO) {
                throw new IllegalStateException("El pedido no está empaquetado. No se puede enviar.");
            }

            // Simulación del proceso de envío
            procesarEnvio(pedido);

            // Cambiar el estado del pedido a ENVIADO
            pedido.setEstado(EstadoPedido.ENVIADO);

            // Otras acciones, como generar un número de seguimiento o notificar al cliente
            String trackingNumber = generarNumeroSeguimiento();
            System.out.println("Pedido " + pedido.getId() + " enviado con número de seguimiento: " + trackingNumber);

        } catch (Exception e) {
            // Manejo de errores en el envío
            System.err.println("Error al procesar el envío del pedido: " + e.getMessage());
        }
    }

    // Método para procesar el envío, puede incluir integración con sistemas externos
    private void procesarEnvio(Pedido pedido) {
        try {
            Thread.sleep(2000); // Simula tiempo de procesamiento de envío
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Reestablecer el estado de interrupción
        }
    }

    // Método simulado para generar un número de seguimiento
    private String generarNumeroSeguimiento() {
        return "TRACK" + (int)(Math.random() * 10000);
    }
}
*/