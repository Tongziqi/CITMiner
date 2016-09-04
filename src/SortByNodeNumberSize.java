import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Administrator on 2015/7/28 0028.
 * 按照大小排序
 */
public class SortByNodeNumberSize implements Comparator {
    /**
     * 重写Comparator函数里面的sort
     *
     * @param o1 比较的参数
     * @param o2 比较的参数
     * @return 最小的size
     */
    @Override
    public int compare(Object o1, Object o2) {
        ArrayList<NodeNumber> arrayList1 = (ArrayList<NodeNumber>) o1;
        ArrayList<NodeNumber> arrayList2 = (ArrayList<NodeNumber>) o2;
        //if (arrayList1.size() < arrayList2.size())
        //   return -1;
        // return 1;
        return arrayList1.size() - arrayList2.size();// 正确的方式
    }
}
