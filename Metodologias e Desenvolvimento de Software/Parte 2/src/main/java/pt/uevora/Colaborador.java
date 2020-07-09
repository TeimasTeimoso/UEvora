package pt.uevora;

import java.util.List;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

public class Colaborador
{
    String nome;
    String password;
    String id;
    int idade;
    int antiguidade;
    int diasATirar;
    int pontuacao;
    short nDePedidos;
    List<PedidoDeFerias> listaDePedidos;
    int pDoPedido;

    
    public Colaborador(String id, String password)
    {
        this.id = id;
        this.password = password;
        this.diasATirar = 22;
        this.pontuacao = 0;
        this.listaDePedidos = new LinkedList<>();
        this.nDePedidos = 0;
        this.pDoPedido = 0;
    }

    public void setName(String name)
    {
        this.nome = name;
    }

    protected String getPassword()
    {
        return this.password;
    }

    public String getName()
    {
        return this.nome;
    }

    public String getId()
    {
        return this.id;
    }
    
    public int getDias()
    {
        return this.diasATirar;
    }

    public PedidoDeFerias marcarFerias(String inicio, String fim) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date dataInicio = format.parse(inicio);
        Date dataFinal = format.parse(fim);

        long temp = diasUteis(dataInicio, dataFinal);
        if (temp == -1) {
            System.out.println("Data de fim é posterior à data de início");
            return null;
        }
        if (temp > this.diasATirar) {
            System.out.println("O colaborador já não têm dias suficientes disponíveis para poder marcar o período indicado.");
            return null;
        }
        else
        {
            this.diasATirar -= temp;
            PedidoDeFerias pedido = new PedidoDeFerias(inicio, fim);
            this.nDePedidos++;
            String id = this.id + inicio.substring(0,2) + inicio.substring(3, 5) + fim.substring(0,2)+fim.substring(3, 5)+ this.nDePedidos;
            pedido.setId(id);
            pedido.diasUteis = (int)temp;
            pedido.pontuacao = pDoPedido;
            this.listaDePedidos.add(pedido);
            return pedido;
        }
    }

    public long diferencaDatas(Date dataInicio, Date dataFinal)
    {
        Date temp1 = dataInicio;
        Date temp2 = dataFinal;
        long diff = temp2.getTime() - temp1.getTime();
        if (diff < 0) {
            return 0;
        }
        else
        {
            diff = diff / 1000 / 60 / 60 / 24;

            return diff + 1;
        }
    }

    public long diasUteis(Date dataInicio, Date dataFinal)
    {
        this.pDoPedido = 0;
        long temp1 = dataInicio.getTime();
        long dias = diferencaDatas(dataInicio, dataFinal);
        if (dias == 0) {
            return -1;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);
        while (dataInicio.getTime() <= dataFinal.getTime()) {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            {
                dias --;
            }
            else
            {
                pontuacao += pontuacao(c);
                this.pDoPedido += pontuacao(c);
            }
            dataInicio.setTime(dataInicio.getTime() + 1000 * 60 * 60 * 24);
            c.setTime(dataInicio);
        }
        dataInicio.setTime(temp1);
        return dias;
    }

    public Date validarData(String data)
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            System.out.println("Data Invalida");
        }
        return date;
    }

    public int pontuacao(Calendar c)
    {
        if(c.get(Calendar.MONTH) == 0 || c.get(Calendar.MONTH) == 1 || c.get(Calendar.MONTH) == 10)
        {
            return 1;
        }
        else if(c.get(Calendar.MONTH) == 2 || c.get(Calendar.MONTH) == 3 || c.get(Calendar.MONTH) == 4 || c.get(Calendar.MONTH) == 9)
        {
            return 2;
        }
        else if(c.get(Calendar.MONTH) == 5 || c.get(Calendar.MONTH) == 8)
        {
            return 3;
        }

        return 4;
    }

    public void consultarPedidos()
    {
        Iterator<PedidoDeFerias> iteratorEstado1 = this.listaDePedidos.iterator();

        if(listaDePedidos.isEmpty())
        {
            System.out.println("Não existem pedidos de férias. \nNão existem pedidos de férias aprovados.");
            return;
        }

        System.out.println("PENDENTES:");
        while(iteratorEstado1.hasNext())
        {
            PedidoDeFerias pedido = iteratorEstado1.next();
            if(pedido.getEstado().equals("Pedido a aguardar aprovação!"))
            {
                System.out.println("id: "+ pedido.getId() + " Data de Inicio:" + pedido.dataInicio + " Data Final: " + pedido.dataFinal);
            }
        }
        System.out.println("");

        Iterator<PedidoDeFerias> iteratorEstado2 = this.listaDePedidos.iterator();
        System.out.println("APROVADOS:");
        while(iteratorEstado2.hasNext())
        {
            PedidoDeFerias pedido = iteratorEstado2.next();
            if(pedido.getEstado().equals("Aprovado"))
            {
                System.out.println("id: "+ pedido.getId() + " Data de Inicio:" + pedido.dataInicio + " Data Final: " + pedido.dataFinal);
            }
        }
        System.out.println("");

        Iterator<PedidoDeFerias> iteratorEstado3 = this.listaDePedidos.iterator();
        System.out.println("RECUSADOS:");
        while(iteratorEstado3.hasNext())
        {
            PedidoDeFerias pedido = iteratorEstado3.next();
            if(pedido.getEstado().equals("Recusado"))
            {
                System.out.println("id: "+ pedido.getId() + " Data de Inicio:" + pedido.dataInicio + " Data Final: " + pedido.dataFinal);
            }
        }
    }
}
