import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Administrator on 2015/6/21 0021.
 * CITMiner算法 童小托
 */
public class CITMinerMain {
    protected ArrayList<Node> alistFromCutting = new ArrayList<Node>();
    private ArrayList<Integer> frequentlyList = new ArrayList<Integer>();
    private static int vauleYouDefine = 2;


    public static void main(String[] args) throws IOException {
        String fileName = "D:/输出的结果.txt";
        CITMinerMain citMinerMain = new CITMinerMain();
        long timeMillis = System.currentTimeMillis();  // 记录系统开始的时间

        File filePath = new File("D:" + File.separator + "test.txt");
        String allNodes = citMinerMain.getNodes(filePath);

        Node[][] nodes = citMinerMain.getNodeListWithHeadAndTailNodes(citMinerMain.getNodesListFromText(allNodes));

        nodes = citMinerMain.makeCompresssionTree(nodes);

        Collections.sort(citMinerMain.frequentlyList);
        Collections.reverse(citMinerMain.frequentlyList);

        for (int i = 0; i < nodes.length; i++) {
            ArrayList<Node> arrayListHeadNodes = citMinerMain.getHeadTailFromCutting(nodes[i], vauleYouDefine);
            if (arrayListHeadNodes.size() >= 1) {
                ArrayList<Node> arrayList = citMinerMain.getNewTreeFromCutting(arrayListHeadNodes, vauleYouDefine);
                writeNodes(fileName, "剪切后第" + i + "的序列是:" + arrayList.toString() + "\n");
                //开始进行压缩,具体压缩的阀值由出现的最大频率来决定
                //压缩所有频繁度
                for (int j = 0; j < citMinerMain.frequentlyList.size(); j++) {
                    citMinerMain.compressTreetest(arrayList, citMinerMain.frequentlyList.get(j));
                }
                if (arrayList.get(0).getNodeNumberArrayList().size() <= 1) {
                    writeNodes(fileName, "没有符合条件的压缩链" + "\n");
                } else
                    writeNodes(fileName, "压缩链是:" + arrayList.get(0).getNodeNumberArrayList().toString() + "\n");
            } else
                writeNodes(fileName, "因为设置的阀值过大,第" + i + "序列不符合的压缩序列" + "\n");
        }
        writeNodes(fileName, "\n" + "\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");

        System.out.println("\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
        System.out.print("\n" + "-----------------------结束-------------------------");
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

    public static void writeNodes(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                nodesList[i][j] = new Node(String.valueOf(numbersList[j]));  //每一个Node不仅仅是一个数值而是一个对象
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
        Node defaultHeadNode = new Node("-3");  //默认头结点为-3
        Node firstNodeHeadNode = new Node("-2"); //默认第一个结点的头结点为-2

        //初始化Node,完成两个工作，第一：去掉前三个节点；第二：每个节点添加默认的头节点
        for (int i = 0; i < nodes.length; i++) {
            nodesList[i] = new Node[nodes[i].length - 3];
            for (int j = 0; j < nodes[i].length - 3; j++) {
                nodesList[i][j] = nodes[i][j + 3];
                nodesList[i][j].setNodeHead(defaultHeadNode);
            }
        }

        for (Node[] aNodesList : nodesList) {
            for (int j = 0; j < aNodesList.length; j++) {
                if (j != aNodesList.length - 1) {
                    if (j == 0) {
                        aNodesList[j].setNodeHead(firstNodeHeadNode); //头结点添加firstNodeHeadNode
                    } else {
                        if (!aNodesList[j].getNodenumber().equals("-1")) {
                            aNodesList[j].setNodeHead(aNodesList[j - 1]); //如果后面的数不是-1,直接连接
                            aNodesList[j - 1].addNodeTail(aNodesList[j]);
                        }
                        if (aNodesList[j].getNodenumber().equals("-1")) { //如果是-1,根据-1的次数来判定位置
                            //这里numberOfLayer表示-1出现了几次 出现几次就找几次nodesList[i][j - 1].getNodeHead()
                            int numberOfLayer = 1;
                            for (int k = j + 1; k < aNodesList.length - 1; k++) {
                                if (aNodesList[k].getNodenumber().equals("-1")) {
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
            Boolean frequentBoolean = false;
            nodes[i][0].setWeightOfNodes(0);
            for (int j = 1; j < nodes[i].length; j++) { //从第二个节点开始算起
                if (!nodes[i][j].getNodenumber().equals("-1")) {
                    int timesOfNodes = 0;
                    nodes[i][j].setWeightOfNodes(1);
                    for (int m = 0; m < nodes.length; m++) {
                        for (int n = 1; n < nodes[m].length; n++) {
                            if (nodes[i][j].getNodenumber().equals(nodes[m][n].getNodenumber()) &&
                                    nodes[i][j].getNodeHead().getNodenumber().equals(nodes[m][n].getNodeHead().getNodenumber())) {
                                timesOfNodes++;
                            }
                        }
                    }
                    nodes[i][j].setTimesOfNodes(timesOfNodes);

                    if (frequentlyList.size() != 0) {
                        for (Integer aFrequentlyList : frequentlyList) {
                            if (aFrequentlyList == timesOfNodes)
                                frequentBoolean = true;
                        }
                    }
                    if (!frequentBoolean) {
                        if (timesOfNodes >= vauleYouDefine)
                            frequentlyList.add(timesOfNodes);
                    }


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
            if (!nodes[i].getNodenumber().equals("-1")) {
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
        for (Node aNodesList : nodesList) {
            aNodesList.setNodeNumberArrayList(aNodesList.getNodenumber(), aNodesList.getWeightOfNodes());
        }
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

    /**
     * 对树进行压缩
     *
     * @param arrayList      剪切后的树
     * @param timesYouDefine 定义压缩的阀值
     * @return 应该返回一个字符串
     */
    public ArrayList<Node> compressTreetest(ArrayList<Node> arrayList, int timesYouDefine) {

        for (int i = 0; i < arrayList.size(); i++) {  //这里不用foreach是因为要对arrayList进行删除工作
            if (arrayList.get(i).getTimesOfNodes() >= timesYouDefine) {
                for (Node broNode : getBrotherNodes(arrayList.get(i))) {
                    broNode.addWeightOfNodes();  //如果大于自定义的阀值，兄弟节点+1
                    broNode.getNodeNumberArrayList().set(0, new NodeNumber(broNode.getNodenumber(), broNode.weightOfNodes));
                }
                for (Node childNode : arrayList.get(i).getArrayListNodeTail()) {
                    arrayList.get(i).getNodeHead().addNodeTail(childNode);  ////添加尾节点
                    childNode.setNodeHead(arrayList.get(i).getNodeHead()); //同时添加父节点
                }
                arrayList.get(i).getNodeHead().addNodeNumberandtimeList(arrayList.get(i).getNodeNumberArrayList());
                arrayList.get(i).getNodeHead().delNodeTail(arrayList.get(i));//删除该节点与父节点的关系
                arrayList.remove(arrayList.get(i));
                i--;
            }
        }

        return arrayList;
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
