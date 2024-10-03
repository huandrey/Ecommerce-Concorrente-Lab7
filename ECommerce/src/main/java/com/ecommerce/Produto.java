package main.java.com.ecommerce;

import java.util.Objects;

public class Produto {
    private String nome;
    

    public Produto(String nome) {
        this.nome = nome;
      
    }

    public String getNome() {
        return nome;
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