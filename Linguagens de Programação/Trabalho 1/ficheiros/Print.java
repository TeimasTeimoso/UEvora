
public class Print extends Output {
    
    public Print(){

    }

    public void executa(TISC maquina){
        System.out.println(maquina.pilhaAval.pop());
    }

    public String toString(){
        return "print";
    }

}