package cn.aiclr.jvm.dsa.tree.threaded;

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 节点
 */
@ToString(exclude = {"left", "right"})
public class ThreadedNode {

    private static final Logger log = LoggerFactory.getLogger(ThreadedNode.class);

    public int id;
    public String name;

    public ThreadedNode left;
    public ThreadedNode right;

    //leftType=0 左子树 1前驱节点
    public int rightType;

    //rightType=0 右子树 1后继节点
    public int leftType;


    public ThreadedNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * <pre>后序遍历 适用于删除操作，先定位到要删除的节点，然后后序遍历删除要删除节点以及子节点
     * C/C++ 无GC语言必须先删子节点，再删父节点，避免悬空指针
     * 保证在删除一个节点之前，它的左右子树已经被删除
     * 如果用前序或中序，可能会先删根节点，导致子节点无法访问（内存泄漏）
     *
     * java/go 有GC 只需要删除目标节点即可，目标节点子节点，会被GC回收，不会造成内存泄露
     */
    public void del(int id) {
        if (this.left != null) {
            this.left.del(id);
            if (this.left.id == id) {
                //java/go 有GC 只需要删除目标节点即可，目标节点子节点，会被GC回收，不会造成内存泄露
//                delSub(this.left);
                this.left = null;
                return;
            }
        }
        if (this.right != null) {
            this.right.del(id);
            if (this.right.id == id) {
                //java/go 有GC 只需要删除目标节点即可，目标节点子节点，会被GC回收，不会造成内存泄露
//                delSub(this.right);
                this.right = null;
                return;
            }
        }
    }

    /**
     * 递归删除子树
     *
     * @param node
     */
    public static void delSub(ThreadedNode node) {
        if (node == null) return;
        delSub(node.left);
        delSub(node.right);
        node.left = null;
        node.right = null;
    }

    public ThreadedNode preFind(int id) {
        log.info("pre");
        if (this.id == id) {
            return this;
        }
        ThreadedNode result = null;
        if (this.left != null) {
            result = this.left.preFind(id);
        }
        if (result != null) {
            return result;
        }
        if (this.right != null) {
            result = this.right.preFind(id);
        }
        return result;
    }


    public ThreadedNode midFind(int id) {
        ThreadedNode result = null;
        if (this.left != null) {
            result = this.left.midFind(id);
        }
        if (result != null) {
            return result;
        }
        log.info("mid");
        if (this.id == id) {
            return this;
        }
        if (this.right != null) {
            result = this.right.midFind(id);
        }
        return result;
    }

    public ThreadedNode postFind(int id) {
        ThreadedNode result = null;
        if (this.left != null) {
            result = this.left.postFind(id);
        }
        if (result != null) {
            return result;
        }
        if (this.right != null) {
            result = this.right.postFind(id);
        }
        if (result != null) {
            return result;
        }
        log.info("post");
        if (this.id == id) {
            return this;
        }
        return result;
    }

    /**
     * <pre>前序遍历
     * 先输出当前节点，再输出左节点，再输出右节点
     */
    public void preOrder() {
        log.info("{}", this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * <pre>中序遍历
     * 先输出左节点，再输出当前节点，再输出右节点
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

    /**
     * <pre>后序遍历
     * 先输出左节点，再输出右节点，再输出当前节点
     */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        log.info("{}", this);
    }

}
