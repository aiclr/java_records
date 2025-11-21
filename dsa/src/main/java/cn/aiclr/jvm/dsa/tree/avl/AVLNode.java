package cn.aiclr.jvm.dsa.tree.avl;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(exclude = {"left", "right"})
public class AVLNode {

    int val;
    public AVLNode left;
    public AVLNode right;

    public AVLNode(int val) {
        this.val = val;
    }

    /**
     * <pre>左旋转  此方法应该设置为 private 在添加结点的时候使用
     * 2.1）左子树的深度 < 右子树的深度 ---- 左旋转：将左子树的深度增加（可参考ipad上的图解）
     * 2.1.1）先创建一个临时结点 temp = 当前结点的值（root.value）
     * 2.1.2）temp 的左子树 = 当前结点的左子树（root.left）
     * 2.1.3）temp 的右子树 = 当前结点的右子树的左子树（root.right.left）
     * 2.1.4）把当前结点的值换为当前结点的右子结点的值,(root.value=root.right.value)
     * 2.1.5) 把当前结点的右子树设置为当前结点的右子树的右子树（root.right=root.right.right）
     * 2.1.6）把当前结点的左子树设置为临时结点(root.left=temp)
     */
    private void leftRotate() {
        AVLNode temp = new AVLNode(val);
        temp.left = left;
        temp.right = right.left;
        val = right.val;
        right = right.right;
        left = temp;
        log.info("左旋转");

    }

    /**
     * 右旋转
     */
    private void rightRotate() {
        AVLNode temp = new AVLNode(val);
        temp.right = right;
        temp.left = left.right;
        val = left.val;
        left = left.left;
        right = temp;
        log.info("右旋转");
    }

    /**
     * @return 左子树的深度
     */
    public int getLeftDeep() {
        if (left == null) {
            return 0;
        }
        return left.getDeep();
    }

    /**
     * @return 右子树的深度
     */
    public int getRightDeep() {
        if (right == null) {
            return 0;
        }
        return right.getDeep();
    }


    /**
     * @return 返回当前结点的深度
     */
    public int getDeep() {
        return Math.max(left == null ? 0 : left.getDeep(), right == null ? 0 : right.getDeep()) + 1;
    }

    /**
     * @param value 希望删除的结点的值
     * @return 找到返回该结点，否则返回null
     */
    public AVLNode search(int value) {
        //找到
        if (value == this.val) {
            return this;
        } else if (value < this.val) {//查找的值，小于当前结点的值，应该往左子树递归
            //如果左子树为空则返回
            if (this.left == null) {
                return null;
            }
            //左子树不为null则递归
            return this.left.search(value);
        } else {//查找的值，大于当前结点的值，应该往右子树递归
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * @param value 要查找的值
     * @return 要删除的结点的父结点，没有返回null
     */
    public AVLNode searchParent(int value) {

        if ((this.left != null && this.left.val == value)
                ||
                (this.right != null && this.right.val == value)) {
            return this;
        } else {
            //小于当前结点的值，并且左子结点不为null，则往左递归
            if (value < this.val && this.left != null) {
                return this.left.searchParent(value);
            } else if (value > this.val && this.right != null) {
                return this.right.searchParent(value);
            }
            return null;//没有父结点
        }
    }


    /**
     * 从 root 结点开始，递归
     *
     * @param n 待插入数据
     */
    public void add(AVLNode n) {
        if (n == null) {
            return;
        }
        //待插入数小于当前结点，应该往左子树方向
        if (n.val < this.val) {
            if (this.left == null) {
                this.left = n;
            } else {
                //往左递归
                this.left.add(n);
            }
        } else {//待插入数大于当前结点，应该往右子树方向
            if (this.right == null) {
                this.right = n;
            } else {
                //往右递归
                this.right.add(n);
            }
        }
        //当添加完一个结点后，如果右子树深度-左子树深度>1,即当前二叉排序树不平衡，左旋转将BST转换为AVL
        if (getRightDeep() - getLeftDeep() > 1) {
            //参考ipad笔记
            // 当整体需要往左旋转时，（右侧树更深，此时需要判断一下右子树的右子树和右子树的左子树的深度）
            // 如果当前结点的右子树的左子树深度大于右子树的右子树深度，
            // 先把右子树进行一个右旋转，将右子树转换成，右子树的右子树深度>右子树的左子树深度（等于不需要考虑，无意义）
            // 再对当前结点进行左旋转
            if (right != null && right.getRightDeep() < right.getLeftDeep()) {
                //左右双旋
                right.rightRotate();
                leftRotate();
            } else {
                leftRotate();
            }
            //此处return,就不会再走下面的逻辑了
            return;
        }
        if (getLeftDeep() - getRightDeep() > 1) {
            // 当整体需要往右旋转时，（左侧树更深，此时需要判断一下左子树的左子树和左子树的右子树的深度）
            // 如果当前结点的左子树的右子树深度大于左子树的左子树深度，
            // 先把左子树进行一个左旋转，将左子树转换成，左子树的左子树深度>左子树的右子树深度（等于不需要考虑，无意义）
            // 再对当前结点进行右旋转
            if (left != null && left.getRightDeep() > left.getLeftDeep()) {
                //右左双旋
                left.leftRotate();
                rightRotate();
            } else {
                rightRotate();
            }
        }
    }

    /**
     * 中序遍历
     */
    public void midOrder() {
        if (this.left != null) {
            this.left.midOrder();
        }
        log.info("{}", this);
        if (this.right != null) {
            this.right.midOrder();
        }
    }
}
