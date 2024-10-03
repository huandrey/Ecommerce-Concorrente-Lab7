package main.java.com.ecommerce;

import java.util.Map;

class Pedido {
	private final int id;
	private final Map<Produto, Integer> itens;
	private int clienteId;

	public Pedido(int id, Map<Produto, Integer> itens, int clienteId) {
        this.id = id;
        this.itens = itens;
        this.clienteId = clienteId;
    }
    public int getId() {
        return id;
    }

    public Map<Produto, Integer> getProdutos() {
        return itens;
    }
    
    public int getClienteId() {
        return clienteId;
    }

    public double calcularValorTotal(Map<String, Double> precos) {
        return itens.entrySet().stream()
                .mapToDouble(entry -> precos.get(entry.getKey()) * entry.getValue())
                .sum();
    }
}