package main.java.com.ecommerce;

import java.util.Map;

class Pedido {
	private final int id;
	private final Map<String, Integer> itens;

    public Pedido(int id, Map<String, Integer> itens) {
        this.id = id;
        this.itens = itens;
    }

    public int getId() {
        return id;
    }

    public Map<String, Integer> getProdutos() {
        return itens;
    }

    public double calcularValorTotal(Map<String, Double> precos) {
        return itens.entrySet().stream()
                .mapToDouble(entry -> precos.get(entry.getKey()) * entry.getValue())
                .sum();
    }
}