package cn.aiclr.jvm.dsa.tree.binary;

/**
 * <pre>数组存储二叉树
 *
 * n为下标，
 * 左子树 2n+1
 * 右子树 2n+2
 * 父结点（n-1)/2
 */
public class ArrBinaryTree {
    int[] a;

    public ArrBinaryTree(int[] a) {
        this.a = a;
    }

    /**
     * <pre>前序遍历（Pre-order Traversal）
     * 访问顺序：根 → 左 → 右
     * 应用场景：
     *  复制二叉树
     *  输出表达式树的前缀表达式（如 + A B）
     *  深度优先搜索的典型应用
     */
    public void preOrder(int index) {
        if (a == null || a.length == 0) {
            System.err.println("空数组");
            return;
        }
        System.err.print(a[index]);
        if ((2 * index + 1) < a.length) {
            preOrder(2 * index + 1);
        }
        if ((2 * index + 2) < a.length) {
            preOrder(2 * index + 2);
        }
    }

    /**
     * <pre>中序遍历（In-order Traversal）
     * 访问顺序：左 → 根 → 右
     * 应用场景：
     *  BST 中获取有序数据（二叉搜索树（BST），中序遍历的结果是升序排列）
     *  验证是否为二叉搜索树
     *  表达式树的中缀表达式（如 A + B）
     */
    public void midOrder(int index) {
        if (a == null || a.length == 0) {
            System.err.println("空数组");
            return;
        }
        if ((2 * index + 1) < a.length) {
            midOrder(2 * index + 1);
        }
        System.err.print(a[index]);
        if ((2 * index + 2) < a.length) {
            midOrder(2 * index + 2);
        }
    }

    /**
     * <pre>后序遍历（Post-order Traversal）
     * 访问顺序：左 → 右 → 根
     * 应用场景：
     *  删除二叉树（先删子节点，再删根）
     *  计算目录大小（先算子目录，再累加）
     *  表达式树的后缀表达式（逆波兰表达式，如 A B +）
     */
    public void postOrder(int index) {
        if (a == null || a.length == 0) {
            System.err.println("空数组");
            return;
        }
        if ((2 * index + 1) < a.length) {
            postOrder(2 * index + 1);
        }
        if ((2 * index + 2) < a.length) {
            postOrder(2 * index + 2);
        }
        System.err.print(a[index]);
    }
}
