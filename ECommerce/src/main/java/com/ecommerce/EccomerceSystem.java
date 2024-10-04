package main.java.com.ecommerce;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// AINDA FALTA FAZER A PARTE DE CALCULA O PRECO TOTAL DE CADA PEDIDO
// eu não tenho certeza se os pedidos pendentes estão sendo processados - necessario verificar
// Sinto que ainda tem algo errado, ele ta rejeitando muito pedido e não estou vendo os pedidos pedentes serem processados
public class EccomerceSystem {
	private static final int CAPACIDADE_DA_FILA = 100;
	private static BlockingQueue<Pedido> filaPedidos = new LinkedBlockingQueue<>(CAPACIDADE_DA_FILA);
	private static BlockingQueue<Pedido> filaPedidosPendentes = new LinkedBlockingQueue<>(CAPACIDADE_DA_FILA);
	private static List<ProcessadorPedidos> processadores = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
      Estoque estoque = new Estoque();

      ScheduledExecutorService geradorExecutor = Executors.newScheduledThreadPool(1);
      geradorExecutor.scheduleAtFixedRate(() -> {
          GeradorDePedidos geradorPedidos = new GeradorDePedidos(filaPedidos);
          try {
              geradorPedidos.run();
          } catch (Exception e) {
              e.printStackTrace();
          }
      }, 0, 10, TimeUnit.SECONDS);

      ScheduledExecutorService processadorExecutor = Executors.newScheduledThreadPool(5);
      for (int i = 0; i < 5; i++) {
          ProcessadorPedidos processador = new ProcessadorPedidos(filaPedidos, filaPedidosPendentes, estoque);
          processadores.add(processador);
          processadorExecutor.submit(processador);  // Submetendo o processador para o Executor
      }

      ScheduledExecutorService reabastecedor = Executors.newScheduledThreadPool(1);
      reabastecedor.scheduleAtFixedRate(() -> {
          estoque.reabastecer();
          for (ProcessadorPedidos processador : processadores) {
              processador.reprocessarPedidosPendentes();
          }
      }, 1, 10, TimeUnit.SECONDS);

      ScheduledExecutorService relatorioVendas = Executors.newScheduledThreadPool(1);
      relatorioVendas.scheduleAtFixedRate(() -> Relatorio.gerarRelatorio(), 30, 30, TimeUnit.SECONDS);
  }
}
