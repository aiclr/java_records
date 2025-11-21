package cn.aiclr.jvm.dsa.tree.huffman;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(exclude = {"left", "right"})
public class DataNode implements Comparable<DataNode> {
    Byte data;//ASCI码 a=97 空格' '=32
    int weight;
    DataNode left;
    DataNode right;

    //前序遍历
    public void postOrder() {
        log.info("{}", this);
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
    }

    public DataNode(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(DataNode o) {
        return this.weight - o.weight;
    }
}
