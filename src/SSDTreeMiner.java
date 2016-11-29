import java.util.ArrayList;

/**
 * Created by tongxiaotuo on 2016/11/29.
 * 获得半结构化数据流中的频繁模式
 */
public class SSDTreeMiner {
    // 添加新链到滑动窗口
    public static ArrayList<FrequenceTree> addList(ArrayList<FrequenceTree> newList, ArrayList<FrequenceTree> oldList) {
        ArrayList<FrequenceTree> mixList;
        mixList = oldList;
        if (newList == null) return mixList;

        for (FrequenceTree tree : mixList
                ) {
            tree.setFrequentNum(tree.frequentNum * DefaultSetting.decrease); //衰减
        }

        double increaseOld = 0;
        double increaseNew = 0;
        for (FrequenceTree newtree : newList
                ) {

            for (int i = 0; i < mixList.size(); i++) {
                FrequenceTree oldtree = mixList.get(0);
//            }
//            for (FrequenceTree oldtree : mixList
//                    ) {
                if (newtree.equals(oldtree)) {
                    increaseOld = newtree.frequentNum * DefaultSetting.increase;
                } else if (newtree.contain(oldtree)) {
                    increaseOld = Math.max(increaseOld, newtree.frequentNum * DefaultSetting.increase);
                } else if (oldtree.contain(newtree)) {
                    increaseNew = Math.max(increaseNew, oldtree.frequentNum);
                }
                //如果现有集合中不包含新来对数据
                if (!mixList.contains(newtree)) {
                    mixList.add(newtree);
                }
            }
        }
        for (FrequenceTree tree : mixList
                ) {
            tree.setFrequentNum(tree.frequentNum + increaseOld); //衰减
        }
        return mixList;
    }

    // 从滑动窗口中删除旧链
    public static ArrayList<FrequenceTree> removeList(ArrayList<FrequenceTree> leaveList, ArrayList<FrequenceTree> oldList) {
        ArrayList<FrequenceTree> mixList;
        mixList = oldList;
        if (leaveList == null) return mixList;

        for (FrequenceTree tree : leaveList
                ) {
            tree.setFrequentNum(tree.frequentNum * Math.pow(DefaultSetting.decrease, DefaultSetting.duringTime) * DefaultSetting.increase); //滑动窗口末端衰减
        }
        double increaseOld = 0;
        for (FrequenceTree leavetree : leaveList
                ) {
            for (FrequenceTree oldtree : mixList
                    ) {
                if (leavetree.equals(oldtree)) {
                    increaseOld = leavetree.frequentNum;
                } else if (leavetree.contain(oldtree)) {
                    increaseOld = Math.max(increaseOld, leavetree.frequentNum);
                }
            }
        }
        for (FrequenceTree tree : mixList
                ) {
            tree.setFrequentNum(tree.frequentNum - increaseOld); //衰减
        }
        return mixList;
    }

    // 检测当前滑动窗口内压缩链是否满足条件 写入文件
    public static void checkList(int time, ArrayList<FrequenceTree> list) {
        for (FrequenceTree tree : list
                ) {
            if (tree.getFrequentNum() >= DefaultSetting.vauleYouDefine) {
                //符合条件的直接写入文件
                Tools.writeInString(DefaultSetting.outputFileName, "时间戳为:" + (time - DefaultSetting.duringTime) + "到" + time + " 频繁度为" + tree.getFrequentNum() + "的频繁子树是" + tree.getNodeBranches().toString() + "\n");
                //Tools.writeInString(DefaultSetting.outputFileName, "时间戳为:" + (time - DefaultSetting.duringTime) + "到" + time + "满足条件的频繁子树是: " + tree.getNodeBranches().toString() + "\n");
            }
        }
    }
}
