package cn.aiclr.jvm.dsa.tree.huffman;

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ToString(exclude = {"left", "right"})
public class HuffmanNode implements Comparable<HuffmanNode> {

    private static final Logger log = LoggerFactory.getLogger(HuffmanNode.class);

    int value;//权
    HuffmanNode left;
    HuffmanNode right;

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


    public HuffmanNode(int value) {
        this.value = value;
    }

    /**
     * <pre>
     * For the mathematically inclined, the relation that defines the natural ordering on a given class C is:
     *          {(x, y) such that x.compareTo(y) <= 0}.
     * The quotient for this total order is:
     *          {(x, y) such that x.compareTo(y) == 0}.
     *
     * @param o 待比较的对象
     * @return 负数 this 小，正数 this 大，0相等
     */
    @Override
    public int compareTo(HuffmanNode o) {
        //从小到大
        return this.value - o.value;
        //从大到小
//        return o.value-this.value;
    }
}
