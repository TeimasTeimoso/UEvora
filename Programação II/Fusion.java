
import java.util.*;

public class Fusion{
    public static void main(String args[]){

        int linha, coluna;
        Board2 tabuleiro;
        int boardSize=0;
        int Color = 0;

        Scanner scan = new Scanner(System.in);
        System.out.println("Bem vindo ao Fusion!");
        //Restrições do Board em relação ao tamanho
        System.out.println("Introduza o tamanho do board:");
        try{
            boardSize = scan.nextInt();
            while(boardSize<3){
                System.out.println("Introduza um valor valido");
                boardSize = scan.nextInt();
            }
                    //Restrições do Board em relação às cores
            System.out.println("Introduza o numero de cores:");
            Color = scan.nextInt();
            while(Color<2 || Color>9){
                System.out.println("Introduza um valor valido");
                Color = scan.nextInt();
            }
        }catch(InputMismatchException e){
            System.out.println("O input introduzido não foi um intero!");
        }

        


        tabuleiro = new Board2(boardSize, Color); //Cria um novo objeto do tipo Board2
        
        tabuleiro.PrintBoard();

        while(tabuleiro.Verifica()){                //Metodos vindos da classe Board 2
            System.out.println("Linha?");           //Que garantem o funcionamento do programa 
            linha = scan.nextInt();
            while(linha>=boardSize){
                System.out.println("Linha?");
                linha = scan.nextInt(); 
            }                
            System.out.println("Coluna?");
            coluna = scan.nextInt();
            while(coluna>=boardSize){
                System.out.println("Coluna?");
                coluna = scan.nextInt(); 
            }
            System.out.print("\n");
            tabuleiro.Arround(linha, coluna);       //Este ciclo será executado até que não existam mais jogadas
            tabuleiro.Remove(linha,coluna);
            tabuleiro.Drop();
            tabuleiro.Score();
            tabuleiro.Fill(Color);    
        }
        int total = tabuleiro.Score();              //Dá ao utilizador a pontuação final
        System.out.println("Fim do jogo!\nA sua pontuação foi: "+total);
    }
}