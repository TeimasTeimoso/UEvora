
import java.util.*;

public class Board2{
    double total= 0;
    int score = 0;
    int boardSize, esquerda, direita, acima, abaixo;
    public int[][] board;                               //matriz que vai conter o board
    Random rand;
    
    public Board2(int boardSize, int Color){
        this.rand = new Random();
        this.board = new int[boardSize][boardSize];
        this.boardSize = boardSize;

        for(int i = 0; i<boardSize; i++){                   //Preenhcimento aleatório do board
			for (int j = 0; j<boardSize; j++){              
                board[i][j] = rand.nextInt(Color)+1;
            }
        } 
    }

    public void PrintBoard(){
        System.out.print("\n");
        System.out.print("   ");                         //Permite alinhar os char com as colunas
        for (int z=0; z<boardSize; z++){                 //Acrescenta os char progressivamente
            System.out.print((char)(48+z)+" ");
        }
        System.out.println("\n");
       
        for(int i = 0; i<boardSize; i++){
            System.out.print((char)(48+i)+"  ");         //Acrescenta os char progressivamente nas linhas
			for (int j = 0; j<boardSize; j++){
                if(j==boardSize-1){
                    System.out.print(board[i][j]);
                    System.out.println("");
                }else{
                    System.out.print(board[i][j]+" ");
                }
            }
        }
        System.out.print("\n");   
    }
    /*Metdo que vai percorrer o board nos
    *eixos Y, -Y, X, -X em busca
    *de peças sequenciais da mesma cor */
    
    public void Arround(int i, int j){
        Reset();
        for(int y = i; y <boardSize-1; y++){				 //Casas Abaixo da Celula
            if(board[y][j] == board[y+1][j]){                //Percorre o board para baixo, a partir da posição dada, até encontrar uma cor diferente
			    abaixo++;							         //Quando encontra, para
			}else{									
				break;									
			}
		}

		for(int y = i; y>0; y--){				
            if(board[y][j] == board[y-1][j]){			    //Casas Acima da Celula
			    acima++;									//Comporta-se da mesma forma que o ciclo acima, mas percorrendo o board para cima
			}else{									
				break;									
			}
		}

		for(int y = j; y<boardSize-1; y++){				
			if(board[i][y] == board[i][y+1]){			
			    direita++;									//Casas à Direita da Celula
			}else{									        //Comporta-se da mesma forma que o ciclo acima, mas percorrendo o board para a direita
				break;									
			}
		}
    
		for(int y = j; y>0; y--){				
			if(board[i][y] == board[i][y-1]){			    //Casas à Esquerda da Celula
			    esquerda++;							        //Comporta-se da mesma forma que o ciclo acima, mas percorrendo o board para a esquerda
			}else{									
				break;									
			}
        }
        //System.out.println("abaixo: "+ abaixo);


		//System.out.println("acima: "+ acima);


		//System.out.println("direita: "+ direita);


        //System.out.println("esquerda: "+ esquerda);

    }

    /*Metodo que irá remover as casas
    *adjacentes caso existam 3 ou mais
    *numa coluna ou linha */
    public void Remove(int a, int b){
        if((esquerda+direita)>=2 || (acima+abaixo)>=2){                     //Apenas é necessário existir 2 casa na soma de um dos eixos
                                                                            //Pois a 3ª casa é a posição escolhida
			board[a][b] = 0;

			//Elimina Celulas abaixo
            for(int y = 0; y<=abaixo; y++){                                 //vai eliminar as casas abaixo até o ciclo chegar
				board[a+y][b] = 0;										    //ao fim(quando fizer tantas ciclos quanto o int abaixo);
			}

			//Elima Celulas acima
			for(int y = 0; y<=acima; y++){                                  //Os restantes ciclos conformam-se de igual forma
				board[a-y][b] = 0;										    //para as direções pertendidas
			}

			//Elimina Celulas à Direita
			for(int y = 0; y<=direita; y++){
				board[a][b+y] = 0;										
			}

			//Elimina Celulas à Esquerda
			for(int y = 0; y<=esquerda; y++){
				board[a][b-y] = 0;										
			}
            Reset();
            PrintBoard();
        }
    }

    /*Método que conta os scores parciais
    *e devolve o score total no fim*/ 
    public int Score(){
        score = 0;
        for(int i = 0; i<boardSize; i++){               //Percorre o board, se achar uma casa a 0
            for(int j = 0;j<boardSize; j++){            //Então é um ponto feito pelo utilizador (casa eliminada);
                if(board[i][j] == 0){
                    score++;
                    
                }
            }
        }
        total = total + (Math.pow(score, 2));
        return (int)total;
    }


    /*Método que permite as peças descerem 
    *para as casas vazias*/
    public void Drop(){
        for(int i =boardSize-1; i>=0; i--){                             //Começa de baixo para cima
            for(int j=boardSize-1;j>=0;j-- ){                           //Caso a casa de baixo esteja vazia, irá verificar se a casa acima
                if(board[j][i]==0){                                     //Está preenchida, caso esteja, a mesma desce, e o ciclo continua
                    for(int k=j-1; k>=0; k--){                          //Se nenhuma das casas estiver preenchidas, o método Fill tratará disso
                        if(board[k][i]!=0){
                            board[j][i]=board[k][i];
                            board[k][i]=0;
                            break;
                        }
                    }
                }
            }
        }
        //PrintBoard();
    }

    /*Método que preenche com novas
    *cores as casas vazias */
    public void Fill(int Color){
		for (int x = 0; x < boardSize; x++ ){
			for (int y = 0; y < boardSize; y++){
				if(board[x][y] == 0){                           //Percorre o board em busca de casas vazias
					board[x][y] = rand.nextInt(Color)+1;        //Ao encontra-las, preenche com uma cor random
				}
			}	
        }
        PrintBoard();                                           //Dá print do novo board
	}

    //Método que verifica se existem jogadas possiveis no board 
    public boolean Verifica(){
    	for (int i = 0; i < boardSize-1; i++ ){                             //Percorre o board
			for (int j = 0; j < boardSize-1; j++){                          
				Arround(i, j);                                              //Chamada do metodo Arround para verificar as casas à volta da casa atual
				if((esquerda+direita)>=2 || (acima+abaixo)>=2) {            //Caso exista alguma jogada possivel retorna true
					return true;
				}
            }
    	}
    	return false;                                                        //Senão retorna false(condição de paragem parao jogo)
    }  
    
    public void Reset(){
        esquerda=0;                                     //Dá reset nos eixos X, -X, Y, -Y, no que toca a casas
        direita=0;
        acima=0;
        abaixo=0;
    }
}