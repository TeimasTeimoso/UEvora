public class Exp extends Aritmetica{
    
    public Exp(TISC maquina){
        super(maquina);
    }

    public void executa(TISC maquina){
        int o2 = maquina.pilhaAval.pop();
        int o1 = maquina.pilhaAval.pop() ;
        int result = (int)Math.pow(o1, o2); 

        maquina.pilhaAval.add(result);

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "exp";
    }
}