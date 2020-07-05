public class SaltoLt extends Salto{

    public SaltoLt(String dest){
        super(dest);
    }

    public void executa(TISC maquina){
        int argA = maquina.pilhaAval.pop();
        int argB = maquina.pilhaAval.pop();


        if(argA > argB){
            maquina.PC = maquina.labels.get(this.labelNome);
        }
        else{
            maquina.PC++;
        }
    }

    public String toString(){
        return "jlt " + this.labelNome;
    }
}