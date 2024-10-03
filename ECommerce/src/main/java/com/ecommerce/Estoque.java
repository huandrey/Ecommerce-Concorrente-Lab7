package main.java.com.ecommerce;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Estoque {
    private final Map<String, Integer> produtos;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore catracaEscrita = new Semaphore(1);
    private final Semaphore catracaLeitura = new Semaphore(1);
    private int countLeitor = 0;
    private int countEscritor = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Random random = new Random();
//    Preciso de um lock aqui

    public Estoque() {
        produtos = new ConcurrentHashMap<>();
    }

//    public void adicionarProduto(Produto produto) throws InterruptedException {
//        mutex.acquire();
//        countEscritor++;
//        if (countEscritor == 1) {
//            catracaLeitura.acquire();
//        }
//        mutex.release();
//
//        catracaEscrita.acquire();
//
//        produtos.put(produto.getNome(), produto);
//
//        catracaEscrita.release();
//
//        mutex.acquire();
//        countEscritor--;
//        if (countEscritor == 0) {
//            catracaLeitura.release();
//        }
//        mutex.release();
//       
//    }
//
//    public boolean verificarDisponibilidade(Pedido pedido) throws InterruptedException {
//    	catracaLeitura.acquire();
//        catracaLeitura.release();
//
//        mutex.acquire();
//        countLeitor++;
//        if (countLeitor == 1) {
//            catracaEscrita.acquire();
//        }
//        mutex.release();
//
//        boolean data = false;
//        for (Map.Entry<Produto, Integer> entry : pedido.getItens()) {
//            Produto p = entry.getKey();
//            int quantidadePedido = entry.getValue();
//            if (!produtos.containsKey(p.getNome()) || produtos.get(p.getNome()).getQuantidade() < quantidadePedido) {
//                data = false;
//                break;
//            }
//            data = true;
//        }
//          
//
//        mutex.acquire();
//        countLeitor--;
//        if (countLeitor == 0) {
//            catracaEscrita.release(); 
//        }
//        mutex.release();
//
//        return data;
//    }
//
//    public void atualizarEstoque(Pedido pedido)  throws InterruptedException {
//        mutex.acquire();
//        countEscritor++;
//        if (countEscritor == 1) {
//            catracaLeitura.acquire();
//        }
//        mutex.release();
//
//        catracaEscrita.acquire();
//
//    	for (Map.Entry<Produto, Integer> entry : pedido.getItens()) {
//            Produto p = entry.getKey();
//            int quantidadePedido = entry.getValue();
//            Produto produtoEstoque = produtos.get(p.getNome());
//            if (produtoEstoque != null) {
//                produtoEstoque.retirarProduto(quantidadePedido);
//            }
//        }
//        catracaEscrita.release();
//
//        mutex.acquire();
//        countEscritor--;
//        if (countEscritor == 0) {
//            catracaLeitura.release();
//        }
//        mutex.release();
//    }
    
    public void reabastecer() {
        lock.writeLock().lock();
        try {
            String[] listaDeProdutos = {"Produto A", "Produto B", "Produto C", "Produto D", "Produto E", "Produto F"};

            for (String produto : listaDeProdutos) {
                int quantidade = random.nextInt(20) + 1;
                produtos.put(produto, produtos.getOrDefault(produto, 0) + quantidade);
            }

        } finally {
            lock.writeLock().unlock();
        }
    }
 
    public boolean processarPedido(Pedido pedido) {
        lock.readLock().lock();
        boolean podeProcessar = true;

        try {
            for (Map.Entry<String, Integer> entry : pedido.getProdutos().entrySet()) {
                String produto = entry.getKey();
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
                for (Map.Entry<String, Integer> entry : pedido.getProdutos().entrySet()) {
                    String produto = entry.getKey();
                    int quantidade = entry.getValue();
                    produtos.put(produto, produtos.get(produto) - quantidade);
                }

                
//                 double valorTotal = pedido.calcularValorTotal(getPrecoProdutos());
                 Relatorio.incrementarPedidosProcessados();
//                 Relatorio.incrementarValorTotalVendas(valorTotal);

                return true;
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            return false;
        }
    }
    
   
}
