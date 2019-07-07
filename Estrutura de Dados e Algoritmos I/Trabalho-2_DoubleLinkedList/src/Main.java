public class Main {
    public static void main(String[] args){
        LineEditor text = new LineEditor();

        text.insertEnd("Primeiro input");
        text.insertEnd("Segundo input");
        text.insertEnd("input para remover");
        text.insert(3, "input para pos 3");
        text.insertEnd("input end");
        text.print();
        text.delete(); //remover cursor
        text.print();
        text.insert("isto");
        text.print();
        text.edit("qualquer");
        text.print();
        text.insert(1, "ini");
        text.print();
        for (int i = 1; i < 3; i++){
            text.delete(i);
            text.print();

        }
        text.lineDown();

        text.lineDown();
        text.print();
        text.lineDown();
        text.print();
        text.search("input");
    /*    text.delete(1); //remove primeira pos
        text.print();
        text.edit( "sim"); // edit cursor
        text.print();
        text.search("Segundo");
        text.print(); */
    }
}