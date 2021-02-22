// import sun.print.SunPrinterJobService;

public class ABP<E extends Comparable<? super E>> {
    static class LinkedListIterator<E> implements java.util.Iterator<E> {
        private SingleNode<E> current;

        public LinkedListIterator(SingleNode<E> c){
            current=c;
        }
        public boolean hasNext(){
            return current!=null;
        }


        public E next(){
            if (!hasNext())
                throw new java.util.NoSuchElementException("No element");

            E nextItem = current.element() ;
            current = current.getNext();
            return nextItem;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }


    static class Node<E extends Comparable<? super E>> {
        E elemento;
        Node<E> esq;
        Node<E> dir;

        public Node(E e) {
            elemento = e;
            esq = null;
            dir = null;
        }

        public Node(E element, Node<E> esq, Node<E> dir) {
            elemento = element;
            this.esq = esq;
            this.dir = dir;

        }

        public E getElemento() {
            return elemento;
        }

        public Node<E> getNodeEsq() {
            return esq;
        }

        public Node<E> getNodeDir() {
            return dir;
        }

        public void setElemento(E e) {
            elemento = e;
        }

        public void setEsq(Node<E> e) {
            this.esq = e;
        }

        public void setDir(Node<E> e) {
            this.dir = e;
        }

        public String toString() {
            return elemento.toString();
        }
    }

    ///////////////////////////////////////////
    Node<E> root;

    @SuppressWarnings("unchecked")
    public ABP(E e) {
        root = new Node<E>(e);
    }

    public ABP(Node<E> r) {
        root = r;
    }

    public ABP() {
        root = null;
    }

    public ABP(E r, Node<E> e, Node<E> d) {
        root = new Node<E>(r, e, d);
    }

    private boolean isEmpty() {
        return root == null;
    }

    public E findMin() {
        if (isEmpty()) {
            return null;
        } else {
            return findMin(root);
        }
    }

    public E findMin(Node<E> n) {
        if (n.getNodeEsq() == null) {
            return n.getElemento();
        } else {
            return findMin(n.getNodeEsq());
        }
    }

    public E findMax() {
        if (isEmpty()) {
            return null;
        } else {
            return findMax(root);
        }
    }

    public E findMax(Node<E> n) {
        if (n.getNodeDir() == null) {
            return n.getElemento();
        } else {
            return findMax(n.getNodeDir());
        }
    }

    public boolean contains(E x) {
        return contains(x, root);
    }

    public boolean contains(E x, Node<E> n) {
        if (n == null) {
            return false;
        } else {
            if (n.getElemento().compareTo(x) < 0) {
                return contains(x, n.getNodeDir());
            } else {
                if (n.getElemento().compareTo(x) > 0) {
                    return contains(x, n.getNodeEsq());
                } else {
                    return true;
                }
            }
        }
    }

    public void insert(E x) {
        root = insert(x, root);
    }

    public Node<E> insert(E x, Node<E> n) {
        if (n == null) {
            n = new Node<E>(x);
        } else if (n.getElemento().compareTo(x) > 0) {
            n.setEsq(insert(x, n.getNodeEsq()));
        } else if (n.getElemento().compareTo(x) < 0) {
            n.setDir(insert(x, n.getNodeDir()));
        }
        return n;
    }

    public void remove(E x) {
        root = remove(x, root);
    }

    public Node<E> remove(E x, Node<E> n) {
        if (n == null) {
            return n;
        }
        if (n.getElemento().compareTo(x) < 0) {
            n.setDir(remove(x, n.getNodeDir()));
        } else if (n.getElemento().compareTo(x) > 0) {
            n.setEsq(remove(x, n.getNodeEsq()));
        } else if (n.getNodeEsq() != null && n.getNodeDir() != null) {
            E min = findMin(n.getNodeDir());
            n.setElemento(min);
            n.setDir(remove(min, n.getNodeDir()));
        } else if (n.getNodeEsq() == null) {
            n = n.getNodeDir();
        } else{
            n = n.getNodeEsq();
        }

        return n;
    }

    LinkedListIterator iterator = new LinkedListIterator();

    public void desfolhar(){
        desfolhar(getroot());
    }
    public void desfolhar(Node n){
        while(iterator.hasNext()){
            if(n.getNodeEsq() == null && n.getNodeDir()==null){
                remove(n);
            }
        }
    }


    public Node getroot(){
        return root;
    }


    public void printEmOrdem() {
        printEmOrdem(root);
    }

    public void printEmOrdem(Node<E> n) {
        if (n != null) {
            printEmOrdem(n.getNodeEsq());
            System.out.println(n.getElemento().toString());
            printEmOrdem(n.getNodeDir());
        }
        if (root == null){
            System.out.println("A arvore esta vazia!");
        }
    }

    public static void main(String[] args){
        ABP a = new ABP();
        a.insert(2);
        a.insert(3);
        a.insert(4);
        a.insert(19);
    }
}

