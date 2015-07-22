import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2015/6/21 0021.
 * CITMiner算法 童小托
 */
public class CITMinerMain {
    protected ArrayList<Node> alistFromCutting = new ArrayList<Node>(); // 这里控制仅仅有一NodeList,便于添加数据
    private ArrayList<Integer> frequentlyList = new ArrayList<Integer>();  //这里记录所有出现的频繁度，只压缩出现的频繁度
    private static int vauleYouDefine = 2;


    public static void main(String[] args) throws IOException {
        String fileName = "D:/输出的结果.txt";
        CITMinerMain citMinerMain = new CITMinerMain();
        long timeMillis = System.currentTimeMillis();  // 记录系统开始的时间
        File filePath = new File("D:" + File.separator + "test.txt");
        String allNodes = citMinerMain.getNodes(filePath);

        Node[][] nodes = citMinerMain.getNodesListFromText(allNodes);
        nodes = citMinerMain.makeCompressTree(nodes);

        Collections.sort(citMinerMain.frequentlyList);
        Collections.reverse(citMinerMain.frequentlyList);

        for (int i = 0; i < nodes.length; i++) {
            ArrayList<Node> arrayListHeadNodes = citMinerMain.getHeadTailFromCutting(nodes[i], vauleYouDefine);
            if (arrayListHeadNodes.size() >= 1) {
                ArrayList<Node> arrayList = citMinerMain.getNewTreeFromCutting(arrayListHeadNodes, vauleYouDefine).get(0);
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
        writeNodes(fileName, "\r----------------------------执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 " + "\n" + "\r");
        System.out.println("\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
        //System.out.println("\r该步骤执行耗时 : " + citMinerMain.timeMillis + " 毫秒 ");
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
     * @return 一个带头结点和尾节点的二维Node链表
     */
    public Node[][] getNodesListFromText(String nodesFronText) {
        Node firstNodeHeadNode = new Node("-2"); //默认第一个结点的头结点为-2
        Node defaultHeadNode = new Node("-3");  //默认头结点为-3

        String lineNumbersOfArray[] = nodesFronText.split("\n");
        int arrayListnumbers = lineNumbersOfArray.length;
        Node nodesList[][] = new Node[arrayListnumbers][];  //控制行数

        for (int i = 0; i < arrayListnumbers; i++) {
            String numbersList[] = lineNumbersOfArray[i].split(" |\r");  //一行有几个数
            nodesList[i] = new Node[numbersList.length - 3]; //每行去掉前三个数

            for (int j = 0; j < numbersList.length - 3; j++) {
                nodesList[i][j] = new Node(String.valueOf(numbersList[j + 3]));  //每一个Node不仅仅是一个数值而是一个对象
                nodesList[i][j].setNodeHead(defaultHeadNode);
                //合并功能
                if (j != nodesList[i].length - 1) {
                    if (j == 0) {
                        nodesList[i][j].setNodeHead(firstNodeHeadNode); //头结点添加firstNodeHeadNode
                    } else {
                        if (!nodesList[i][j].getNodenumber().equals("-1")) {
                            nodesList[i][j].setNodeHead(nodesList[i][j - 1]); //如果后面的数不是-1,直接连接
                            nodesList[i][j - 1].addNodeTail(nodesList[i][j]);
                        }
                        if (nodesList[i][j].getNodenumber().equals("-1")) { //如果是-1,根据-1的次数来判定位置
                            //这里numberOfLayer表示-1出现了几次 出现几次就找几次nodesList[i][j - 1].getNodeHead()
                            int numberOfLayer = 1;
                            for (int k = j + 1; k < nodesList[i].length - 1; k++) {
                                if (numbersList[k + 3].equals("-1")) {
                                    numberOfLayer++;
                                } else {
                                    break;
                                }
                            }
                            if (j + numberOfLayer != nodesList[i].length - 1) { //如果是最后一个位置，即-1时候，默认头结点 排除最后一个位置
                                Node node = nodesList[i][j - 1].getNodeHead();
                                //这个循环是为了循环找getNodeHead
                                for (int num = 1; num < numberOfLayer; num++) {
                                    node = node.getNodeHead();
                                    nodesList[i][j + num] = new Node(String.valueOf(numbersList[j + 3 + num])); //这里新建跳过的-1节点
                                }
                                nodesList[i][j + numberOfLayer] = new Node(String.valueOf(numbersList[j + 3 + numberOfLayer]));
                                nodesList[i][j + numberOfLayer].setNodeHead(node);
                                node.addNodeTail(nodesList[i][j + numberOfLayer]);
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
    public Node[][] makeCompressTree(final Node[][] nodes) {
        int defaultValue = 1;
        Boolean frequentBoolean = false;
        HashMap<ArrayList<Integer>, Integer> hashMap = new HashMap<ArrayList<Integer>, Integer>();
        //第一次循环 找出所有的频繁次数
        for (Node[] node : nodes) {
            for (int j = 1; j < node.length; j++) {
                if (!node[j].getNodenumber().equals("-1")) {
                    ArrayList<Integer> arrayListKey = new ArrayList<Integer>();
                    arrayListKey.add(Integer.valueOf(node[j].getNodenumber()));
                    arrayListKey.add(Integer.valueOf(node[j].getNodeHead().getNodenumber()));
                    if (hashMap.containsKey(arrayListKey)) {
                        hashMap.replace(arrayListKey, hashMap.get(arrayListKey) + 1);
                    } else hashMap.put(arrayListKey, defaultValue);
                }
            }
        }
        //第二次循环 为Node[][]更新压缩树的边频繁度值
        for (Node[] node : nodes) {
            node[0].setWeightOfNodes(0);
            node[0].setTimesOfNodes(0);
            for (int n = 1; n < node.length; n++) { //从第二个节点开始算起
                node[n].setWeightOfNodes(1);
                if (!node[n].getNodenumber().equals("-1")) {
                    ArrayList<Integer> arrayListKey = new ArrayList<Integer>();
                    arrayListKey.add(Integer.valueOf(node[n].getNodenumber()));
                    arrayListKey.add(Integer.valueOf(node[n].getNodeHead().getNodenumber()));
                    if (hashMap.containsKey(arrayListKey)) {
                        node[n].setTimesOfNodes(hashMap.get(arrayListKey));
                    }
                }
            }
        }

        //这里，把循环的次数记录到列表中，方便后期压缩用
        for (ArrayList<Integer> arrayList : hashMap.keySet()) {
            if (frequentlyList.size() != 0) {  //这里面frequentlyList是出现的所有频繁度
                for (Integer aFrequentlyList : frequentlyList) {
                    if (aFrequentlyList.equals(hashMap.get(arrayList)))
                        frequentBoolean = true;
                }
            }
            if (!frequentBoolean) {
                if (hashMap.get(arrayList) >= vauleYouDefine)
                    frequentlyList.add(hashMap.get(arrayList));
            }
        }
        return nodes;

    }

    /**
     * 找出所有压缩后可以构建树的节点
     * 至里面仅仅遍历了一遍数据即可完成删边和清理工作，比论文的简单.
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
     * @return 一颗ArrayList<ArrayList<Node>>,里面可以存放很多颗树，一般只返回一棵树
     */
    public ArrayList<ArrayList<Node>> getNewTreeFromCutting(ArrayList<Node> arrayList, int timesYouDefine) {

        ArrayList<ArrayList<Node>> nodesList = new ArrayList<ArrayList<Node>>();
        for (Node aNode : arrayList) {
            addNodeFromTree(aNode, timesYouDefine);
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
     * 这里面会遍历一遍所有的数据
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
