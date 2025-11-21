package cn.aiclr.jvm.dsa.tree.avl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>平衡二叉搜索树(Balanced Binary Search Tree, BBST)是一种自平衡的二叉搜索树，
 * 它能自动保持树的高度平衡，从而保证查找、插入和删除操作的时间复杂度稳定在 O(log n)。
 *
 * Java 标准库中没有直接暴露“平衡二叉搜索树”的底层实现类，但提供了基于平衡二叉搜索树的集合类。
 * Java 标准库中 TreeMap 和 TreeSet 就是基于 红黑树（Red-Black Tree） 实现的平衡二叉搜索树。
 *
 * 平衡二叉搜索树是满足以下条件的二叉树：
 *  是一棵二叉搜索树（BST）：
 *      左子树所有节点值 < 根节点值
 *      右子树所有节点值 > 根节点值
 *      左右子树也都是二叉搜索树
 *  是一棵平衡树：
 *      任意节点的左右子树高度差不超过 1（如 AVL 树）
 *      或通过其他机制保持整体平衡（如红黑树）
 *
 * 常见类型：
 *      AVL 树：严格平衡，插入/删除可能需要多次旋转。
 *      红黑树（Red-Black Tree）：近似平衡，插入/删除最多两次旋转，性能更稳定。
 *      Splay 树、Treap、B 树变种等。
 *
 * AVLTree是最早的自平衡二叉搜索树，由 G. M. Adelson-Velsky 和 E. M. Landis 发明。其核心特性是：
 *      任意节点的左右子树高度差（平衡因子）不超过 1。
 *      通过 四种旋转 维持平衡：左旋、右旋、左右双旋、右左双旋。
 *      所有操作（插入、删除、查找）时间复杂度为 O(log n)。
 * eg：
 *        1
 *   2        3
 * 4   5
 * 左深度=2,右深度=1,差 1 是平衡二叉搜索树
 * eg:
 *            1
 *       2        3
 *     4   5
 *   6
 * 左深度=3,右深度=1,差 2，不是平衡二叉搜索树
 *
 * 意义：
 * 为了优化二叉排序树BST的查询效率
 * eg:
 * {1,2,3,4,5}
 * 生成的二叉排序树是
 * 1
 *   2
 *     3
 *       4
 *         5
 * 这样的排序树更像是单链表，
 * 插入速度不影响，
 * 查询速度还没单链表快（比单链表多了判断左子树是否存在的逻辑），所以要使用平衡二叉排序树提升
 *
 *
 * 创建平衡二叉搜索树：
 * 1）先创建 BinarySortTree
 * 2）然后判断是否是平衡二叉排序树
 *   2.1）左子树的深度 < 右子树的深度----左旋转：将左子树的深度增加（可参考ipad上的图解）
 *       2.1.1）先创建一个临时结点 temp = 当前结点的值（root.value）
 *       2.1.2）temp 的左子树 = 当前结点的左子树（root.left）
 *       2.1.3）temp 的右子树 = 当前结点的右子树的左子树（root.right.left）
 *       2.1.4）把当前结点的值换为当前结点的右子结点的值,(root.value=root.right.value)
 *       2.1.5) 把当前结点的右子树设置为当前结点的右子树的右子树（root.right=root.right.right）
 *       2.1.6）把当前结点的左子树设置为临时结点(root.left=temp)
 *   2.2）右子树的深度 < 左子树的深度----右旋转：将右子树的深度增加
 * eg:
 * {1,2,3,4,5}
 * 1
 *
 * 1
 *  2
 *
 * 增加左子树深度
 * 1
 *  2
 *   3
 * 左旋
 *  2
 * 1 3
 *
 *  2
 * 1 3
 *    4
 * 增加左子树深度
 *  2
 * 1 3
 *    4
 *     5
 * 左旋
 *   3
 *  2 4
 * 1   5
 */
@Slf4j
@Getter
public class AVLTree {

    private AVLNode root;

    public void del(int val) {
        if (root == null) {
            return;
        } else {
            AVLNode targetNode = search(val);
            //没找到
            if (targetNode == null) {
                return;
            }
            // 只有一个跟结点，且是目标结点，直接将root置空
            // 等价于没有父结点，不需要查找父结点，直接return
            // 后面删除含有单结点的逻辑，还要考虑是否是root结点，88和99行
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            //父结点
            AVLNode parent = searchParent(val);
            //要删除的是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                //判断要删除的是父结点的左子结点还是右子结点
                if (parent.left != null && parent.left.val == val) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.val == val) {
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {
                //删除有两个子树的结点---递归
                // 如果想将当前结点的右子树部分提到被删除位置，
                // 则需要找到右子树里的小值，以保证右侧都大于新结点的值（左侧肯定都是小于右侧的）
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.val = minVal;
                // 如果想将当前结点的左子树部分提到被删除位置，
                // 则需要找到左子树里的大值，以保证左侧都小于新结点的值（右侧肯定都是大于左侧的）
//                int maxVal=delLeftTreeMax(targetNode.left);
//                targetNode.val=maxVal;
            } else {
                //有左子树
                if (targetNode.left != null) {
                    if (parent != null) {
                        //判断要删除的是父结点的左子结点还是右子结点
                        if (parent.left != null && parent.left.val == val) {
                            parent.left = targetNode.left;
                        } else if (parent.right != null && parent.right.val == val) {
                            parent.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }
                } else {
                    if (parent != null) {
                        //判断要删除的是父结点的左子结点还是右子结点
                        if (parent.left != null && parent.left.val == val) {
                            parent.left = targetNode.right;
                        } else if (parent.right != null && parent.right.val == val) {
                            parent.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }
                }
            }


        }

    }

    /**
     * 返回以node为根结点的二叉排序树的最小结点的  值
     * 删除node为跟结点的二叉排序树的最小结点
     *
     * @param node 传入的结点
     * @return 返回以node为根结点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(AVLNode node) {
        AVLNode target = node;
        //循环左结点，找最小值
        while (target.left != null) {
            target = target.left;
        }
        //删除最小结点
        del(target.val);
        return target.val;
    }

    /**
     * 返回以node为根结点的二叉排序树的最大结点的  值
     * 删除node为跟结点的二叉排序树的最大结点
     *
     * @param node 传入的结点
     * @return 返回以node为根结点的二叉排序树的最大结点的值
     */
    public int delLeftTreeMax(AVLNode node) {
        AVLNode target = node;
        //循环左结点，找最小值
        while (target.right != null) {
            target = target.right;
        }
        //删除最小结点
        del(target.val);
        return target.val;
    }


    //查删除结点
    public AVLNode search(int val) {
        if (root == null) {
            return null;
        } else {
            return root.search(val);
        }
    }

    //查父结点
    public AVLNode searchParent(int val) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(val);
        }
    }

    public void add(AVLNode node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void midOrder() {
        if (root != null) {
            root.midOrder();
        } else {
            System.err.println("空树");
        }
    }

}
