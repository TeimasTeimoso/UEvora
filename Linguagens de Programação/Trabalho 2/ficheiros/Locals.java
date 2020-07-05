public class Locals extends ChamaFunc{
    
    public Locals(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){
        maquina.memExecut.add(this.arg1); // Numero de argumentos
        maquina.memExecut.add(this.arg2); // Numero de variaveis

        /* Coloca os argumentos no RA */
        for(int i = 0; i < this.arg1; i++) {
            maquina.memExecut.add(maquina.tmpArgs.get(i));
        }

        /* Esvazia a lista de argumentos da maquina */
        maquina.tmpArgs.clear();
        
        /* Inicializa as posicoes de mem com valor 0 */
        for(int i = 0; i < this.arg2; i++) {
            maquina.memExecut.add(0);
        }

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "locals " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}