import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CITMinerMain {

    public static void main(String[] args) throws IOException {

        ArrayList<Integer> frequentlyList = new ArrayList<Integer>(); //这里记录所有出现的频繁度，只压缩出现的频繁度
        ArrayList<Node> headNode = new ArrayList<Node>();//剪切清理过后得到的头节点
        ArrayList<NodeNumber> arrayListsFromCompress = new ArrayList<NodeNumber>(); //记录每一次的压缩的压缩链
        ArrayList<ArrayList<NodeNumber>> allArrayListsFromCompress = new ArrayList<ArrayList<NodeNumber>>();//存储所有的压缩链
        //ArrayList<ArrayList<NodeBranch>> allLonggestCommonList;//存储着最长公共链
        ArrayList<NodeBranch> list; //存储着最长公共链
        long timeMillis = System.currentTimeMillis();  // 记录系统开始的时间
        Node[] nodes = MakeCompressTree.makeCompressTree(Tools.getNodesListFromText(new File(DefaultSetting.inputFileName)), frequentlyList, DefaultSetting.vauleYouDefine);

        CuttingTree.getAllTreeFromCutting(nodes, headNode);//剪切
        CuttingTree.cleanTree(headNode);//清理
        Collections.sort(frequentlyList);
        Collections.reverse(frequentlyList);

        for (int frequentNum : frequentlyList) {
            for (Node node : headNode) {
                allArrayListsFromCompress.add((ArrayList<NodeNumber>) CompressTree.compress(node, frequentNum, arrayListsFromCompress).clone());
                arrayListsFromCompress.clear();
            }
            //每一步压缩过后,这里已经得到压缩链,还需要信息(这轮压缩中压缩的边(x,y)),以及边(x,y)出现的压缩链集合,
            // 不是所有的压缩链都一定要出现(x,y),例如频繁度为2,就不可能3条链都出现(x,y)
            //如何从allArrayListsFromCompress里获得出现(x,y)的所有链是目前的问题
            list = LonggestList.getLonggest(allArrayListsFromCompress);
            allArrayListsFromCompress.clear();
            System.out.println("\r频繁度为" + frequentNum + "的频繁子树为: " + list.toString());
            //Tools.writeInString(DefaultSetting.outputFileName, "阀值为" + frequentNum + "的公共链是" + allLonggestCommonList.toString() + "\n");
        }
        System.out.println("\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
    }
}
