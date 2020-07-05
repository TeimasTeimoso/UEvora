public class Div extends Aritmetica{
    
    public Div(TISC maquina){
        super(maquina);
    }

    public void executa(TISC maquina) throws ArithmeticException{
        int o2 = maquina.pilhaAval.pop();
        int o1 = maquina.pilhaAval.pop() ;

        maquina.pilhaAval.add(o1 / o2);

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "div";
    }
}