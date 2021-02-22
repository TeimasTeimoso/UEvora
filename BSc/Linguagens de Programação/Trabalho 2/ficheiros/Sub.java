public class Sub extends Aritmetica{
    
    public Sub(TISC maquina){
        super(maquina);
    }

    public void executa(TISC maquina){ 
        int o2 = maquina.pilhaAval.pop();
        int o1 = maquina.pilhaAval.pop() ;

        maquina.pilhaAval.add(o1-o2);

        maquina.PC++;
    }

    public String toString(){
        return "sub";
    }
}