import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tongxiaotuo on 16/9/4.
 * 获得最大公共链,现在问题就是如何传入allArrayListsFromCompress
 * 频繁度为3传入的是2条链,频繁度为2其实传入的是3条链中的两条
 * allArrayListsFromCompress还需要处理一下
 */
public class LonggestList {
    public static ArrayList<NodeBranch> getLonggest(ArrayList<ArrayList<NodeNumber>> allArrayListsFromCompress) {
        Collections.sort(allArrayListsFromCompress, new SortByNodeNumberSize());
        //ArrayList<NodeNumber> list = allArrayListsFromCompress.get(0); //获得最短的链
        //判读其它链都是否存在
        ArrayList<NodeBranch> smallestNodeBranch;
        ArrayList<ArrayList<NodeBranch>> allNodeBranch = new ArrayList<ArrayList<NodeBranch>>();

        for (int i = 0; i < allArrayListsFromCompress.size(); i++) {
            allNodeBranch.add(getNodeBranchFromNodeNumber(allArrayListsFromCompress.get(i)));
        }
        smallestNodeBranch = allNodeBranch.get(0);
        for (int i = 0; i < smallestNodeBranch.size(); i++) {
            NodeBranch branch = smallestNodeBranch.get(i);//判断这个branch在其它链中是否存在

            for (ArrayList<NodeBranch> nodeBranch : allNodeBranch) {
                if (!nodeBranch.contains(branch)) { //如果不包含这个brunch
                    smallestNodeBranch.remove(i);//删除该brunch(x,y),并且删除以y为父元素的所有元素
                    removeNodeAsFather(smallestNodeBranch, branch);//只可能删除后续没匹配的,所以只要i = i - 1;
                    i = i - 1;
                    break; //这里面可以跳出么?
                }
            }
        }
        return smallestNodeBranch;
    }

    /**
     * 把压缩链变成一个链表 里面放着所有的边
     *
     * @param arrayListNodeNumber 需要处理的压缩链
     * @return 一个存着所有边的链表
     * eg:[3,4, 3,5, 3,6, 3,7]
     */
    public static ArrayList<NodeBranch> getNodeBranchFromNodeNumber(ArrayList<NodeNumber> arrayListNodeNumber) {
        ArrayList<NodeBranch> aNodeBranches = new ArrayList<NodeBranch>();
        for (int j = 1; j < arrayListNodeNumber.size(); j++) {
            int m = Math.abs(j - arrayListNodeNumber.get(j).getWeightOfNodes());
            //如果出现压缩链有问题的情况,跳过
            if (arrayListNodeNumber.size() <= m) {
                continue;
            }
            aNodeBranches.add(new NodeBranch(Integer.valueOf(arrayListNodeNumber.get(m).getNodenumber()), Integer.valueOf(arrayListNodeNumber.get(j).getNodenumber())));
        }
        return aNodeBranches;
    }

    //删除nodeBranch中的以branch(x,y)的y为父元素的所有元素
    //返回删除后的nodeBranch
    public static ArrayList<NodeBranch> removeNodeAsFather(ArrayList<NodeBranch> nodeBranch, NodeBranch branch) {
        int y = branch.tailNode;
        for (int i = 0; i < nodeBranch.size(); i++) {
            if (nodeBranch.get(i).tailNode == y) {
                nodeBranch.remove(i);
                i--;
            }
        }
        return nodeBranch;
    }
}
