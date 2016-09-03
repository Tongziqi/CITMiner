/**
 * Created by tongxiaotuo on 16/9/3.
 * 测试类
 */
public class ReadTree {
    //读取整棵树
    public static void readTree(Node node) {
        System.out.println(node.getNodenumber());

        for (Node n : node.getArrayListNodeTail()
                ) {
            readTree(n);
        }
    }

    //读取压缩链
    public static void readNodeNumberList(Node node) {
        for (NodeNumber n : node.getNodeNumberArrayList()
                ) {
            System.out.print(node.getNodenumber() + "的压缩链是:" + n.toString() + "\n");
        }

        for (Node n : node.getArrayListNodeTail()
                ) {
            readNodeNumberList(n);

        }
    }
}
