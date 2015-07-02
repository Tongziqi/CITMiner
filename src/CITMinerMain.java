import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2015/6/21 0021.
 * CITMiner算法 童小托
 */
public class CITMinerMain {
    protected ArrayList<Node> alistFromCutting = new ArrayList<Node>();

    public static void main(String[] args) throws IOException {
        CITMinerMain citMinerMain = new CITMinerMain();

        File filePath = new File("D:" + File.separator + "test.txt");
        String allNodes = citMinerMain.getNodes(filePath);
        Node[][] nodes = citMinerMain.getNodeListWithHeadAndTailNodes(citMinerMain.getNodesListFromText(allNodes));
        nodes = citMinerMain.makeCompresssionTree(nodes);
//        System.out.println("父节点是:" + nodes[0][8].getNodeHead().getNodenumber());
//        System.out.println("出现的次数是:" + nodes[0][8].getTimesOfNodes());
//        System.out.println("权重是:" + nodes[0][8].getWeightOfNodes());
//        System.out.println("尾节点是:" + nodes[0][0].getArrayListNodeTail().toString());
//        System.out.println("第一颗子树的分子数:" + citMinerMain.getHeadTailFromCutting(nodes[1], 2).toString());
        ArrayList<Node> arrayList = citMinerMain.getNewTreeFromCutting(citMinerMain.getHeadTailFromCutting(nodes[1], 2), 2);
        System.out.println("压缩后的结果:" + citMinerMain.compressTreetest(arrayList, 3).toString());
        System.out.println("剪切后的序列是:" + arrayList.toString());


    }


    /**
     * 文件读取（内存映射方式）
     *
     * @param filePath 文件的路径
     * @return 返回一个String类型的字符串
     * @throws IOException 抛出异常
     */
    public String getNodes(File filePath) throws IOException {
        FileInputStream in = new FileInputStream(filePath);
        FileChannel chan = in.getChannel();
        MappedByteBuffer buf = chan.map(FileChannel.MapMode.READ_ONLY, 0, filePath.length());
        byte[] b = new byte[(int) filePath.length()];
        int len = 0;
        while (buf.hasRemaining()) {
            b[len] = buf.get();
            len++;
        }
        chan.close();
        in.close();
        return new String(b, 0, len, "GBK");
    }


    /**
     * 把文件里面的数字组成一个二维Node链表
     *
     * @param nodesFronText 表示从文件里面读取的数值
     * @return 一个二维Node链表
     */
    public Node[][] getNodesListFromText(String nodesFronText) {
        String lineNumbersOfArray[] = nodesFronText.split("\n");
        int arrayListnumbers = lineNumbersOfArray.length;
        Node nodesList[][] = new Node[arrayListnumbers][];
        for (int i = 0; i < arrayListnumbers; i++) {
            String numbersList[] = lineNumbersOfArray[i].split(" |\r");
            nodesList[i] = new Node[numbersList.length];

            for (int j = 0; j < numbersList.length; j++) {
                nodesList[i][j] = new Node(Integer.valueOf(numbersList[j]));  //每一个Node不仅仅是一个数值而是一个对象
            }
        }
        return nodesList;
    }

    /**
     * nodesnodes
     * 将获得的二维Node链表转换成一个带头结点和尾节点的二维Node链表
     *
     * @param nodes 获得的二维Node链表
     * @return 一个带头结点和尾节点的二维Node链表
     */
    public Node[][] getNodeListWithHeadAndTailNodes(Node[][] nodes) {
        Node nodesList[][] = new Node[nodes.length][];
        Node defaultHeadNode = new Node(-3);  //默认头结点为-3
        Node defaultTailNode = new Node(-4);  //默认尾节点为-4
        Node firstNodeHeadNode = new Node(-2); //默认第一个结点的头结点为-2

        //初始化Node,完成两个工作，第一：去掉前三个节点；第二：每个节点添加默认的头节点
        for (int i = 0; i < nodes.length; i++) {
            nodesList[i] = new Node[nodes[i].length - 3];
            for (int j = 0; j < nodes[i].length - 3; j++) {
                nodesList[i][j] = nodes[i][j + 3];
                nodesList[i][j].setNodeHead(defaultHeadNode);
                //nodesList[i][j].addNodeTail(defaultTailNode);
                nodesList[i][j].setSequence(j);  //这里新添加了位置，为了方面下面的查找
            }
        }

        for (Node[] aNodesList : nodesList) {
            for (int j = 0; j < aNodesList.length; j++) {
                if (j != aNodesList.length - 1) {
                    if (j == 0) {
                        aNodesList[j].setNodeHead(firstNodeHeadNode); //头结点添加firstNodeHeadNode
                    } else {
                        if (aNodesList[j].getNodenumber() != -1) {
                            aNodesList[j].setNodeHead(aNodesList[j - 1]); //如果后面的数不是-1,直接连接
                            aNodesList[j - 1].addNodeTail(aNodesList[j]);
                        }
                        if (aNodesList[j].getNodenumber() == -1) { //如果是-1,根据-1的次数来判定位置
                            //这里numberOfLayer表示-1出现了几次 出现几次就找几次nodesList[i][j - 1].getNodeHead()
                            int numberOfLayer = 1;
                            for (int k = j + 1; k < aNodesList.length - 1; k++) {
                                if (aNodesList[k].getNodenumber() == -1) {
                                    numberOfLayer++;
                                } else {
                                    break;
                                }
                            }
                            if (j + numberOfLayer != aNodesList.length - 1) { //如果是最后一个位置，即-1时候，默认头结点 排除最后一个位置
                                Node node = aNodesList[j - 1].getNodeHead();
                                //这个循环是为了循环找getNodeHead
                                for (int num = 0; num < numberOfLayer - 1; num++) {
                                    node = node.getNodeHead();
                                }
                                aNodesList[j + numberOfLayer].setNodeHead(node);
                                node.addNodeTail(aNodesList[j + numberOfLayer]);
                                j += numberOfLayer;
                            }
                        }
                    }
                }
            }
        }
        return nodesList;
    }

    /**
     * 构建一颗压缩树，即添加权重和出现的次数
     *
     * @param nodes 原始树
     * @return 带权重和次数的树
     */
    public Node[][] makeCompresssionTree(Node[][] nodes) {
        for (int i = 0; i < nodes.length; i++) {

            nodes[i][0].setWeightOfNodes(0);
            for (int j = 1; j < nodes[i].length; j++) { //从第二个节点开始算起
                if (nodes[i][j].getNodenumber() != -1) {
                    int timesOfNodes = 0;
                    nodes[i][j].setWeightOfNodes(1);
                    for (int m = 0; m < nodes.length; m++) {
                        for (int n = 1; n < nodes[m].length; n++) {
                            if (nodes[i][j].getNodenumber() == nodes[m][n].getNodenumber() &&
                                    nodes[i][j].getNodeHead().getNodenumber() == nodes[m][n].getNodeHead().getNodenumber()) {
                                timesOfNodes++;
                            }
                        }
                    }
                    nodes[i][j].setTimesOfNodes(timesOfNodes);
                } else {
                    nodes[i][j].setTimesOfNodes(0);
                    nodes[i][j].setWeightOfNodes(0);
                }
            }
        }

        return nodes;
    }

    /**
     * 找出所有压缩后可以构建树的节点
     *
     * @param nodes          原始序列
     * @param timesYouDefine 定义的阀值
     * @return 一个ArrayList<Node> 里面放着新生成树的所有头结点 可能一个子树里面会新生成很多新的树 所以不能值返回一个node
     */
    public ArrayList<Node> getHeadTailFromCutting(Node[] nodes, int timesYouDefine) {

        ArrayList<Node> nodesList = new ArrayList<Node>();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getNodenumber() != -1) {
                if (nodes[i].getTimesOfNodes() < timesYouDefine) {
                    if (nodes[i].getArrayListNodeTail().size() >= 1) {
                        Iterator iter = nodes[i].getArrayListNodeTail().iterator();
                        while (iter.hasNext()) {
                            if (((Node) iter.next()).getTimesOfNodes() >= timesYouDefine) {
                                nodesList.add(nodes[i]);
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
     * @return 一颗压缩后的树 但是有问题 如果getHeadTailFromCutting返回了不止一个节点 那么节点构建的树就不止一个 目前先返回一颗树
     */
    public ArrayList<Node> getNewTreeFromCutting(ArrayList<Node> arrayList, int timesYouDefine) {
        ArrayList<Node> nodesList = new ArrayList<Node>();
        Node node = arrayList.get(0);

        addNodeFromTree(node, timesYouDefine);
        nodesList = (ArrayList<Node>) alistFromCutting.clone();
        nodesList.add(0, node);
        alistFromCutting.clear();
        return nodesList;
    }

    /**
     * 迭代添加压缩树中节点
     *
     * @param node           压缩树的头结点（经过处理后得到的）
     * @param timesYouDefine 定义的阀值
     */

    public void addNodeFromTree(Node node, int timesYouDefine) {

        for (int i = 0; i < node.getArrayListNodeTail().size(); i++) {
            if (node.getArrayListNodeTail().get(i).getTimesOfNodes() >= timesYouDefine) {
                alistFromCutting.add(node.getArrayListNodeTail().get(i));
                addNodeFromTree(node.getArrayListNodeTail().get(i), timesYouDefine);

            } else {
                node.getArrayListNodeTail().get(i).getNodeHead().delNodeTail(node.getArrayListNodeTail().get(i));
                i--;
            }
        }
    }

    public Node compressTreetest(ArrayList<Node> arrayList, int timesYouDefine) {
        for (Node aNode : arrayList) {
            if (aNode.getTimesOfNodes() == timesYouDefine) {
                for (Node broNode : getBrotherNodes(aNode)) {
                    broNode.addWeightOfNodes();  //如果大于自定义的阀值，兄弟节点+1
                }
                for (Node childNode : aNode.getArrayListNodeTail()) {
                    aNode.getNodeHead().addNodeTail(childNode);  ////添加尾节点
                }
                aNode.getNodeHead().delNodeTail(aNode);//删除该节点
            }
        }
        return arrayList.get(0);
    }


    /**
     * 得到一个节点的兄弟节点
     *
     * @param node 需要判断的节点
     * @return 返回该节点的兄弟节点
     */
    public ArrayList<Node> getBrotherNodes(Node node) {
        ArrayList<Node> nodeArrayList = new ArrayList<Node>();
        for (Node aNode : node.getNodeHead().getArrayListNodeTail()) {
            if (aNode != node) {
                nodeArrayList.add(aNode);
            }
        }
        return nodeArrayList;
    }
}
