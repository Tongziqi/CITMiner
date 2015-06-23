import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2015/6/21 0021.
 * CITMiner算法 童小托
 */
public class CITMinerMain {

    public static void main(String[] args) throws IOException {
        CITMinerMain citMinerMain = new CITMinerMain();

        File filePath = new File("D:" + File.separator + "test.txt");
        String allNodes = citMinerMain.getNodes(filePath);
        Node[][] nodes = citMinerMain.getNodeListWithHeadAndTailNodes(citMinerMain.getNodesListFromText(allNodes));
        nodes = citMinerMain.makeCompresssionTree(nodes);
        System.out.println("父节点是:" + nodes[1][9].getNodeHead().getNodenumber());
        System.out.println("出现的次数是:" + nodes[1][9].getTimesOfNodes());
        System.out.println("权重是:" + nodes[1][9].getWeightOfNodes());

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
                //nodesList[i][j].setNodenumber(Integer.valueOf(numbersList[j]));  //这里单单赋值 会空指针异常
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
        Node defaultHeadNode = new Node(-3);
        Node firstNodeHeadNode = new Node(-2);

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
                        if (aNodesList[j].getNodenumber() != -1) {
                            aNodesList[j].setNodeHead(aNodesList[j - 1]); //如果后面的数不是-1,直接连接
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
                            if (j + numberOfLayer != aNodesList.length - 1) { //如果是最后一个位置，即-1时候，默认头结点
                                Node node = aNodesList[j - 1].getNodeHead();
                                //这个循环是为了循环找getNodeHead
                                for (int num = 0; num < numberOfLayer - 1; num++) {
                                    node = node.getNodeHead();
                                }
                                aNodesList[j + numberOfLayer].setNodeHead(node);
                                j += numberOfLayer;
                            }
                        }
                    }

                }
            }
        }
        return nodesList;
    }

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
}
