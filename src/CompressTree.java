import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 对树进行压缩
 * 这里面会遍历一遍所有的数据
 */
public class CompressTree {
    @Deprecated
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
