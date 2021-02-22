public class PushInt extends Instrucao {
    
    int arg;

    public PushInt(int a){
        this.arg = a;
    }

    public void executa(TISC maquina){ 
        maquina.pilhaAval.push(this.arg);

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "push_int " + Integer.toString(this.arg);
    }
}