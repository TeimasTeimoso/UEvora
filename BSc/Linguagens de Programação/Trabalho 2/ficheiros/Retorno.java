public class Retorno extends ChamaFunc{
    
    public Retorno(){
        super();
    }

    public void executa(TISC maquina){
        int endRetorno = maquina.memExecut.get(maquina.EnvPnt+2);
        int tmpEnvPt = maquina.EnvPnt;

        maquina.EnvPnt =  maquina.memExecut.get(maquina.EnvPnt); // EnvPnt fica com o valor do control link do RA
        int tamanho = maquina.memExecut.size()-1;
        /* Remove o RA da memExecut */
        for( int i = tamanho; i > tmpEnvPt; i--){
            maquina.memExecut.remove(i);
        }

        maquina.PC = endRetorno;    // PC aponta para a instrução do endereço de retorno
    }

    public String toString(){
        return "return";
    }
}