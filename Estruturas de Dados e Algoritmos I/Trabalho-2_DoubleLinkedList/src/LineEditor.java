public class LineEditor {

    int cursor = 0;
    DoubleLinkedList<String> DLList;

    public LineEditor(){
        DLList = new DoubleLinkedList<String>();
    }

    public void insertEnd(String input){
        DLList.add(input);
        cursor++;
    }

    public void insert (int n, String input){
        DLList.add(n, input);
        cursor++;
    }

    public void insert(String input){
        DLList.add(cursor, input);
        cursor++;
    }

    public void delete (int n){
        DLList.remove(n);
        cursor--;
    }

    public void delete(){
        DLList.remove(cursor);
        cursor--;
    }

    public void edit (int n, String text){
        DLList.add(n, text);
        DLList.remove(n+1);
    }

    public void edit(String text){
        edit(cursor, text);
    }

    public void print(){
        System.out.println(DLList.toString() + "\nCursor: "+ String.valueOf(cursor));
    }

    public void lineUp(){
        if(cursor == 1) {
            System.out.println("Cant go up.");
        }else{
            cursor--;
        }
    }

    public void lineDown(){
        if(cursor == DLList.Size()){
            System.out.println("Cant go down");
        }else{
            cursor++;
        }
    }

    public void search (String text){
        System.out.println(" Linha nº " + DLList.find(text));
    }
}