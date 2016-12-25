/******************************************************************************/
/* Nathan Gibson                                                              */
/* Algorithms Oct 16, 2016                                                    */
/* Min heap implementation in java that supports heapsort                    */
/******************************************************************************/
import java.lang.StringBuilder;
import java.lang.reflect.Array;

public class Heap<E extends Comparable<E>>{

    private Object heap[];
    private int numberOfElements;

    /*
        default constructor that creates an empty heap of size six
    */
    public Heap(){
        heap = new Object[6];
        numberOfElements = 0;
    }

    /*
        builds a heap from a pre-existing array of data
    */
    public Heap(E[] array){
        heap = new Object[array.length];
        for(int i = 0; i < array.length; i++){
            heap[i] = array[i];
        }
        numberOfElements = array.length;
        createHeap();
        
    }
    /*
        heapify method that takes an element and moves it up the tree
    */
    private void heapification(int i){
        Object tmp = heap[i];    
        while (i > 0 && ((Comparable)tmp).compareTo(heap[(i - 1) / 2]) < 0)
        {
            heap[i] = heap[ (i - 1) / 2 ];
            i = (i - 1) / 2;
        }                   
        heap[i] = tmp;
    }

    /*
        heapifys the entire tree
    */
    public void createHeap(){
        for (int i = 0; i < numberOfElements; i++) {
            heapification(i);
        }
    }

    /*
        removes and returns the first element in the heap
    */
    public E delete(){
        if (numberOfElements == 0){
            return null;
        }

        Object keyItem = heap[0];
        heap[0] = heap[numberOfElements - 1];
        numberOfElements--;
        createHeap();       
        return (E) keyItem;
    }

    /*
        inserts an element at the bottom of the tree
        and the heapifies it up to where it belongs
    */
    public void insert(E item){
        if(heap.length - 1 == numberOfElements) { doubleSize(); }
        heap[numberOfElements++] = item;
        heapification(numberOfElements - 1);
    }

    /*
        increases the size of the underlying array
    */
    public void doubleSize(){
        Object[] newHeap = new Object[heap.length * 2];
        for(int i = 0; i < heap.length; i++){
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /*
        provides a string representation of our heap
    */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < numberOfElements; i++){
            s.append(i != numberOfElements - 1 ? heap[i] + ", " : heap[i] + "");
        }
        return s.toString();
    }

    /*
        removes elements from the heap, in sorted order and stores them. These elements
        are put into an array and returned.

        had some trouble getting the type system to cooperate with generic arrays, made it so 
        the user provides a type.
    */
    public E[] heapSort(Class<E> c){
        E[] result = (E[]) Array.newInstance(c, numberOfElements);
        int x = numberOfElements;

        for(int i = 0; i < x; i++){
            result[i] = (E) delete();
        }

        for(int i = 0; i < x; i++){
            insert(result[i]);
        }
        return (E[]) result;
    }

    public static void main(String[]args){
        Heap<String> strings = new Heap<String>();
        strings.insert("one");
        strings.insert("two");
        strings.insert("three");
        strings.insert("four");
        strings.insert("five");
        strings.insert("six");

        String[] sortedStrings = strings.heapSort(String.class);
        for(int i = 0; i < sortedStrings.length; i++){
            System.out.print(i != sortedStrings.length - 1 ? sortedStrings[i] + ", " : sortedStrings[i] + "\n");
        }

        Integer[] arr = new Integer[] {1,4,2,3,5,8,6,9,0,7,10,13,11,14,12};    
        Heap<Integer> numbers = new Heap<Integer>(arr);
        Integer[] sortedNumbers = numbers.heapSort(Integer.class);  
        for(int i = 0; i < sortedNumbers.length; i++){
            System.out.print(i != sortedNumbers.length - 1 ? sortedNumbers[i] + ", " : sortedNumbers[i] + "\n");
        }  

        numbers.delete();
        numbers.delete();
        System.out.println(numbers);
    }
}