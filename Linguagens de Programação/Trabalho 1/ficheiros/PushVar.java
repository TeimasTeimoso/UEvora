public class PushVar extends AcessVars{

    public PushVar(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){
        //codigo para a fase 2
    }

    public String toString(){
        return "push_var " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}