package main.java.com.ecommerce;

import java.text.DecimalFormat;

class Relatorio {
    private static int pedidosProcessados = 0;
    private static double valorTotalVendas = 0;
    private static int pedidosRejeitados = 0;

    public static synchronized void incrementarPedidosProcessados() {
        pedidosProcessados++;
    }

    public static synchronized void incrementarValorTotalVendas(double valor) {
        valorTotalVendas += valor;
    }

    public static synchronized void incrementarPedidosRejeitados() {
        pedidosRejeitados++;
    }

    public static synchronized void gerarRelatorio() {
        System.out.println("----------------------------------");
        System.out.println("Relatório de Vendas:");
        System.out.println("Pedidos Processados: " + pedidosProcessados);
        System.out.println("Valor Total das Vendas: R$ " + calculaValorFormatado());
        System.out.println("Pedidos Rejeitados: " + pedidosRejeitados);
        System.out.println("----------------------------------");
        Relatorio.resetarContadores();
    }

    private static String calculaValorFormatado() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valorTotalVendas);
    }

    private static void resetarContadores() {
        pedidosRejeitados = 0;
        pedidosProcessados = 0;
        valorTotalVendas = 0;
    }
}
