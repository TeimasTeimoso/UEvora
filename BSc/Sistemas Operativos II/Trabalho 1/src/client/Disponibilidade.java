package client;

public class Disponibilidade implements java.io.Serializable{
    
    protected String produto;
    protected String loja;
    protected int quantidade;
    protected String data;
    protected String hora;
    
    public Disponibilidade(String produto, String loja, int quantidade, String data, String hora) {
        this.produto = produto;
        this.loja = loja;
        this.quantidade = quantidade;
        this.data = data;
        this.hora = hora;
    }
    
    public String getProduto(){
        return this.produto;
    }
    
    public String getLoja(){
        return this.loja;
    }
    
    public int getQuantidade(){
        return this.quantidade;
    }
    
    public String getData(){
        return this.data;
    }
    
    public String getHora(){
        return this.hora;
    }
}
