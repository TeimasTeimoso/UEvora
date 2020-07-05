public class Mod extends Aritmetica{
    
    public Mod(TISC maquina){
        super(maquina);
    }

    public void executa(TISC maquina){
        int o2 = maquina.pilhaAval.pop();
        int o1 = maquina.pilhaAval.pop() ;

        maquina.pilhaAval.add(o1 % o2);

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "mod";
    }
}