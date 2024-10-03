package main.java.com.ecommerce;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Estoque {
    private final Map<Produto, Integer> produtos;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Random random = new Random();


    public Estoque() {
        produtos = new ConcurrentHashMap<>();
    }
    
    public void reabastecer() {
        lock.writeLock().lock();
        int totalItensReabastecidos = 0;
        try {
            
            for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
                Produto produto = entry.getKey();
                int quantidadeAtual = entry.getValue();
                
                int quantidadeAdicional = random.nextInt(100) + 1;
                
                produtos.put(produto, quantidadeAtual + quantidadeAdicional);
                totalItensReabastecidos += quantidadeAdicional;
                
            }
            System.out.println("Estoque abastecido com " + totalItensReabastecidos + " itens de " + produtos.size() + " produtos.");
        } finally {
            lock.writeLock().unlock();
        }
    }
 
    public boolean processarPedido(Pedido pedido) {
        lock.readLock().lock();
        boolean podeProcessar = true;

        try {
            for (Map.Entry<Produto, Integer> entry : pedido.getProdutos().entrySet()) {
                Produto produto = entry.getKey();
                int quantidade = entry.getValue();
                if (produtos.getOrDefault(produto, 0) < quantidade) {
                    podeProcessar = false;
                    break;
                }
            }
        } finally {
            lock.readLock().unlock();
        }

        if (podeProcessar) {
            lock.writeLock().lock();
            try {
                double valor = 0;
                
                for (Map.Entry<Produto, Integer> entry : pedido.getProdutos().entrySet()) {
                    Produto produto = entry.getKey();
                    int quantidade = entry.getValue();
                    valor+=produto.getPreco() * quantidade;
                    produtos.put(produto, produtos.get(produto) - quantidade);
                }
                Relatorio.incrementarPedidosProcessados();
                Relatorio.incrementarValorTotalVendas(valor);             
            } finally {
                lock.writeLock().unlock();
            }

            return true;
        } else {
            return false;
        }
    }
    
   
}
