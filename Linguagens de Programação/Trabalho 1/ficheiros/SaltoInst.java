public class SaltoInst extends Salto{
    
    public SaltoInst(String dest){
        super(dest);
    }

    public void executa(TISC maquina){
        // codigo 2º fase
    }

    public String toString(){
        return "jump " + this.labelNome;
    }
}