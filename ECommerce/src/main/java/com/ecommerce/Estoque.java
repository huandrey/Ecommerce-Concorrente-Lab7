package main.java.com.ecommerce;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Estoque {
    private final Map<String, Produto> produtos;
//    Preciso de um lock aqui

    public Estoque() {
        produtos = new ConcurrentHashMap<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.put(produto.getNome(), produto);
       
    }

    public boolean verificarDisponibilidade(Pedido pedido) {
    	for (Map.Entry<Produto, Integer> entry : pedido.getItens()) {
            Produto p = entry.getKey();
            int quantidadePedido = entry.getValue();
            if (!produtos.containsKey(p.getNome()) || produtos.get(p.getNome()).getQuantidade() < quantidadePedido) {
                return false;
            }
        }
        return true;    
    }

    public void atualizarEstoque(Pedido pedido) {
    	for (Map.Entry<Produto, Integer> entry : pedido.getItens()) {
            Produto p = entry.getKey();
            int quantidadePedido = entry.getValue();
            Produto produtoEstoque = produtos.get(p.getNome());
            if (produtoEstoque != null) {
                produtoEstoque.retirarProduto(quantidadePedido);
            }
        }
    }
}
