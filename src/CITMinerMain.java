import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CITMinerMain {

    public static void main(String[] args) throws IOException {

        int time = 128;
        ArrayList<Integer> frequentlyList = new ArrayList<Integer>(); //这里记录所有出现的频繁度，只压缩出现的频繁度
        ArrayList<Node> headNode = new ArrayList<Node>();//剪切清理过后得到的头节点

        //当作参数传入compress方法中,记录压缩链和压缩点点
        ArrayList<NodeNumber> arrayListsFromCompress = new ArrayList<NodeNumber>(); //记录每一次的压缩的压缩链
        ArrayList<Node> nodeArrayList = new ArrayList<Node>();//记录每一次的压缩的压缩点

        //记录所有的压缩链和所有压缩的压缩点
        ArrayList<ArrayList<NodeNumber>> allArrayListsFromCompress = new ArrayList<ArrayList<NodeNumber>>();
        ArrayList<ArrayList<Node>> allArrayNodeFromCompress = new ArrayList<ArrayList<Node>>();

        //相关参数
        ArrayList<NodeBranch> list; //存储着最长公共链
        long timeMillis = System.currentTimeMillis();  // 记录系统开始的时间

        //算法的步骤,剪切 清理 压缩 序列化
        //不用先开辟一块内存存放nodes
        //Node[] nodes = MakeCompressTree.makeCompressTree(Tools.getNodesListFromText(new File(DefaultSetting.inputFileName)), frequentlyList, DefaultSetting.vauleYouDefine);
        CuttingTree.getAllTreeFromCutting(MakeCompressTree.makeCompressTreeFromArray(Tools.getNodesListFromText(new File(DefaultSetting.inputFileName)).get(time - 1), frequentlyList, DefaultSetting.vauleYouDefine), headNode);//剪切
        CuttingTree.cleanTree(headNode);//清理
        Collections.sort(frequentlyList);
        Collections.reverse(frequentlyList);

        for (int frequentNum : frequentlyList) {
            //frequentNum == 1046会出现问题 压缩链不能转换成边的结构了
            for (Node node : headNode) {
                //得到频繁度为frequentNum的brunchAndList,brunchAndList里面存放了该轮压缩的边和链
                NodeAndList nodeAndList = CompressTree.compress(node, frequentNum, arrayListsFromCompress, nodeArrayList);
                if (!nodeAndList.isEmpty()) {
                    allArrayListsFromCompress.add((ArrayList<NodeNumber>) nodeAndList.getNodeNumberArrayList().clone());
                    allArrayNodeFromCompress.add((ArrayList<Node>) nodeAndList.getNodeArrayList().clone());
                }
                //清空信息 下一次循环用
                nodeArrayList.clear();
                arrayListsFromCompress.clear();
            }
            ArrayList<ArrayList<NodeNumber>> getThisList = new ArrayList<ArrayList<NodeNumber>>();
            ArrayList<ArrayList<Node>> allArrayNodeFromCompressClone = new ArrayList<ArrayList<Node>>();
            allArrayNodeFromCompressClone.addAll(allArrayNodeFromCompress);

            //外层循环
            for (int i = 0; i < allArrayNodeFromCompress.size(); ) {
                //int time = allArrayNodeFromCompress.get(i).get(0).getTime();
                String node = allArrayNodeFromCompress.get(i).get(0).getNodenumber();
                //内层循环
                for (int j = 0; j < allArrayNodeFromCompressClone.size(); j++) {
                    //再循环每个node
                    for (Node n : allArrayNodeFromCompressClone.get(j)) {
                        if (n.getNodenumber().equals(node)) {
                            getThisList.add(allArrayListsFromCompress.get(j));
                            allArrayNodeFromCompress.remove(allArrayNodeFromCompressClone.get(j));
                            break;
                        }
                    }
                }
                list = LonggestList.getLonggest(getThisList);
                if (list.size() != 0) {
                    Tools.writeInString(DefaultSetting.outputFileName, "时间为:" + time + "频繁度为:" + frequentNum + "压缩链是" + list.toString() + "\n");
                }
                // System.out.println("频繁度为" + frequentNum + "的List:" + list.toString());
                getThisList.clear();
            }
            allArrayListsFromCompress.clear();
            allArrayNodeFromCompress.clear();
        }
        Tools.writeInString(DefaultSetting.outputFileName, "\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + "毫秒");
        System.out.println("\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
    }
}
