/**
 * Created by Administrator on 2015/7/28 0028.
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

        if (headNode != that.headNode) return false;
        if (tailNode != that.tailNode) return false;

        return true;
    }

    public int getHeadNode() {
        return headNode;
    }

    public void setHeadNode(int headNode) {
        this.headNode = headNode;
    }

    public int getTailNode() {
        return tailNode;
    }

    public void setTailNode(int tailNode) {
        this.tailNode = tailNode;
    }

    @Override
    public String toString() {
        return headNode + "," + tailNode;
    }
}
