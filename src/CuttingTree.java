import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 剪切树的新方法
 */
public class CuttingTree {

    /**
     * 返回剪切后的树的头节点
     *
     * @param node           原头节点
     * @param timesYouDefine 阀值
     * @param nodeList       存储头节点的列表
     * @return 存储头节点的列表
     */
    public static ArrayList<Node> getTreeFromCutting(Node node, int timesYouDefine, ArrayList<Node> nodeList) {
        node.setNodeNumberArrayList(node.getNodenumber(), node.getWeightOfNodes());
        if (node.getTimesOfNodes() < timesYouDefine) {
            node.getNodeHead().delNodeTail(node);//删除
        }
        if (node.getTimesOfNodes() < timesYouDefine && node.getArrayListNodeTail().size() > 0) {
            nodeList.add(node);
        }

        ArrayList<Node> tails = node.getArrayListNodeTail();
        int m, n;//m,n控制变量,比较前后孩子节点个数,是否发生删减的情况
        for (int i = 0; i < tails.size(); ) {
            m = tails.size();
            getTreeFromCutting(tails.get(i), timesYouDefine, nodeList);
            n = tails.size();
            if (m == n) { //没有发生删除的情况,i++,如果发生删除的情况,不变还是i
                i++;
            }
        }

        return nodeList;
    }

    /**
     * 获得所有剪切后的树的头节点
     */
    public static ArrayList<Node> getAllTreeFromCutting(Node[] nodes, ArrayList<Node> nodeList) {
        for (Node node : nodes) {
            CuttingTree.getTreeFromCutting(node, DefaultSetting.vauleYouDefine, nodeList);
        }
        return nodeList;
    }

    public static ArrayList<Node> cleanTree(ArrayList<Node> nodeList) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getArrayListNodeTail().size() == 0) {
                nodeList.remove(i);
                i--;
            }
        }
        return nodeList;
    }


}
