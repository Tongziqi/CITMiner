/**
 * Created by Administrator on 2015/6/21 0021.
 */
public class Node {
    public int nodenumber;  //节点的数值

    public Node nodeHead;   //节点的头结点
    public Node nodeTail;   //节点的尾节点  //只用头节点 一个是一棵树 一个节点可能有很多尾节点 但是只有一个头结点

    public Node getNodeHead() {
        return nodeHead;
    }

    public Node(Node nodeHead, int nodenumber, Node nodeTail) {
        this.nodeHead = nodeHead;
        this.nodenumber = nodenumber;
        this.nodeTail = nodeTail;
    }

    public Node(int nodenumber) {
        this.nodenumber = nodenumber;
    }

    public void setNodeHead(Node nodeHead) {
        this.nodeHead = nodeHead;
    }

    public Node getNodeTail() {
        return nodeTail;
    }

    public void setNodeTail(Node nodeTail) {
        this.nodeTail = nodeTail;
    }


    public int getNodenumber() {
        return nodenumber;
    }

    public void setNodenumber(int nodenumber) {
        this.nodenumber = nodenumber;
    }
}
