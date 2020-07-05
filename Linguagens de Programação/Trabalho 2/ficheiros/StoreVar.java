public class StoreVar extends AcessVars {
    
    public StoreVar(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){

        int envPtAmbiente = maquina.segueAcessLink(this.arg1);

        int numDeArgs = maquina.memExecut.get(envPtAmbiente+3);
        int valor = maquina.pilhaAval.pop();
        
        maquina.memExecut.set(envPtAmbiente+5+numDeArgs+(this.arg2)-1, valor);
    
        maquina.PC++;
    }

    public String toString(){
        return "store_var " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}