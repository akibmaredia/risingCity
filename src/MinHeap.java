/**
 * MinHeap Implementation for scheduler
 * @author Akib
 */
public class MinHeap {

    public int size = 0;//Holds current size of MinHeap
    public HeapNode[] arr;//Array of Nodes

    /**
     * Insert into heap, double if array full
     * @param key: Executed Time of
     */
    public void insert(int key){
        HeapNode p = new HeapNode(key);
        insertUtil(p);
    }

    public void insert(HeapNode p){
        insertUtil(p);
    }

    /**
     * Insert Node p into heap
     * if array full, double it
     * Update size variable
     * @param p
     */
    private void insertUtil(HeapNode p) {
        if (size > 0 && size == arr.length){
            arrDouble();
        }
        int i = size;
        size++;
        arr[i] = p;

        //Percolate up. Find any parent greater than current node and swap
        while(i != 0 && arr[i].key <= getParent(i).key) {
            if(arr[i].key < getParent(i).key || arr[i].rbNode.key < getParent(i).rbNode.key) {
                swap(i, getParentIndex(i));
                i = getParentIndex(i);
            } else {
                break;
            }
        }

        /*while (i != 0 && getParent(i).key >= arr[i].key) {
            if(getParent(i).key > arr[i].key || getParent(i).rbNode.key > arr[i].rbNode.key) {
                swap(i, getParentIndex(i));
                i = getParentIndex(i);
            } else {
                break;
            }
        }*/
    }

    /**
     * Find any node greater than one of its child and swap
     * @param i index
     */
    private void heapify(int i) {
        int l = i*2 + 1;//left index
        int r = i*2 + 2;//right index

        //replace ith with smallest among left child and right child
        int smallest = i;
        if(l < size && arr[i].key >= arr[l].key && (arr[i].key > arr[l].key || arr[i].rbNode.key > arr[l].rbNode.key)) {
            smallest = l;
        }
        if(r < size && arr[smallest].key >= arr[r].key && (arr[smallest].key > arr[r].key || arr[smallest].rbNode.key > arr[r].rbNode.key)) {
            smallest = r;
        }

        if (smallest != i && arr[i].key >= arr[smallest].key &&
                (arr[i].key > arr[smallest].key || arr[i].rbNode.key > arr[smallest].rbNode.key)) {
            swap(i, smallest);
            heapify(smallest);
        }

//        if (l < size && arr[i].key >= arr[i].key && (arr[i].key > arr[l].key || arr[i].rbNode.key > arr[])) {
//            smallest = l;
//        }
//        if (r < size && arr[r].key <= arr[smallest].key && arr[r].rbNode.key < arr[smallest].rbNode.key){
//            smallest = r;
//        }
//        if (smallest != i){
//            swap(i,smallest);
//            heapify(smallest);//recur if a swap occurs
//        }

    }

    /**
     * Returns first element of array, which is always minimum of all
     * Put last element to root(first position) and heapify
     * @return Min
     */
    public HeapNode extractMin(){
        if (size == 1){
            HeapNode min = arr[0];
            size--;
            arr[0] = null;
            return min;
        }

        HeapNode min = arr[0];
        arr[0] = arr[size-1];
        arr[size-1] = null;
        size--;
        heapify(0);

        return min;
    }

    private void swap(int i, int j) {
        HeapNode temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private HeapNode getParent(int i){
        return arr[getParentIndex(i)];
    }

    private int getParentIndex(int i){
        return (i - 1) / 2;
    }

    /**
     * Create a new temp array of double current value and set reference to original array
     */
    private void arrDouble() {
        HeapNode[] temp = new HeapNode[arr.length*2];
        System.arraycopy(arr, 0, temp, 0, arr.length);
        arr = temp;
    }

    public MinHeap(){
        arr = new HeapNode[1];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    //Used for testing purposes only
//    public static void main(String[] args) {
//        /*MinHeap heap = new MinHeap();
//        heap.insert(0);
//        heap.insert(5);
//        heap.insert(0);
//        heap.insert(5);
//        heap.extractMin();
//        print(heap);*/
//    }
}