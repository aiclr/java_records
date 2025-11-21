package cn.aiclr.jvm.dsa.tree.avl;

/**
 * <pre>
 * 平衡二叉树（Balanced Binary Tree）是一类高度平衡的二叉搜索树（BST），
 * 其核心目标是避免普通二叉搜索树在极端情况下退化为链表（时间复杂度从 O(log n) 退化为 O(n)）。
 *
 * 最常见的平衡二叉树是 AVL 树（Adelson-Velsky and Landis Tree）
 *
 * 平衡二叉树 = 二叉搜索树 + 平衡约束
 * AVL 树通过旋转维持平衡，保证 O(log n) 操作
 * 适用于查找频繁、数据静态或变化较少的场景
 * 实现需维护高度和平衡因子，代码较复杂
 *
 * 在实际工程中，红黑树更常用（如 Linux 内核、STL），但 AVL 树在理论教学和某些高性能查找场景中仍有价值。
 *
 * 一、基本概念
 * 1. 平衡因子（Balance Factor, BF）
 * 对任意节点，定义其平衡因子为：BF = 左子树高度 − 右子树高度
 * 在 AVL 树中，每个节点的平衡因子只能是 -1、0 或 1。
 * 若插入或删除导致某节点 BF 超出该范围，则需通过旋转操作恢复平衡。
 *
 * 2. 高度平衡
 * 一棵 AVL 树的高度 h 与节点数 n 满足：h=O(log n)
 * 因此，查找、插入、删除操作的时间复杂度均为: O(log n)。
 *
 *
 * 二、AVL 树的旋转操作
 * 当插入或删除破坏平衡时，需在最小不平衡子树的根节点处进行旋转。共有四种情况：
 * 1 LL（左左）	新节点插入在左孩子的左子树	右旋（Right Rotation）
 * 2 RR（右右）	新节点插入在右孩子的右子树	左旋（Left Rotation）
 * 3 LR（左右）	新节点插入在左孩子的右子树	先左旋再右旋（Left-Right）
 * 4 RL（右左）	新节点插入在右孩子的左子树	先右旋再左旋（Right-Left）
 *
 * 1 右旋
 *      A                B
 *     /                / \
 *    B       →        C   A
 *   /
 *  C
 * 2 左旋
 *     A                 B
 *     \                / \
 *      B      →       A   C
 *       \
 *        C
 * 3.1 直接右旋
 *      A                B
 *     /                  \
 *    B       →            A
 *     \                  /
 *      C                C
 * 3.2 左子树先左旋，整体再右旋
 *      A       A       C
 *     /       /       / \
 *    B    →  C   ->  B   A
 *     \     /
 *      C   B
 * 4.1 直接左旋
 *    A                B
 *     \              /
 *      B      →     A
 *     /             \
 *    C               C
 * 4.2 右子树先右旋，整体再左旋
 *    A      A         C
 *     \      \       / \
 *      B  →   C  -> A   B
 *     /        \
 *    C          B
 *
 * 三、AVL 树的插入操作
 * 1 按 BST 规则插入新节点；
 * 2 从插入点向上回溯，更新每个祖先节点的高度；
 * 3 检查每个节点的平衡因子；
 * 4 若发现不平衡，根据类型进行旋转；
 * 5 旋转后子树高度恢复，无需继续向上检查（AVL 的重要性质）。
 *
 * 四、删除操作（略复杂）
 * 1 按 BST 删除（三种情况：叶子、单孩子、双孩子）；
 * 2 向上回溯，更新高度；
 * 3 检查平衡因子；
 * 4 若不平衡，进行旋转（注意：删除后可能需要多次旋转，因为高度变化可能向上传播）。
 *
 * AVL 树 vs 红黑树
 * 特性	      AVL 树	                红黑树
 * 平衡程度	 更严格（高度差 ≤1）	    较宽松（最长路径 ≤ 2×最短）
 * 查找性能	 更快（树更矮）	        稍慢
 * 插入/删除	 旋转更多，开销大	        旋转较少，更高效
 * 应用场景	 查找密集型（如数据库索引）	插入/删除频繁（如 C++ std::map 用红黑树）
 */
public class AVLTreePro {

    private AVLNodePro root;

    // 获取节点高度（空节点高度为 0）
    private int height(AVLNodePro node) {
        return node == null ? 0 : node.height;
    }

    // 获取平衡因子：左子树高度 - 右子树高度
    private int getBalance(AVLNodePro node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // 更新节点高度
    private void updateHeight(AVLNodePro node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    
    /**
     * 右旋转（用于 Left-Left 情况） 增加右子树深度
     * @param oldRoot 旧根节点
     * @return 新根节点 oldRoot.left
     */
    private AVLNodePro rotateRight(AVLNodePro oldRoot) {
        AVLNodePro newRoot = oldRoot.left;//新根节点 为旧根节点的左子节点
        AVLNodePro newRootRight = newRoot.right;//临时保存新根节点的右子节点（会被旧根节点代替），后续需要将其挂到旧根节点的左节点

        // 执行旋转l
        newRoot.right = oldRoot;//旧根节点 挂到新根节点的右子节点
        oldRoot.left = newRootRight;//临时保存的新根节点的右子节点，挂载到旧根节点的左子树

        // 更新高度（先更新 y，再更新 x）
        updateHeight(oldRoot);
        updateHeight(newRoot);

        return newRoot; // 新的子树根
    }
    
    /**
     * 左旋转（用于 Right-Right 情况）增加左子树深度
     * @param oldRoot 旧根节点
     * @return 新根节点 oldRoot.right
     */
    private AVLNodePro rotateLeft(AVLNodePro oldRoot) {
        AVLNodePro newRoot = oldRoot.right;//新根节点 为旧根节点的右子节点
        AVLNodePro newRootLeft = newRoot.left;//临时保存新根节点的左子节点（会被旧根节点代替），后续需要将其挂到旧根节点的右节点

        // 执行旋转
        newRoot.left = oldRoot;//旧根节点 挂到新根节点的左子节点
        oldRoot.right = newRootLeft;//临时保存的新根节点的左子节点，挂载到旧根节点的右子树

        // 更新高度
        updateHeight(oldRoot);
        updateHeight(newRoot);

        return newRoot; // 新的子树根
    }

    // 插入节点
    public AVLNodePro insert(AVLNodePro node, int key) {
        // 1. 标准 BST 插入
        if (node == null) {
            return new AVLNodePro(key);
        }

        if (key < node.val) {
            node.left = insert(node.left, key);
        } else if (key > node.val) {
            node.right = insert(node.right, key);
        } else {
            // 不允许重复键
            return node;
        }

        // 2. 更新当前节点高度
        updateHeight(node);

        // 3. 获取平衡因子
        int balance = getBalance(node);

        // 4. 如果失衡，进行四种情况处理
        if (balance > 1) {
            //1. 左子树-右子树 > 1 需要右旋增加右子树深度
            if(key > node.left.val){
                //插入的值 > 根节点的左子节点，插入位置为左子节点的右侧，必使左子节点的右子树深度 > 左子节点的左子树深度
                //如果仅做简单整体右旋，左子节点的右子树会挂到旧根节点的左子树位置，这会导致新树出现 左子树-右子树 < -1 的情况。
                //所以需要先将左子树进行左旋，再对整体右旋
                //1.1 左子树左旋
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            //2. 左子树-右子树 < -1 需要左旋增加左子树深度
            if(key < node.right.val){
                //插入的值 < 根节点的右子节点，插入位置为右子节点的左侧，必使右子节点的左子树深度 > 右子节点的右子树深度
                //如果仅做简单整体左旋，右子节点的左子树会挂到旧根节点的右子树位置，这会导致新树出现 左子树-右子树 > 1 的情况。
                //所以需要先将右子树进行右旋，再对整体左旋
                //2.1. 右子树右旋
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    // 删除节点
    public AVLNodePro delete(AVLNodePro node, int key) {
        // 1. 标准 BST 删除
        if (node == null) return null;

        if (key < node.val) {
            node.left = delete(node.left, key);
        } else if (key > node.val) {
            node.right = delete(node.right, key);
        } else {
            // 找到要删除的节点
            if (node.left == null || node.right == null) {
                // 只有一个子节点或无子节点
                AVLNodePro child = (node.left != null) ? node.left : node.right;
                if (child == null) {
                    node = null;
                } else {
                    node = child; // 替换为子节点
                }
            } else {
                // 有两个子节点：找中序后继（右子树最小值）
                AVLNodePro successor = findMin(node.right);
                node.val = successor.val;
                node.right = delete(node.right, successor.val);
            }
        }

        // 如果删除后节点为空，直接返回
        if (node == null) return null;

        // 2. 更新高度
        updateHeight(node);

        // 3. 获取平衡因子
        int balance = getBalance(node);

        // 4. 四种失衡情况处理
        // 4. 如果失衡，进行四种情况处理
        if (balance > 1) {
            //1. 左子树-右子树 > 1 需要右旋增加右子树深度
            if(getBalance(node.left) < 0){
                //左子节点的左子树深度 - 左子节点的右子树深度 < 0
                //如果仅做简单整体右旋，左子节点的右子树会挂到旧根节点的左子树位置，这会导致新树出现 左子树-右子树 < -1 的情况。
                //所以需要先将左子树进行左旋，再对整体右旋
                //1.1 左子树左旋
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            //2. 左子树-右子树 < -1 需要左旋增加左子树深度
            if(getBalance(node.right) > 0){
                //右子节点的左子树深度 - 右子节点的右子树深度 > 0
                //如果仅做简单整体左旋，右子节点的左子树会挂到旧根节点的右子树位置，这会导致新树出现 左子树-右子树 > 1 的情况。
                //所以需要先将右子树进行右旋，再对整体左旋
                //2.1. 右子树右旋
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    // 查找最小值节点（用于后继）
    private AVLNodePro findMin(AVLNodePro node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 查找节点
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(AVLNodePro node, int key) {
        if (node == null) return false;
        if (key == node.val) return true;
        return key < node.val ?
                searchRecursive(node.left, key) :
                searchRecursive(node.right, key);
    }

    // 中序遍历（输出有序序列）
    public void inorder(AVLNodePro node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.val + " ");
            inorder(node.right);
        }
    }

    // 前序遍历（用于打印结构）
    public void preorder(AVLNodePro node) {
        if (node != null) {
            System.out.print(node.val + "(" + node.height + ") ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    // 插入接口
    public void insert(int key) {
        root = insert(root, key);
    }

    // 删除接口
    public void delete(int key) {
        root = delete(root, key);
    }

    // 获取根节点（用于遍历）
    public AVLNodePro getRoot() {
        return root;
    }
}
