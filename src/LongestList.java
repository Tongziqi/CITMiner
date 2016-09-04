import java.util.ArrayList;
import java.util.Collections;

@Deprecated
public class LongestList {
    /**
     * 获得最大公共链
     *
     * @param arrayLists      所有的压缩链 就是那些链被压缩了
     * @param timesYouDinfine 压缩的阀值
     * @return 最大的公共链
     */
    public static ArrayList<ArrayList<NodeBranch>> getLongestCommonList(ArrayList<ArrayList<NodeNumber>> arrayLists, int timesYouDinfine) {
        int numberOfMinSize = 0;//有几个最短链
        ArrayList<NodeNumber> longestCommonList;
        ArrayList<NodeBranch> lastLongestCommonList = new ArrayList<NodeBranch>();
        ArrayList<ArrayList<NodeBranch>> allLastLongestCommonList = new ArrayList<ArrayList<NodeBranch>>(); //记录所有的公共链
        ArrayList<ArrayList<NodeNumber>> newArrayLists = (ArrayList<ArrayList<NodeNumber>>) arrayLists.clone();//这里面复制下arrayLists 用作排序用
        Collections.sort(newArrayLists, new SortByNodeNumberSize());

        longestCommonList = newArrayLists.get(0);//这里得到最短的压缩链(但是存在其他的情况,比如前两个链都是最短的)

        for (ArrayList<NodeNumber> newArrayList : newArrayLists) {
            if (newArrayList.size() == longestCommonList.size()) {
                numberOfMinSize++; //如果有一个相等 那么表示只有一个最短链
            } else {
                break;
            }
        }

        ArrayList<ArrayList<NodeBranch>> allnodeBranches = getNodeBranchFromNodeNumber(arrayLists);
        int times = timesYouDinfine;

        for (int i = 0; i < numberOfMinSize; i++) {
            int longest = arrayLists.indexOf(newArrayLists.get(i));//这里面获得最短压缩链的位置
            ArrayList<NodeBranch> longestNodeBranches = allnodeBranches.get(longest);  //这里找出最短的压缩链

            for (NodeBranch longestnodeBranch : longestNodeBranches) {
                for (ArrayList<NodeBranch> arrayList : allnodeBranches) {
                    for (NodeBranch allnodeBranch : arrayList) {
                        if (allnodeBranch.equals(longestnodeBranch)) {
                            times = times - 1;   //这里控制出现的次数 每出现一次就减去1 如果最后是负值或者为0,即满足条件
                            break;  //但是现在只能得到一条
                        }
                    }
                }
                if (times <= 0) {
                    lastLongestCommonList.add(longestnodeBranch); //如果满足条件 那么添加进去
                }
            }
            allLastLongestCommonList.add((ArrayList<NodeBranch>) lastLongestCommonList.clone());
            lastLongestCommonList.clear();
        }
        return allLastLongestCommonList;
    }


    /**
     * 把压缩链变成一个链表 里面放着所有的边
     *
     * @param arrayListNodeNumber 需要处理的压缩链
     * @return 一个存着所有边的链表
     */
    public static ArrayList<ArrayList<NodeBranch>> getNodeBranchFromNodeNumber(ArrayList<ArrayList<NodeNumber>> arrayListNodeNumber) {
        ArrayList<ArrayList<NodeBranch>> nodeBranches = new ArrayList<ArrayList<NodeBranch>>();
        ArrayList<NodeBranch> aNodeBranches = new ArrayList<NodeBranch>();
        for (ArrayList<NodeNumber> anArrayListNodeNumber : arrayListNodeNumber) {
            for (int j = 1; j < anArrayListNodeNumber.size(); j++) {
                aNodeBranches.add(new NodeBranch(Integer.valueOf(anArrayListNodeNumber.get(Math.abs(j - anArrayListNodeNumber.get(j).getWeightOfNodes())).getNodenumber()), Integer.valueOf(anArrayListNodeNumber.get(j).getNodenumber())));
            }
            nodeBranches.add((ArrayList<NodeBranch>) aNodeBranches.clone());
            aNodeBranches.clear();
        }
        return nodeBranches;
    }


}
