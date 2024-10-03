package main.java.com.ecommerce;

import java.util.List;
import java.util.Map;

class Pedido {
    private List<Map.Entry<Produto, Integer>> itens;

    public Pedido(List<Map.Entry<Produto, Integer>> itens) {
        this.itens = itens;
    }

    public List<Map.Entry<Produto, Integer>> getItens() {
        return itens;
    }
}