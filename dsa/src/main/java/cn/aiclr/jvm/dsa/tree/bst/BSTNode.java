package cn.aiclr.jvm.dsa.tree.bst;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
@ToString(exclude = {"left", "right"})
public class BSTNode {

    int val;
    BSTNode left;
    BSTNode right;

    public BSTNode(int val) {
        this.val = val;
    }

    /**
     * @param root 根节点
     * @param val  待删除目标值
     */
    public static BSTNode del(BSTNode root, int val) {
        if (root == null) return null;
        if (val < root.val) {
            root.left = del(root.left, val);
        } else if (val > root.val) {
            root.right = del(root.right, val);
        } else {
            //找到待删除节点
            // 情况1: 无子节点 或 只有一个子节点
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            // 情况2: 有两个子节点
            // 找右子树中的最小值（中序后继）
            BSTNode min = findMin(root.right);
            root.val = min.val; // *** 拷贝值 ***
            root.right = del(root.right, min.val); // 删除后继

            // 情况2: 有两个子节点
            // 找左子树最大值（中序前驱）
//            Node max = findMin(root.left);
//            root.val = max.val; // *** 拷贝值 ***
//            root.left = del(root.left, max.val); // 删除后继
        }
        return root;
    }

    public static BSTNode findMin(BSTNode root) {
        while (root.left != null)
            root = root.left;
        return root;
    }

    public static BSTNode findMax(BSTNode root) {
        while (root.right != null)
            root = root.right;
        return root;
    }

    /**
     * 在 BST 中查找是否存在值为 val 的节点：利用 BST 的有序性，递归比较当前节点值与目标值
     *
     * @param root  根节点
     * @param value 希望删除节点的值
     * @return 找到并返回该节点，否则返回 null
     */
    public static BSTNode search(BSTNode root, int value) {
        if (root == null || root.val == value) {
            return root;
        }
        if (value < root.val) {
            return search(root.left, value);
        } else {
            return search(root.right, value);
        }
    }

    /**
     * 在 BST 中查找是否存在值为 val 的节点：利用 BST 的有序性，迭代比较当前节点值与目标值
     *
     * @param root  根节点
     * @param value 希望删除节点的值
     * @return 找到并返回该节点，否则返回 null
     */
    public static BSTNode searchByWhile(BSTNode root, int value) {
        while (root != null && root.val != value) {
            if (value < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return root;
    }

    /**
     * @param value 要查找的值
     * @return 要删除的节点的父节点，没有返回null
     */
    public BSTNode searchParent(int value) {

        if (
                (this.left != null && this.left.val == value)
                        ||
                        (this.right != null && this.right.val == value)
        ) {
            return this;
        } else {
            //小于当前节点的值，并且左子节点不为 null，则往左递归
            if (value < this.val && this.left != null) {
                return this.left.searchParent(value);
            } else if (value > this.val && this.right != null) {
                return this.right.searchParent(value);
            }
            //没有父节点
            return null;
        }
    }


    /**
     * 从根开始，找到合适的“空位”插入。 递归
     *
     * @param root 根节点
     * @param node 待插入节点
     */
    public static BSTNode insert(BSTNode root, BSTNode node) {
        if (root == null) {
            return node;
        }
        if (node.val < root.val) {
            //待插入数小于当前节点，应该往左子树方向
            root.left = insert(root.left, node);
        } else if (node.val > root.val) {
            //待插入数大于当前节点，应该往右子树方向
            root.right = insert(root.right, node);
        } else {
            log.warn("不插入重复节点 {} ", node);
        }
        return root;
    }

    /**
     * 从根开始，找到合适的“空位”插入。 迭代
     *
     * @param root 根节点
     * @param node 待插入节点
     */
    public static BSTNode insertByWhile(BSTNode root, BSTNode node) {
        if (root == null) {
            return node;
        }

        BSTNode cur = root;

        while (true) {
            if (node.val < cur.val) {
                if (cur.left == null) {
                    cur.left = node;
                    break;
                }
                cur = cur.left;
            } else if (node.val > cur.val) {
                if (cur.right == null) {
                    cur.right = node;
                    break;
                }
                cur = cur.right;
            } else {
                log.warn("不插入重复节点 {} ", node);
                break;
            }
        }
        return root;
    }

    /**
     * <pre>中序遍历得到有序序列 迭代
     *    4
     * 2    6
     * 1 3  5 7
     */
    public static void midOrder(BSTNode root) {
        Stack<BSTNode> stack = new Stack<>();

        while (root != null) {
            if (root.right != null) {
                stack.push(root.right);
                root = root.right;
                continue;
            }
            if (root.left != null) {
                stack.push(root.left);
                root = root.left;
                continue;
            }
        }
        while (!stack.empty()) {
            log.info("{}", stack.pop());
        }
    }

    /**
     * 中序遍历得到有序序列 递归
     */
    public void midOrder() {
        if (this.left != null) {
            this.left.midOrder();
        }
        log.info("中序遍历：{}", this);
        if (this.right != null) {
            this.right.midOrder();
        }
    }
}
