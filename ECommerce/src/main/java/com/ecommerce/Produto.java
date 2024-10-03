package main.java.com.ecommerce;

import java.util.Objects;

public class Produto {
    private String nome;
    private Double preco;
    

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
      
    }

    public String getNome() {
        return nome;
    }
    
    public Double getPreco() {
        return preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return nome.equals(produto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

}