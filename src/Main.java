import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        //得到所有的时间节点
        ArrayList<ArrayList<Node[]>> allNodesWithTime = Tools.getNodesListFromText(new File(DefaultSetting.inputFileName));
        //获得静态频繁模式
        CITMiner.getStaticTree(allNodesWithTime);
    }
}
