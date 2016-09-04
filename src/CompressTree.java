import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 对树进行压缩
 * 这里面会遍历一遍所有的数据
 */
public class CompressTree {

    //能否记录该轮压缩过程中压缩了的节点
    public static ArrayList<NodeNumber> compress(Node node, int timesYouDefine, ArrayList<NodeNumber> arrayListsFromCompress) {
        ArrayList<NodeNumber> aNodeNumberArrayList = new ArrayList<NodeNumber>(); //记录被压缩的数据
        //说明开始压缩
        if (node.getTimesOfNodes() == timesYouDefine) {
            int num = node.getNodeNumberArrayList().size(); //压缩链的个数
            for (Node broNode : getBrotherNodes(node)) {
                broNode.addWeightOfNodesInNodeNumberArrayList(num);//解决了节点链有兄弟的情况
            }
            for (Node childNode : node.getArrayListNodeTail()) {
                int position = node.getNodeHead().getArrayListNodeTail().indexOf(node); //找到它的位置
                node.getNodeHead().addNodeTailInPosition(childNode, position);//在它的位置添加

                childNode.setNodeHead(node.getNodeHead()); //同时添加父节点
            }
            node.getNodeHead().addNodeNumberandtimeList(node.getNodeNumberArrayList()); //改变里面存的值
            node.getNodeHead().delNodeTail(node);//删除该节点与父节点的关系
            aNodeNumberArrayList = node.getNodeHead().getNodeNumberArrayList();//压缩过后,记录最后压缩的链
        }
        ArrayList<Node> tails = node.getArrayListNodeTail();
        int m, n; //m,n控制变量,比较前后孩子节点个数,是否发生删减的情况
        for (int i = 0; i < tails.size(); ) {
            m = tails.size();//原来的节点数
            compress(node.getArrayListNodeTail().get(i), timesYouDefine, arrayListsFromCompress);
            n = tails.size();//现在的节点数
            //m和n发生变化就是发生了删减和孩子节点的添加
            //(1) 没发生删减 n-m=0  i=i+1
            //(2) 删除了一个没有添加 n-m=-1 i不变 i=i+0 i = i+1+(n-m)
            //(3) 删除了一个添加了一个 n-m=-1+1=0 i=i+1 i = i+1+(n-m)
            //(4) 删除了一个添加了两个 n-m=-1+2=1 i=i+2 i = i+1+(n-m)
            //(5) 删除了一个添加了三个 n-m=-1+3=2 i=i+3 i = i+1+(n-m)
            //(x) 删除了一个添加了x个 n-m=-1+x=x-1 i=i+x i = i+1+(n-m)
            i = i + 1 + (n - m);
        }
        //这个可能存在问题 永远添加的其实是最后一个
        if (aNodeNumberArrayList.size() != 0) {
            arrayListsFromCompress.clear();
            arrayListsFromCompress.addAll(aNodeNumberArrayList);
        }

        return arrayListsFromCompress;
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
