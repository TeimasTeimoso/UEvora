public class PushArg extends AcessArgs{

    public PushArg(int a1, int a2){
        super(a1, a2);
    }

    public void executa(TISC maquina){
        int envPtAmbiente = maquina.segueAcessLink(this.arg1);

        maquina.pilhaAval.push(maquina.getArg(envPtAmbiente, this.arg2));

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "push_arg " + Integer.toString(this.arg1) + " " + Integer.toString(this.arg2);
    }
}