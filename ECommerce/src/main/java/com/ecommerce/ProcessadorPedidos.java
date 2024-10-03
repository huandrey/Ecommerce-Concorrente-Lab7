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

                if (estoque.processarPedido(pedido)) {
                    System.out.println("Pedido " + pedido.getId() + " do Cliente " + pedido.getClienteId() + " foi processado com sucesso.");
                } else {
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
            Pedido pedidoPendente = filaPedidosPendentes.poll();
            if (pedidoPendente != null) {
                if (estoque.processarPedido(pedidoPendente)) {
                    System.out.println("Pedido " + pedidoPendente.getId() + " do Cliente " + pedidoPendente.getClienteId() + " foi processado com sucesso.");
                } else {
                    Relatorio.incrementarPedidosRejeitados();
                    filaPedidosPendentes.put(pedidoPendente);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}