import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tongxiaotuo on 16/9/1.
 * 构建一颗压缩树，即添加权重和出现的次数
 * 这里面读取了数据两次 第一次统计边的频繁度，第二次更新压缩树的边频繁度值
 */
public class MakeCompressTree {
/*    public static Node[] makeCompressTree(final Node[][] nodes, ArrayList<Integer> frequentlyList, int vauleYouDefine) {
        Node[] resultNodeList = new Node[nodes.length];
        int defaultValue = 1;
        Boolean frequentBoolean;
        HashMap<ArrayList<Integer>, Integer> hashMap = new HashMap<ArrayList<Integer>, Integer>();
        //第一次循环 找出所有的频繁次数
        for (Node[] node : nodes) {
            for (int j = 1; j < node.length; j++) {
                if (!node[j].getNodenumber().equals("-1")) {
                    ArrayList<Integer> arrayListKey = new ArrayList<Integer>();
                    arrayListKey.add(Integer.valueOf(node[j].getNodenumber()));
                    arrayListKey.add(Integer.valueOf(node[j].getNodeHead().getNodenumber()));
                    if (hashMap.containsKey(arrayListKey)) {  //如果已经出现过一次 那么就把频繁度+1
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
            frequentBoolean = false;
            if (frequentlyList.size() != 0) {  //这里面frequentlyList是出现的所有频繁度
                for (Integer aFrequentlyList : frequentlyList) {
                    if (aFrequentlyList.equals(hashMap.get(arrayList)))
                        frequentBoolean = true;
                }
            }
            if (!frequentBoolean) {
                if (hashMap.get(arrayList) >= vauleYouDefine) {
                    frequentlyList.add(hashMap.get(arrayList));
                }
            }
        }

        for (int i = 0; i < nodes.length; i++) {
            resultNodeList[i] = nodes[i][0];
        }
        return resultNodeList;
        //return nodes;

    }*/

    public static Node[] makeCompressTreeFromArray(final ArrayList<Node[]> nodes, ArrayList<Integer> frequentlyList, int vauleYouDefine) {
        Node[] resultNodeList = new Node[nodes.size()];
        int defaultValue = 1;
        Boolean frequentBoolean;
        HashMap<ArrayList<Integer>, Integer> hashMap = new HashMap<ArrayList<Integer>, Integer>();
        //第一次循环 找出所有的频繁次数
        for (Node[] node : nodes) {
            for (int j = 1; j < node.length; j++) {
                if (!node[j].getNodenumber().equals("-1")) {
                    ArrayList<Integer> arrayListKey = new ArrayList<Integer>();
                    arrayListKey.add(Integer.valueOf(node[j].getNodenumber()));
                    arrayListKey.add(Integer.valueOf(node[j].getNodeHead().getNodenumber()));
                    if (hashMap.containsKey(arrayListKey)) {  //如果已经出现过一次 那么就把频繁度+1
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
            frequentBoolean = false;
            if (frequentlyList.size() != 0) {  //这里面frequentlyList是出现的所有频繁度
                for (Integer aFrequentlyList : frequentlyList) {
                    if (aFrequentlyList.equals(hashMap.get(arrayList)))
                        frequentBoolean = true;
                }
            }
            if (!frequentBoolean) {
                if (hashMap.get(arrayList) >= vauleYouDefine) {
                    frequentlyList.add(hashMap.get(arrayList));
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            resultNodeList[i] = nodes.get(i)[0];
        }
        return resultNodeList;
        //return nodes;

    }

}
