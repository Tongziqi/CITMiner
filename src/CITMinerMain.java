import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2015/6/21 0021.
 * CITMiner算法 童小托
 */
public class CITMinerMain {

    public static void main(String[] args) throws IOException {

        ArrayList<Integer> frequentlyList = new ArrayList<Integer>(); //这里记录所有出现的频繁度，只压缩出现的频繁度
        ArrayList<Node> alistFromCutting = new ArrayList<Node>(); // 这里控制仅仅有一NodeList,便于添加数据
        ArrayList<ArrayList<Node>> allNodeListFromCutting = new ArrayList<ArrayList<Node>>(); //把所有剪切后的树记录下来,用来压缩用 里面记录了出现的频率和次数
        ArrayList<ArrayList<NodeNumber>> arrayListsFromCompress = new ArrayList<ArrayList<NodeNumber>>(); //记录每一次的压缩的压缩链
        ArrayList<ArrayList<NodeBranch>> allLonggestCommonList;//存储着最长公共链
        long timeMillis = System.currentTimeMillis();  // 记录系统开始的时间
        File filePath = new File("/Users/tongxiaotuo/Desktop/Citdata/" + File.separator + "test.txt");
        Node[][] nodes = Tools.getNodesListFromText(Tools.getNodes(filePath));
        nodes = MakeCompressTree.makeCompressTree(nodes, frequentlyList, DefaultSetting.vauleYouDefine);

        Collections.sort(frequentlyList);
        Collections.reverse(frequentlyList);

        //完成所有树的剪切工作
        //这里不一边剪切一边压缩 因为这样无法用每次压缩的所有结果进行闭合化
        //为什么修改过后算法变得那么慢？因为原来是剪切一条链后，就对其进行压缩，一次迭代压缩完所有的频繁度，而且只写入文档最后结果
        //时间的差异大都在写入文档
        //但是现在为了闭合化，需要压缩一个频繁度的所有所有链，找出公共部分，即为频繁子树，所以需要记录每一个频繁度的所有压缩链，就不能安装原来的算法了，需要同时压缩，还要记录中间值。

        ////修改 2015年9月15日 21:55:26 这里面算法有问题 为什么呢 因为这里进行了很多重复工作，频繁度为2的公共链一定程度上包含了频繁度为f(f>2)的公共链,每计算一个新的公共链，都要全部比较一次
        ////看看能不能用上上次的结果 但是f=2的公共链也不一定包含f=3的公共链(不一定么？)
        for (Node[] node : nodes) {
            ArrayList<Node> arrayListHeadNodes = Cutting.getHeadTailFromCutting(node, DefaultSetting.vauleYouDefine);
            if (arrayListHeadNodes.size() >= 1) {
                ArrayList<Node> arrayList = Cutting.getNewTreeFromCutting(arrayListHeadNodes, DefaultSetting.vauleYouDefine, alistFromCutting).get(0); //这里改了就行 以后把这里改成迭代的形式，获得所有的arraylist不只get(0)
                allNodeListFromCutting.add(arrayList); //把结果添加进去
            }
        }
        Tools.writeNodes(DefaultSetting.fileName, "---------------------------------------------------------------------------------------------" + "\n");
        //开始进行压缩,具体压缩的阀值由出现的最大频率来决定
        for (Integer aFrequentlyList : frequentlyList) {
            for (ArrayList<Node> arrayList : allNodeListFromCutting) {
                CompressTree.compressTreetest(arrayList, aFrequentlyList, arrayListsFromCompress);
            }
            Tools.writeNodes(DefaultSetting.fileName, "阀值为" + aFrequentlyList + "的压缩链是:" + arrayListsFromCompress + "共有" + arrayListsFromCompress.size() + "个压缩链" + "\n" + "\r");

            allLonggestCommonList = LongestList.getLongestCommonList(arrayListsFromCompress, aFrequentlyList);
            arrayListsFromCompress.clear();
            Tools.writeNodes(DefaultSetting.fileName, "阀值为" + aFrequentlyList + "的公共链是" + allLonggestCommonList.toString() + "\n");
        }
        Tools.writeNodes(DefaultSetting.fileName, "\r----------------------------执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 " + "\n" + "\r");
        System.out.println("\r执行耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
        //System.out.println("\r该步骤执行耗时 : " + citMinerMain.timeMillis + " 毫秒 ");
        System.out.print("\n" + "-----------------------结束-------------------------");
    }
}
