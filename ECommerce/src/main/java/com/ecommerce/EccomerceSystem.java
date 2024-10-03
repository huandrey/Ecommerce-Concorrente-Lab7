package main.java.com.ecommerce;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EccomerceSystem {
    public static void main(String[] args) throws InterruptedException {
//    	TESTE - ARRUMAR JEITO DE CADASTRAR OS PRODUTOS
        Estoque estoque = new Estoque();
        estoque.adicionarProduto(new Produto("ProdutoA", 50));
        estoque.adicionarProduto(new Produto("ProdutoB", 30));

        FilaPedidos filaDePedidos = new FilaPedidos(10);
        Relatorio gerenciadorDeRelatorios = new Relatorio(30000);

//        POR ENQUANTO FIZ COM THREAD PRA TESTAR, MAS AIDA NÃO PEGA :(
        new Thread(gerenciadorDeRelatorios).start();


        for (int i = 0; i < 5; i++) {
            new Thread(new ProcessadorPedidos(filaDePedidos, estoque)).start();
        }

//        CODIGO DO CHAT GPT: PRECISA SER AVALIADO
        for (int i = 0; i < 20; i++) {
            List<Map.Entry<Produto, Integer>> produtos = Arrays.asList(
                new AbstractMap.SimpleEntry<>(new Produto("Produto1", 0), 2), // quantidade do Produto1
                new AbstractMap.SimpleEntry<>(new Produto("Produto2", 0), 1)  // quantidade do Produto2
            );
            Pedido pedido = new Pedido(produtos);
            try {
                filaDePedidos.adicionarPedido(pedido);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000); // Simula o tempo entre pedidos
        }

        // Simulação do reabastecimento automático
//        new Thread(() -> {
//            try {
//                while (true) {
//                    Thread.sleep(10000); // Espera 10 segundos entre os reabastecimentos
//                    
//                    // Verifica se os produtos estão no estoque antes de reabastecer
//                    Produto produtoA = estoque.getProduto("ProdutoA");
//                    Produto produtoB = estoque.getProduto("ProdutoB");
//                    
//                    if (produtoA != null) {
//                        produtoA.reabastecer(5); // Reabastece 5 unidades do ProdutoA
//                    }
//                    if (produtoB != null) {
//                        produtoB.reabastecer(5); // Reabastece 5 unidades do ProdutoB
//                    }
//                    
//                    System.out.println("Estoque reabastecido.");
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt(); // Restaura o status de interrupção
//            }
//        }).start();
    }
}