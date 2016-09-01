import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 对树进行压缩
 * 这里面会遍历一遍所有的数据
 */
public class CompressTree {
    public static ArrayList<Node> compressTreetest(ArrayList<Node> arrayList, int timesYouDefine, ArrayList<ArrayList<NodeNumber>> arrayListsFromCompress) {
        boolean whetherAddHead = true;
        ArrayList<NodeNumber> aNodeNumberArrayList = new ArrayList<NodeNumber>(); //记录被压缩的数据
        for (int i = 0; i < arrayList.size(); i++) {  //这里不用foreach是因为要对arrayList进行删除工作
            if (arrayList.get(i).getTimesOfNodes() >= timesYouDefine) {
                int num = arrayList.get(i).getNodeNumberArrayList().size(); //把压缩链元素个数存起来(有几个压缩链)
                if (whetherAddHead) {
                    aNodeNumberArrayList.addAll(arrayList.get(i).getNodeHead().getNodeNumberArrayList()); //只能添加一次 添加头的信息 每次压缩形成压缩链只有一个头结点 和树没有关系了 已经形成单独的链了
                    whetherAddHead = false;
                }
                for (Node broNode : getBrotherNodes(arrayList.get(i))) {
                    broNode.addWeightOfNodesInNodeNumberArrayList(num);//解决了节点链有兄弟的情况 //不能都加1啊大哥！！！！！！！！！要修改成个数
                }
                for (Node childNode : arrayList.get(i).getArrayListNodeTail()) {
                    arrayList.get(i).getNodeHead().addNodeTail(childNode);  ////添加尾节点
                    childNode.setNodeHead(arrayList.get(i).getNodeHead()); //同时添加父节点
                }
                arrayList.get(i).getNodeHead().addNodeNumberandtimeList(arrayList.get(i).getNodeNumberArrayList()); //改变里面存的值
                aNodeNumberArrayList.addAll(arrayList.get(i).getNodeNumberArrayList()); //这里面记录被压缩的数据
                arrayList.get(i).getNodeHead().delNodeTail(arrayList.get(i));//删除该节点与父节点的关系
                arrayList.remove(arrayList.get(i));
                i--;
            }
        }
        if (aNodeNumberArrayList.size() != 0)
            arrayListsFromCompress.add(aNodeNumberArrayList); //这里添加压缩的链 就是在这次压缩过程中压缩的链
        return arrayList;
    }

    /**
     * 得到一个节点的兄弟节点
     *
     * @param node 需要判断的节点
     * @return 返回该节点的兄弟节点
     */
    public static ArrayList<Node> getBrotherNodes(Node node) {
        ArrayList<Node> nodeArrayList = new ArrayList<Node>();
        for (Node aNode : node.getNodeHead().getArrayListNodeTail()) {
            if (aNode != node) {
                nodeArrayList.add(aNode);
            }
        }
        return nodeArrayList;
    }
}
