import java.util.concurrent.Callable;

public class ProcesamientoPago implements Callable<Boolean> {
    private Pedido pedido;

    public ProcesamientoPago(Pedido pedido) {
        this.pedido = pedido;
    }
    @Override
    public Boolean call() {
        // Lógica de procesamiento de pago
        System.out.println("Processing payment for order: " + pedido.getId());
        // Simular procesamiento de pago
        try {
            Thread.sleep(100); // Simulación de tiempo de procesamiento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        pedido.setEstado(EstadoPedido.PAGO_VERIFICADO);
        return true; // Retornar verdadero si el pago fue exitoso
    }
}