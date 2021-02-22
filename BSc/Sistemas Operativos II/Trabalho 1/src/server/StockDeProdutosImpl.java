package server;

import client.ClientInterface;
import client.Disponibilidade;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;

public class StockDeProdutosImpl extends UnicastRemoteObject implements StockDeProdutos, java.io.Serializable {


    HashMap<String, ClientInterface> clientList;
    PostgresConnector db;

    public StockDeProdutosImpl(String host, String db, String user, String pw) throws RemoteException {
        this.clientList = new HashMap<>();
        this.db = new PostgresConnector(host, db, user, pw);
    }

    @Override
    public ArrayList<Disponibilidade> verificarDisponibilidade(String produto) throws RemoteException {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cant connect do db");
        }

        Statement stm = db.getStatement();
        ArrayList<Disponibilidade> listaDeDisponibildiade = new ArrayList<>();

        try {
            ResultSet rslt = stm.executeQuery(String.format("SELECT * FROM disponibilidade WHERE disponibilidade.produto = '%s';", produto));

            while (rslt.next()) {
                listaDeDisponibildiade.add(new Disponibilidade(rslt.getString("produto"), rslt.getString("nome_loja"), rslt.getInt("stock"), rslt.getString("data_encomenda"), rslt.getString("hora_encomenda")));
            }

            rslt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problems fetching data");
        }

        db.disconnect();

        return listaDeDisponibildiade;
    }

    @Override
    public void registarDisponibilidadeEm(String produto, String loja, int stock) throws RemoteException {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cant connect do db");
        }

        /* Funcao que obtem um statement e regista o produto case nao exista */
        Statement stm = registarProduto(db, produto);
        ResultSet produto_existe = null;

        try {
            produto_existe = stm.executeQuery(String.format("select * from disponibilidade WHERE produto = '%s' AND nome_loja = '%s';", produto, loja));

            if (!produto_existe.next()) {
                stm.executeUpdate(String.format("INSERT INTO disponibilidade VALUES ('%s', '%s', CURRENT_DATE, CURRENT_TIME, '%d');", loja, produto, stock));
                System.out.println("Nova disponibilidade foi adicionada \n");
            } else {
                stm.executeUpdate(String.format("UPDATE disponibilidade SET data_encomenda = CURRENT_DATE, hora_encomenda = CURRENT_TIME, stock = '%d' WHERE nome_loja = '%s' AND produto = '%s';", stock, loja, produto));
                System.out.println("As disponibilidades foram atualizadas \n");
            }
            produto_existe.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problems recording availability");
        }

        notificarClientes(getClientes(stm, produto), new Disponibilidade(produto, loja, stock, "", ""));

        db.disconnect();
    }

    @Override
    public int registarNecessidade(String produto, String id) throws RemoteException {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cant connect do db");
        }

        int codigo = -1;

        /* Funcao que obtem um statement e regista o produto case nao exista */
        Statement stm = registarProduto(db, produto);

        try {
            ResultSet rslt = stm.executeQuery(String.format("INSERT INTO procura (id_cliente, produto, data_procura, hora_procura, resposta_recebida) VALUES ('%s', '%s', CURRENT_DATE, CURRENT_TIME, FALSE) RETURNING id_procura;", id, produto));

            if (rslt.next()) {
                codigo = rslt.getInt(1);
            }

            rslt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problems fetching data");
        }

        db.disconnect();

        return codigo;
    }

    @Override
    public void registarClientEmEspera(String id, ClientInterface client) throws RemoteException {
        if (!(clientList.containsValue(client))) {
            clientList.put(id, client);
        }
        System.out.println("Client Em espera");
    }

    @Override
    public void removerClientEmEspera(ClientInterface client) throws RemoteException {
        if (clientList.containsValue(client)) {
            clientList.remove(client);
        }
        System.out.println("Client Saiu da fila");
    }

    public Statement registarProduto(PostgresConnector db, String produto) {
        Statement stmt = db.getStatement();
        try {
            stmt.executeUpdate(String.format("INSERT INTO produto VALUES ('%s');", produto));
            System.out.println("< " + produto + " > Foi adicionado 'a tabela de produtos");
        } catch (Exception e) {
            System.out.println("< " + produto + " > Already exists in \"produto\"");
        }
        return stmt;
    }

    /* Recebe um produto e verifica na db que clientes estao em espera */
    public ArrayList<String> getClientes(Statement stm, String produto) {

        ArrayList<String> listaDeClientes = new ArrayList<>();

        try {

            ResultSet rslt = stm.executeQuery(String.format("SELECT id_cliente FROM procura WHERE procura.produto = '%s';", produto));

            while (rslt.next()) {
                listaDeClientes.add(rslt.getString(1));
            }

            stm.executeUpdate(String.format("UPDATE procura SET resposta_recebida = TRUE WHERE produto = '%s' AND resposta_recebida = FALSE;", produto));

            rslt.close();

        } catch (SQLException ex) {
            Logger.getLogger(StockDeProdutosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDeClientes;
    }

    public void notificarClientes(ArrayList<String> clients, Disponibilidade d) throws RemoteException {
        for (String c : clients) {
            if (this.clientList.containsKey(c)) {
                (this.clientList.get(c)).notifica(String.format("\n '%s' Esta' agora disponviel na loja '%s'. Existem '%d' unidades!", d.getProduto(), d.getLoja(), d.getQuantidade()));
                removerClientEmEspera(this.clientList.get(c));
            }
        }
    }

    @Override
    public String getID() throws RemoteException {
        Date id = new Date();
        Timestamp ts = new Timestamp(id.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("ddH-Hmm-ssS-SSS");
        String str_ID = formatter.format(id);

        return str_ID;
    }
}
