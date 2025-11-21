package cn.aiclr.jvm.dsa.tree.huffman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre> 哈夫曼编码（Huffman Coding）是一种用于无损数据压缩的熵编码算法，由 David A. Huffman 在 1952 年提出。
 * 它的核心思想是：出现频率高的字符用较短的编码，出现频率低的字符用较长的编码，从而使得整体编码长度最短。
 *
 * 方法：
 * 1. 以字符出现频率为权值，构建哈夫曼树。
 * 2. 从根到每个叶子的路径即为该字符的编码（左为 0，右为 1，或反之）。
 *
 * 特点：
 * 1. 前缀码（Prefix Code）：没有任何一个编码是另一个编码的前缀（避免歧义，无需分隔符即可解码）。
 * 2. 最优前缀码：总编码长度最短。
 *
 * 性质：
 * 哈夫曼树中只有度为 0（叶子）和度为 2 的节点，没有度为1的节点（一个节点的度 Degree of a Node 是指该节点拥有的子节点（直接后继）的个数）。
 * 若有 n 个叶子节点，则总节点数为 2n−1。
 * 哈夫曼树不唯一（当有相同权值时，合并顺序可能不同），但 WPL 唯一。
 *
 * 应用场景：
 * 广泛用于数据文件压缩，压缩率通常在 20% ~ 90% 之间（重复字越多，压缩率越高，）
 *      ZIP、GZIP、JPEG、MP3 等格式中均有应用（常与其他算法结合）
 *      文件压缩（ZIP、GZIP 等）
 *      图像压缩（JPEG 中的熵编码部分）
 *      网络传输中的数据压缩
 * 通信中的信源编码
 * huffman code 是可变字长编码（VLC）的一种，1952 年提出的编码方法，称之为最佳编码
 *
 * 优点：
 * 1.最优前缀码：在给定频率下，平均码长最短（对于独立符号）。
 * 2.无损压缩：可完全还原原始数据。
 * 3.广泛使用：ZIP、GZIP、JPEG、MP3 等格式中均有应用（常与其他算法结合）。
 *
 * 缺点：
 * 1.需要预先知道字符频率（静态哈夫曼），或需两次扫描（一次统计，一次编码）。
 * 2.对短文本压缩效果有限。
 * 3.不适用于所有数据（如已加密或随机数据，无统计冗余）。
 *
 * 变种:
 * 1.动态哈夫曼编码（如 Vitter 算法）：边编码边更新树，适用于流式数据。
 * 2.自适应哈夫曼编码：无需预先知道频率。
 *
 * eg:
 * 待处理字符串
 * i like like like java do you like a java
 * ascii 编码二进制的长度为 539
 * huffman 编码无损压缩后的长度为 133
 *
 * 统计各字符出现的次数
 * d=1 y=1 u=1 j=2 v=2 o=2 l=4 k=4 e=4 i=5 a=5 空格=9
 * 按照上面字符出现次数，构建一棵 huffmanTree，出现次数 = 权值
 *
 * 构建 huffmanTree 时比较权值的规则不同生成的树也不同，相等的权值怎么排序会影响树的形状
 * 每个字符的编码会不一样
 * 但是 wpl 不会变 === huffman 编码长度不会变 = 133
 *
 * 根据 huffmanTree 进行编码，向左路径为 0,向右路径为 1
 *           40
 *      0/         \1
 *     17           23
 *  0/   1\       0/  1\
 * 8  (空格)9   10     13
 * 。。。。。。
 *
 * 空格 9 的编码即为 01
 * 如果 8 也是某个字符，则 8 的编码为 00
 * 分析 huffmanTree 原有数据，都在叶子节点，每个叶子节点的路径肯定不一样，
 * 这样就不会出现多义编码
 *
 * eg：多义编码
 * 空格 = 01
 * i = 010
 * i空格 = 01001
 * 当解析时，
 * 扫描前 2 位 01,解析成了空格
 * 扫描前 3 位 010,解析成了 i
 * 这就出现了多义
 * huffmanTree 处理后的字符编码，
 * 由于每个字符数据都在叶子节点，
 * 任一字符编码的前几位（父节点），绝对不会是其他字符编码
 */
public class HuffmanCoding {

    private static final Logger log = LoggerFactory.getLogger(HuffmanCoding.class);

    public Map<Byte, String> huffmanCodes;
    public int lastByteLength;

    public HuffmanCoding(Map<Byte, String> huffmanCodes) {
        this.huffmanCodes = huffmanCodes;
    }

    /**
     * @param srcFile 待解压缩文件路径
     * @param dstFile 解压缩后文件路径
     */
    public void unzipFile(String srcFile, String dstFile) {

        try (
                InputStream is = new FileInputStream(srcFile);
                ObjectInputStream ois = new ObjectInputStream(is);
                OutputStream os = new FileOutputStream(dstFile)
        ) {
            byte[] huffmanBytes = (byte[]) ois.readObject();
            Map<Byte, String> huffmanCode = (Map<Byte, String>) ois.readObject();
            byte[] decode = decode(huffmanBytes, huffmanCode);
            //写数据到文件
            os.write(decode);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }


    }


    /**
     * <pre>压缩文件
     * 注意事项：
     *  如果文件本身就是经过压缩处理的，那么 huffman 编码再压缩效率不会有明显变化，比如视频、ppt等
     *  huffman 编码是按字节来处理的，因此可以处理所有的文件
     *  如果一个文件中的内容重复数据不多，压缩效果也不明显
     *
     * @param srcFile 待压缩文件路径
     * @param dstFile 压缩后文件路径
     */
    public void zipFile(String srcFile, String dstFile) {
        try (
                FileInputStream is = new FileInputStream(srcFile);
                FileOutputStream os = new FileOutputStream(dstFile);
                ObjectOutputStream oos = new ObjectOutputStream(os)
        ) {
            byte[] bytes = new byte[is.available()];
            int read = is.read(bytes);
            //压缩文件
            byte[] huffmanZip = huffmanZip(bytes);
            //以  对象流  的方式写入huffman编码，是为了以后恢复源文件使用
            oos.writeObject(huffmanZip);
            oos.writeObject(huffmanCodes);//huffman编码也要写入压缩文件，解码时需要使用
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }


    /**
     * 解码
     *
     * @param zip
     * @return 原来字符串对应的byte数组
     */
    public byte[] decode(byte[] zip, Map<Byte, String> huffmanCode) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < zip.length; i++) {
            if (i == zip.length - 1) {
                builder.append(byteToBitString(zip[i], lastByteLength));
                break;
            }
            builder.append(byteToBitString(zip[i], 8));
        }
//        log.info("使用huffmanCodes解码字符串为\n{}", builder);

        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCode.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        //遍历得到原始字符串的byte[]
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < builder.length(); ) {
            int count = 1;
            boolean bol = true;
            Byte b = null;
            while (bol) {
                //public String substring(int start, int end)  start ,end是下标 [start,end) 包含start ，不包含end
                //abcdef,substring(1,3)==bc
                String str = builder.substring(i, i + count);
                b = map.get(str);
                if (b == null) {
                    count++;
                } else {
                    bol = false;
                }
            }
            list.add(b);
            i += count;
        }
        byte[] by = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            by[i] = list.get(i);
        }
        return by;
    }


    /**
     * <pre>将 byte 转成二进制的字符串
     * 如果原数据的长度不是 8 的倍数，即最后一位的长度不是 8 位，如果首位为 0，防止 0 丢失，需要使用最后一位的长度进行截取
     *
     * @param b    byte
     * @param length 除了压缩后最后一位 byte 长度可能小于8，其余均为 8
     * @return b 对应的二进制补码字符串
     */
    private static String byteToBitString(byte b, int length) {
        // b|256 补高位 防止第一位为 0 时，0丢失
        int tmp = b | 256;
        //返回tmp对应的二进制的补码
        String s = Integer.toBinaryString(tmp);
        return s.substring(s.length() - length);
    }


    /**
     * @param data 原始字符串对应的数组
     * @return 压缩后的数组
     */
    public byte[] huffmanZip(byte[] data) {
        //获取huffmanTree
        DataNode root = createHuffmanTree(getNodes(data));
        //前序遍历huffmanTree
//        log.info("前序遍历huffmanTree");
        preOrder(root);
        //获取huffmanCodes
        //{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
        getCodes(root);
//        log.info("根据huffmanTree获取的huffman编码为：\n{}\n压缩前byte数组长度={}\n压缩前byte数组\n{}", huffmanCodes, data.length, data);
        //使用 huffmanCodes 编码
        byte[] zip = zip(data);
//        log.info("压缩后byte数组长度={}\n压缩后byte数组\n{}", zip.length, zip);
        return zip;
    }

    /**
     * 使用huffmanCode压缩原文
     *
     * @param data 待压缩字符串 的 byte[]40个
     *             [i, ,l,i,k,e, ,l,i,k,e, ,l,i,k,e, ,j,a,v,a, ,d,o, ,y,o,u, ,l,i,k,e, ,a, ,j,a,v,a]
     *             是各字母对应的asci码值
     *             [105, 32, 108, 105, 107, 101, 32, 108, 105, 107, 101, 32, 108, 105, 107, 101, 32, 106, 97, 118, 97, 32, 100, 111, 32, 121, 111, 117, 32, 108, 105, 107, 101, 32, 97, 32, 106, 97, 118, 97]
     * @return 压缩后的 byte[] 17个
     * [8, -121, -88, -121, -88, -121, -87, -115, 47, 110, 72, 122, 115, -68, 36, -114, 92]
     */
    public byte[] zip(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (Byte b : data) {
            builder.append(huffmanCodes.get(b));
        }
        String str = builder.toString();
//        log.info("使用 huffmanCodes 转换后的机器码字符串为：\n{}", str);

        //将编码后的字符串，转成机器码，补码，反码
        // byte 8位，第一位符号位不动 0正1负
        // 1 0101000 求补码(-1)--->1 0100111 求反码---> 1 1011000 转十进制---> -2^6+2^4+2^3=-88
        // -88转机器码 负号占首位，8位10进制 1 1011000求反码---> 1 0100111求补码+1--->10101000即机器码
//        log.info("{}",(byte) Integer.parseInt("10101000", 2));//一定要强制转换为byte

        //现在需要将编码的字符串 8个一组 转换为10进制 存放到 byte[]，这样才能起到压缩的作用

        int strLength = str.length();
        // byte[] 数组长度=(strLength+7)/8
        // 如果是8的倍数,+7不会改变结果 ===> (16+7)/8=2
        // 如果不是8的倍数,+7肯定会使结果+1 ===> (12+7)/8=2
        int byteLength = (strLength + 7) / 8;
        //计算最后一位 byte 长度，解码时防止丢失头部的 0
        lastByteLength = strLength % 8;
        byte[] bytes = new byte[byteLength];
        int index = 0;
        for (int i = 0; i < strLength; i += 8) {
            if (i + 8 < strLength) {
                bytes[index] = (byte) Integer.parseInt(str.substring(i, i + 8), 2);
            } else {
                //最后一个字节 需要补位 防止 0 开头解压数据时丢失0
                bytes[index] = (byte) Integer.parseInt(str.substring(i), 2);
            }
            index++;
        }
        return bytes;
    }


    public void getCodes(DataNode root) {
        if (root == null) {
            return;
        }
        getCodes(root.left, "0", new StringBuilder());
        getCodes(root.right, "1", new StringBuilder());
    }


    /**
     * 递归遍历 huffmanTree,往左为 0,往右为 1,使用 StringBuilder 拼接获取编码
     *
     * @param node          huffmanTree 根节点
     * @param code          路径：左子节点=0,右子节点=1
     * @param stringBuilder 拼接code
     */
    public void getCodes(DataNode node, String code, StringBuilder stringBuilder) {
        StringBuilder builder = new StringBuilder(stringBuilder);
        builder.append(code);
        if (node != null) {
            //非有效节点就去遍历子树
            if (node.data == null) {
                //递归
                getCodes(node.left, "0", builder);
                getCodes(node.right, "1", builder);
            } else {
                //找到有效节点
                huffmanCodes.put(node.data, builder.toString());
            }
        }
    }


    /**
     * 创建哈夫曼树
     *
     * @param nodes 待转换huffmanTree的数据
     * @return huffmanTree根节点
     */
    public static DataNode createHuffmanTree(List<DataNode> nodes) {
        while (nodes.size() > 1) {
            //从小到大
            Collections.sort(nodes);
            //取出权值最小的节点
            DataNode leftNode = nodes.get(0);
            //取出权值次小的节点
            DataNode rightNode = nodes.get(1);

            DataNode parent = new DataNode(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            //从集合中删除处理过的节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * 计算字符出现次数，并创建对应 node
     *
     * @param bytes 字符数组
     * @return 节点
     */
    private static List<DataNode> getNodes(byte[] bytes) {
        List<DataNode> nodes = new ArrayList<>();
        Map<Byte, Integer> count = new HashMap<>();
        for (byte b : bytes) {
            if (count.containsKey(b)) {
                count.compute(b, (key, val) -> (val == null) ? 1 : val + 1);
            } else {
                count.put(b, 1);
            }
        }
        for (Byte b : count.keySet()) {
            DataNode dataNode = new DataNode(b, count.get(b));
            nodes.add(dataNode);
        }
        return nodes;
    }


    /**
     * 前序遍历
     *
     * @param root 根节点
     */
    public static void preOrder(DataNode root) {
        if (root == null) {
            log.info("空树");
            return;
        }
        root.postOrder();
    }

    public static void testBit() {
        log.info("测试 byte 转二进制字符串");
        //二进制正数和负数 同 256 按位或运算，
        // 或运算 该位有 1 结果即是 1
        //正数1000      补高位 0 0000 1000 | 1 0000 0000=1 0000 1000
        //负数1010 1000 补高位 0 1010 1000 | 1 0000 0000=1 1010 1000
        //如果只取后 8 位，可见，正数补位到 8 位，负数不变
        log.info(" 8 应该是0000 1000===>{}", Integer.toBinaryString(8 | 256));
        log.info(" 2 应该是0000 0010===>{}", Integer.toBinaryString(2 | 256));
        log.info(" 1 应该是0000 0001===>{}", Integer.toBinaryString(1 | 256));
        log.info(" 0 应该是0000 0000===>{}", Integer.toBinaryString(0 | 256));

        //负数 第一位为符号位1,先不管符号求得二进制原码，按位取反得反码，反码 +1 得补码，最后加上符号位，即是机器码
        //正数 原码，反码，补码相同
        //负数需要截取后八位
        log.info("-1 应该是1111 1111===>{}", Integer.toBinaryString(-1));
        log.info("-2 应该是1111 1110===>{}", Integer.toBinaryString(-2));
        log.info("-88应该是1010 1000===>{}", Integer.toBinaryString(-88));
    }
}

