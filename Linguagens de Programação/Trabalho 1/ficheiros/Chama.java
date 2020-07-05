public class Chama extends ChamaFunc{
    
    public Chama(int a1, String label){
        super(a1, label);
    }

    public void executa(TISC maquina){
        // codigo da fase 2
    }

    public String toString(){
        return "call " + Integer.toString(this.arg1) + " " + this.labelName;
    }
}