import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/21 0021.
 * Node的数据结构
 */
public class Node {
    public ArrayList<NodeNumber> nodeNumberArrayList;
    public Node nodeHead;   //节点的头结点
    public String nodenumber;  //节点的数值
    public int timesOfNodes; //出现的次数
    public int weightOfNodes; //节点的权重
    public ArrayList<Node> arrayListNodeTail = new ArrayList<Node>(); //孩子们

    public Node(String nodenumber) {

        this.nodenumber = nodenumber;
        nodeNumberArrayList = new ArrayList<NodeNumber>();
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

    public void addWeightOfNodesInNodeNumberArrayList(int num) {  //不能都加1啊大哥！！！！！！！！！
        for (NodeNumber aNodeNumber : nodeNumberArrayList) {
            aNodeNumber.addWeightOfNodes(num);
        }
    }

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

    public void addNodeTailInPosition(Node nodeTail, int position) {
        arrayListNodeTail.add(position, nodeTail);
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

    public Node getNodeHead() {
        return nodeHead;
    }

    public void setNodeHead(Node nodeHead) {
        this.nodeHead = nodeHead;
    }

    public String getNodenumber() {
        return nodenumber;
    }
}
