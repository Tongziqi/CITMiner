import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/21 0021.
 */
public class Node {
    public ArrayList<NodeNumber> nodeNumberArrayList;
    public Node nodeHead;   //节点的头结点
    public String nodenumber;  //节点的数值
    public int timesOfNodes; //出现的次数
    public int weightOfNodes; //节点的权重

    public void addNodeNumberandtime(NodeNumber nodeNumberandtime) {
        nodeNumberArrayList.add(nodeNumberandtime);
    }

    public void addNodeNumberandtimeList(ArrayList<NodeNumber> list) {
        nodeNumberArrayList.addAll(list);
    }

    public void setNodeNumberArrayList(String nodenumber, int weightOfNodes) {
        nodeNumberArrayList.add(0, new NodeNumber(nodenumber, weightOfNodes));
    }

    public ArrayList<NodeNumber> getNodeNumberArrayList() {
        return nodeNumberArrayList;
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
        nodeNumberArrayList = new ArrayList<NodeNumber>();
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
