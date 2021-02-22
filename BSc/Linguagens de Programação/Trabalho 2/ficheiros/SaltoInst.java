public class SaltoInst extends Salto{
    
    public SaltoInst(String dest){
        super(dest);
    }

    public void executa(TISC maquina){
        /* Salta para a instrucao da label */
        maquina.PC = maquina.labels.get(this.labelNome);
    }

    public String toString(){
        return "jump " + this.labelNome;
    }
}