import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
Alunos:
Luís Ressonha - 35003
Rúben Teimas - 39868
*/


public class Sort {

    public static Comparable[] le_array(){
        Scanner input = new Scanner(System.in);
        String array_elements = input.nextLine();
        StringTokenizer token = new StringTokenizer(array_elements);
        Comparable[] array = new Comparable[token.countTokens()];
        for(int i = 0; i<array.length; i++) {
            try {
                array[i] = (Integer.parseInt(token.nextToken()));
            } catch (Exception e) {
                try {
                    array[i] = token.nextToken();
                }catch (Exception erro) {
                    return array;
                }
            }
        }
        return array;
    }

    //Compares index with index-1, saves index_element, if second is smaller than first
    //it puts second as first, recursively until it is well placed
    //When it is placed, puts de first index_element in the current index
    public static void InsertionSort(Comparable[] array){
        for(int i = 1; i<array.length; i++){
            int j = i;
            Comparable tmp = array[i];
            while(j>0 && tmp.compareTo(array[j-1])<0){
                array[j] = array[j-1];
                j--;
            }
            array[j]=tmp;
        }
    }


    //heapify's a subtree rooted with node i, from bottom to top
    public static void heapify(Comparable[] array, int size, int i){
        int largest = i; //largest as root
        int left = 2*i+1;
        int right = 2*i+2;

        //if left child is bigger
        if(left<size && array[left].compareTo(array[largest])>0){
            largest = left;
        }
        //if right child is bigger
        if(right<size && array[right].compareTo(array[largest])>0){
            largest = right;
        }

        //if largest not root
        if(largest != i){
            Comparable swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            //recursively heapify
            heapify(array, size, largest);
        }
    }

    public static void HeapSort(Comparable[] array){
        int size = array.length;

        //Build heap
        for(int i = (size/2-1); i>=0; i--){
            heapify(array, size, i);
        }

        //Extract element from heap
        for(int i=size-1; i>=0; i--){
            //Move root to end(heap)
            Comparable tmp = array[0];
            array[0] = array[i];
            array[i] = tmp;

            //call max heapify on new heap
            heapify(array, i, 0);
        }
    }

    public static void printArray(Object[] array){
        int n = array.length;
        for (int i=0; i<n; ++i){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        Comparable[] array = le_array();
        InsertionSort(array);
        HeapSort(array);
        printArray(array);
    }
}
