public abstract class ChamaFunc extends Instrucao{
    int arg1;
    int arg2;
    String labelName;

    public ChamaFunc(int arg){
        this.arg1 = arg;
    }

    public ChamaFunc(int arg, String label){
        this.arg1 = arg;
        this.labelName = label;
    }

    public ChamaFunc(int a1, int a2){
        this.arg1 = a1;
        this.arg2 = a2;
    }

    public ChamaFunc(){
        // construtor do return
    }

}