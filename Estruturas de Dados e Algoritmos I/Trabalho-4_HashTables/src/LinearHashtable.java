public class LinearHashtable<E> extends Hashtable<E>{

    public LinearHashtable(){
        super();
    }

    public LinearHashtable(int x){
        super(x);
    }


    @Override
    protected int procPos(E x){
        int colisao = 1;
        int pos = Math.abs(x.hashCode() % table.length);
        while(table[pos] != null && !table[pos].elemento.equals(x))
        {
            pos += colisao; // f(i) = i
            colisao += 1;
            if(pos >= table.length)
                pos = pos % table.length;
        }
        return pos;
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
