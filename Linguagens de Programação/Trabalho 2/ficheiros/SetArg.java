public class SetArg extends ChamaFunc{
    
    public SetArg(int a1){
        super(a1);
    }

    public void executa(TISC maquina){
        int argValue = maquina.pilhaAval.pop();

        maquina.tmpArgs.add(this.arg1-1, argValue); //Coloca o argumento na pos certa
        maquina.PC++;
    }

    public String toString(){
        return "set_arg " + Integer.toString(this.arg1);
    }
}