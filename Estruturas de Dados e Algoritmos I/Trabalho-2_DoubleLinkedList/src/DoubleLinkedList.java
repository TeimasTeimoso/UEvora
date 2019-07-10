public class DoubleLinkedList<E> { // implements java.util.Iterator<E>{

    Node<E> header;
    Node<E> footer;
    int theSize;

    static class Node<E> {

        E elemento;
        Node<E> next;
        Node<E> prev;

        public Node(E x) {
            elemento = x;
            next = null;
            prev = null;
        }

        public Node() {
            this(null);
        }

        public Node(E o, Node<E> n, Node<E> p) {
            elemento = o;
            next = n;
            prev = p;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setElement(E x) {
            this.elemento = x;
        }

        public void setNext(Node<E> n) {
            next = n;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public E getElement() {
            return elemento;
        }

        public String getStringElement(){
            String x = String.valueOf(elemento);
            return x;
        }
    }

    // Iterador
    public class Iterator<E> implements java.util.Iterator<E> {
        public Node<E> current;

        public Iterator(Node<E> c) {
            current = c;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }


            E nextItem = current.getElement();
            current = current.getNext();
            return nextItem;
        }

        public boolean hasPrev() {
            return current != null;
        }

        public E prev() {
            if (!hasPrev()) {
                throw new java.util.NoSuchElementException();
            }

            E prevItem = current.getElement();
            current = current.getPrev();
            return prevItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator iterator() {
        return new Iterator<E>(header.getNext());
    }

    //Lista
    public DoubleLinkedList(){
        header = new Node<>();
        footer = new Node<>();
        header.setNext(footer);
        footer.setPrev(header);
        theSize = 0;
    }

    public boolean isEmpty(){
        return theSize == 0;
    }

    public int Size(){
        return theSize;
    }

    public void add(E nn){
        Node<E> newNode = new Node<E>(nn, null, null);

        newNode.setPrev(footer.getPrev());
        footer.setPrev(newNode);
        newNode.setNext(footer);
        newNode.getPrev().setNext(newNode);
        theSize++;
    }

    public void add(int pos, E nn){
        Node<E> newNode = new Node<E>(nn);

        if(pos == theSize + 1){
            add(nn);
        }else{
            if(pos > theSize){
                System.out.println("Não pode inserir nessa posição");
            }else{
                Node<E> nodeNext = getPos(pos);
                Node<E> nodePrev = getPos(pos).getPrev();

                nodePrev.setNext(newNode);
                newNode.setPrev(nodePrev);
                newNode.setNext(nodeNext);
                nodeNext.setPrev(newNode);
                theSize++;
            }
        }
    }

    public Node getPos(int pos){
        int counter = 0;
        Node<E> current = header;
        if(pos == 0) {
            System.out.println("Cant Insert on header.");
        }else{
            while(counter != pos) {
                current = current.getNext();
                counter++;
            }
        }
        return current;
    }

    public String toString(){
        int nmr_linha= 1;
        Node<E> temp = header.getNext();
        String lista = "";
        while (temp!=footer){
            lista += String.valueOf(nmr_linha)+ " " +temp.getElement();
            nmr_linha++;
            temp = temp.getNext();
            if(temp != footer){
                lista+="\n";
            }
        }
        return lista;
    }

    Node<E> getNode(int i){
        int index = -1;
        Node<E> s = header;
        while(index++ < i)
            s = s.getNext();
        return s;
    }

////// Remove pos
    void remove(Node<E> prev){
        prev.setNext(prev.getNext().getNext());
        theSize--;
    }

    public void remove(int ind){
        remove(getNode(ind-2));
    }
//////

////// Remove full data
    public void remove(E x){
        try{
            remove(findPrev(x));
        }
        catch(java.util.NoSuchElementException e){}
    }

    Node<E> findPrev(E x) {
        Node<E> prev = header;
        int s = Size();
        //System.out.println(s);
        int counter = 0;
        String v = null;
        v = prev.next.getStringElement();

        while (counter < s)

            if (v.equals(x))
                return prev;
            else {
                prev = prev.getNext();
                counter++;
                //System.out.println(counter);
                v = prev.next.getStringElement();
            }
        throw new java.util.NoSuchElementException( );
        //return null;
    }

    public int find(String x) {
        Node<E> prev = header;
        int s = Size();
        //System.out.println(s);
        int counter = 0;
        String v = prev.next.getStringElement();

        while (counter < s)

            if (v.contains(x)) {
                System.out.print("' " + v + " '");
                return counter + 1;
            }
            else {
                prev = prev.getNext();
                counter++;
                //System.out.println(counter);
                v = prev.next.getStringElement();
            }
        throw new java.util.NoSuchElementException( );
        //return null;
    }
}