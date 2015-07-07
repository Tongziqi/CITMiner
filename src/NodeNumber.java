/**
 * Created by Administrator on 2015/7/6 0006.
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

    public void setNodenumber(String nodenumber) {
        this.nodenumber = nodenumber;
    }

    public int getWeightOfNodes() {
        return weightOfNodes;
    }

    public void setWeightOfNodes(int weightOfNodes) {
        this.weightOfNodes = weightOfNodes;
    }

    @Override
    public String toString() {
        return nodenumber + '\'' + weightOfNodes;
    }
}
