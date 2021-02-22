public class PushVar extends AcessVars{

    public PushVar(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){
        int envPtAmbiente = maquina.segueAcessLink(this.arg1);

        maquina.pilhaAval.push(maquina.getVar(envPtAmbiente, this.arg2));
        
        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "push_var " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}