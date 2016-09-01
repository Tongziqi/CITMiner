import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 工具类 包括文件读取等等
 */
public class Tools {
    /**
     * 文件读取（内存映射方式）
     *
     * @param filePath 文件的路径
     * @return 返回一个String类型的字符串
     * @throws IOException 抛出异常
     */

    public static String getNodes(File filePath) throws IOException {
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
     * Node包含它的头结点和它的一堆尾节点
     *
     * @param nodesFronText 表示从文件里面读取的数值
     * @return 一个带头结点和尾节点的二维Node链表
     */
    public static Node[][] getNodesListFromText(String nodesFronText) {
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
}
