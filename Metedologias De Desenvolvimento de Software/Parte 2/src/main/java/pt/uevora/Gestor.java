package pt.uevora;

import java.util.Iterator;
import java.util.Scanner;

import pt.uevora.Colaborador;

public class Gestor extends Colaborador
{

    public Gestor(String id, String password)
    {
        super(id, password);
    }

    public boolean listarPedidos(LoginDatabase db)
    {
        boolean pedidos = false;
        Iterator<Colaborador> iterator = db.database.iterator();

        while (iterator.hasNext()) 
        {
            Colaborador temp = iterator.next();
            Iterator<PedidoDeFerias> iteratorEstado = temp.listaDePedidos.iterator();
            //System.out.println("idColaborador: "+ temp.id);
            while(iteratorEstado.hasNext())
            {
                PedidoDeFerias pedido = iteratorEstado.next();
                if(pedido.getEstado().equals("Pedido a aguardar aprovação!"))
                {
                    pedidos = true;
                    System.out.println("idColaborador: "+ temp.id + " id: " + pedido.getId() + " Data de Inicio:" + pedido.dataInicio + " Data Final: " + pedido.dataFinal);
                    //return;
                }
            }
        }
        if (!pedidos) {
            System.out.println("Não existe nenhum pedido a aguardar aprovação");
            return pedidos;
        }
        return pedidos;
    }

    public Boolean avaliarPedidos(String idPedido, LoginDatabase db)
    {
        Scanner scanner = new Scanner(System.in);
        Iterator<Colaborador> iterator = db.database.iterator();

        while(iterator.hasNext()) 
        {
            Colaborador temp = iterator.next();
            Iterator<PedidoDeFerias> iteradorEstado = temp.listaDePedidos.iterator();
            while(iteradorEstado.hasNext())
            {
                PedidoDeFerias pedido = iteradorEstado.next();
                if(pedido.idPedido.equals(idPedido))
                {
                    System.out.print("(A)provar ou (R)ecusar? ");
                    String escolha = scanner.nextLine();
                    switch(escolha)
                    {
                        case "A":
                            pedido.setEstado("Aprovado");
                            System.out.println("Pedido aprovado com sucesso!");
                            break;
                        case "R":
                            pedido.setEstado("Recusado");
                            temp.diasATirar += pedido.diasUteis;
                            System.out.println("Pedido recusado!");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }



        
}
