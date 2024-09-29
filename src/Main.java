public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Configuramos el gestor con diferentes números de hilos para cada etapa
        Gestor gestor = new Gestor(4, 3, 2);  // Ejemplo: 4 hilos para pago, 3 para empaquetado, 2 para envíos

        // Simulamos la creación y procesamiento de 10 pedidos
        for (int i = 1; i <= 10; i++) {
            // Simulamos si el pedido es urgente o no (cada pedido par es urgente)
            Pedido pedido = new Pedido(i, i % 2 == 0);
            System.out.println("Procesando pedido: " + pedido.getId() + ", Urgente: " + pedido.esUrgente());

            // Procesamos el pedido enviándolo a la cola de procesamiento de pago
            gestor.procesarPedido(pedido);
        }

        // Iniciamos el empaquetado de los pedidos
        gestor.gestionarEmpaquetado();

        // Iniciamos los envíos de los pedidos
        gestor.gestionarEnvios();

        // Esperamos un tiempo para que todas las tareas se completen
        System.out.println("Esperando que todos los procesos terminen...");
        Thread.sleep(10000);  // Simulación de tiempo para completar las tareas

        // Cerramos el gestor de hilos
        gestor.shutdown();
        System.out.println("Todos los procesos han terminado correctamente.");
    }
}

