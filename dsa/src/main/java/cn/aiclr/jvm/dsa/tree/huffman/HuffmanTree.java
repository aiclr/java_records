package cn.aiclr.jvm.dsa.tree.huffman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * 哈夫曼树（Huffman Tree），又称最优二叉树，是一种带权路径长度（WPL, Weighted Path Length）最短的二叉树，广泛应用于数据压缩（如 ZIP、JPEG、MP3 等）中。
 *
 * 路径长度：从树中一个节点到另一个节点之间的分支数
 * 树的路径长度：从根节点到每个节点的路径长度之和
 * 带权路径长度（WPL）：设树中有 n 个叶子节点，每个叶子节点有权值 wi，从根到该叶子的路径长度为 li，WPL= w1*l1 + w2*l2 + ... + wn*ln
 * 哈夫曼树：在所有带相同权值的叶子节点的二叉树中，WPL 最小的那棵树
 *
 * wpl 最小的树即是 huffmanTree
 * wpl: Weighted Path Length of Tree 树的所有叶子节点的带权路径长度之和，称为树的带权路径长度表示为 WPL
 *
 * 第一层     1
 * 第二层   2   3
 * 第三层  4 5 6 7
 * 第四层 8 9
 * eg：8
 * 权 = 节点值 = 8
 * 路径 = 根节点到该节点的深度 = 层数 - 1 = 4 - 1 = 3
 * 带权路径 = 权 × 路径 = 8 × 3 = 24
 * wpl=8×3+9×3+5×2+6×2+7×2=87
 */
public class HuffmanTree {

    private static final Logger log = LoggerFactory.getLogger(HuffmanTree.class);

    public static void preOrder(HuffmanNode root) {
        if (root == null) {
            log.info("空树");
            return;
        }
        root.postOrder();
    }

    /**
     * <pre>构造哈夫曼树（贪心算法）
     * 步骤（以权值集合为例，如 {5, 29, 7, 8, 14, 23, 3, 11}）：
     *
     * 1. 将每个权值视为一个独立的节点（单节点树），组成一个森林。
     * 2. 从森林中选出两个权值最小的树。
     * 3. 创建一个新节点，作为这两个树的父节点，其权值为二者之和。
     * 4. 将新树放回森林，重复步骤 2–3，直到只剩一棵树。
     * 5. 这棵树就是哈夫曼树。
     * 注意：每次合并都选最小的两个，这是典型的贪心策略。
     *
     * 所有权值均在叶子节点
     *
     * @param a 权值数组 int array
     * @return root node
     */
    public static HuffmanNode createHuffmanTree(int[] a) {
        List<HuffmanNode> nodeLis = new ArrayList<>();
        for (int value : a) {
            nodeLis.add(new HuffmanNode(value));
        }
        while (nodeLis.size() > 1) {

            //从小到大
            Collections.sort(nodeLis);
            log.info("{}", nodeLis);

            //取出权值最小的节点
            HuffmanNode leftNode = nodeLis.get(0);
            //取出权值次小的节点
            HuffmanNode rightNode = nodeLis.get(1);

            HuffmanNode parent = new HuffmanNode(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            //从集合中删除处理过的节点
            nodeLis.remove(leftNode);
            nodeLis.remove(rightNode);
            nodeLis.add(parent);
        }
        return nodeLis.get(0);
    }

}

