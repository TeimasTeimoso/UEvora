package server;

import client.ClientInterface;
import client.Disponibilidade;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface StockDeProdutos extends java.rmi.Remote {
  
    public ArrayList<Disponibilidade> verificarDisponibilidade(String produto) throws RemoteException;
    
    public void registarDisponibilidadeEm(String produto, String loja, int stock) throws RemoteException;
    
    public int registarNecessidade(String produto, String id) throws RemoteException;
        
    public void registarClientEmEspera(String id, ClientInterface client) throws RemoteException;
    
    public void removerClientEmEspera(ClientInterface client) throws RemoteException;
    
    public String getID() throws RemoteException;
    
}
