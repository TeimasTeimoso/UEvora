import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

// Tiny Instruction Set Computer

public class TISC {
  /** Executa o programa TISC carregado na maquina. */

  public ArrayList<Instrucao> memInstrucao;
  public Stack<Integer> pilhaAval;
  public Hashtable<String, Integer> labels;

  public TISC()
  {
    memInstrucao = new ArrayList<>();
    pilhaAval = new Stack<>();
    labels = new Hashtable<>();
  }

  public void executa()
  {
    for (Instrucao temp : this.memInstrucao) 
    {
      System.out.println(temp.toString());  
    } 
  }
}
