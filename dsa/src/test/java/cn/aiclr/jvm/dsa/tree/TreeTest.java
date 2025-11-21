package cn.aiclr.jvm.dsa.tree;

import cn.aiclr.jvm.dsa.tree.avl.AVLNode;
import cn.aiclr.jvm.dsa.tree.avl.AVLTree;
import cn.aiclr.jvm.dsa.tree.avl.AVLTreePro;
import cn.aiclr.jvm.dsa.tree.binary.ArrBinaryTree;
import cn.aiclr.jvm.dsa.tree.binary.BinaryTree;
import cn.aiclr.jvm.dsa.tree.binary.HeroNode;
import cn.aiclr.jvm.dsa.tree.bst.BSTNode;
import cn.aiclr.jvm.dsa.tree.bst.BinarySortTree;
import cn.aiclr.jvm.dsa.tree.huffman.HuffmanCoding;
import cn.aiclr.jvm.dsa.tree.huffman.HuffmanTree;
import cn.aiclr.jvm.dsa.tree.threaded.ThreadedBinaryTree;
import cn.aiclr.jvm.dsa.tree.threaded.ThreadedNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@DisplayName("树")
class TreeTest {

    private static final Logger log = LoggerFactory.getLogger(TreeTest.class);

    /**
     * <pre>
     *      0
     *   1     2
     * 3  4  5   6
     */
    @Test
    @DisplayName("二叉树前中后序遍历")
    void testBinaryTree() {
        HeroNode king = new HeroNode(0, "king");
        HeroNode caddy = new HeroNode(1, "caddy");
        HeroNode jack = new HeroNode(2, "jack");
        HeroNode lync = new HeroNode(3, "lync");
        HeroNode bob = new HeroNode(4, "bob");
        HeroNode tom = new HeroNode(5, "tom");
        HeroNode jerry = new HeroNode(6, "jerry");
        king.left = caddy;
        king.right = jack;
        caddy.left = lync;
        caddy.right = bob;
        jack.left = tom;
        jack.right = jerry;
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.setRoot(king);

        log.info("0134256");
        binaryTree.pre();
        log.info("3140526");
        binaryTree.mid();
        log.info("3415620");
        binaryTree.post();

        log.info("{}", binaryTree.find(5, 0));
        log.info("{}", binaryTree.find(5, 1));
        log.info("{}", binaryTree.find(5, 2));

        binaryTree.del(1);
        log.info("0256");
        binaryTree.pre();
    }

    @Test
    @DisplayName("中序线索化二叉树")
    void testThreadedBinaryTree() {
        ThreadedNode king = new ThreadedNode(0, "king");
        ThreadedNode caddy = new ThreadedNode(1, "caddy");
        ThreadedNode jack = new ThreadedNode(2, "jack");
        ThreadedNode lync = new ThreadedNode(3, "lync");
        ThreadedNode bob = new ThreadedNode(4, "bob");
        ThreadedNode tom = new ThreadedNode(5, "tom");
        ThreadedNode jerry = new ThreadedNode(6, "jerry");
        king.left = caddy;
        king.right = jack;
        caddy.left = lync;
        caddy.right = bob;
        jack.left = tom;
        jack.right = jerry;
        ThreadedBinaryTree binaryTree = new ThreadedBinaryTree();
        binaryTree.setRoot(king);
        log.info("3140526");
        binaryTree.mid();
        /*
         * <pre> 线索化前
         *      0
         *   1     2
         * 3  4  5   6
         */
        binaryTree.threadedNodes();
        /*
         * <pre>线索化后
         *           0
         *     1           2
         *  3    4     5      6
         *   1 1  0  0   2  2
         *
         */
        log.info("线索化后序遍历");
        binaryTree.threadedShow();
        log.info("根结点:{} \nleft: {} \nright:{}", king, king.left, king.right);
        log.info("lync:{} \nleft: {} \nright:{}", lync, lync.left, lync.right);
        log.info("bob:{} \nleft: {} \nright:{}", bob, bob.left, bob.right);
        log.info("tom:{} \nleft: {} \nright:{}", tom, tom.left, tom.right);
        log.info("jerry:{} \nleft: {} \nright:{}", jerry, jerry.left, jerry.right);
    }

    /**
     * <pre>数组存储二叉树
     *
     * n为下标，
     * 左子树 2n+1
     * 右子树 2n+2
     * 父节点（n-1)/2
     *
     *       0
     *    1     2
     *  3  4  5   6
     */
    @DisplayName("数组存储二叉树")
    @Test
    void testArrBinaryTree() {
        int[] a = {0, 1, 2, 3, 4, 5, 6};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(a);
        arrBinaryTree.preOrder(0);
        System.err.println();
        log.info("0134256");
        arrBinaryTree.midOrder(0);
        System.err.println();
        log.info("3140526");
        arrBinaryTree.postOrder(0);
        System.err.println();
        log.info("3415620");
    }

    /**
     * <pre>
     *      7
     *    3   10
     *  1  5 9  12
     * 2
     */
    @DisplayName("二叉排序树-BST")
    @Test
    void testBinarySortTree() {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 12, 2};
        BinarySortTree tree = new BinarySortTree();

        for (int i : arr) {
            tree.add(new BSTNode(i));
        }

        /*
         *     7
         *   3   10
         * 1  5 9  12
         *  2
         */
        tree.midOrder();
        log.info("中序遍历获得有序序列: 1,2,3,5,7,9,10,12");


        log.info("删除不存在节点13");
        Assertions.assertNull(tree.search(13));
        tree.del(13);

        //叶子
        log.info("删除无子节点的叶子2");
        tree.del(2);
        /*
         *     7
         *   3   10
         * 1  5 9  12
         */
        tree.midOrder();
        log.info("中序遍历获得有序序列: 1,3,5,7,9,10,12");

        //两个子节点
        log.info("删除有两个子节点的节点10");
        tree.del(10);
        /*
         *     7
         *   3   12
         * 1  5 9
         */
        tree.midOrder();
        log.info("中序遍历获得有序序列: 1,3,5,7,9,12");

        //1个子节点
        log.info("删除有一个子节点的节点12");
        tree.del(12);
        /*
         *     7
         *   3   9
         * 1  5
         */
        tree.midOrder();
        log.info("中序遍历获得有序序列: 1,3,5,7,9");

        log.info("删除根节点");
        tree.del(7);
        /*
         *     9
         *   3
         * 1  5
         */
        tree.del(9);
        /*
         *   3
         * 1  5
         */
        tree.del(3);
        /*
         *   5
         * 1
         */
        tree.del(5);
        /*
         * 1
         */
        tree.del(1);
    }

    static Stream<Arguments> provideData() {
        return Stream.of(
                new int[]{4, 3, 6, 5, 7, 8}   //左子树深度 < 右子树-1,单次旋转增加左子树深度即可
                ,
                new int[]{10, 12, 8, 9, 7, 6} //左子树深度 > 右子树+1,单次旋转增加右子树深度即可
                ,
                new int[]{10, 7, 11, 6, 8, 9}  //需要多次旋转的时候 == 双旋转
        ).map(Arguments::of);
    }

    /**
     * <pre>
     * {4, 3, 6, 5, 7, 8}
     * 4
     *
     *  4
     * 3
     *
     *  4
     * 3 6
     *
     *   4
     * 3   6
     *    5
     *
     *   4
     * 3   6
     *    5 7
     *
     *   4
     * 3   6
     *    5 7
     *       8
     * 左旋
     *    6
     *  4   7
     * 3 5   8
     *
     * {10, 12, 8, 9, 7, 6}
     * 10
     *
     * 10
     *  12
     *
     *   10
     * 8   12
     *
     *   10
     * 8   12
     *  9
     *
     *    10
     *  8   12
     * 7 9
     *
     *     10
     *   8   12
     *  7 9
     * 6
     * 右旋
     *     8
     *  7     10
     * 6    9   12
     *
     * {10, 7, 11, 6, 8, 9}
     * 10
     *
     *  10
     * 7
     *
     *  10
     * 7  11
     *
     *   10
     *  7  11
     * 6
     *
     *   10
     *  7  11
     * 6 8
     *
     *      10
     *   7     11
     * 6   8
     *      9
     *
     * 左子树的右子树深度 大于左子树的左子树 先将左子树左旋
     *       10
     *    8     11
     *  7   9
     * 6
     * 再将整体右旋
     *    8
     *  7   10
     * 6   9 11
     *
     * @param arr 待处理数组
     */
    @DisplayName("平衡二叉排序树-AVLTree")
    @ParameterizedTest
    @MethodSource("provideData")
    void testAVLTree(int[] arr) {
        AVLTree avlTree = new AVLTree();
        for (int i : arr) {
            avlTree.add(new AVLNode(i));
        }

        log.info("中序遍历");
        avlTree.midOrder();

        log.info("根结点深度为{}、左子树深度为{}、右子树深度为{}", avlTree.getRoot().getDeep(), avlTree.getRoot().left.getDeep(), avlTree.getRoot().right.getDeep());
        log.info("根结点是 {}", avlTree.getRoot());
    }

    @DisplayName("平衡二叉排序树-AVLTree")
    @ParameterizedTest
    @MethodSource("provideData")
    void testAVLTreePro(int[] arr) {
        AVLTreePro avlTree = new AVLTreePro();
        for (int i : arr) {
            avlTree.insert(i);
        }

        log.info("中序遍历");
        avlTree.inorder(avlTree.getRoot());
        System.out.println();
        log.info("前序遍历");
        avlTree.preorder(avlTree.getRoot());
        System.out.println();

        log.info("根结点深度为{}、左子树深度为{}、右子树深度为{}", avlTree.getRoot().height, avlTree.getRoot().left.height, avlTree.getRoot().right.height);
        log.info("根结点是 {}", avlTree.getRoot());

        // 查找测试
        log.info("查找 7: {}", avlTree.search(7)); // true
        log.info("查找 100: {}", avlTree.search(100)); // false

        // 删除测试
        log.info("删除节点 7:");
        avlTree.delete(7);

        log.info("中序遍历");
        avlTree.inorder(avlTree.getRoot());
        System.out.println();

        log.info("前序遍历");
        avlTree.preorder(avlTree.getRoot());
        System.out.println();
    }

    /**
     * <pre> 最终树结构
     *         67
     * 29                 38
     *            15             23
     *          7   8       10        13
     *                   4     6
     *                 1   3
     */
    @DisplayName("哈夫曼树-Huffman Tree")
    @Test
    void testHuffmanTree() {
        int[] a = {13, 7, 8, 3, 29, 6, 1};
        HuffmanTree.preOrder(HuffmanTree.createHuffmanTree(a));
        log.info("前序遍历: 67,29,38,15,7,8,23,10,4,1,3,6,13");
    }

    @DisplayName("哈夫曼编码-压缩字符串")
    @ParameterizedTest
    @CsvSource({
            "cs cs cs cs cs cs cs cs",
            "abcdefghjklmn",
            "i like like like java do you like a java java java like java like you",
    })
    void testStringHuffmanCoding(String str) {
        HuffmanCoding.testBit();

        Map<Byte, String> huffmanCodes = new HashMap<>();
        HuffmanCoding huffmanCoding = new HuffmanCoding(huffmanCodes);

        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        log.info("源byte\n{}", data);

        byte[] zip = huffmanCoding.huffmanZip(data);
        log.info("编码byte\n{}", zip);
        //解码
        byte[] decode = huffmanCoding.decode(zip, huffmanCodes);
        Assertions.assertArrayEquals(data, decode);
        Assertions.assertEquals(str, new String(decode));
    }

    /**
     * 图片压缩测试当图片颜色很丰富压缩率不高，当图片很单调时压缩率还可以
     *
     * @param srcFile 源文件
     * @param dstFile 压缩文件
     */
    @DisplayName("哈夫曼编码-压缩文件")
    @ParameterizedTest
    @CsvSource({
            "/home/caddy/Pictures/bg/KUN_1858.NEF,/home/caddy/DSC_0099.zip",//25M,25M 色彩丰富
            "/home/caddy/Pictures/20201203_bak/KUN_1858.NEF,/home/caddy/KUN_1858.NEF.zip",//43M,29M 单调图片
    })
    @Disabled
    void testFileHuffmanCoding(String srcFile, String dstFile) {
        Map<Byte, String> huffmanCodes = new HashMap<>();
        HuffmanCoding huffmanCoding = new HuffmanCoding(huffmanCodes);
        huffmanCoding.zipFile(srcFile, dstFile);

    }


    /**
     * 图片压缩测试当图片颜色很丰富压缩率不高，当图片很单调时压缩率还可以
     *
     * @param srcFile 源文件
     * @param dstFile 压缩文件
     */
    @DisplayName("哈夫曼编码-解压缩文件")
    @ParameterizedTest
    @CsvSource({
            "/home/caddy/KUN_1858.NEF.zip,/home/caddy/KUN_1858.NEF",//29M,43M 文件解压缩
    })
    @Disabled
    void testUnzipFileHuffmanCoding(String srcFile, String dstFile) {
        Map<Byte, String> huffmanCodes = new HashMap<>();
        HuffmanCoding huffmanCoding = new HuffmanCoding(huffmanCodes);
        //文件解压缩
        huffmanCoding.unzipFile(srcFile, dstFile);
    }
}
