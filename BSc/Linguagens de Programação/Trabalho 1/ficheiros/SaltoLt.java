public class SaltoLt extends Salto{

    public SaltoLt(String dest){
        super(dest);
    }

    public void executa(TISC maquina){
        // codigo 2º fase
        /*
        
        protected int argA = maquina.pilhaAval.pop();
        protected int argB = maquina.pilhaAval.pop();


        if(argA > argB){
            // a proxima instrucao a executar e' a identificada por <etiqueta>
        }
        else{
            // e' a que se segue a jlt na ordem do programa. 
        }

        */
    }

    public String toString(){
        return "jlt " + this.labelNome;
    }
}