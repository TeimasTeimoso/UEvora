
public class Print extends Output {
    
    public Print(){

    }

    public void executa(TISC maquina){
        System.out.print(Integer.toString(maquina.pilhaAval.pop())+" ");

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "print";
    }

}