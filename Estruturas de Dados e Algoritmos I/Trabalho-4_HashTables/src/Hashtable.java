@SuppressWarnings("unchecked")
public abstract class Hashtable<E>{

//    ------------------
    static class Elemento<E> {
        E elemento;
        boolean removed;

        public Elemento(){
            this(null);
            removed = false;
        }
        public Elemento(E elemento){
            this.elemento = elemento;
            removed = false;
        }

        public E getElemento(){
            return elemento;
        }

        public void setElemento(E elemento){
            this.elemento = elemento;
        }
    }

//    ------------------


    Elemento<E> table[];
    Elemento<E> tempTable[];
    int ocupado;

    public Hashtable(){
        ocupado = 0;
    }

    public Hashtable(int n){
        table = new Elemento[n];
        ocupado = 0;
    }



    //retorna nmr elementos da tabela
    public int ocupados(){
        return ocupado;
    }

    //retorna factor de carga
    public float factorCarga(){
        return ocupado/(float)table.length;
    }

    //retorna a pos em que s será inserido ou se s existe, a sua pos
    protected abstract int procPos(E s);

    //Nova table dimensão especificada
    public void alocarTabela(int dim){
        table = (Elemento<E>[])new Elemento[proxPrimo(dim)];
        ocupado = 0;
    }

    //Esvaziar tabela em uso
    public void tornarVazia(){
        alocarTabela(table.length);
    }

    //returna elemento que está na tabela, se x não tiver return null
    public E procurar(E x){
        int PosExpectavel = procPos(x);
        if(table[PosExpectavel] != null){
            if(table[PosExpectavel].getElemento().equals(x)){
                return table[PosExpectavel].getElemento();
            }
        }
        return null;
    }

    //remove elemento da tabela
    public void remove(E x){
        int PosExpectavel = procPos(x);
        if(table[PosExpectavel] != null && !table[PosExpectavel].removed){
            table[PosExpectavel].removed = true;
            ocupado--;
        }else{
            System.out.println("Não existe!");
        }
    }

    //insere x na tabela
    public void insere(E x){
        int insertPos = procPos(x);
        if(table[insertPos] == null || table[insertPos].removed){
            table[insertPos] = new Elemento<E>(x);
            ocupado ++;
        }
        if(factorCarga()>0.5){
            rehash();
        }
    }

    //Verifica se um numero é primo
    public boolean isPrime(int n){
        if(n==1 || n % 2 == 0){
            return false;
        }
        if(n == 2 || n == 3){
            return true;
        }
        for(int i = 3; i < n; i += 2){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }

    //Retorna o primeiro numero primo após n
    public int proxPrimo(int n){
        boolean primo = false;
        while(!primo){
            if(isPrime(n)){
                primo = true;
            }else{
                n++;
            }
        }
        return n;
    }

    //faz rehashing
    public void rehash(){
        Elemento<E> tempTable[] = table;
        alocarTabela(proxPrimo(tempTable.length*2));
        for(int i = 0; i < tempTable.length; i++){
            if(tempTable[i] != null && !tempTable[i].removed){
                insere(tempTable[i].getElemento());
            }
        }
    }
    //lista os elemnentos da tabela
    public void print(){
        for(int i = 0; i < table.length; i++){
            if(table[i] != null && !table[i].removed){
                System.out.println(table[i].getElemento());
            }
        }
    }
}

