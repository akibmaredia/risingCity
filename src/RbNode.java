/**
 * Node object has left, right and parent object references
 */
public class RbNode {

    public static final RbNode nil = new RbNode(new BuildingNode(-999,0,0), Rbt.COLOR.BLACK);//sentinel

    public BuildingNode key; //Unique buildingNum
    public RbNode left = nil;
    public RbNode right = nil;
    public RbNode parent = nil;
    public Rbt.COLOR color;//RED/Black color
    public HeapNode heapNode;//Object reference to heapNode in MinHeap

    //Constructors:
    public RbNode(BuildingNode key, HeapNode heapNode) {
        this.key = key;
        this.heapNode = heapNode;
    }

    public RbNode(BuildingNode key) {
        this.key = key;
    }

    public RbNode(BuildingNode key, Rbt.COLOR color) {
        this.key = key;
        this.color = color;
    }
}