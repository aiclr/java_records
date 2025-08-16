package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>68.频繁插入和删除时使用 {@link java.util.LinkedList}
 *
 * 1.插入元素
 *  {@link java.util.LinkedList} 是一个双向链表，它的插入 {@link java.util.LinkedList#add(int, Object)} 只是修改相邻元素的 {@link java.util.LinkedList.Node.next} 和 {@link java.util.LinkedList.Node.prev}引用
 *  把自己插入到链表，然后再把前节点的 {@link java.util.LinkedList.Node.next} 和后节点的 {@link java.util.LinkedList.Node.prev} 指向自己
 *
 *  {@link java.util.ArrayList#add(int, Object)}插入元素涉及到拷贝其他无关的元素，效率会影响
 *  经过书籍作者实际测试得，{@link java.util.LinkedList} 的插入效率比 {@link java.util.ArrayList} 快50倍以上
 *
 * 2.删除元素
 *  {@link java.util.ArrayList#remove(int)} 提供了删除指定位置上的元素、删除指定值元素、删除一个下标范围内的元素集等删除动作，三者的实现原理基本相似，都是找到索引位置，然后删除
 *  删除的 index 位置后的元素都向前移动了一位，最后一个位置空出来了，这又是一次数组拷贝，和插入一样，如果数据量大，删除动作必然会暴露出性能和效率方面的问题。
 *  {@link java.util.ArrayList#remove(Object)} 删除方法与此相似
 *
 *  {@link java.util.LinkedList} 提供了非常多的删除操作，比如删除指定位置元素、删除头元素等，与之相关的 {@link java.util.LinkedList#poll()} 方法也会执行删除动作
 *  双向链表的标准删除算法，没有任何耗时的操作，全部是引用指针的变更，效率高。
 *  在实际测试中得知，处理大批量的删除动作，{@link java.util.LinkedList} 比 {@link java.util.ArrayList} 快40倍以上
 *
 * 3.修改元素
 *  修改元素值，在这一点上 {@link java.util.LinkedList} 输给了 {@link java.util.ArrayList}，
 *  这是因为 {@link java.util.LinkedList} 是顺序存取的，因此定位元素必然是一个遍历过程，效率大打折
 *  {@link java.util.LinkedList} 这种顺序存取列表的元素定位方式会折半遍历，这是一个极耗时的操作。
 *  而 {@link java.util.ArrayList} 的修改动作则是数组元素的直接替换，简单高效。
 *  在修改动作上，LinkedList比 {@link java.util.ArrayList} 慢很多，特别是要进行大量的修改时，两者完全不在一个数量级
 *
 * 一个实时交易的系统，即使写作操再少，使用 {@link java.util.LinkedList} 也比 {@link java.util.ArrayList} 合适，
 * 因为此类系统是争分夺秒的，多N个毫秒可能就会造成交易数据不准确；
 *
 * 而对于一个批量系统来说，几十毫秒、几百毫秒，甚至是几千毫秒的差别意义都不大，
 * 这时是使用 {@link java.util.LinkedList} 还是 {@link java.util.ArrayList} 就看个人爱好了，
 * 当然，如果系统已经处于性能临界点了那就必须使用 {@link java.util.LinkedList}
 *
 * 4.写操作 {@link java.util.LinkedList#add(Object)} 还是 {@link java.util.ArrayList#add(Object)}
 * 两者在增加元素时性能上基本没有什么差别。
 * 区别只是在增加时 {@link java.util.LinkedList} 生成了一个 {@link java.util.LinkedList.Node} 元素，其{@link java.util.LinkedList.Node.prev} 指向倒数第二个 {@link java.util.LinkedList.Node}，{@link java.util.LinkedList.Node.next}置空；
 * 而 {@link java.util.ArrayList} 则是把元素追加到了数组中而已，
 * 两者的性能差别非常微小
 */
public class Cp {
}
