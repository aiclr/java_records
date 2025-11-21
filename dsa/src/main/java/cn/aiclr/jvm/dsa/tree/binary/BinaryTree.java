package cn.aiclr.jvm.dsa.tree.binary;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>二叉树
 * 前中后遍历 --- 看父结点输出位置
 */
@Setter
public class BinaryTree {

    private static final Logger log = LoggerFactory.getLogger(BinaryTree.class);

    public HeroNode root;

    public void del(int id) {
        if (root != null) {
            if (root.id == id) {
                root = null;
            } else {
                root.del(id);
            }
        } else {
            log.warn("空树");
        }
    }

    public HeroNode find(int id, int mark) {
        if (root != null) {
            return switch (mark) {
                case 0 -> root.preFind(id);
                case 1 -> root.midFind(id);
                default -> root.postFind(id);
            };
        }
        return null;
    }

    /**
     * <pre>前序遍历（Pre-order Traversal）
     * 访问顺序：根 → 左 → 右
     * 应用场景：
     *  复制二叉树
     *  输出表达式树的前缀表达式（如 + A B）
     *  深度优先搜索的典型应用
     */
    public void pre() {
        if (root != null) {
            root.preOrder();
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
    public void mid() {
        if (root != null) {
            root.midOrder();
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
    public void post() {
        if (root != null) {
            root.postOrder();
        }
    }
}
