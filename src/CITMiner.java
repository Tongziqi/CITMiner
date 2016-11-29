import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by tongxiaotuo on 2016/11/29.
 * 获得静态压缩数据
 */
public class CITMiner {
    public static HashMap<Integer, ArrayList<FrequenceTree>> getStaticTree(ArrayList<ArrayList<Node[]>> allNodesWithTime) {
        HashMap<Integer, ArrayList<FrequenceTree>> results = new HashMap<Integer, ArrayList<FrequenceTree>>();
        //算法的步骤,剪切 清理 压缩 序列化
        for (int time = DefaultSetting.startTime; time < DefaultSetting.endTime; time++) {
            ArrayList<Integer> frequentlyList = new ArrayList<Integer>(); //这里记录所有出现的频繁度，只压缩出现的频繁度 每个时间节点不一样
            ArrayList<Node> headNode = new ArrayList<Node>();//剪切清理过后得到的头节点
            //当作参数传入compress方法中,记录压缩链和压缩点点
            ArrayList<NodeNumber> arrayListsFromCompress = new ArrayList<NodeNumber>(); //记录每一次的压缩的压缩链
            ArrayList<Node> nodeArrayList = new ArrayList<Node>();//记录每一次的压缩的压缩点

            //记录所有的压缩链和所有压缩的压缩点
            ArrayList<ArrayList<NodeNumber>> allArrayListsFromCompress = new ArrayList<ArrayList<NodeNumber>>();
            ArrayList<ArrayList<Node>> allArrayNodeFromCompress = new ArrayList<ArrayList<Node>>();
            ArrayList<NodeBranch> longestList; //存储着最长公共链

            // 记录数据用
            ArrayList<FrequenceTree> frequenceTreeArrayList = new ArrayList<FrequenceTree>();//记录所有的静态挖掘结果

            ArrayList<Node[]> nodes = allNodesWithTime.get(time - 1);
            CuttingTree.getAllTreeFromCutting(MakeCompressTree.makeCompressTreeFromArray(nodes, frequentlyList, DefaultSetting.vauleYouDefine), headNode);//剪切
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
                    longestList = LonggestList.getLonggest(getThisList);
                    if (longestList.size() != 0) {
                        //不写入文件保存
                        //Tools.writeInString(DefaultSetting.outputFileName, "时间为:" + time + "频繁度为:" + frequentNum + "压缩链是" + longestList.toString() + "\n");
                        frequenceTreeArrayList.add(new FrequenceTree(frequentNum, longestList));
                    }
                    getThisList.clear();
                }
                results.put(time, frequenceTreeArrayList);
                allArrayListsFromCompress.clear();
                allArrayNodeFromCompress.clear();
            }
        }
        return results;
    }
}
