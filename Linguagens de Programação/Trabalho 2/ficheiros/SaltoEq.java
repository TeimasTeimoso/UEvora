public class SaltoEq extends Salto{

    public SaltoEq(String dest){
        super(dest);
    }

    public void executa(TISC maquina){
        int arg1 = maquina.pilhaAval.pop();
        int arg2 = maquina.pilhaAval.pop();


        /* Se forem iguais o pc vai para a posicao da label
           se não, avança uma pos */
        if(arg1 == arg2){
            maquina.PC = maquina.labels.get(this.labelNome);
        }
        else{
            maquina.PC++;
        }
    }

    public String toString(){
        return "jeq " + this.labelNome;
    }
}