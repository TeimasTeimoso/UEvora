
public class PrintStr extends Output {
    
    public PrintStr(String str){
        this.arg = str;
    }

    public void executa(TISC maquina){
        System.out.println(this.arg);
    }

    public String toString(){
        return "print_nl " + this.arg;
    }

}