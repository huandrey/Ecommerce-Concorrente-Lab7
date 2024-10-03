package main.java.com.ecommerce;



public class ProcessadorPedidos implements Runnable {
    private final FilaPedidos filaDePedidos;
    private final Estoque estoque;
    
    public ProcessadorPedidos(FilaPedidos filaDePedidos, Estoque estoque) {
        this.filaDePedidos = filaDePedidos;
        this.estoque = estoque;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pedido pedido = filaDePedidos.removerPedido();
//                 A LOGICA PODE TA ERRADA, ACHO QUE TEM QUE VERIFICAR A DISPONABILIDADE DO PEDIDO
//                processarPedido(pedido);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

//    private void processarPedido(Pedido pedido) {
//        boolean pedidoProcessado = true;
//        for (Map.Entry<Produto, Integer> item : pedido.getItens()) {
//            Produto produto = estoque.getProduto(item.getKey().getNome());
//            if (produto == null || !produto.retirar(item.getValue())) {
//                pedidoProcessado = false;
//                break;
//            }
//        }
//       
//    }
}