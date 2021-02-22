package server;

import java.rmi.registry.*;

public class StockDeProdutosServer {

    public static void main(String args[]) {

        /* port default no qual o servico de nomes "escuta" */
        int regPort = 1099; 

        /*Execucao do classe requer 5 argumentos*/
        if (args.length != 5) {
            System.out.println("USO: java -classpath build/classes:resources/postgresql.jar server.StockDeProdutosServer regPort regHost dbName user pass");
            System.exit(1);
        }

        try {
            /* Port relativo ao binder */
            regPort = Integer.parseInt(args[0]);

            /* criar um Objeto Remoto */
            StockDeProdutos obj = new StockDeProdutosImpl(args[1], args[2], args[3], args[4]);

            /* Retorna a referencia do objeto remoto registado no serviço de nomes */
            Registry registry = LocateRegistry.getRegistry(regPort);

            /* atribui o nome no binder do servico ao objeto remoto criado 
            nome do service = stockDeProdutos */
            registry.rebind("stockDeProdutos", obj); 

            System.out.println("Objecto Remoto registado com sucesso no binder!");

            System.out.println("servidor pronto");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
