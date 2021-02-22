package client;

import server.StockDeProdutos;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockDeProdutosClient extends UnicastRemoteObject implements ClientInterface, java.io.Serializable {

    private String regHost;
    private String regPort;
    private String clientId;
    private Scanner sc;

    public StockDeProdutosClient(String regHost, String regPort) throws RemoteException {
        this.regHost = regHost;
        this.regPort = regPort;
        this.clientId = null;
        this.sc = new Scanner(System.in);
    }

    public StockDeProdutosClient(String regHost, String regPort, String id) throws RemoteException {
        this.regHost = regHost;
        this.regPort = regPort;
        this.clientId = id;
        this.sc = new Scanner(System.in);
    }

    public String getClientId() {
        return this.clientId;
    }

    public void menu(StockDeProdutos sp) throws RemoteException, SQLException {
        String produto;
        String loja;
        int stock = -1;

        System.out.println("Seja bem vindo ao sistema de partilha de Disponibiliade e Necessidade de Produtos! \n"
                + "O seu ID de user é: " + this.getClientId());

        while (true) {
            System.out.println("\n\n################################ Menu ################################");
            System.out.println("1 - Verificar a disponibilidade de um produto:\n"
                    + "2 - Registar a disponibilidade de um produto numa loja:\n"
                    + "3 - Registar a necessidade de um produto:\n");

            System.out.print("Escolha: ");
            String choice = sc.next();

            switch (choice) {
                case "1":
                    System.out.print("Produto: ");
                    sc.nextLine();
                    produto = sc.nextLine();

                    ArrayList<Disponibilidade> resposta = sp.verificarDisponibilidade(produto);

                    if (resposta.isEmpty()) {
                        System.out.println("O produto não se encontra disponivel");
                    } else {
                        for (Disponibilidade d : resposta) {
                            System.out.println(String.format("%s || %s || %d || %s || %s\n", d.getProduto(), d.getLoja(), d.getQuantidade(), d.getData(), d.getHora()));
                        }
                    }

                    break;

                case "2":
                    System.out.println("Intruduza a disponibilidade");
                    sc.nextLine();

                    System.out.print("Produto: ");
                    produto = sc.nextLine();

                    System.out.print("Loja: ");
                    loja = sc.nextLine();
                    
                    while (true) {
                        try {
                            System.out.print("Stock: ");
                            stock = sc.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Wrong type. Introduza um valor numerico (int) em Stock");
                            sc.nextLine();
                        }
                    }

                    sp.registarDisponibilidadeEm(produto, loja, stock);
                    break;

                case "3":
                    System.out.println("Intruduza a necessidade");
                    sc.nextLine();
                    System.out.print("Produto: ");
                    produto = sc.nextLine();

                    int r = sp.registarNecessidade(produto, this.getClientId());
                    sp.registarClientEmEspera(this.clientId, this);
                    System.out.println("Necessidade regastada com o ID: " + Integer.toString(r));
                    break;

                default:
                    System.out.println("Insira uma opçao valida!");
            }
        }
    }

    @Override
    public void notifica(String msg) throws RemoteException {
        System.out.println(msg);
    }

    public static void main(String args[]) throws RemoteException {

        if (args.length != 2 && args.length != 3) {
            System.out.println("USO: java -classpath build/classes/ client.StockDeProdutosClient regHost regPort");
            System.exit(1);
        }

        StockDeProdutosClient stockClient;

        if (args.length == 2) {
            stockClient = new StockDeProdutosClient(args[0], args[1]);
        } else {
            stockClient = new StockDeProdutosClient(args[0], args[1], args[2]);
        }

        try {
            /* objeto que fica associado ao proxy para objeto remoto */
            StockDeProdutos sp = (StockDeProdutos) java.rmi.Naming.lookup("rmi://" + stockClient.regHost + ":" + stockClient.regPort + "/stockDeProdutos");
            if (stockClient.getClientId() == null) {
                stockClient.clientId = sp.getID();
            }

            stockClient.menu(sp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
