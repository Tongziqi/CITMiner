import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/21 0021.
 */
public class Node {
    public int nodenumber;  //节点的数值
    public int timesOfNodes; //出现的次数
    public int weightOfNodes; //节点的权重
    public int sequence; //在链表中的位置 方便剪切查找
    public String nodeStringNumber; //压缩后的字符串

    public String getNodeStringNumber() {
        return nodeStringNumber;
    }

    public void setNodeStringNumber(String nodeStringNumber) {
        this.nodeStringNumber = nodeStringNumber;
    }

    public void addNodeStringNumber(String addnodeStringNumber) {
        nodeStringNumber = nodeStringNumber + " " + addnodeStringNumber;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Node nodeHead;   //节点的头结点

    public ArrayList<Node> arrayListNodeTail = new ArrayList<Node>();


    public ArrayList<Node> getArrayListNodeTail() {
        return arrayListNodeTail;
    }

    @Override
    public String toString() {
        return "Node{" + +nodenumber + "}";
    }


    public void addNodeTail(Node nodeTail) {
        arrayListNodeTail.add(nodeTail);
    }

    public void delNodeTail(Node nodeTail) {
        arrayListNodeTail.remove(nodeTail);
    }

    public int getTimesOfNodes() {
        return timesOfNodes;
    }

    public void setTimesOfNodes(int timesOfNodes) {
        this.timesOfNodes = timesOfNodes;
    }

    public int getWeightOfNodes() {
        return weightOfNodes;
    }

    public void setWeightOfNodes(int weightOfNodes) {
        this.weightOfNodes = weightOfNodes;
    }

    public void addWeightOfNodes() {
        this.weightOfNodes = weightOfNodes + 1;
    }


    public Node getNodeHead() {
        return nodeHead;
    }


    public Node(int nodenumber) {
        this.nodenumber = nodenumber;
    }

    public void setNodeHead(Node nodeHead) {
        this.nodeHead = nodeHead;
    }


    public int getNodenumber() {
        return nodenumber;
    }

    public void setNodenumber(int nodenumber) {
        this.nodenumber = nodenumber;
    }
}
