import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 剪切树
 */
public class Cutting {

    /**
     * 找出所有压缩后可以构建树的节点
     * 至里面仅仅遍历了一遍数据即可完成删边和清理工作，比论文的简单.
     *
     * @param nodes          原始序列
     * @param timesYouDefine 定义的阀值
     * @return 一个ArrayList<Node> 里面放着新生成树的所有头结点 可能一个子树里面会新生成很多新的树 所以不能值返回一个node
     */
    public static ArrayList<Node> getHeadTailFromCutting(Node[] nodes, int timesYouDefine) {

        ArrayList<Node> nodesList = new ArrayList<Node>();

        for (Node node : nodes) {
            if (!node.getNodenumber().equals("-1")) {
                if (node.getTimesOfNodes() < timesYouDefine) {
                    if (node.getArrayListNodeTail().size() >= 1) {
                        Iterator iter = node.getArrayListNodeTail().iterator();
                        while (iter.hasNext()) {
                            if (((Node) iter.next()).getTimesOfNodes() >= timesYouDefine) {
                                nodesList.add(node);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return nodesList;
    }

    /**
     * 找到压缩后的子树
     *
     * @param arrayList 传入getHeadTailFromCutting返回的节点
     * @return 剪切过后的所有点
     */
    public static ArrayList<ArrayList<Node>> getNewTreeFromCutting(ArrayList<Node> arrayList, int timesYouDefine, ArrayList<Node> alistFromCutting) {

        ArrayList<ArrayList<Node>> nodesList = new ArrayList<ArrayList<Node>>();
        for (Node aNode : arrayList) {
            addNodeFromTree(aNode, timesYouDefine, alistFromCutting);
            alistFromCutting.add(0, aNode);
            for (Node aNodesList : alistFromCutting) {
                aNodesList.setNodeNumberArrayList(aNodesList.getNodenumber(), aNodesList.getWeightOfNodes());
            }
            nodesList.add((ArrayList<Node>) alistFromCutting.clone()); //这里不能只添加alistFromCutting，因为后面要删掉alistFromCutting
            alistFromCutting.clear(); //默认只有一个alistFromCutting，用完删掉
        }
        return nodesList;
    }

    /**
     * 迭代添加压缩树中节点
     *
     * @param node           压缩树的头结点（经过处理后得到的）
     * @param timesYouDefine 定义的阀值
     */

    public static void addNodeFromTree(Node node, int timesYouDefine, ArrayList<Node> alistFromCutting) {

        for (int i = 0; i < node.getArrayListNodeTail().size(); i++) {
            if (node.getArrayListNodeTail().get(i).getTimesOfNodes() >= timesYouDefine) {
                alistFromCutting.add(node.getArrayListNodeTail().get(i));
                addNodeFromTree(node.getArrayListNodeTail().get(i), timesYouDefine, alistFromCutting);

            } else {
                node.getArrayListNodeTail().get(i).getNodeHead().delNodeTail(node.getArrayListNodeTail().get(i));
                i--;//这里面为什么-1，因为这里面删了一个节点，下一步还有i++, 才能继续从开始节点开始判断（这时候开始节点已经改变了）
            }
        }
    }
}
