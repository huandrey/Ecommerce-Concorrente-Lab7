package main.java.com.ecommerce;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProcessadorPedidos implements Runnable {
    private BlockingQueue<Pedido> filaDePedidos;
    private BlockingQueue<Pedido> filaPedidosPendentes = new LinkedBlockingQueue<>();
    private final Estoque estoque;
    
    public ProcessadorPedidos(BlockingQueue<Pedido> filaPedidos, BlockingQueue<Pedido> filaPedidosPendentes, Estoque estoque) {
        this.filaDePedidos = filaPedidos;
        this.filaPedidosPendentes = filaPedidosPendentes;
        this.estoque = estoque;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pedido pedido = filaDePedidos.take();

                if (!estoque.processarPedido(pedido)) {
                	Relatorio.incrementarPedidosRejeitados();
                    filaPedidosPendentes.put(pedido);
                }
                
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void reprocessarPedidosPendentes() {
        try {
            while (true) {   
                Pedido pedidoPendente = filaPedidosPendentes.take();

                if (!estoque.processarPedido(pedidoPendente)) {
                	Relatorio.incrementarPedidosRejeitados();
                    filaPedidosPendentes.put(pedidoPendente);
                } else {
                	System.out.println("Pedido pendente processado com sucesso.");
                }

               
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}