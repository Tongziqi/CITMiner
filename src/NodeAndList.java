import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 16/9/4.
 * 记录该轮压缩压缩了哪些Node,又形成了哪个压缩链
 */
public class NodeAndList {
    ArrayList<NodeNumber> nodeNumberArrayList;//压缩链
    ArrayList<Node> nodeArrayList;//压缩了哪些点

    public NodeAndList(ArrayList<NodeNumber> nodeNumberArrayList, ArrayList<Node> nodeArrayList) {
        this.nodeNumberArrayList = nodeNumberArrayList;
        this.nodeArrayList = nodeArrayList;
    }

    public ArrayList<NodeNumber> getNodeNumberArrayList() {
        return nodeNumberArrayList;
    }

    public void setNodeNumberArrayList(ArrayList<NodeNumber> nodeNumberArrayList) {
        this.nodeNumberArrayList = nodeNumberArrayList;
    }

    public ArrayList<Node> getNodeArrayList() {
        return nodeArrayList;
    }

    public void setNodeArrayList(ArrayList<Node> nodeArrayList) {
        this.nodeArrayList = nodeArrayList;
    }

    public boolean isEmpty() {
        if (nodeArrayList.size() == 0 && nodeNumberArrayList.size() == 0) {
            return true;
        }
        return false;
    }
}
