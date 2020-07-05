public class Chama extends ChamaFunc{
    
    public Chama(int a1, String label){
        super(a1, label);
    }

    /* Faz push do novo RA, slide 15 ppt 7 */
    public void executa(TISC maquina){
        maquina.memExecut.add(maquina.EnvPnt); // Control Link = EnvPointer
        maquina.EnvPnt = maquina.memExecut.size()-1; // EnvPointer = 1º pos do novo RA
        maquina.memExecut.add(maquina.atribAcessLink(this.arg1)); // Acess Link
        maquina.memExecut.add(++maquina.PC); // Endereco de retorno
        maquina.PC = maquina.labels.get(this.labelName); //Program Counter na posição da função chamada
    }

    public String toString(){
        return "call " + Integer.toString(this.arg1) + " " + this.labelName;
    }
}