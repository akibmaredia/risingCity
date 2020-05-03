/**
 * MinHeap Implementation for risingCity
 */
public class MinHeap {

    public int size = 0;//Holds size of MinHeap
    public HeapNode[] arr;//Array of Nodes


    public void insert(HeapNode p) {
        insertUtil(p);
    }

    /**
     * Insert node into heap
     * if array is full, double the array
     * update size
     * @param p
     */
    private void insertUtil(HeapNode p) {
        if (size > 0 && size == arr.length){
            arrDouble();
        }
        int i = size;
        size++;
        arr[i] = p;

        //Find any parent greater than current node and swap
        while(i != 0 && arr[i].key.getExecutedTime() <= getParent(i).key.getExecutedTime()) {
            if(arr[i].key.getExecutedTime() < getParent(i).key.getExecutedTime() || arr[i].rbNode.key.getBuildingNum() < getParent(i).rbNode.key.getBuildingNum()) {
                swap(i, getParentIndex(i));
                i = getParentIndex(i);
            }
            else {
                break;
            }
        }
    }

    /**
     * find invalid order and swap
     * @param i index
     */
    private void heapify(int i) {
        int l = i*2 + 1;//left index
        int r = i*2 + 2;//right index

        //replace ith with smaller among left child and right child
        int smaller = i;
        if(l < size && arr[i].key.getExecutedTime() >= arr[l].key.getExecutedTime() && (arr[i].key.getExecutedTime() > arr[l].key.getExecutedTime() || arr[i].rbNode.key.getBuildingNum() > arr[l].rbNode.key.getBuildingNum())) {
            smaller = l;
        }
        if(r < size && arr[smaller].key.getExecutedTime() >= arr[r].key.getExecutedTime() && (arr[smaller].key.getExecutedTime() > arr[r].key.getExecutedTime() || arr[smaller].rbNode.key.getBuildingNum() > arr[r].rbNode.key.getBuildingNum())) {
            smaller = r;
        }

        if (smaller != i && arr[i].key.getExecutedTime() >= arr[smaller].key.getExecutedTime() &&
                (arr[i].key.getExecutedTime() > arr[smaller].key.getExecutedTime() || arr[i].rbNode.key.getBuildingNum() > arr[smaller].rbNode.key.getBuildingNum())) {
            swap(i, smaller);
            heapify(smaller);
        }
    }

    /**
     * remove and return the min in the heap which is the root node
     * Put last element to root(first position) and heapify
     * @return Min
     */
    public HeapNode extractMin() {
        if (size == 1) {
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

    private HeapNode getParent(int i) {
        return arr[getParentIndex(i)];
    }

    private int getParentIndex(int i) {
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

    public MinHeap() {
        arr = new HeapNode[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
