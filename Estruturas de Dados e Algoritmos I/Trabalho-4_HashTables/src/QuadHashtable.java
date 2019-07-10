public class QuadHashtable<E> extends Hashtable<E>{

    public QuadHashtable() {
        super();
    }

    public QuadHashtable(int n) {
        super(n);
    }

    @Override
    protected int procPos(E x){
        int colisao = 1;
        int pos = Math.abs(x.hashCode() % table.length);
        int new_pos = pos;
        while(table[new_pos] != null && !table[new_pos].getElemento().equals(x)){
            new_pos = pos + colisao*colisao;
            colisao++;
            if(new_pos >= table.length){
                new_pos = new_pos % table.length;
            }
        }
        return new_pos;
    }

    public void print(){
        int counter = 0;
        System.out.println("Elementos na HashTable:");

        while(counter < this.table.length){
            Elemento elemento = table[counter];
            if(elemento != null && !elemento.removed){
                System.out.println(procPos((E) elemento.getElemento()) + " -> " + elemento.getElemento());
            }
            counter++;
        }

    }
}
