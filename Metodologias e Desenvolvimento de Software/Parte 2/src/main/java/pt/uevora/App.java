package pt.uevora;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class App {

    public static void login(LoginDatabase db) throws ParseException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduza o id:");
        String id = scanner.nextLine();
        System.out.println("Intoduza a palavra pass:");
        String pass = scanner.nextLine();

        if(db.verifyCredentialsColaborador(id, pass))
        {
            if(id.charAt(0) == 'C')
            {
                Colaborador c = db.getColaboratorData(id);
                String escolha = "";
                System.out.println("Bem Vindo!");

                while (!escolha.equals("3"))
                {
                    
                    System.out.println("1. Marcar Férias");
                    System.out.println("2. Consultar Férias");
                    System.out.println("3. Terminar Sessão");
                    escolha = scanner.nextLine();

                    switch(escolha)
                    {
                        case "1":
                            String inicio;
                            String fim;
                            System.out.println("Data de Inicio(dd/mm/yyyy): ");
                            inicio = scanner.nextLine();
                            if (c.validarData(inicio) != null) {
                                System.out.println("Data de Fim(dd/mm/yyyy): ");
                                fim = scanner.nextLine();
                                if (c.validarData(fim) != null) {
                                    PedidoDeFerias p = c.marcarFerias(inicio, fim);
                                    if (p != null) {
                                        System.out.println("Dias Uteis do Pedido: " + p.diasUteis);
                                        System.out.println("Pontuação do Pedido: " + c.pDoPedido);
                                        System.out.println("Dias restantas para marcar: " + c.diasATirar);
                                        System.out.println(p.getEstado());
                                    }
                                }
                            }
                            
                            break;

                        case "2":
                            c.consultarPedidos();
                            System.out.println("Dias restantas para marcar: " + c.diasATirar);
                            break;

                        case "3":
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                }


            }
        }
        else if (db.verifyCredentialsGestor(id, pass)) {
            if(id.charAt(0) == 'G')
            {
                Gestor g = db.getGestorData(id);
                String escolha = "";
                System.out.println("Bem Vindo!");

                while (!escolha.equals("4"))
                {
                    System.out.println("1. Marcar Férias");
                    System.out.println("2. Consultar Férias");
                    System.out.println("3. Aprovar férias");
                    System.out.println("4. Terminar Sessão");
                    escolha = scanner.nextLine();

                    switch(escolha)
                    {
                        case "1":
                        String inicio;
                        String fim;
                        System.out.println("Data de Inicio(dd/mm/yyyy): ");
                        inicio = scanner.nextLine();
                        if (g.validarData(inicio) != null) {
                            System.out.println("Data de Fim(dd/mm/yyyy): ");
                            fim = scanner.nextLine();
                            if (g.validarData(fim) != null) {
                                g.marcarFerias(inicio, fim);
                                System.out.println("Pontuação do Pedido: " + g.pDoPedido);
                                System.out.println(g.getDias());
                            }
                        }
                            break;

                        case "2":
                            g.consultarPedidos();
                            break;

                        case "3":
                            if(g.listarPedidos(db))
                            {
                                System.out.print("Escolha o ID do Pedido: ");
                                String idPedido = scanner.nextLine();
                                if(!g.avaliarPedidos(idPedido, db))
                                {
                                    System.out.println("Esse pedido não existe.");
                                }
                            }
                            
                            break;

                        case "4":
                            
                            break;

                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                }
            }
        }
        else
            System.out.println("Credencias inválidas!");
    }
    

    public static void main(String[] args) throws ParseException
    {
        LoginDatabase db = new LoginDatabase();

        db.addColaboradorToDB("C001", "001");
        db.addColaboradorToDB("C002", "002");
        db.addColaboradorToDB("C003", "003");

        Colaborador ctemp = db.getColaboratorData("C003");
        ctemp.marcarFerias("27/07/2019", "06/08/2019");
        Colaborador c2temp = db.getColaboratorData("C001");
        c2temp.marcarFerias("01/01/01", "05/01/01");

        db.addGestorToDB("G001", "001");
        
        while (true) {
            login(db);
        }

    }
        
}
