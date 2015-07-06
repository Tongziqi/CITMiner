import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/21 0021.
 */
public class Node {
    public NodeNumber nodeNumberandtime;
    public Node nodeHead;   //节点的头结点
    public String nodenumber;  //节点的数值
    public int timesOfNodes; //出现的次数
    public int weightOfNodes; //节点的权重
    public int sequence; //在链表中的位置 方便剪切查找


    public NodeNumber getNodeNumberandtime() {
        return nodeNumberandtime;
    }

    public void setNodeNumberandtime(String nodenumber, int weightOfNodes) {
        nodeNumberandtime.setNodenumber(nodenumber);
        nodeNumberandtime.setWeightOfNodes(weightOfNodes);
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }


    public ArrayList<Node> arrayListNodeTail = new ArrayList<Node>();


    public ArrayList<Node> getArrayListNodeTail() {
        return arrayListNodeTail;
    }

    @Override
    public String toString() {
        return "Node" + nodenumber;
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


    public Node(String nodenumber) {

        this.nodenumber = nodenumber;
        nodeNumberandtime = new NodeNumber(nodenumber, 0);
    }

    public void setNodeHead(Node nodeHead) {
        this.nodeHead = nodeHead;
    }


    public String getNodenumber() {
        return nodenumber;
    }

    public void setNodenumber(String nodenumber) {
        this.nodenumber = nodenumber;
    }
}
