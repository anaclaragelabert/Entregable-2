import java.util.List;

public class EmpaquetadoPedidos {
    public static void main(String[] args) {
        // Crear una lista de pedidos para prueba
        List<Pedido> pedidos = List.of(
                new Pedido(1, false),
                new Pedido(2, false),
                new Pedido(3, true),
                new Pedido(4, false)
                // Agregar más pedidos aquí
        );

        // Procesar en paralelo
        pedidos.parallelStream().forEach(pedido -> {
            empaquetar(pedido);
        });

        // Verificar el estado de los pedidos
        pedidos.forEach(pedido -> System.out.println("Pedido " + pedido.getId() + " está en estado: " + pedido.getEstado()));
    }

    private static void empaquetar(Pedido pedido) {
        // Lógica para empaquetar el pedido
        System.out.println("Empaquetando pedido " + pedido.getId());
        pedido.setEstado(EstadoPedido.EMPAQUETADO);
    }
}





public class EmpaquetadoPedidos {

    public void empaquetarPedidosEnParalelo(List<Pedido> pedidos) {
        // Usar parallelStream para empaquetar pedidos en paralelo
        pedidos.parallelStream().forEach(pedido -> {
            empaquetar(pedido);
        });
    }

    private void empaquetar(Pedido pedido) {
        // Lógica para empaquetar el pedido
        System.out.println("Empaquetando pedido " + pedido.getId());
        pedido.setEstado(EstadoPedido.EMPAQUETADO);
    }

    public static void main(String[] args) {
        // Crear una lista de pedidos para prueba
        List<Pedido> pedidos = List.of(
                new Pedido(1, false),
                new Pedido(2, false),
                new Pedido(3, true),
                new Pedido(4, false),
                new Pedido(5, true)
                // Agregar más pedidos aquí
        );

        EmpaquetadoPedidosParallelStreams empaquetador = new EmpaquetadoPedidosParallelStreams();
        empaquetador.empaquetarPedidosEnParalelo(pedidos);

        // Verificar el estado de los pedidos
        pedidos.forEach(pedido -> System.out.println("Pedido " + pedido.getId() + " está en estado: " + pedido.getEstado()));
    }
}

