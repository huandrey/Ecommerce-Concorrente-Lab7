package main.java.com.ecommerce;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FilaPedidos {
	private final BlockingQueue<Pedido> fila;

    public FilaPedidos(int capacidade) {
        fila = new ArrayBlockingQueue<>(capacidade);
    }

    public void adicionarPedido(Pedido pedido) throws InterruptedException {
        fila.put(pedido);
    }

    public Pedido removerPedido() throws InterruptedException {
        return fila.take();
    }

}
