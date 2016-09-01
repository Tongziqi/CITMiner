/**
 * Created by Administrator on 2015/7/28 0028.
 * Node节点组成的边
 */
public class NodeBranch {
    public int headNode;
    public int tailNode;

    public NodeBranch(int headNode, int tailNode) {
        this.headNode = headNode;
        this.tailNode = tailNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeBranch that = (NodeBranch) o;

        return headNode == that.headNode && tailNode == that.tailNode;

    }

    @Override
    public String toString() {
        return headNode + "," + tailNode;
    }
}
