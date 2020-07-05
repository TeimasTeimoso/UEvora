public class SaltoEq extends Salto{

    public SaltoEq(String dest){
        super(dest);
    }

    public void executa(TISC maquina){
        // codigo 2º fase
        /*
        protected int arg1 = maquina.pilhaAval.pop();
        protected int arg2 = maquina.pilhaAval.pop();


        if(arg1 == arg2){
            // A  instrucao  seguinte  a  ser  executada  e' identificada  por <etiqueta> se os  valores  nas duas posicoes no topo da pilha de avaliaco forem iguais;
        }
        else{
            //caso contrario, sera' a instrucaoseguinte 'a instrucao jeq na ordem do programa.
        }
        */
    }

    public String toString(){
        return "jeq " + this.labelNome;
    }
}