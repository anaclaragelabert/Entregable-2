import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Gestor {
    private final ExecutorService pagoExecutor;
    private final ExecutorService empaquetadoExecutor;
    private final ExecutorService envioExecutor;
    private final SharedQueue pagoQueue;
    private final SharedQueue empaquetadoQueue;
    private final SharedQueue envioQueue;

    // Constructor que inicializa diferentes pools de hilos para cada etapa
    public Gestor(int hilosPago, int hilosEmpaquetado, int hilosEnvio) {
        // Inicializamos los pools de hilos específicos para cada etapa
        this.pagoExecutor = Executors.newFixedThreadPool(hilosPago);
        this.empaquetadoExecutor = Executors.newFixedThreadPool(hilosEmpaquetado);
        this.envioExecutor = Executors.newFixedThreadPool(hilosEnvio);
        
        // Inicializamos las colas para las diferentes fases del proceso
        this.pagoQueue = new SharedQueue();
        this.empaquetadoQueue = new SharedQueue();
        this.envioQueue = new SharedQueue();
    }

    // Método para procesar un nuevo pedido
    public void procesarPedido(Pedido pedido) {
        // Añadimos la tarea de procesar el pago a la cola de ExecutorService
        pagoExecutor.submit(new ProcesamientoPago(pedido, empaquetadoQueue));
    }

    // Método para gestionar el empaquetado de los pedidos
    public void gestionarEmpaquetado() {
        empaquetadoExecutor.submit(new EmpaquetadoPedidos(empaquetadoQueue, envioQueue));
    }

    // Método para gestionar los envíos de los pedidos
    public void gestionarEnvios() {
        envioExecutor.submit(new Envios(envioQueue));
    }

    // Método para cerrar el gestor (esperar que todas las tareas terminen y cerrar los pools)
    public void shutdown() throws InterruptedException {
        // Apagamos los ejecutores de pago, empaquetado y envío
        pagoExecutor.shutdown();
        empaquetadoExecutor.shutdown();
        envioExecutor.shutdown();

        // Esperamos a que todos los hilos finalicen
        if (!pagoExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
            pagoExecutor.shutdownNow();
        }
        if (!empaquetadoExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
            empaquetadoExecutor.shutdownNow();
        }
        if (!envioExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
            envioExecutor.shutdownNow();
        }
    }

    // Método para ejecutar el flujo completo
    public static void main(String[] args) throws InterruptedException {
        // Probar con diferentes configuraciones de hilos
        Gestor gestor = new Gestor(4, 3, 2);  // Ejemplo: 4 hilos para pago, 3 para empaquetado, 2 para envíos

        // Simular pedidos y procesamiento
        for (int i = 1; i <= 10; i++) {
            Pedido pedido = new Pedido(i, i % 2 == 0);  // Simular si es urgente
            gestor.procesarPedido(pedido);  // Procesar cada pedido
        }

        // Gestionar empaquetado y envíos en paralelo
        gestor.gestionarEmpaquetado();
        gestor.gestionarEnvios();

        // Esperamos un tiempo para que todas las tareas se completen
        Thread.sleep(10000); // Simulación de espera

        // Cerramos el gestor
        gestor.shutdown();
    }
}
