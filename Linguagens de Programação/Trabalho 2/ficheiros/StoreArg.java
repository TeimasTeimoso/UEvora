public class StoreArg extends AcessArgs{
    
    public StoreArg(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){
        int envPtAmbiente = maquina.segueAcessLink(this.arg1);
        maquina.memExecut.set(envPtAmbiente+5+(this.arg2)-1, maquina.pilhaAval.pop()); 
        
        maquina.PC++;
    }

    public String toString(){
        return "store_arg " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}
