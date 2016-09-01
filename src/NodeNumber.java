/**
 * Created by Administrator on 2015/7/6 0006.
 * Node节点上的number
 */
public class NodeNumber {
    public String nodenumber;
    public int weightOfNodes;

    public NodeNumber(String nodenumber, int weightOfNodes) {
        this.nodenumber = nodenumber;
        this.weightOfNodes = weightOfNodes;
    }

    public String getNodenumber() {
        return nodenumber;
    }


    public int getWeightOfNodes() {
        return weightOfNodes;
    }


    public void addWeightOfNodes(int num) {
        weightOfNodes = weightOfNodes + num;
    }

    @Override
    public String toString() {
        return nodenumber + '\'' + weightOfNodes;
    }
}
