import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        long timeMillis = System.currentTimeMillis();  // 记录系统开始的时间

        //得到所有的时间节点
        ArrayList<ArrayList<Node[]>> allNodesWithTime = Tools.getNodesListFromText(new File(DefaultSetting.inputFileName));
        //获得静态频繁模式
        HashMap<Integer, ArrayList<FrequenceTree>> staticResluts = CITMiner.getStaticTree(allNodesWithTime);
        System.out.println("\r挖掘静态数据耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
        timeMillis = System.currentTimeMillis();

        ArrayList<FrequenceTree> startWindows = staticResluts.get(1);
        for (int i = DefaultSetting.startTime + 1; i < DefaultSetting.endTime; i++) {
            //获得当前时刻的频繁子树，添加到开始窗口中
            ArrayList<FrequenceTree> currentTime = staticResluts.get(i);
            startWindows = SSDTreeMiner.addList(currentTime, startWindows);
            //过期数据从窗口总删除
            if (i >= DefaultSetting.duringTime) {
                ArrayList<FrequenceTree> leaveTime = staticResluts.get(i + 1 - DefaultSetting.duringTime);
                startWindows = SSDTreeMiner.removeList(leaveTime, startWindows);
                SSDTreeMiner.checkList(i, startWindows);
            }
        }
        System.out.println("\r挖掘动态数据耗时 : " + (System.currentTimeMillis() - timeMillis) + " 毫秒 ");
    }
}
