import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 2016/11/29.
 * 获得的静态频繁模式结果
 */
public class FrequenceTree {
    double frequentNum; //出现的频繁度
    ArrayList<NodeBranch> nodeBranches; //具体的压缩链


    public FrequenceTree(double frequentNum, ArrayList<NodeBranch> nodeBranches) {
        this.frequentNum = frequentNum;
        this.nodeBranches = nodeBranches;
    }

    public double getFrequentNum() {
        return frequentNum;
    }

    public void setFrequentNum(double frequentNum) {
        this.frequentNum = frequentNum;
    }

    public ArrayList<NodeBranch> getNodeBranches() {
        return nodeBranches;
    }

    public void setNodeBranches(ArrayList<NodeBranch> nodeBranches) {
        this.nodeBranches = nodeBranches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrequenceTree that = (FrequenceTree) o;

        return !(nodeBranches != null ? !nodeBranches.equals(that.nodeBranches) : that.nodeBranches != null);

    }

    public boolean contain(FrequenceTree frequenceTree) {
        for (NodeBranch nodeBranch : frequenceTree.nodeBranches
                ) {
            if (!this.nodeBranches.contains(nodeBranch)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FrequenceTree{" +
                "nodeBranches=" + nodeBranches +
                '}';
    }
}
