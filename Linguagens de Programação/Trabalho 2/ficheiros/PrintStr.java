
public class PrintStr extends Output {
    
    public PrintStr(String str){
        this.arg = str;
    }

    public void executa(TISC maquina){
        System.out.print(this.arg);

        maquina.PC++; //Incrementa o program counter
    }

    public String toString(){
        return "print_nl " + this.arg;
    }

}