package cn.aiclr.jvm.dsa.tree.threaded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>线索化二叉树（Threaded Binary Tree）是一种对普通二叉树的改进结构，
 * 目的是利用空指针域来存储遍历顺序中的前驱或后继信息，从而在不使用递归或栈的情况下实现高效的遍历。
 *
 * 一、背景与动机
 * 在普通的二叉链表存储结构中，每个节点有：
 * left 指针（指向左孩子）
 * right 指针（指向右孩子）
 * 对于 n 个节点的二叉树，总共有 2n 个指针域，但只有 n - 1 个指针被用于连接孩子节点，因此有 n + 1 个空指针域。
 *      A
 *   /     \
 *  B       C
 * / \     / \
 * 共有 2 * 3 = 6个指针域（B <- A、A -> C、null <- B、B -> null、null <- C、C -> null）
 * 3 - 1 = 2 个指针被使用（B <- A、A -> C）
 * 3 + 1 = 4 个空指针域（null <- B、B -> null、null <- C、C -> null）
 *
 * 这些空指针可以被“线索化”（即指向某种遍历顺序下的前驱或后继），从而加速遍历。
 *
 * 二、线索化类型
 * 根据遍历顺序不同，线索化二叉树可分为：
 *
 * 先序线索二叉树
 * 中序线索二叉树（最常用）
 * 后序线索二叉树
 *
 *三、节点结构
 * 线索化二叉树的节点通常增加两个布尔标志位，用于区分指针是指向孩子还是线索：
 * ltag == 0：left 指向左孩子
 * ltag == 1：left 指向中序前驱
 * rtag == 0：right 指向右孩子
 * rtag == 1：right 指向中序后继
 *
 * 四、中序线索化（最常见）
 * 算法思想：
 * 按中序遍历顺序访问节点；
 * 记录前一个访问的节点 pre；
 * 若当前节点 p 的左孩子为空，则 p->left = pre，并设 ltag = 1；
 * 若 pre 的右孩子为空，则 pre->right = p，并设 rtag = 1；
 * 更新 pre = p。
 *
 * 五、线索二叉树的遍历（无需栈/递归）
 * 以中序线索树为例，从最左节点开始，利用线索依次访问后继：
 *
 * 六、优缺点
 * 优点：
 * 节省空间（利用空指针）
 * 遍历无需递归或栈，效率高（O(1) 空间复杂度）
 * 快速获取前驱/后继（对某些应用如迭代器很有用）
 *
 * 缺点：
 * 插入/删除操作复杂（需维护线索）
 * 节点结构更复杂（需额外标志位）
 *
 * 七、应用场景
 * 内存受限环境下的树遍历
 * 需频繁获取前驱/后继的场景（如表达式求值、符号表）
 * 数据库索引结构（某些变体）
 *
 *
 * eg:
 * 线索化二叉树
 * 中序线索化
 * 按照中序遍历顺序，扩展二叉树，
 * 线索化前
 *     0
 *  1     2
 * 3  4  5   6
 * 中序遍历顺序=3140526
 * 3的前驱是空，左子树不用处理，后继是1,判断3右子树，为空则将3的右子树指向1,设置3的右子树类型为后继节点=1
 * 1号，前驱3后继4,但是1号左右子树都不为空，则不处理（此时左右子树类型为左右子树，默认值0）
 * 4号，前驱1,后继0，4号左右子树都为空，所以左右子树类型都设置为节点=1，左子树指向前驱1，右子树指向后继0
 * 0类似1
 * 5类似4
 * 2类似1
 * 6的后继空，右子树不用处理，前驱是2，判断6左子树，为空则将6的左子树指向2，设置6的左子树类型为前驱节点=2
 * 线索化后二叉树扩展为
 *         0
 *    1           2
 * 3    4     5      6
 *  1 1  0  0   2  2
 */
public class ThreadedBinaryTree {

    private static final Logger log = LoggerFactory.getLogger(ThreadedBinaryTree.class);

    public ThreadedNode root;

    //为了实现线索化，创建当前节点的前驱节点，辅助指针
    //递归线索化时，pre 始终是前一个节点
    public ThreadedNode pre = null;

    public void setRoot(ThreadedNode root) {
        this.root = root;
    }

    /**
     * <pre>中序遍历线索化二叉树
     *         0
     *    1          2
     * 3    4     5    6
     *  1 1  0  0  2  2
     */
    public void threadedShow() {
        ThreadedNode node = root;
        // 第一步：找到最左边的节点（中序第一个节点）
        //从左开始找到起始节点 : leftType = 1，中序线索化时，会将初始节点 leftType 设置为 1
        while(node != null && node.leftType == 0){
            node = node.left;
        }
        // 第二步：依次访问后继
        while (node != null) {
            log.info("{}",node);
            if(node.rightType == 1){
                // 如果右指针是线索，则直接跳到后继
                node = node.right;
            }else {
                // 否则，进入右子树，并找到其最左节点
                node = node.right;
                if (node != null) {
                    while (node.left != null && node.leftType == 0) {
                        node = node.left;
                    }
                }
            }
        }
    }

    /**
     * 重载
     */
    public void threadedNodes() {
        this.threadedNodes(root);
    }

    /**
     * 中序线索化
     *
     * @param node 当前需要线索化的节点
     */
    public void threadedNodes(ThreadedNode node) {
        //null不线索化
        if (node == null) {
            return;
        }
        //1 先线索化左子树
        threadedNodes(node.left);

        //2 再线索化当前节点
        //先处理前驱节点 中序遍历的第一个节点的leftType = 1,left = null
        if (node.left == null) {
            node.left = pre;
            node.leftType = 1;
        }
        //后继节点
        if (pre != null && pre.right == null) {
            pre.right = node;
            pre.rightType = 1;
        }
        //每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;
        //3 再线索化右子树
        threadedNodes(node.right);
    }


    public void del(int id) {
        if (root != null) {
            if (root.id == id) {
                root = null;
            } else {
                root.del(id);
            }
        } else {
            log.info("空树");
        }
    }

    public ThreadedNode find(int id, int mark) {
        if (root != null) {
            return switch (mark) {
                case 0 -> root.preFind(id);
                case 1 -> root.midFind(id);
                default -> root.postFind(id);
            };
        }
        return null;
    }

    public void pre() {
        if (root != null) {
            root.preOrder();
        }
    }

    public void mid() {
        if (root != null) {
            root.midOrder();
        }
    }

    public void post() {
        if (root != null) {
            root.postOrder();
        }
    }
}
