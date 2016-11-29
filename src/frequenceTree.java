import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 2016/11/29.
 * 获得的静态频繁模式结果
 */
public class FrequenceTree {
    int frequentNum; //出现的频繁度
    ArrayList<NodeBranch> nodeBranches; //具体的压缩链


    public FrequenceTree(int frequentNum, ArrayList<NodeBranch> nodeBranches) {
        this.frequentNum = frequentNum;
        this.nodeBranches = nodeBranches;
    }

}
