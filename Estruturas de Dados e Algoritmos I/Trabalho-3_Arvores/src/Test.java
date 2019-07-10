
public class Test {
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        Agenda a = new Agenda();
        System.out.println(" ");
        a.add("Ruben", 107);
        a.add("Luis", 2);
        a.add("Samuel", 114);
        a.add("Luis", 4);
        a.add("Salome", 5);
        a.add("Ruben", 7);
        a.lista();
        a.editar("Salome", "Ana Salome");
        System.out.println("------------------------------------------------------------------------------");
        a.remove("Luis");
        System.out.println("------------------------------------------------------------------------------");

        a.lista();
    /*
        System.out.println(" ");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" ");

        a.search("Luis");
        System.out.println(" ");
        a.search("olga");

        System.out.println(" ");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" ");
        a.chamador(114);
        System.out.println(" ");

        a.chamador(506546500);

        System.out.println(" ");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" ");

        a.remove(2);

        a.lista();

      //  a.printRoot();


        System.out.println(" ");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" ");

        a.editar("Ruben", 13);

        a.lista();

*/

    }
}
