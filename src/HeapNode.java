
public class HeapNode {

    public BuildingNode key;//executed time
    public RbNode rbNode;//Object reference of RbNode

    //Constructor
    public HeapNode(BuildingNode k) {
        this.key = k;
    }

    public String toString() {
        return Integer.toString(key.getExecutedTime());
    }
}