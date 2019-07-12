package pt.uevora;

import java.util.Calendar;
import java.util.Date;

public class PedidoDeFerias
{
    String dataInicio;
    String dataFinal;
    String idPedido;
    String estado;
    int pontuacao;
    int diasUteis;
    boolean flagDatas;

    public PedidoDeFerias(String dataInicio, String dataFinal)
    {
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.estado = "Pedido a aguardar aprovação!";
        this.pontuacao = 0;
        this.flagDatas = true;
    }

    public void setPontuacao(int pontuacao)
    {
        this.pontuacao = pontuacao;
    }

    public void setEstado(String novoEstado)
    {
        this.estado = novoEstado;
    }

    public String getEstado()
    {
        return this.estado;
    }

    public void setId(String id)
    {
        this.idPedido = id;
    }

    public String getId()
    {
        return this.idPedido;
    }

    public String getData(Date data)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(data);

        int ano = c.get(Calendar.YEAR);
        int mes =  c.get(Calendar.MONTH)+1;
        int dia = c.get(Calendar.DAY_OF_MONTH);

        String dataDoPedido = dia+"/"+mes+"/"+ano;
        return dataDoPedido;
    }
}