package main.java.com.ecommerce;

class Relatorio implements Runnable {
    private int totalPedidosProcessados;
    private int totalPedidosRejeitados;
    private final long intervalo;

    public Relatorio(long intervalo) {
        this.intervalo = intervalo;
    }

    public synchronized void pedidoProcessado() {
        totalPedidosProcessados++;
    }

    public synchronized void pedidoRejeitado() {
        totalPedidosRejeitados++;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(intervalo);
                System.out.println("Relatório de Vendas:");
                System.out.println("Total de Pedidos Processados: " + totalPedidosProcessados);
                System.out.println("Total de Pedidos Rejeitados: " + totalPedidosRejeitados);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}