import java.util.LinkedList;
import java.util.List;

/**bs
 * RedBlack Tree implementation for rising
 * Uses sentinel nil values instead of null
 */
public class Rbt {

    public RbNode nil;//sentinel initialized in constructor
    public RbNode root;// initialized in constructor, set to nil

    public enum COLOR {
        RED, BLACK
    }

    //Constructor
    public Rbt() {
        nil = RbNode.nil;
        root = nil;
        root.left = nil;
        root.right = nil;
    }

    public RbNode search(int key) {
        return search(root, key);
    }

    //Simple binary search for parameter key
    private RbNode search(RbNode root, int key) {
        if (root == nil) {
            return null;
        }
        if (root.key.getBuildingNum() == key) {
            return root;
        }
        else if (key < root.key.getBuildingNum()) {
            return search(root.left, key);
        }
        else {
            return search(root.right, key);
        }
    }

    //Returns Nodes between key2 > key > key1
    public List<RbNode> searchInRange(int key1, int key2) {
        List<RbNode> list = new LinkedList<RbNode>();
        searchInRange(root, list, key1, key2);
        return list;
    }

    //Searches for nodes between parameter key1 and parameter key2
    private void searchInRange(RbNode root, List<RbNode> list, int key1, int key2) {
        if (root == nil) {
            return;
        }
        if (key1 < root.key.getBuildingNum()) {
            searchInRange(root.left, list, key1, key2);
        }

        if (key1 <= root.key.getBuildingNum() && key2 >= root.key.getBuildingNum()) {
            list.add(root);
        }

        if (key2 > root.key.getBuildingNum()) {
            searchInRange(root.right, list, key1, key2);
        }
    }

    /*
    Simple right rotation similar to AVL R rotation.
    Updates parent and child values after rotation of required nodes
    */
    private RbNode rightRotate(RbNode a) {


        RbNode b = a.left;
        RbNode ar = a.right;
        RbNode bl = b.left;
        RbNode br = b.right;

        a.left = br;
        if(br != nil) {
            br.parent = a;
        }

        b.parent = a.parent;

        if (a.parent == nil){
            root = b;
        }
        else if (a == a.parent.left){
            a.parent.left = b;
        }
        else {
            a.parent.right = b;
        }
        b.right = a;
        a.parent = b;
        return b;
    }

    /**
     * Simple left rotation, similar to AVL L rotation.
     *      Updates parent and child values after rotation of required nodes
     * @param b
     * @return
     */
    private RbNode leftRotate(RbNode b) {
        RbNode br = b.right;
        RbNode bl = br.left;

        b.right = bl;
        if(bl != nil) {
            bl.parent = b;
        }

        br.parent = b.parent;

        if (b.parent == nil) {
            root = br;
        }
        else if (b == b.parent.left) {
            b.parent.left = br;
        }
        else {
            b.parent.right = br;
        }
        br.left = b;
        b.parent = br;
        return br;
    }

    /**
     * Inserts node into RedBlack Tree
     * @param p
     */
    public void insertNode(RbNode p) {
        p.color = COLOR.RED;
        //Update root if nil
        if (root == nil || root.key == p.key) {
            root = p;
            root.color = COLOR.BLACK;//Root is set as black
            root.parent = nil;
            return;
        }
        insertUtil(root, p);//do simple BST insertion
        insertFix(p);//Fix violations of Red-Black Tree
    }

    /**
     * BST insertion
     * Updates parent and child references
     * @param root
     * @param p
     */
    private void insertUtil(RbNode root, RbNode p) {
        if (p.key.getBuildingNum() < root.key.getBuildingNum()) {
            if (root.left == nil) {
                //Insert here and return
                root.left = p;
                p.parent = root;
            }
            else {
                insertUtil(root.left, p);
            }
        }
        else {
            if (root.right == nil) {
                //Insert here and return
                root.right = p;
                p.parent = root;
            }
            else {
                insertUtil(root.right, p);
            }
        }
    }

    /***
     * Fix violations after insertion
     * @param p
     * Newly inserted RBNode
     */
    private void insertFix(RbNode p) {
        //Color of p is null at this point
        RbNode pp = nil;
        RbNode gp = nil;
        if (p.key == root.key) {
            p.color = COLOR.BLACK;
            return;
        }
        while (root.key != p.key && p.color != COLOR.BLACK && p.parent.color == COLOR.RED){
            pp = p.parent;
            gp = pp.parent;

            //Case Left
            if (pp == gp.left) {
                RbNode y = gp.right;
                //Case when sibling of parent(p's uncle) is red
                if (y != nil && y.color == COLOR.RED) {
                    //Recolor
                    gp.color = COLOR.RED;
                    pp.color = COLOR.BLACK;
                    y.color = COLOR.BLACK;
                    //Update current Node repeat until we reach root
                    p = gp;
                }
                else {
                    //Case when sibling of parent is Black
                    //LRb Case
                    if (pp.right == p) {
                        pp = leftRotate(pp);
                        p = pp.left;
                    }
                    //LLb Case
                    rightRotate(gp);
                    swapColors(pp, gp);
                    p = pp;
                }

            }
            //Case Right (Symmetric to Left)
            else if (pp == gp.right) {
                RbNode u = gp.left;
                //Case when sibling of parent is red
                if (u != nil && u.color == COLOR.RED) {
                    //Color Flip
                    gp.color = COLOR.RED;
                    pp.color = COLOR.BLACK;
                    u.color = COLOR.BLACK;
                    //Update current Node repeat until we reach root
                    p = gp;
                }
                else {
                    //Case when sibling of parent is Black
                    //RLb Case
                    if (pp.left == p){
                        pp = rightRotate(pp);
                        p = pp.right;
                    }
                    //RRb Case
                    leftRotate(gp);
                    swapColors(pp,gp);
                    p = pp;
                }
            }
        }
        root.color = COLOR.BLACK;
    }

    /**
     * Move b in the place of a which is a level above
     * @param a Higher level
     * @param b Lower level
     */
    private void levelUp(RbNode a, RbNode b) {
        if (a.parent == nil) {
            root = b;
        }
        else if (a == a.parent.left) {
            a.parent.left = b;
        }
        else {
            a.parent.right = b;
        }
        b.parent = a.parent;
    }

    /**
     * BST delete if color is red. Otherwise fix violations
     * @param key
     * @return
     */
    public boolean delete(int key) {
        RbNode y = search(root,key);
        if (y == null) {
            return false;
        }
        RbNode v;
        RbNode temp = y;
        COLOR origColor = y.color;

        //If left child is nil
        if (y.left == nil) {
            v = y.right;
            levelUp(y, y.right);//swap with right child

        }

        //If right child is nil
        else if (y.right == nil) {
            v = y.left;
            levelUp(y, y.left);//swap with left child

        }

        //If both children are not nil
        else {
            temp = getMin(y.right);//temp holds leftmost node of right child of y
            origColor = temp.color;
            v = temp.right;
            //temp is root of subtree in the right of y
            if (temp.parent == y) {
                v.parent = temp;
            }
            //level up right of temp
            else {
                levelUp(temp, temp.right);
                temp.right = y.right;
                temp.right.parent = temp;
            }
            levelUp(y, temp);
            temp.left = y.left;
            temp.left.parent = temp;
            temp.color = y.color;
        }
        //If color is black call deleteFix
        if (origColor == COLOR.BLACK) {
            deleteFix(v);
        }
        return true;
    }

    /**
     * remove violations, if any
     * @param py
     */
    private void deleteFix(RbNode py){

        //loop until py is root or py is red
        while(py!=root && py.color == COLOR.BLACK) {
            //If py is left child of ppy
            if(py == py.parent.left) {
                //v holds the ppy's right child
                RbNode v = py.parent.right;

                //if v is red, left rotation is required
                if(v.color == COLOR.RED) {
                    v.color = COLOR.BLACK;
                    py.parent.color = COLOR.RED;
                    leftRotate(py.parent);
                    v = py.parent.right;
                }
                //if v's both children are black, recolor
                if(v.left.color == COLOR.BLACK && v.right.color == COLOR.BLACK) {
                    v.color = COLOR.RED;
                    py = py.parent;
                    continue;
                }
                //if only right child is black, recolor and right rotate
                else if(v.right.color == COLOR.BLACK) {
                    v.left.color = COLOR.BLACK;
                    v.color = COLOR.RED;
                    rightRotate(v);
                    v = py.parent.right;
                }
                //if v's right child is red, recolor and left rotate
                if(v.right.color == COLOR.RED) {
                    v.color = py.parent.color;
                    py.parent.color = COLOR.BLACK;
                    v.right.color = COLOR.BLACK;
                    leftRotate(py.parent);
                    py = root;
                }

                //If py is right child of ppy(symmetric case)
            } else {
                //v holds the ppy's left child
                RbNode v = py.parent.left;

                //if v is red, recolor and right rotate
                if(v.color == COLOR.RED) {
                    v.color = COLOR.BLACK;
                    py.parent.color = COLOR.RED;
                    rightRotate(py.parent);
                    v = py.parent.left;
                }

                //if v's children are both black, change v's color
                if(v.right.color == COLOR.BLACK && v.left.color == COLOR.BLACK) {
                    v.color = COLOR.RED;
                    py = py.parent;
                    continue;
                }
                //if only v's left child is black, recolor and left rotate
                else if(v.left.color == COLOR.BLACK) {
                    v.right.color = COLOR.BLACK;
                    v.color = COLOR.RED;
                    leftRotate(v);
                    v = py.parent.left;
                }
                //if v's left child is red, recolor and right rotate
                if(v.left.color == COLOR.RED) {
                    v.color = py.parent.color;
                    py.parent.color = COLOR.BLACK;
                    v.left.color = COLOR.BLACK;
                    rightRotate(py.parent);
                    py = root;
                }
            }
        }
        //py should be black
        py.color = COLOR.BLACK;
    }

    /**
     * For minimum, get the left most node
     * @param root
     * @return
     */
    private RbNode getMin(RbNode root){
        while (root.left != nil){
            root = root.left;
        }
        return root;
    }

    private void swapColors(RbNode pp, RbNode gp) {
        COLOR temp = pp.color;
        pp.color = gp.color;
        gp.color = temp;
    }

}
