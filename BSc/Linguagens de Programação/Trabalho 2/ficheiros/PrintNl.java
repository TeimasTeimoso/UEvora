
public class PrintNl extends Output {
    
    public PrintNl(){

    }

    public void executa(TISC maquina){
        System.out.println("\n");

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "print_nl";
    }

}