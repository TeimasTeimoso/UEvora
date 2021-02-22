public class StoreVar extends AcessVars {
    
    public StoreVar(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){
        //
    }

    public String toString(){
        return "store_var " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}