/**
 * Node object has left, right and parent object references
 */
public class RbNode {

    public static final RbNode nil = new RbNode(-999, Rbt.COLOR.BLACK);//sentinel

    public int key; //Unique buildingNum
    public int totalTime;//Total time for building
    public RbNode left = nil;
    public RbNode right = nil;
    public RbNode parent = nil;
    public Rbt.COLOR color;//RED/Black color
    public HeapNode heapNode;//Object reference to heapNode in MinHeap

    //Constructors:
    public RbNode(int key, HeapNode heapNode){
        this.key = key;
        this.heapNode = heapNode;
    }

    public RbNode(int key){
        this.key = key;
    }

    public RbNode(int key, Rbt.COLOR color){
        this.key = key;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Key:"+this.key+",Color:"+this.color.name();
    }
}