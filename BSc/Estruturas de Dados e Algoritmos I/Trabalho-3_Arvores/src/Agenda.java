public class Agenda {

    static class Contactos implements Comparable<Contactos> {

        String nome;
        int numero;
        int numero2 = -5;

        public Contactos(String name, int phone) {
            this.nome = name;
            this.numero = phone;
            int numero2 = -5;

        }

        public Contactos(String name, int phone, int phone2) {
            this.nome = name;
            this.numero = phone;
            this.numero2 = phone2;
        }

        public String getNome() {
            return nome;
        }

        public int getNumero() {
            return numero;
        }

        public int getNumero2() {
            return numero2;
        }

        public String toString(){
            String x =  (numero2 ==-5) ? "" : numero2 + "";
            return ("Nome: " + getNome() + "\nNumero: " + getNumero() + " "+ x);
        }
        public String Info() {
            return ("Nome: " + getNome() + "\nNumero: " + getNumero());
        }

        public int compareTo(Contactos other) {
            if (getNome().equals(other.getNome())) {
                return 0;
            } else if (getNome().compareTo(other.getNome()) < 0) {
                return -1;
            } else {
                return 1;
            }
        }

    }

    ABP<Contactos> arvore;
    Contactos novoContacto;
    Contactos pro_node = null;
    boolean existe;

    public Agenda() {
        arvore = new ABP<Contactos>();
    }

    public void add(String nome, int numero) {
        if (addsearch(arvore.getroot(), nome, numero) == -1) {
            return;
        }
        novoContacto = new Contactos(nome, numero);
        arvore.insert(novoContacto);
    }

    public int addsearch(ABP.Node<Contactos> n, String nome, int novoNumero) {
        if (n == null) {
            return -2;
        }
        if (n != null) {
            if (n.getElemento().getNome().equals(nome)) {
                if (n.getElemento().getNumero2() != -5) {
                    System.out.println("Contacto ja exitente e com 2 numeros");
                    return -1;
                }
                n.getElemento().numero2 = novoNumero;
                return -1;
            }
            if (n.getElemento().getNome().compareTo(nome) < 0) {
                addsearch(n.getNodeDir(), nome, novoNumero);
            } else
                addsearch(n.getNodeEsq(), nome, novoNumero);
        }
        return -2;
    }

    public void lista() {
        //lista(arvore.getroot());
        arvore.printEmOrdem();
    }

    public void lista(ABP.Node<Contactos> n) {
        if (n != null) {
            lista(n.getNodeEsq());
            System.out.print("->Nome: " + n.getElemento().getNome() + "     -> Numero: " + n.getElemento().getNumero());
            if (n.getElemento().getNumero2() != -5)
                System.out.println("     -> Numero2: " + n.getElemento().getNumero2());
            else System.out.println(" ");
            lista(n.getNodeDir());
        }
        if (arvore.getroot() == null) {
            System.out.println("A Agenda esta vazia!");
        }
    }



    public boolean search(String nome){
        return search(arvore.getroot(), nome);
    }

    public boolean search(ABP.Node<Contactos> n, String nome) {
        if (n == null) {
            System.out.println("Desconhecido");
            return false;
        } else {
            if (n.getElemento().getNome().compareTo(nome) < 0) {
                return search(n.getNodeDir(), nome);
            } else {
                if (n.getElemento().getNome().compareTo(nome) > 0) {
                    return search(n.getNodeEsq(), nome);
                } else {
                    System.out.print("Search -> Nome: " + n.getElemento().getNome() + "     -> Numero: " + n.getElemento().getNumero());
                    if (n.getElemento().getNumero2() != -5)
                        System.out.println("     -> Numero2: " + n.getElemento().getNumero2());
                    else System.out.println(" ");
                    return true;
                }
            }
        }
    }

    public boolean removeNome() {
        /// if remove return true
        return true;
    }

    public void chamador(int numero){
        if (arvore.getroot() == null) {
            System.out.println("Numero desconhecido");
            return;
        }
        existe = false;
        chamador(arvore.getroot(), numero);

        if (!existe)
            System.out.println("Numero desconhecido");
    }

    public void chamador(ABP.Node<Contactos> n, int numero){
            if (n != null) {
                chamador(n.getNodeEsq(),numero);
                if (n.getElemento().getNumero() == numero || n.getElemento().getNumero2() == numero) {
                    System.out.print("search numero  -> Nome: " + n.getElemento().getNome() + "     -> Numero: " + n.getElemento().getNumero());
                    if (n.getElemento().getNumero2() != -5)
                        System.out.println("     -> Numero2: " + n.getElemento().getNumero2());
                    else System.out.println(" ");
                    existe = true;
                }
                chamador(n.getNodeDir(),numero);
            }
    }




    public void procurar(ABP.Node<Contactos> n, String nome){
        if (n.getElemento().getNome().compareTo(nome) < 0) {
            procurar(n.getNodeDir(), nome);
        }
        if (n.getElemento().getNome().compareTo(nome) > 0) {
            procurar(n.getNodeEsq(), nome);
        }
        if (n.getElemento().getNome().compareTo(nome) == 0) {
            pro_node = n.getElemento();
        }
        else  {
            return;
        }
    }

    public void procurar(ABP.Node<Contactos> n, int numero){
        if (n != null) {
            procurar(n.getNodeEsq(),numero);
            if (n.getElemento().getNumero() == numero || n.getElemento().getNumero2() == numero) {
                pro_node = n.getElemento();
            }
            procurar(n.getNodeDir(),numero);
        }
        else  {
            return;
        }
    }


    public void remove(String nome){
        pro_node = null;
        if (arvore.getroot() == null) {
            System.out.println("nome desconhecido");
            return;
        }
        procurar(arvore.getroot(), nome);
        arvore.remove(pro_node);
        return;
    }

    public void remove(int numero){
        pro_node = null;
        if (arvore.getroot() == null) {
            System.out.println("Numero desconhecido");
            return;
        }
        procurar(arvore.getroot(), numero);
        arvore.remove(pro_node);
        return;
    }

    public void editar(String nome, int novoNumero){
        pro_node = null;
        if (arvore.getroot() == null) {
            System.out.println("nome editar desconhecido");
            return;
        }
        procurar(arvore.getroot(), nome);
         if (pro_node != null){
             pro_node.numero = novoNumero;
         }
    }

    public void editar(String nome, String novoNome){
        pro_node = null;
        if (arvore.getroot() == null) {
            System.out.println(" desconhecido");
            return;
        }
        procurar(arvore.getroot(), nome);
        if (pro_node != null){
            pro_node.nome = novoNome;
        }
    }


    public void printRoot(){
        ABP.Node<Contactos> n = arvore.getroot();
        System.out.println(n.getElemento().getNome() + " + " + n.getNodeEsq().getElemento().getNome() + " + " + n.getNodeDir().getElemento().getNome());

    }
}
