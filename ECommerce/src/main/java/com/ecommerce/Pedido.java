package main.java.com.ecommerce;

import java.util.Map;

class Pedido {
  	private final int id;
  	private int clienteId;
  	private final Map<Produto, Integer> itens;

  	public Pedido(int id, Map<Produto, Integer> itens, int clienteId) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Itens do pedido não podem ser nulos ou vazios.");
        }
        this.id = id;
        this.clienteId = clienteId;
        this.itens = itens;
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

    public Integer calcularValorTotal() {
        int total = 0;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            total += produto.getPreco() * quantidade;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", itens=" + itens +
                '}';
    }
}
