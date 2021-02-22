public class SetArg extends ChamaFunc{
    
    public SetArg(int a1){
        super(a1);
    }

    public void executa(TISC maquina){
        // codigo da fase 2
    }

    public String toString(){
        return "set_arg " + Integer.toString(this.arg1);
    }
}