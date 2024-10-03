package main.java.com.ecommerce;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

class Estoque {
    private final Map<String, Produto> produtos;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore catracaEscrita = new Semaphore(1);
    private final Semaphore catracaLeitura = new Semaphore(1);
    private int countLeitor = 0;
    private int countEscritor = 0;
//    Preciso de um lock aqui

    public Estoque() {
        produtos = new ConcurrentHashMap<>();
    }

    public void adicionarProduto(Produto produto) throws InterruptedException {
        mutex.acquire();
        countEscritor++;
        if (countEscritor == 1) {
            catracaLeitura.acquire();
        }
        mutex.release();

        catracaEscrita.acquire();

        produtos.put(produto.getNome(), produto);

        catracaEscrita.release();

        mutex.acquire();
        countEscritor--;
        if (countEscritor == 0) {
            catracaLeitura.release();
        }
        mutex.release();
       
    }

    public boolean verificarDisponibilidade(Pedido pedido) throws InterruptedException {
    	catracaLeitura.acquire();
        catracaLeitura.release();

        mutex.acquire();
        countLeitor++;
        if (countLeitor == 1) {
            catracaEscrita.acquire();
        }
        mutex.release();

        boolean data = false;
        for (Map.Entry<Produto, Integer> entry : pedido.getItens()) {
            Produto p = entry.getKey();
            int quantidadePedido = entry.getValue();
            if (!produtos.containsKey(p.getNome()) || produtos.get(p.getNome()).getQuantidade() < quantidadePedido) {
                data = false;
                break;
            }
            data = true;
        }
          

        mutex.acquire();
        countLeitor--;
        if (countLeitor == 0) {
            catracaEscrita.release(); 
        }
        mutex.release();

        return data;
    }

    public void atualizarEstoque(Pedido pedido)  throws InterruptedException {
        mutex.acquire();
        countEscritor++;
        if (countEscritor == 1) {
            catracaLeitura.acquire();
        }
        mutex.release();

        catracaEscrita.acquire();

    	for (Map.Entry<Produto, Integer> entry : pedido.getItens()) {
            Produto p = entry.getKey();
            int quantidadePedido = entry.getValue();
            Produto produtoEstoque = produtos.get(p.getNome());
            if (produtoEstoque != null) {
                produtoEstoque.retirarProduto(quantidadePedido);
            }
        }
        catracaEscrita.release();

        mutex.acquire();
        countEscritor--;
        if (countEscritor == 0) {
            catracaLeitura.release();
        }
        mutex.release();
    }
}
