package main.java.com.ecommerce;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class GeradorDePedidos implements Runnable  {
	
	private BlockingQueue<Pedido> filaPedidos;
    private Random random = new Random();
    private int idPedido = 0;
    private Set<Pedido> pedidosGerados = new HashSet<>();

	public GeradorDePedidos(BlockingQueue<Pedido> filaPedidos) {
		this.filaPedidos = filaPedidos;
	}

	@Override
    public void run() {
        try {
            while (true) {
                Map<String, Integer> produtos = gerarProdutosAleatorios();

                Pedido pedido = new Pedido(++idPedido, produtos);

                if (!pedidosGerados.contains(pedido)) {
                    filaPedidos.put(pedido);
                  
                    
                    pedidosGerados.add(pedido);
                    if (pedidosGerados.size() > 100) {
                        pedidosGerados.clear();
                    }
                } 

                Thread.sleep(random.nextInt(2000) + 500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

	private Map<String, Integer> gerarProdutosAleatorios() {
	    Map<String, Integer> produtos = new HashMap<>();

	    String[] listaDeProdutos = {"Produto A", "Produto B", "Produto C", "Produto D", "Produto E", "Produto F"};

	    int quantidadeDeProdutos = random.nextInt(listaDeProdutos.length) + 1;

	    List<String> produtosAleatorios = Arrays.asList(listaDeProdutos);
	    Collections.shuffle(produtosAleatorios);

	    for (int i = 0; i < quantidadeDeProdutos; i++) {
	        String produto = produtosAleatorios.get(i);
	        int quantidade = random.nextInt(10) + 1;
	        produtos.put(produto, quantidade);
	    }

	    return produtos;
	}

}
