package cn.aiclr.jvm.dsa.tree.bst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>二叉排序树（Binary Search Tree, BST），又称二叉查找树或二叉搜索树，
 *
 * BST 的魅力在于其结构天然支持有序操作（如中序遍历得到有序序列），是理解更高级数据结构（如 AVL、红黑树、B 树）的基础。
 *
 * 尽量避免相同值
 *
 * 二叉排序树（Binary Search Tree, BST），又称二叉查找树或二叉搜索树，是一种重要的数据结构，支持高效的插入、删除、查找操作。其核心性质是：
 * 对于任意一个节点：
 *  1. 左子树中所有节点的值 小于 该节点的值。
 *  2. 右子树中所有节点的值 大于 该节点的值。
 *  3. 左右子树也分别是二叉排序树。
 *
 *  查找：在 BST 中查找是否存在值为 val 的节点：利用 BST 的有序性，递归或迭代比较当前节点值与目标值
 *  插入：将值为 val 的节点插入到 BST 中，保持 BST 性质：从根开始，找到合适的“空位”插入。
 *  删除：
 *      1. 被删节点是叶子（无子节点）    直接删除
 *      2. 被删节点只有左子树或右子树    用子树替代该节点
 *      3. 被删节点有左右两个子树        用中序后继（右子树最小值）或中序前驱（左子树最大值）替代，然后删除那个后继/前驱
 *
 * 时间复杂度总结
 *  操作  平均情况    最坏情况
 *  查找  O(log n)    O(n)
 *  插入  O(log n)    O(n)
 *  删除  O(log n)    O(n)
 *
 * 最坏情况发生在树严重不平衡时（如插入有序数据），此时 BST 退化为链表
 *
 * 为避免 BST 退化，实际工程中常使用自平衡二叉搜索树：
 *  1. AVL 树：严格平衡，插入删除可能频繁旋转
 *  2. 红黑树：近似平衡，STL 中的 map、set 常用
 *  3. Splay 树：伸展树，适合访问局部性场景
 * 这些结构在插入/删除时自动调整，保证树高为 O(log n)，从而保证操作效率
 */
public class BinarySortTree {

    private static final Logger log = LoggerFactory.getLogger(BinarySortTree.class);

    private BSTNode root;

    public void del(int val) {
        root = BSTNode.del(root, val);
    }

    //查删除节点
    public BSTNode search(int val) {
//        return Node.search(root, val);
        return BSTNode.searchByWhile(root, val);
    }

    /**
     * 从根开始，找到合适的“空位”插入。
     *
     * @param node 待插入节点
     */
    public void add(BSTNode node) {
//        root = Node.insert(root, node);
        root = BSTNode.insertByWhile(root, node);
    }

    /**
     * 中序遍历 天然有序
     */
    public void midOrder() {
        if (root != null) {
            root.midOrder();
        } else {
            log.warn("空树");
        }
//        Node.midOrder(root);
    }
}
