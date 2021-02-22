import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

/* Tiny Instruction Set Computer */

public class TISC {
  /** Executa o programa TISC carregado na maquina. */

  public ArrayList<Instrucao> memInstrucao;
  public ArrayList<Integer> memExecut;
  public ArrayList<Integer> tmpArgs;
  public Stack<Integer> pilhaAval;
  public Hashtable<String, Integer> labels;
  int EnvPnt; //Environment Pointer
  int PC; //Program Counter

  public int ProgCounter;

  public TISC() {
    this.ProgCounter = 0;
    this.memInstrucao = new ArrayList<>();
    this.memExecut = new ArrayList<>();
    this.tmpArgs = new ArrayList<>();
    this.pilhaAval = new Stack<>();
    this.labels = new Hashtable<>();
    this.EnvPnt = 0;
    this.PC = 0;
  }


  // int profundidadeEnv = resultado de seguir o acess link
  public int getArg(int profundidadeEnvPt, int index) {
    /* RA = [CL, AL, ER, NdeArgs, NdeVars, ARGS, VARS] */
    return this.memExecut.get(profundidadeEnvPt+5+(index-1)); // Pq as inst começam em 1
  }

  public int getVar(int profundidadeEnvPt, int index) {
    /* RA = [CL, AL, ER, NdeArgs, NdeVars, ARGS, VARS] */
    int numDeArgs = this.memExecut.get(profundidadeEnvPt+3);
    return this.memExecut.get(profundidadeEnvPt+5+numDeArgs+(index-1));
  }

  /* Acha a Funcao Inicial e coloca o program counter
     nessa posição da memória de instrucoes,
     executando de seguida a instrucao */
  public void comecaMain() {
      this.PC = this.labels.get("program");
      
      /* Preenche RA com menos 1 até ao ER */
      for(int i = 0; i < 3; i++)
        this.memExecut.add(-1);

      this.memInstrucao.get(this.PC).executa(this);
  }

  /* Segue o acess link e retorna a pos desse ambito */
  public int segueAcessLink(int profundidade) {    
    int blocoPos = this.EnvPnt;

    if(profundidade == 0)
      return blocoPos;

    for(int i = profundidade; i > 0; i--)
        blocoPos = this.memExecut.get(blocoPos+1);

    return blocoPos;
  }

  /* Retorna posicao a atribuir do acess Link à função */
  public int atribAcessLink(int profundidade) {
    int tmpAL = this.EnvPnt;

    /* Se p = -1, ent Acess Link = Control Link */
    if(profundidade == -1)
      return this.memExecut.get(this.EnvPnt);


    tmpAL = this.memExecut.get(tmpAL); // Vai ao control link do anterior
    for(int i = profundidade; i >= 0; i--)
      tmpAL = this.memExecut.get(tmpAL+1);

    return tmpAL;
  }

  public void executa() {
    this.comecaMain();
    while(this.EnvPnt != -1)
      this.memInstrucao.get(this.PC).executa(this);
  }

}
