import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Corrector {
//    LinearHashtable<String> hashtable;
    QuadHashtable<String> hashtable;
    BufferedReader reader, input_reader;
    StringTokenizer word_input;


    public Corrector() throws IOException {
        hashtable = new QuadHashtable<>(691170);
//        reader = new BufferedReader(new FileReader(new File("D:\\Windows\\Documents\\Git\\EDA1\\untitled\\src/wordlist-ao-20101027_2.txt")));     //windows
        reader = new BufferedReader(new FileReader(new File("/home/luis/Documents/Git/EDA1/untitled/src/wordlist-ao-20101027_2.txt")));       //lunux
        dic_builder();
    }

    public void dic_builder() throws IOException{
        String dicionario_palavras;
        while((dicionario_palavras = reader.readLine()) != null){
            hashtable.insere(dicionario_palavras);
        }
    }

    public void spellchecker(String n) throws IOException{
        //Leitura do Ficheiro
  //      input_reader = new BufferedReader(new FileReader(new File("D:\\Windows\\Documents\\Git\\EDA1\\untitled\\src/" + n)));         //windows
        input_reader = new BufferedReader(new FileReader(new File("/home/luis/Documents/Git/EDA1/untitled/src/" + n)));     //linux
        String line = input_reader.readLine();
        int counter = 1;

        while(line != null){
   //     while(counter != 5){
            word_input = new StringTokenizer(line);  //Parte a String em tokens
            while(word_input.hasMoreTokens()){
                String word = word_input.nextToken(); //guarda o valor do token
                if(hashtable.procurar(word) == null || !hashtable.procurar(word).equals(word)){
                    System.out.println("Linha " + counter + "\n" + word + " ------->Não existe\n" + "Sugestões Remove: " + sugestaoRemove(word));
                    System.out.println("Sugestões Add: " + sugestaoAdd(word));
                    System.out.println("Sugestões Troca Adjacente: " + sugestaoTroca(word));
                    System.out.println("Sugestões Troca new char: " + sugestaoTrocaNew(word) + "\n");


                }
            }
            //Le next line e aumenta o contador
            line = input_reader.readLine();
            counter++;
            System.out.println(" ");
            System.out.println(" ");

        }
    }

    public String sugestaoRemove(String n){
        String sugestoes = "";

        for(int i = 0; i < n.length(); i++){
            StringBuilder palavra_chars = new StringBuilder(n);
            StringBuilder nova_palavra = palavra_chars.deleteCharAt(i);
            try{
                if(hashtable.procurar(nova_palavra.toString()).equals(nova_palavra.toString())){
                    sugestoes = sugestoes + " | " +  nova_palavra;
                }
            }catch (NullPointerException e){
              //  System.out.println("");
            }

        }
        return sugestoes;
    }


    public String sugestaoAdd(String n){
        String sugestoes = "";

        for(int pos = 0; pos < n.length()+1; pos++) {
            for (int i = 0; i < 256; i++) {
                char newChar = (char)i;

                StringBuilder palavra_chars = new StringBuilder(n);
                StringBuilder nova_palavra = palavra_chars.insert(pos, newChar);

                try {
                    if (hashtable.procurar(nova_palavra.toString()).equals(nova_palavra.toString())) {
                        sugestoes = sugestoes + " | " + nova_palavra;
                    }

                } catch (NullPointerException e) {
                    //  System.out.println("");
                }

            }
        }
        return sugestoes;
    }


    public String sugestaoTroca(String n){
        String sugestoes = "";

        for(int pos = 0; pos < n.length()-1; pos++) {

            StringBuilder palavra_chars = new StringBuilder(n);
            StringBuilder nova_palavra;

            char char_1 = palavra_chars.charAt(pos);
            char char_2 = palavra_chars.charAt(pos+1);

            nova_palavra = palavra_chars.deleteCharAt(pos);
            nova_palavra = palavra_chars.deleteCharAt(pos);
            nova_palavra = palavra_chars.insert(pos, char_1);
            nova_palavra = palavra_chars.insert(pos, char_2);


            try {
                if (hashtable.procurar(nova_palavra.toString()).equals(nova_palavra.toString())) {
                    sugestoes = sugestoes + " | " + nova_palavra;
                }

            } catch (NullPointerException e) {
                //  System.out.println("");
            }

        }
        return sugestoes;
    }





    public String sugestaoTrocaNew(String n){
        String sugestoes = "";

        for(int pos = 0; pos < n.length(); pos++) {
            for (int i = 0; i < 256; i++) {
                char newChar = (char)i;

                StringBuilder palavra_chars = new StringBuilder(n);
                StringBuilder nova_palavra;

                nova_palavra = palavra_chars.deleteCharAt(pos);
                nova_palavra = palavra_chars.insert(pos, newChar);


                try {
                    if (hashtable.procurar(nova_palavra.toString()).equals(nova_palavra.toString())) {
                        sugestoes = sugestoes + " | " + nova_palavra;
                    }

                } catch (NullPointerException e) {
                    //  System.out.println("");
                }

            }
        }
        return sugestoes;
    }

}
